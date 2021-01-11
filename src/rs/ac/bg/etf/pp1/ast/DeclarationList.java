// generated with ast extension for cup
// version 0.8
// 11/0/2021 21:16:19


package rs.ac.bg.etf.pp1.ast;

public class DeclarationList extends DeclList {

    private DeclList DeclList;
    private TypeDecl TypeDecl;

    public DeclarationList (DeclList DeclList, TypeDecl TypeDecl) {
        this.DeclList=DeclList;
        if(DeclList!=null) DeclList.setParent(this);
        this.TypeDecl=TypeDecl;
        if(TypeDecl!=null) TypeDecl.setParent(this);
    }

    public DeclList getDeclList() {
        return DeclList;
    }

    public void setDeclList(DeclList DeclList) {
        this.DeclList=DeclList;
    }

    public TypeDecl getTypeDecl() {
        return TypeDecl;
    }

    public void setTypeDecl(TypeDecl TypeDecl) {
        this.TypeDecl=TypeDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclList!=null) DeclList.accept(visitor);
        if(TypeDecl!=null) TypeDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclList!=null) DeclList.traverseTopDown(visitor);
        if(TypeDecl!=null) TypeDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclList!=null) DeclList.traverseBottomUp(visitor);
        if(TypeDecl!=null) TypeDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationList(\n");

        if(DeclList!=null)
            buffer.append(DeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypeDecl!=null)
            buffer.append(TypeDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationList]");
        return buffer.toString();
    }
}
