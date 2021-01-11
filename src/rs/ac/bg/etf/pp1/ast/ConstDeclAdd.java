// generated with ast extension for cup
// version 0.8
// 11/0/2021 21:16:19


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclAdd extends ConstDeclAddition {

    private ConstDeclAddition ConstDeclAddition;
    private String constName;
    private ConstVar ConstVar;

    public ConstDeclAdd (ConstDeclAddition ConstDeclAddition, String constName, ConstVar ConstVar) {
        this.ConstDeclAddition=ConstDeclAddition;
        if(ConstDeclAddition!=null) ConstDeclAddition.setParent(this);
        this.constName=constName;
        this.ConstVar=ConstVar;
        if(ConstVar!=null) ConstVar.setParent(this);
    }

    public ConstDeclAddition getConstDeclAddition() {
        return ConstDeclAddition;
    }

    public void setConstDeclAddition(ConstDeclAddition ConstDeclAddition) {
        this.ConstDeclAddition=ConstDeclAddition;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public ConstVar getConstVar() {
        return ConstVar;
    }

    public void setConstVar(ConstVar ConstVar) {
        this.ConstVar=ConstVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclAddition!=null) ConstDeclAddition.accept(visitor);
        if(ConstVar!=null) ConstVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclAddition!=null) ConstDeclAddition.traverseTopDown(visitor);
        if(ConstVar!=null) ConstVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclAddition!=null) ConstDeclAddition.traverseBottomUp(visitor);
        if(ConstVar!=null) ConstVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclAdd(\n");

        if(ConstDeclAddition!=null)
            buffer.append(ConstDeclAddition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(ConstVar!=null)
            buffer.append(ConstVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclAdd]");
        return buffer.toString();
    }
}
