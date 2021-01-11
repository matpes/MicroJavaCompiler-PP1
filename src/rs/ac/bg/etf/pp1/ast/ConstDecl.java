// generated with ast extension for cup
// version 0.8
// 11/0/2021 13:21:30


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private String constName;
    private ConstVar ConstVar;
    private ConstDeclAddition ConstDeclAddition;

    public ConstDecl (Type Type, String constName, ConstVar ConstVar, ConstDeclAddition ConstDeclAddition) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.constName=constName;
        this.ConstVar=ConstVar;
        if(ConstVar!=null) ConstVar.setParent(this);
        this.ConstDeclAddition=ConstDeclAddition;
        if(ConstDeclAddition!=null) ConstDeclAddition.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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

    public ConstDeclAddition getConstDeclAddition() {
        return ConstDeclAddition;
    }

    public void setConstDeclAddition(ConstDeclAddition ConstDeclAddition) {
        this.ConstDeclAddition=ConstDeclAddition;
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
        if(ConstVar!=null) ConstVar.accept(visitor);
        if(ConstDeclAddition!=null) ConstDeclAddition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstVar!=null) ConstVar.traverseTopDown(visitor);
        if(ConstDeclAddition!=null) ConstDeclAddition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstVar!=null) ConstVar.traverseBottomUp(visitor);
        if(ConstDeclAddition!=null) ConstDeclAddition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
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

        if(ConstDeclAddition!=null)
            buffer.append(ConstDeclAddition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
