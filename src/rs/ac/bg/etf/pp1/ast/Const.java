// generated with ast extension for cup
// version 0.8
// 29/11/2020 21:9:32


package rs.ac.bg.etf.pp1.ast;

public class Const extends Factor {

    private ConstVar ConstVar;

    public Const (ConstVar ConstVar) {
        this.ConstVar=ConstVar;
        if(ConstVar!=null) ConstVar.setParent(this);
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
        if(ConstVar!=null) ConstVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstVar!=null) ConstVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstVar!=null) ConstVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Const(\n");

        if(ConstVar!=null)
            buffer.append(ConstVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Const]");
        return buffer.toString();
    }
}
