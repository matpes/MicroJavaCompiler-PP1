// generated with ast extension for cup
// version 0.8
// 11/0/2021 21:16:19


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclExtendsMethods extends ClassDecl {

    private ClassName ClassName;
    private Type Type;
    private VarDecList VarDecList;
    private MethodDeclList MethodDeclList;

    public ClassDeclExtendsMethods (ClassName ClassName, Type Type, VarDecList VarDecList, MethodDeclList MethodDeclList) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDecList=VarDecList;
        if(VarDecList!=null) VarDecList.setParent(this);
        this.MethodDeclList=MethodDeclList;
        if(MethodDeclList!=null) MethodDeclList.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
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

    public MethodDeclList getMethodDeclList() {
        return MethodDeclList;
    }

    public void setMethodDeclList(MethodDeclList MethodDeclList) {
        this.MethodDeclList=MethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(VarDecList!=null) VarDecList.accept(visitor);
        if(MethodDeclList!=null) MethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDecList!=null) VarDecList.traverseTopDown(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDecList!=null) VarDecList.traverseBottomUp(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclExtendsMethods(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
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

        if(MethodDeclList!=null)
            buffer.append(MethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclExtendsMethods]");
        return buffer.toString();
    }
}
