// generated with ast extension for cup
// version 0.8
// 29/11/2020 21:9:32


package rs.ac.bg.etf.pp1.ast;

public class VarDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private String varName;
    private OptBox OptBox;
    private VarDeclAddition VarDeclAddition;

    public VarDecl (Type Type, String varName, OptBox OptBox, VarDeclAddition VarDeclAddition) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.varName=varName;
        this.OptBox=OptBox;
        if(OptBox!=null) OptBox.setParent(this);
        this.VarDeclAddition=VarDeclAddition;
        if(VarDeclAddition!=null) VarDeclAddition.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public OptBox getOptBox() {
        return OptBox;
    }

    public void setOptBox(OptBox OptBox) {
        this.OptBox=OptBox;
    }

    public VarDeclAddition getVarDeclAddition() {
        return VarDeclAddition;
    }

    public void setVarDeclAddition(VarDeclAddition VarDeclAddition) {
        this.VarDeclAddition=VarDeclAddition;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OptBox!=null) OptBox.accept(visitor);
        if(VarDeclAddition!=null) VarDeclAddition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OptBox!=null) OptBox.traverseTopDown(visitor);
        if(VarDeclAddition!=null) VarDeclAddition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OptBox!=null) OptBox.traverseBottomUp(visitor);
        if(VarDeclAddition!=null) VarDeclAddition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(OptBox!=null)
            buffer.append(OptBox.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclAddition!=null)
            buffer.append(VarDeclAddition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDecl]");
        return buffer.toString();
    }
}
