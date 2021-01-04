// generated with ast extension for cup
// version 0.8
// 4/0/2021 0:48:14


package rs.ac.bg.etf.pp1.ast;

public class FormalParamDeclaration extends FormalParamDecl {

    private Type Type;
    private String pi;
    private OptBox OptBox;

    public FormalParamDeclaration (Type Type, String pi, OptBox OptBox) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.pi=pi;
        this.OptBox=OptBox;
        if(OptBox!=null) OptBox.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi=pi;
    }

    public OptBox getOptBox() {
        return OptBox;
    }

    public void setOptBox(OptBox OptBox) {
        this.OptBox=OptBox;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OptBox!=null) OptBox.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OptBox!=null) OptBox.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OptBox!=null) OptBox.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParamDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+pi);
        buffer.append("\n");

        if(OptBox!=null)
            buffer.append(OptBox.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParamDeclaration]");
        return buffer.toString();
    }
}
