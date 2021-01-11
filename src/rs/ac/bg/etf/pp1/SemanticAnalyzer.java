package rs.ac.bg.etf.pp1;

import java.util.LinkedList;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	// MOZDA TREBA ODVOJITI METODU KOJA SE DEFINISE I POZIVA METODE
	Obj currentMethod = null;
	Stack<Obj> calledMethod = new Stack<>();
	boolean returnFound = false;
	boolean errorDetected = false;
	int flagConst = -1;
	int nVars;
	int doCount = 0;
	LinkedList<String> varName = new LinkedList<>();
	Stack<Object> constVars = new Stack<>();
	Stack<Boolean> isArray = new Stack<>();
	LinkedList<LinkedList<Expr>> actualParams = new LinkedList<>();
	Stack<Integer> relops = new Stack<>();
	LinkedList<Integer> arrayFirst = new LinkedList<>();
	// String className;

	Logger log = Logger.getLogger(getClass());

	// TIPOVI PROMENLJIVIH
	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct nullType = new Struct(Struct.Class);
	Struct intArrayType, charArrayType, boolArrayType;
	Struct currType;
	int currParam;
	boolean mainFound = false;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	// VISIT METODE:
	// HERE
	// 3
	// 2
	// 1
	// 0
	// ...

	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if(nVars > 65536) {
			report_error("Program ne sme imati vise od 65536 globalnih promenljivih!", program);
		}
		if(!mainFound) {
			report_error("Program mora sadrzati void main() funkciju", program);
		}
	}

	public void visit(ProgName progName) {

		String name = "bool";
		Tab.currentScope.addToLocals(new Obj(Obj.Type, name, boolType, -1, -1));
		// Tab.currentScope.addToLocals(new Obj(Obj.Type, "null", nullType, -1, -1));
		// Tab.insert(Obj.Type, name, boolType);
		progName.obj = Tab.insert(Obj.Prog, progName.getPName(), Tab.noType);
		Tab.openScope();

		boolArrayType = new Struct(Struct.Array);
		boolArrayType.setElementType(boolType);

		intArrayType = new Struct(Struct.Array);
		intArrayType.setElementType(Tab.intType);

		charArrayType = new Struct(Struct.Array);
		charArrayType.setElementType(Tab.charType);
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}
		}

		currType = type.struct;
	}

	// VAR

	public void visit(VariableName variableName) {
		varName.add(variableName.getVarName());
	}

	public void visit(VaraibleNameError varaibleNameError) {
		varName = null;
	}

	public void visit(BoxYes boxYes) {
		isArray.push(true);
	}

	public void visit(BoxNo boxNo) {
		isArray.push(false);
	}

	public void visit(VarDeclAdd varDeclAdd) {

		String variableName = varName.removeLast();
		Obj cc = Tab.currentScope.findSymbol(variableName);
		if (cc != null) {
			report_error("Deklarisana variabla " + variableName + " vec postoji. Greska na liniji "
					+ varDeclAdd.getVariableIdent().getLine(), null);
			return;
		}
		cc = Tab.find(variableName);
		if (cc != null && cc.getKind() == Obj.Con) {
			report_error("Deklarisana variabla " + variableName + " vec postoji. Greska na liniji "
					+ varDeclAdd.getVariableIdent().getLine(), null);
			return;
		}
		if (variableName != null && currType != Tab.noType) {
			varDeclCount++;
			Obj varNode = null;
			if (isArray.pop()) {
				if (currType == Tab.intType) {
					varNode = Tab.insert(Obj.Var, variableName, intArrayType);
				}
				if (currType == Tab.charType) {
					varNode = Tab.insert(Obj.Var, variableName, charArrayType);
				}
				if (currType == boolType) {
					varNode = Tab.insert(Obj.Var, variableName, boolArrayType);
				}
			} else {
				// Obj varNode = Tab.insert(Obj.Var, varName, varDecl.getType().struct);
				varNode = Tab.insert(Obj.Var, variableName, currType);
			}
			varDeclAdd.obj = varNode;
			report_info(
					"Deklarisana promenljiva " + variableName + " na liniji " + varDeclAdd.getVariableIdent().getLine(),
					null);
		} else {
			report_error("Greska pri deklarisanju promenljive ", varDeclAdd);
		}
	}

	public void visit(VarDecl varDecl) {
		String variableName = varName.removeLast();
		Obj cc = Tab.currentScope.findSymbol(variableName);
		if (cc != null) {
			report_error("Deklarisana variabla " + variableName + " vec postoji. Greska ", varDecl);
			return;
		}
		if (variableName != null && currType != Tab.noType) {
			varDeclCount++;
			Obj varNode = null;
			if (isArray.pop()) {
				if (currType == Tab.intType) {
					varNode = Tab.insert(Obj.Var, variableName, intArrayType);
				}
				if (currType == Tab.charType) {
					varNode = Tab.insert(Obj.Var, variableName, charArrayType);
				}
				if (currType == boolType) {
					varNode = Tab.insert(Obj.Var, variableName, boolArrayType);
				}
			} else {
				// Obj varNode = Tab.insert(Obj.Var, varName, varDecl.getType().struct);
				varNode = Tab.insert(Obj.Var, variableName, currType);
			}
			varDecl.obj = varNode;

			report_info("Deklarisana promenljiva " + variableName, varDecl);
		} else {
			report_error("Greska pri deklarisanju promenljive ", varDecl);
		}
	}

	// CONST

	public void visit(NumberConst numberConst) {
		constVars.push(numberConst.getNumConst());
		flagConst = 0;
	}

	public void visit(CharConst charConst) {
		constVars.push(charConst.getCharConst());
		flagConst = 1;
	}

	public void visit(BoolConst boolConst) {
		constVars.push(boolConst.getBoolConst());
		flagConst = 2;
	}

	public void visit(ConstDeclAdd constDeclAdd) {
		if (constDeclAdd.getConstName() != null) {
			Object constVar = constVars.pop();
			Obj cc = Tab.currentScope.findSymbol(constDeclAdd.getConstName());
			if (cc != null) {
				report_error("Deklarisana variabla " + constDeclAdd.getConstName() + " vec postoji. Greska na liniji "
						+ constDeclAdd.getConstVar().getLine(), null);
				return;
			}
			if (constVar instanceof Integer && currType == Tab.intType) {
				Obj c = Tab.insert(Obj.Con, constDeclAdd.getConstName(), currType);
				c.setAdr((Integer) constVar);
				report_info("Deklarisana int konstantna " + constDeclAdd.getConstName() + " na liniji "
						+ constDeclAdd.getConstVar().getLine(), null);

			} else if (constVar instanceof Character && currType == Tab.charType) {
				Obj c = Tab.insert(Obj.Con, constDeclAdd.getConstName(), currType);
				report_info("Deklarisana char konstantna " + constDeclAdd.getConstName() + " na liniji "
						+ constDeclAdd.getConstVar().getLine(), null);
				c.setAdr((Character) constVar);
			} else if (constVar instanceof Boolean && currType == boolType) {
				Obj c = Tab.insert(Obj.Con, constDeclAdd.getConstName(), currType);
				report_info("Deklarisana boolean konstantna " + constDeclAdd.getConstName() + " na liniji "
						+ constDeclAdd.getConstVar().getLine(), null);
				c.setAdr(((Boolean) constVar ? 1 : 0));
			} else {
				report_error("Greska pri deklarisanju konstantne. Razliciti su tipovi " + " na liniji "
						+ constDeclAdd.getConstVar().getLine(), null);
			}
		} else {
			report_error("Greska pri deklarisanju konstantne promenljive " + " na liniji "
					+ constDeclAdd.getConstVar().getLine(), null);
		}
	}

	public void visit(ConstDecl constDecl) {
		if (constDecl.getConstName() != null) {
			Object constVar = constVars.pop();
			Obj cc = Tab.currentScope.findSymbol(constDecl.getConstName());
			if (cc != null) {
				report_error("Deklarisana variabla " + constDecl.getConstName() + " vec postoji. Greska ", constDecl);
				return;
			}
			if (constVar instanceof Integer && constDecl.getType().struct == Tab.intType) {
				Obj c = Tab.insert(Obj.Con, constDecl.getConstName(), constDecl.getType().struct);
				c.setAdr((Integer) constVar);
				report_info("Deklarisana int konstantna " + constDecl.getConstName(), constDecl);

			} else if (constVar instanceof Character && constDecl.getType().struct == Tab.charType) {
				Obj c = Tab.insert(Obj.Con, constDecl.getConstName(), constDecl.getType().struct);
				report_info("Deklarisana char konstantna " + constDecl.getConstName(), constDecl);
				c.setAdr((Character) constVar);
			} else if (constVar instanceof Boolean && constDecl.getType().struct == boolType) {
				Obj c = Tab.insert(Obj.Con, constDecl.getConstName(), constDecl.getType().struct);
				report_info("Deklarisana boolean konstantna " + constDecl.getConstName(), constDecl);
				c.setAdr(((Boolean) constVar ? 1 : 0));
			} else {
				report_error("Greska pri deklarisanju konstantne. Razliciti su tipovi ", constDecl);
			}
		} else {
			report_error("Greska pri deklarisanju konstantne promenljive ", constDecl);
		}
	}

	// CLASS
	// FOOCK OFF, C LEVEL

	/*
	 * public void visit(ClassName className) { this.className =
	 * className.getClassName(); Tab.insert(Obj.Var, this.className, classType);
	 * Tab.openScope(); }
	 * 
	 * public void visit(ClassDeclSimple classDeclSimple) {
	 * 
	 * }
	 * 
	 * public void visit(ClassDeclMethods classDeclMethods) {
	 * 
	 * }
	 */

	// METHOD

	public void visit(MetTypeName metTypeName) {
		currentMethod = Tab.insert(Obj.Meth, metTypeName.getMethName(), metTypeName.getType().struct);
		metTypeName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + metTypeName.getMethName(), metTypeName);
	}

	public void visit(MetVoidName metTypeName) {
		if("main".equals(metTypeName.getMethName())) {
			mainFound = true;
		}
		currentMethod = Tab.insert(Obj.Meth, metTypeName.getMethName(), Tab.noType);
		metTypeName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + metTypeName.getMethName(), metTypeName);
	}

	public void visit(MethodDeclaration methodDeclaration) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDeclaration.getLine() + ": funkcija "
					+ currentMethod.getName() + " nema return iskaz!", null);
		}
		Tab.chainLocalSymbols(currentMethod);
		if(currentMethod.getLocalSymbols().size()>256) {
			report_error("Metoda ne sme imati vise od 256 lokalnih promenljivih!", methodDeclaration);
		}
		Tab.closeScope();
		returnFound = false;
		currentMethod = null;
	}

	public void visit(FormalParamDeclaration formalParamDeclaration) {

		Obj formDecl = null;
		if (isArray.pop()) {
			if (currType.equals(Tab.intType)) {
				formDecl = Tab.insert(Obj.Var, formalParamDeclaration.getPi(), intArrayType);
			} else if (currType.equals(Tab.charType)) {
				formDecl = Tab.insert(Obj.Var, formalParamDeclaration.getPi(), charArrayType);
			} else if (currType.equals(boolType)) {
				formDecl = Tab.insert(Obj.Var, formalParamDeclaration.getPi(), boolArrayType);

			} else {
				report_error("Greska na liniji " + formalParamDeclaration.getLine() + " : nepostojeci tip! ", null);
			}
		} else {
			formDecl = Tab.insert(Obj.Var, formalParamDeclaration.getPi(), formalParamDeclaration.getType().struct);
		}
		formDecl.setFpPos(currentMethod.getLevel());
		currentMethod.setLevel(currentMethod.getLevel() + 1);
		report_info("Definisan fomralni parametar metode " + formalParamDeclaration.getPi(), formalParamDeclaration);
	}

	public void visit(ErrorFPClass x) {
		report_error("Greska pri deklarisanju formalnih parametara funkcije ", x);
	}

	public void visit(DesignatorIdent designator) {
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) {
			report_error("Greska na liniji " + designator.getLine() + " : ime " + designator.getName()
					+ " nije deklarisano! ", null);
		}
		designator.obj = obj;
		if(obj.getType().getKind() == Struct.Array) {
			isFirstLevelArray = true;
			arrayFirst.push(0);
		}
		if (obj.getKind() == Obj.Meth) {
			calledMethod.push(obj);
			actualParams.push(new LinkedList<>());
			currParam = 0;
		}
	}

	boolean isFirstLevelArray = true;

	public void visit(DesignatorExprList designatorExprList) {
		if (designatorExprList.getDesignator() == null) {
			report_error("Greska na liniji " + designatorExprList.getDesignator().getLine() + " : ime "
					+ designatorExprList.getDesignator().obj.getName() + " nije deklarisan! ", null);
			designatorExprList.obj = Tab.noObj;
			return;
		}
		if (designatorExprList.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji " + designatorExprList.getDesignator().getLine() + " : ime "
					+ designatorExprList.getDesignator().obj.getName() + " nije niz! ", null);
			designatorExprList.obj = Tab.noObj;
			return;
		}

		if (designatorExprList.getExpr().struct != Tab.intType && designatorExprList.getExpr().struct != intArrayType ) {
			report_error("Greska na liniji " + designatorExprList.getDesignator().getLine()
					+ " : vrednost izmedju [] mora biti tipa int ", null);
			designatorExprList.obj = Tab.noObj;
			return;
		}

		if (isFirstLevelArray) {
			//++arrayLevel;
			//arrayLevel = 1;
			arrayFirst.push(arrayFirst.pop() + 1);
			//isFirstLevelArray = false;
		} else {
			arrayFirst.pop();
			report_error("Greska na liniji " + designatorExprList.getDesignator().getLine()
					+ " : Dozvoljeni su samo nizovi!", null);
			designatorExprList.obj = Tab.noObj;
			return;
		}

		designatorExprList.obj = designatorExprList.getDesignator().obj;

		report_info("Pristup elementu niza", designatorExprList);

	}



	// NEPOTREBNO, JER JE ZA C NIVO
	/*
	 * public void visit(DesignatorIdentList designatorIdentList) { Obj des =
	 * designatorIdentList.getDesignator().obj; Obj subName =
	 * Tab.find(designatorIdentList.getSubName()); if (des != null) { if
	 * (des.getType().getKind() == Struct.Class) { if (subName.getKind() == Obj.Fld
	 * || subName.getKind() == Obj.Meth) { //I DUNNO WHAT NEXT } else {
	 * report_error("Greska na liniji " + designatorIdentList.getLine() + " : " +
	 * designatorIdentList.getSubName() + " nije polje ili metoda klase!", null); }
	 * } else { report_error("Greska na liniji " + designatorIdentList.getLine() +
	 * " : promenljiva nije klasa!", null); } } else {
	 * report_error("Greska na liniji " + designatorIdentList.getLine() +
	 * " : promenljiva nije definisana!", null); } }
	 */

	public void visit(Const cnst) {
		switch (flagConst) {
		case -1:
		default:
			report_error("Greska pri definisanju konstante ", cnst);
			break;
		case 0:
			cnst.struct = Tab.intType;
			break;
		case 1:
			cnst.struct = Tab.charType;
			break;
		case 2:
			cnst.struct = boolType;
			break;
		}
		
	}

	public void visit(Var var) {
		if (var.getDesignator().obj == null) {
			var.struct = Tab.noType;
		} else {
			var.struct = var.getDesignator().obj.getType();
		}
	}

	public void visit(FactorTerm term) {
		term.struct = term.getFactor().struct;
	}

	// FACTOR
	
	public void visit(FactorExpr factorExpr) {
		factorExpr.struct = factorExpr.getExpr().struct;
	}
	
	public void visit(NewVar newVar) {
		newVar.struct = Tab.noType;
		if (newVar.getExpr().struct != Tab.intType && newVar.getExpr().struct != intArrayType) {
			report_error("Tip promenljive unutar [] mora biti int", newVar);
		}
		if (newVar.getType().struct == Tab.intType) {
			newVar.struct = intArrayType;
		}
		if (newVar.getType().struct == Tab.charType) {
			newVar.struct = charArrayType;
		}
		if (newVar.getType().struct == boolType) {
			newVar.struct = boolArrayType;
		}
	}
	
	public void visit(DesignatorFuncCall designatorFuncCall) {
		Obj func = designatorFuncCall.getDesignator().obj;
		if (Obj.Meth == func.getKind()) {
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + designatorFuncCall.getLine(),
					null);
			designatorFuncCall.struct = func.getType();
			Obj calledmethod = calledMethod.pop();
			if (actualParams.peek().size() == calledmethod.getLevel()) {
				int i = 0;
				for (Obj o : calledmethod.getLocalSymbols()) {
					if (i == calledmethod.getLevel()) {
						break;
					}
					Expr e = actualParams.peek().remove();
					if (!e.struct.assignableTo(o.getType())) {
						report_error("Argumenti na poziciji " + i + " moraju biti podudarajucih tipova ",
								designatorFuncCall);
					}
					i++;
				}
			} else {
				report_error("Nedovoljan broj parametara", designatorFuncCall);
			}
		} else {
			report_error(
					"Greska na liniji " + designatorFuncCall.getLine() + " : ime " + func.getName() + " nije funkcija!",
					null);
			designatorFuncCall.struct = Tab.noType;
		}
		actualParams.remove();
	}

	public void visit(FuncCall funcCall) {
		if (calledMethod.peek() != null) {

			Obj calledMethodObj = calledMethod.pop();
			if (actualParams.peek().size() == calledMethodObj.getLevel()) {
				int i = 0;
				for (Obj o : calledMethodObj.getLocalSymbols()) {
					if (i == calledMethodObj.getLevel()) {
						break;
					}
					Struct e = actualParams.peek().remove().struct;
					if(e.getKind() == Struct.Array && arrayFirst.pop() >= 1) {
						e = e.getElemType();
					}
					if (!e.assignableTo(o.getType())) {
						report_error("Argumenti na poziciji " + i + " moraju biti podudarajucih tipova ", funcCall);
					}
					i++;
				}
			} else {
				report_error("Nedovoljan broj parametara ", funcCall);
			}
		} else {
			report_error("Promenljiva nije funkcija ", funcCall);
		}
		actualParams.remove();
		//calledMethod = null;
		funcCall.struct = funcCall.getDesignator().obj.getType();
	}

	public void visit(TermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
	}

	public void visit(NegTermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
		if (!termExpr.struct.equals(Tab.intType)) {
			report_error("Izraz nije int tipa ", termExpr);
		}
	}

	public void visit(ActualParam param) {
		actualParams.getFirst().add(param.getExpr());
	}

	public void visit(ActualParams param) {
		actualParams.getFirst().add(param.getExpr());
	}

	public void visit(Expresion expresion) {
		expresion.struct = expresion.getExpr1().struct;
	}
	
	

	public void visit(AddExpr addExpr) {
		Struct te = addExpr.getExpr1().struct;
		Struct t = addExpr.getTerm().struct;
		if ((t == Tab.intType || t == intArrayType) && (te == Tab.intType || te == intArrayType)) {
			addExpr.struct = te;
		} else {
			report_error("Greska na liniji " + addExpr.getLine()
					+ " : nekompatibilni tipovi u izrazu za sabiranje ili oduzimanje.", null);
			addExpr.struct = Tab.noType;
		}
	}

	public void visit(MullTerm mullTerm) {
		Struct te = mullTerm.getFactor().struct;
		Struct t = mullTerm.getTerm().struct;
		if ((te == Tab.intType || te == intArrayType) && (t == Tab.intType || t == intArrayType)) {
			mullTerm.struct = t;
		} else {
			report_error("Greska na liniji " + mullTerm.getLine()
					+ " : nekompatibilni tipovi u izrazu za mnozenje ili deljenje.", null);
			mullTerm.struct = Tab.noType;
		}
	}

	// RETURN STATEMENTS

	public void visit(ReturnExpr returnExpr) {
		if (currentMethod == null) {
			report_error("Greska na liniji " + returnExpr.getLine() + " : "
					+ " return naredba se mora nalaziti unutar funkcije", null);
		} else {
			returnFound = true;
			Struct currMethType = currentMethod.getType();
			if (!currMethType.compatibleWith(returnExpr.getExpr().struct) 
					&& (currMethType.getKind() == Struct.Array && !currMethType.getElemType().compatibleWith(returnExpr.getExpr().struct))) {
				report_error("Greska na liniji " + returnExpr.getLine() + " : "
						+ "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
						+ currentMethod.getName(), null);
			}
		}
	}

	public void visit(ReturnNoExpr returnExpr) {
		if (currentMethod == null) {
			report_error("Greska na liniji " + returnExpr.getLine() + " : "
					+ " return naredba se mora nalaziti unutar funkcije", null);
		} else {
			returnFound = true;
			Struct currMethType = currentMethod.getType();
			if (currMethType != Tab.noType) {
				report_error("Greska na liniji " + returnExpr.getLine() + " : "
						+ "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
						+ currentMethod.getName(), null);
			}
		}
	}

	// Assignement statements
	public void visit(DesignatorAssignExpression assignment) {
		if (assignment.getDesignator().obj.getKind() == Obj.Var) {
			Struct expr = assignment.getExpr().struct;
			Struct des = assignment.getDesignator().obj.getType();
			if (des.getKind() == Struct.Array) {
				des = des.getElemType();
			}
			if (expr.getKind() == Struct.Array) {
				expr = expr.getElemType();
			}
			if (!expr.assignableTo(des))
				report_error("Greska na liniji " + assignment.getLine() + " : "
						+ "nekompatibilni tipovi u dodeli vrednosti! ", null);
		} else {
			report_error(
					"Greska na liniji " + assignment.getLine() + " : " + "Leva strana jednakosti nije promenljiva! ",
					null);
		}

	}

	public void visit(DesignatorIncrement designatorIncrement) {
		Struct des = designatorIncrement.getDesignator().obj.getType();
		if (des.getKind() == Struct.Array && arrayFirst.pop() >= 1) {
			des = des.getElemType();
		}
		if (!(des.equals(Tab.intType))) {
			report_error("Greska na liniji: " + designatorIncrement.getLine() + " Tip promenljive mora biti int!",
					null);
		}
	}

	public void visit(DesignatorDecrement designatorDecrement) {
		Struct des = designatorDecrement.getDesignator().obj.getType();
		if (des.getKind() == Struct.Array && arrayFirst.pop() >= 1) {
			des = des.getElemType();
		}
		if (!(des.equals(Tab.intType))) {
			report_error("Greska na liniji: " + designatorDecrement.getLine() + " Tip promenljive mora biti int!",
					null);
		}
	}

	public void visit(ConditionExpr conditionExpr) {
		Expr1 e2 = conditionExpr.getExpr11();
		Expr1 e3 = conditionExpr.getExpr12();
		if (e2.struct.equals(e3.struct) 
				|| (e2.struct.getKind()==Struct.Array && e2.struct.getElemType().equals(e3.struct)) 
				|| (e3.struct.getKind()==Struct.Array && e3.struct.getElemType().equals(e2.struct))) {
			conditionExpr.struct = e2.struct;
		} else {
			conditionExpr.struct = Tab.noType;
			report_error("Drugi i treci tip moraju biti istih tipova ", conditionExpr);

		}
	}

	// CONDITIONS

	public void visit(ConditionOr conditionOr) {
		if (conditionOr.getCondition().struct == boolType && conditionOr.getCondTerm().struct == boolType) {
			conditionOr.struct = boolType;
		} else {
			conditionOr.struct = Tab.noType;
			report_error("Fact i Term moraju biti bool tip", conditionOr);
		}
	}

	public void visit(ConditionTerm condTerm) {
		if (condTerm.getCondTerm().struct == boolType) {
			condTerm.struct = condTerm.getCondTerm().struct;
		} else {
			condTerm.struct = Tab.noType;
			report_error("Cond Term mora biti bool tip", condTerm);
		}
	}

	public void visit(ConditionFact condFact) {
		if (condFact.getCondFact().struct == boolType) {
			condFact.struct = condFact.getCondFact().struct;
		} else {
			condFact.struct = Tab.noType;
			report_error("Cond Term mora biti bool tip", condFact);
		}
	}

	public void visit(ConditionAnd conditionAnd) {
		if (conditionAnd.getCondFact().struct == boolType && conditionAnd.getCondTerm().struct == boolType) {
			conditionAnd.struct = boolType;
		} else {
			conditionAnd.struct = Tab.noType;
			report_error("Fact i Term moraju biti bool tip", conditionAnd);
		}
	}

	public void visit(CondFactExpr condFactExpr) {
		if (condFactExpr.getExpr1().struct == boolType) {
			condFactExpr.struct = condFactExpr.getExpr1().struct;
		} else {
			condFactExpr.struct = Tab.noType;
			report_error("Expresion mora biti bool tip", condFactExpr);
		}
	}

	public void visit(CondFactRelop condFactRelop) {
		condFactRelop.struct = boolType;
		Expr1 ex1 = condFactRelop.getExpr1();
		Struct s1 = ex1.struct;
		if (ex1.struct.getKind() == Struct.Array && arrayFirst.pop() >= 1) {
			s1 = ex1.struct.getElemType();
		}
		Expr1 ex2 = condFactRelop.getExpr11();
		Struct s2 = ex2.struct;
		if (ex2.struct.getKind() == Struct.Array && arrayFirst.pop() >= 1) {
			s2 = ex2.struct.getElemType();
		}
		if (relops.pop() > 0) {
			if (!s1.compatibleWith(s2)) {
				condFactRelop.struct = Tab.noType;
				report_error("Tipovi promenljivih koje se uporedjuju nisu kompatibilni ", condFactRelop);
			}
		} else {
			if (s1.isRefType() || s2.isRefType()) {
				condFactRelop.struct = Tab.noType;
				report_error("Nizovi i klase se mogu porediti samo operatorima == i != ", condFactRelop);
			} else {
				if (!s1.equals(s2)) {
					condFactRelop.struct = Tab.noType;
					report_error("Tipovi promenljivih koje se uporedjuju nisu kompatibilni ", condFactRelop);
				}
			}
		}
	}

	public void visit(Equal eq) {
		relops.push(1);
	}

	public void visit(NotEqual neq) {
		relops.push(1);
	}

	public void visit(Greater gt) {
		relops.push(0);
	}

	public void visit(GreaterOrEqual geq) {
		relops.push(0);
	}

	public void visit(Lower lw) {
		relops.push(0);
	}

	public void visit(LowerOrEqual leq) {
		relops.push(0);
	}

	// STATEMENTS

	//READ STMT
	public void visit(ReadStmt readStmt) {
		Designator des = readStmt.getDesignator();
		if (des.obj.getKind() == Obj.Var) {
			Struct s = des.obj.getType();

			if (des.obj.getType().getKind() == Struct.Array && arrayFirst.pop() >= 1) {
				s = des.obj.getType().getElemType();
			}
			if (s == Tab.intType || s == Tab.charType || s == boolType) {
				report_info("Poziv read funkcije", readStmt);
			} else {
				report_error("Argument prosledjen read funkciji nije odgovarajuceg tipa", readStmt);
			}
		} else {
			report_error("Read statement prihvata samo promenljive ili elemente nizova", readStmt);
		}
	}

	
	//PRINT STMT
	public void visit(PrintStmt printStmt) {
		Struct s1 = printStmt.getExpr().struct;
		if (s1.getKind() == Struct.Array && arrayFirst.pop() >= 1) {
			s1 = s1.getElemType();
		}
		if (s1 == Tab.intType || s1 == Tab.charType || s1 == boolType) {
			report_info("Poziv print funkcije", printStmt);
		} else {
			report_error("Argument prosledjen print funkciji mora biti int, char ili bool", printStmt);
		}

	}

	public void visit(PrintStmtAndNumber printStmt) {
		Struct s1 = printStmt.getExpr().struct;
		if (s1.getKind() == Struct.Array && arrayFirst.pop() >= 1) {
			s1 = s1.getElemType();
		}
		if (s1 == Tab.intType || s1 == Tab.charType || s1 == boolType) {
			report_info("Poziv print funkcije", printStmt);
		} else {
			report_error("Argument prosledjen print funkciji mora biti int, char ili bool", printStmt);
		}

	}

	// IF STATEMENT
	public void visit(IfGoodConditions condition) {
		if (condition.getCondition().struct == boolType) {
			report_info("Pronadjen If statement", condition);
		} else {
			report_error("Greska kod if narebe. Uslov mora biti bool tipa", condition);
		}
	}

	// WHILE STATEMENT
	public void visit(DoWhileStart doWhileStart) {
		++doCount;
	}

	public void visit(DoWhile doWhile) {
		--doCount;
		if (doWhile.getCondition().struct == boolType) {
			report_info("Pronadjen Do While statement", doWhile);
		} else {
			report_error("Greska kod do While narebe. Uslov mora biti bool tipa", doWhile);
		}
	}

	// BREAK
	public void visit(Break brk) {
		if (doCount == 0) {
			report_error("Nije moguce izvrsiti break van do while petlje", brk);
		} else {
			report_info("Break free", brk);
		}
	}

	// CONTINUE
	public void visit(Continue cont) {
		if (doCount == 0) {
			report_error("Nije moguce izvrsiti continue van do while petlje", cont);
		} else {
			report_info("Continue free", cont);
		}
	}

	public boolean passed() {
		return !errorDetected;
	}

}
