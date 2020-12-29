// generated with ast extension for cup
// version 0.8
// 29/11/2020 21:9:32


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclExtends extends ClassDecl {

    private String className;
    private Type Type;
    private VarDecList VarDecList;

    public ClassDeclExtends (String className, Type Type, VarDecList VarDecList) {
        this.className=className;
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDecList=VarDecList;
        if(VarDecList!=null) VarDecList.setParent(this);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDecList getVarDecList() {
        return VarDecList;
    }

    public void setVarDecList(VarDecList VarDecList) {
        this.VarDecList=VarDecList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDecList!=null) VarDecList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDecList!=null) VarDecList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDecList!=null) VarDecList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclExtends(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecList!=null)
            buffer.append(VarDecList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclExtends]");
        return buffer.toString();
    }
}
