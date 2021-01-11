package rs.ac.bg.etf.pp1;

import java.util.*;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private int beginSecOp, endSecOp;
	LinkedList<LinkedList<Expr>> actualParams = new LinkedList<>();
	Stack<Integer> doWhileAdr = new Stack<Integer>();
	LinkedList<LinkedList<Integer>> jumpForward = new LinkedList<>();
	LinkedList<LinkedList<Integer>> breaks = new LinkedList<>();
	LinkedList<LinkedList<Integer>> conts = new LinkedList<>();
	int inIf = 0;

	public int getMainPc() {
		return mainPc;
	}

	boolean terFlag = false;
	int numOp = 0;

	// OSNOVNE METODE
	public void visit(ProgName progName) {
		// LEN
		Tab.lenObj.setAdr(Code.pc);
		Code.put(Code.enter); // enter 0 0
		Code.put(0);
		Code.put(0);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// CHR
		Tab.chrObj.setAdr(Code.pc);
		Code.put(Code.enter); // enter 0 0
		Code.put(0);
		Code.put(0);
		Code.put(Code.exit);
		Code.put(Code.return_);

		// ORD
		Tab.ordObj.setAdr(Code.pc);
		Code.put(Code.enter); // enter 0 0
		Code.put(0);
		Code.put(0);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// CONST DECL

	public void visit(ConstDecl constDecl) {
		Code.put(Code.pop);
	}

	public void visit(ConstDeclAdd constDecladd) {
		Code.put(Code.pop);
	}

	// FACTOR
	public void visit(Const cnst) {

	}

	public void visit(Var var) {
		Code.load(var.getDesignator().obj);
	}

	public void visit(NumberConst numberConst) {
		Obj obj = Tab.insert(Obj.Con, "$", Tab.intType);
		obj.setLevel(0);
		obj.setAdr(numberConst.getNumConst());
		Code.load(obj);
	}

	public void visit(CharConst charConst) {
		Obj obj = Tab.insert(Obj.Con, "$", Tab.intType);
		obj.setLevel(0);
		obj.setAdr(charConst.getCharConst());
		Code.load(obj);
	}

	public void visit(BoolConst boolConst) {
		Obj obj = Tab.insert(Obj.Con, "$", Tab.intType);
		obj.setLevel(0);
		if (boolConst.getBoolConst()) {
			obj.setAdr(1);
		} else {
			obj.setAdr(0);
		}
		Code.load(obj);
	}

	// METHOD DECLARATIONS
	public void visit(MetTypeName methodTypeName) {

		methodTypeName.obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel());
		Code.put(methodTypeName.obj.getLocalSymbols().size());

	}

	// DEFINICIJA METODE
	public void visit(MetVoidName methodTypeName) {

		if ("main".equalsIgnoreCase(methodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		// SyntaxNode methodNode = methodTypeName.getParent();

		// Generate the entry
		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel());
		Code.put(methodTypeName.obj.getLocalSymbols().size());

	}

	public void visit(MethodDeclaration methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// TERNARNI
	public void visit(ConditionExpr conditionExpr) {
		Code.fixup(endSecOp);
	}

	public void visit(Question question) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		beginSecOp = Code.pc - 2;

	}

	public void visit(Colon colon) {
		Code.putJump(0);
		endSecOp = Code.pc - 2;
		Code.fixup(beginSecOp);
	}

	// USLOVI
	public void visit(CondFactExpr condFactExpr) {
		Code.loadConst(0);
		Code.putFalseJump(Code.eq, 0);
		jumpForward.peek().add(Code.pc - 2);
	}

	public void visit(CondFactRelop condFactRelop) {
		Relop relop = condFactRelop.getRelop();
		if (relop.getClass() == Equal.class) {
			Code.putFalseJump(Code.eq, 0);
		} else if (relop.getClass() == NotEqual.class) {
			Code.putFalseJump(Code.ne, 0);
		} else if (relop.getClass() == Greater.class) {
			Code.putFalseJump(Code.gt, 0);
		} else if (relop.getClass() == GreaterOrEqual.class) {
			Code.putFalseJump(Code.ge, 0);
		} else if (relop.getClass() == Lower.class) {
			Code.putFalseJump(Code.lt, 0);
		} else if (relop.getClass() == LowerOrEqual.class) {
			Code.putFalseJump(Code.le, 0);
		}
		jumpForward.peek().add(Code.pc - 2);
	}

	public void visit(ConditionAnd conditionAnd) {

	}

	public void visit(And and) {

	}

	public void visit(Or or) {

		if (inIf == 0) {
			Code.putJump(doWhileAdr.peek());
		}
		for (Integer i : jumpForward.peek()) {
			Code.fixup(i);
		}
		jumpForward.peek().clear();
	}

	// STATEMENTS

	// IF

	public void visit(If iff) {
		inIf++;
		jumpForward.push(new LinkedList<>());
	}

	public void visit(Else elsee) {
		Code.putJump(0);
		for (Integer i : jumpForward.peek()) {
			Code.fixup(i);
		}
		jumpForward.peek().clear();
		jumpForward.peek().add(Code.pc - 2);
	}

	public void visit(IfStmt ifStmt) {
		inIf--;
		if (jumpForward.size() > 0) {
			for (Integer i : jumpForward.peek()) {
				Code.fixup(i);
			}
			jumpForward.remove();
		}
	}

	// DO WHILE

	public void visit(DoWhileStart doWhileStart) {
		doWhileAdr.push(Code.pc);
		jumpForward.push(new LinkedList<>());
		breaks.push(new LinkedList<>());
		conts.push(new LinkedList<>());
	}

	public void visit(DoWhile doWhile) {
		if (doWhileAdr.size() > 0) {
			Code.putJump(doWhileAdr.peek());
			doWhileAdr.pop();
		}
		for (Integer i : jumpForward.peek()) {
			Code.fixup(i);
		}
		for (Integer i : breaks.peek()) {
			Code.fixup(i);
		}
		breaks.remove();
		jumpForward.remove();
	}

	public void visit(WhileCond whileCond) {
		for (Integer i : conts.peek()) {
			Code.fixup(i);
		}
		conts.remove();
	}

	// BREAK

	public void visit(Break brk) {
		Code.putJump(0);
		breaks.peek().add(Code.pc - 2);
	}

	// CONTINUE

	public void visit(Continue con) {
		Code.putJump(0);
		conts.peek().add(Code.pc - 2);
	}

	// PRINT
	public void visit(PrintStmt printStmt) {
		if (printStmt.getExpr().struct.equals(Tab.charType)
				|| Tab.charType.equals(printStmt.getExpr().struct.getElemType())) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else if(printStmt.getExpr().struct.equals(Tab.intType)
				|| Tab.intType.equals(printStmt.getExpr().struct.getElemType())){
			Code.loadConst(5);
			Code.put(Code.print);
		}else {
			Code.loadConst(0);
			Code.putFalseJump(Code.eq, 0);
			int add = Code.pc - 2;
			Code.loadConst('f');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('a');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('l');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('s');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.putJump(0);
			int add2 = Code.pc - 2;
			Code.fixup(add);
			Code.loadConst('t');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('r');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('u');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.fixup(add2);
		}
	}

	public void visit(PrintStmtAndNumber printStmtAndNumber) {
		if (printStmtAndNumber.getExpr().struct.equals(Tab.charType)
				|| Tab.charType.equals(printStmtAndNumber.getExpr().struct.getElemType())) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else if(printStmtAndNumber.getExpr().struct.equals(Tab.intType)
				|| Tab.intType.equals(printStmtAndNumber.getExpr().struct.getElemType())){
			Code.loadConst(5);
			Code.put(Code.print);
		}else {
			Code.loadConst(0);
			Code.putFalseJump(Code.eq, 0);
			int add = Code.pc - 2;
			Code.loadConst('f');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('a');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('l');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('s');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.putJump(0);
			int add2 = Code.pc - 2;
			Code.fixup(add);
			Code.loadConst('t');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('r');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('u');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.fixup(add2);
		}
		Code.loadConst(printStmtAndNumber.getN2());
		Code.loadConst(5);
		Code.put(Code.print);
	}

	// READ
	public void visit(ReadStmt readStmt) {
		Obj obj = readStmt.getDesignator().obj;
		if (obj.getType() == Tab.charType) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(obj);
	}

	// PODESAVANJE PARAMETARA ZA POZIV FUNKCIJE
	/*
	 * public void visit(ActualParam param) {
	 * actualParams.getFirst().add(param.getExpr()); }
	 * 
	 * public void visit(ActualParams param) {
	 * actualParams.getFirst().add(param.getExpr()); }
	 */

	// POZIVI FJ-a
	public void visit(FuncCall funcCall) {
		Obj functionObj = funcCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;

		Code.put(Code.call);

		Code.put2(offset);
	}

	public void visit(DesignatorFuncCall designatorFuncCall) { // Poziv funkcije, cija se povratna vrednost ne koristi
		Obj functionObj = designatorFuncCall.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (designatorFuncCall.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	// RETURN
	public void visit(ReturnExpr returnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ReturnNoExpr returnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// DESIGNATOR
	public void visit(DesignatorIdent designatorIdent) {
		SyntaxNode parent = designatorIdent.getParent();
		/*
		 * if (parent.getClass() == DesignatorExprList.class &&
		 * designatorIdent.obj.getType().getKind() == Struct.Array) {
		 * Code.load(designatorIdent.obj); }
		 */
		if (parent.getClass() != Var.class && parent.getClass() != DesignatorFuncCall.class
				&& parent.getClass() != FuncCall.class && parent.getClass() != DesignatorAssignExpression.class) {
			Code.load(designatorIdent.obj);
		}

		if (designatorIdent.obj.getKind() == Obj.Meth) {
			actualParams.push(new LinkedList<>());
		}
	}

	// DESIGNATOR NIZ
	public void visit(DesignatorExprList designatorExprList) {
		// Obj obj = designatorExprList.getDesignator().obj;
		designatorExprList.obj = new Obj(Obj.Elem, designatorExprList.obj.getName(), designatorExprList.obj.getType(),
				designatorExprList.obj.getAdr(), designatorExprList.obj.getLevel());

	}

	// DODELA VREDNOSTI
	public void visit(DesignatorAssignExpression designatorAssignExpression) {
		Code.store(designatorAssignExpression.getDesignator().obj); // Radi posa za promenljive, radi i za alociranje
																	// nizova, ali ne i nizove

	}

	// BROJEVNE OPERACIJE
	// SABIRANJE
	public void visit(AddExpr addExpr) {
		if (addExpr.getAddop().getClass() == Addop.class) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}

	// MOZENJE
	public void visit(MullTerm mullTerm) {
		if (mullTerm.getMulop().getClass() == Mul.class) {
			Code.put(Code.mul);
		}
		if (mullTerm.getMulop().getClass() == Div.class) {
			Code.put(Code.div);
		}
		if (mullTerm.getMulop().getClass() == Mod.class) {
			Code.put(Code.rem);
		}
	}

	// INCREMENT
	public void visit(DesignatorIncrement designatorIncrement) {
		Obj des = designatorIncrement.getDesignator().obj;
		if (des.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
			Code.load(designatorIncrement.getDesignator().obj);
		}
		Code.loadConst(1);
		Code.put(Code.add);

		Code.store(designatorIncrement.getDesignator().obj);
	}

	// DECREMENT
	public void visit(DesignatorDecrement designatorDecrement) {
		Obj des = designatorDecrement.getDesignator().obj;
		if (des.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
			Code.load(designatorDecrement.getDesignator().obj);
		}
		Code.loadConst(-1);
		Code.put(Code.add);
		Code.store(designatorDecrement.getDesignator().obj);
	}

	public void visit(NegTermExpr termExpr) {
		Code.put(Code.neg);

	}

	// ALOKACIJA
	public void visit(NewVar newVar) {
		if (newVar.getType().struct == Tab.charType) {
			Code.put(Code.newarray);
			Code.put(0);
		} else {
			Code.put(Code.newarray);
			Code.put(1);
		}
	}

}
