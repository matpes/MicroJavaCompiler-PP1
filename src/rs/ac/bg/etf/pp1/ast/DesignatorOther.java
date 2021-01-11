// generated with ast extension for cup
// version 0.8
// 11/0/2021 21:16:19


package rs.ac.bg.etf.pp1.ast;

public class DesignatorOther extends DesignatorStatement {

    private OtherDesignatorStatement OtherDesignatorStatement;

    public DesignatorOther (OtherDesignatorStatement OtherDesignatorStatement) {
        this.OtherDesignatorStatement=OtherDesignatorStatement;
        if(OtherDesignatorStatement!=null) OtherDesignatorStatement.setParent(this);
    }

    public OtherDesignatorStatement getOtherDesignatorStatement() {
        return OtherDesignatorStatement;
    }

    public void setOtherDesignatorStatement(OtherDesignatorStatement OtherDesignatorStatement) {
        this.OtherDesignatorStatement=OtherDesignatorStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OtherDesignatorStatement!=null) OtherDesignatorStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OtherDesignatorStatement!=null) OtherDesignatorStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OtherDesignatorStatement!=null) OtherDesignatorStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorOther(\n");

        if(OtherDesignatorStatement!=null)
            buffer.append(OtherDesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorOther]");
        return buffer.toString();
    }
}
