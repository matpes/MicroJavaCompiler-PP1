// generated with ast extension for cup
// version 0.8
// 11/0/2021 21:16:19


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclSimple extends ClassDecl {

    private ClassName ClassName;
    private VarDecList VarDecList;

    public ClassDeclSimple (ClassName ClassName, VarDecList VarDecList) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.VarDecList=VarDecList;
        if(VarDecList!=null) VarDecList.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
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
        if(ClassName!=null) ClassName.accept(visitor);
        if(VarDecList!=null) VarDecList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(VarDecList!=null) VarDecList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(VarDecList!=null) VarDecList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclSimple(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecList!=null)
            buffer.append(VarDecList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclSimple]");
        return buffer.toString();
    }
}
