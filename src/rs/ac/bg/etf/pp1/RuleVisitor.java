package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor {

	Logger log = Logger.getLogger(getClass());

	int printCallCount = 0;
	int varDeclCount = 0;

	public void visit(PrintStmt PrintStmt) {
		visit();
		printCallCount++;
		log.info("Prepoznata naredba print!");

	}

	public void visit(PrintStmtAndNumber PrintStmtAndNumber) {
		visit();
		printCallCount++;
		log.info("Prepoznata prosirena naredba print!");

	}
	
	public void visit(VarDecl VarDecl) {
		visit();
		varDeclCount++;
		log.info("Prepoznata promenljiva!");
	}
}
