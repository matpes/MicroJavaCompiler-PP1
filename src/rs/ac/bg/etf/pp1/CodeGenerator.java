package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	//Stack<>;
	
	public int getMainPc() {
		return mainPc;
	}

	// ConstDecl

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

		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		//SyntaxNode methodNode = methodTypeName.getParent();

		// Generate the entry
		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel());
		Code.put(methodTypeName.obj.getLocalSymbols().size());

	}
	
	public void visit(MetVoidName methodTypeName) {

		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		//SyntaxNode methodNode = methodTypeName.getParent();

		// Generate the entry
		Code.put(Code.enter);
		Code.put(methodTypeName.obj.getLevel());
		Code.put(methodTypeName.obj.getLocalSymbols().size());

	}

	public void visit(MethodDeclaration methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// STATEMENTS
	public void visit(PrintStmt printStmt) {
		if (printStmt.getExpr().struct.equals(Tab.charType)) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(5);
			Code.put(Code.print);
		}
	}

	public void visit(PrintStmtAndNumber printStmtAndNumber) {
		if (printStmtAndNumber.getExpr().struct.equals(Tab.charType)) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(5);
			Code.put(Code.print);
		}
		Code.loadConst(printStmtAndNumber.getN2());
		Code.loadConst(5);
		Code.put(Code.print);
	}
	
	public void visit(DesignatorIdent designatorIdent) {
		if(designatorIdent.obj.getKind()==Obj.Var /*&& designatorIdent.obj.getType().getKind() != Struct.Array*/) {
			//Code.load(designatorIdent.obj);
		}
	}
	
	public void visit(DesignatorExprList designatorExprList) {
		//Obj obj = designatorExprList.getDesignator().obj;
		designatorExprList.obj = new Obj(Obj.Elem, designatorExprList.obj.getName(), designatorExprList.obj.getType(), designatorExprList.obj.getAdr(), designatorExprList.obj.getLevel());
		Code.load(designatorExprList.getDesignator().obj);
		Code.put(Code.dup2);
		Code.put(Code.pop);
	}
	
	public void visit(DesignatorAssignExpression designatorAssignExpression) {
		Code.store(designatorAssignExpression.getDesignator().obj); //Radi posa za promenljive, radi i za alociranje nizova, ali ne i nizove
		if(designatorAssignExpression.getExpr().struct.getKind() == Struct.Array) {
			Code.put(Code.pop);
		}
	}
	
	
	//BROJEVNE OPERACIJE
	
	public void visit(AddExpr addExpr) {
		if(addExpr.getAddop().getClass() == Addop.class) {
			Code.put(Code.add);
		}else {
			Code.put(Code.sub);
		}
	}
	
	public void visit(MullTerm mullTerm) {
		if(mullTerm.getMulop().getClass() == Mul.class) {
			Code.put(Code.mul);
		}
		if(mullTerm.getMulop().getClass() == Div.class) {
			Code.put(Code.div);
		}
		if(mullTerm.getMulop().getClass() == Mod.class) {
			Code.put(Code.rem);
		}
	}
	
	public void visit(DesignatorIncrement designatorIncrement) {
		Code.load(designatorIncrement.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorIncrement.getDesignator().obj);
	}
	
	public void visit(DesignatorDecrement designatorDecrement) {
		Code.load(designatorDecrement.getDesignator().obj);
		Code.loadConst(-1);
		Code.put(Code.add);
		Code.store(designatorDecrement.getDesignator().obj);
	}
	
	public void visit(NewVar newVar) {
		if(newVar.getType().struct == Tab.charType) {
			Code.put(Code.newarray);
			Code.put(0);
		}else {
			Code.put(Code.newarray);
			Code.put(1);
		}
	}
	

}
