// generated with ast extension for cup
// version 0.8
// 29/11/2020 15:55:1


package rs.ac.bg.etf.pp1.ast;

public class Assign extends Assignop {

    public Assign () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Assign(\n");

        buffer.append(tab);
        buffer.append(") [Assign]");
        return buffer.toString();
    }
}
