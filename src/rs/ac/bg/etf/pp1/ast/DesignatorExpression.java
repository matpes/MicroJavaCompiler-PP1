// generated with ast extension for cup
// version 0.8
// 11/0/2021 21:16:19


package rs.ac.bg.etf.pp1.ast;

public class DesignatorExpression extends DesignatorStatement {

    private ExpressionStatement ExpressionStatement;

    public DesignatorExpression (ExpressionStatement ExpressionStatement) {
        this.ExpressionStatement=ExpressionStatement;
        if(ExpressionStatement!=null) ExpressionStatement.setParent(this);
    }

    public ExpressionStatement getExpressionStatement() {
        return ExpressionStatement;
    }

    public void setExpressionStatement(ExpressionStatement ExpressionStatement) {
        this.ExpressionStatement=ExpressionStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExpressionStatement!=null) ExpressionStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExpressionStatement!=null) ExpressionStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExpressionStatement!=null) ExpressionStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorExpression(\n");

        if(ExpressionStatement!=null)
            buffer.append(ExpressionStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorExpression]");
        return buffer.toString();
    }
}
