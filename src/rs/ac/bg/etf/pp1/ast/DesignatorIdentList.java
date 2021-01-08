// generated with ast extension for cup
// version 0.8
// 8/0/2021 19:52:44


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdentList extends Designator {

    private Designator Designator;
    private String subName;

    public DesignatorIdentList (Designator Designator, String subName) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.subName=subName;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName=subName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorIdentList(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+subName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdentList]");
        return buffer.toString();
    }
}
