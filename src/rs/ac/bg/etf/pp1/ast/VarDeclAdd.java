// generated with ast extension for cup
// version 0.8
// 11/0/2021 13:21:30


package rs.ac.bg.etf.pp1.ast;

public class VarDeclAdd extends VarDeclAddition {

    private VarDeclAddition VarDeclAddition;
    private VariableIdent VariableIdent;
    private OptBox OptBox;

    public VarDeclAdd (VarDeclAddition VarDeclAddition, VariableIdent VariableIdent, OptBox OptBox) {
        this.VarDeclAddition=VarDeclAddition;
        if(VarDeclAddition!=null) VarDeclAddition.setParent(this);
        this.VariableIdent=VariableIdent;
        if(VariableIdent!=null) VariableIdent.setParent(this);
        this.OptBox=OptBox;
        if(OptBox!=null) OptBox.setParent(this);
    }

    public VarDeclAddition getVarDeclAddition() {
        return VarDeclAddition;
    }

    public void setVarDeclAddition(VarDeclAddition VarDeclAddition) {
        this.VarDeclAddition=VarDeclAddition;
    }

    public VariableIdent getVariableIdent() {
        return VariableIdent;
    }

    public void setVariableIdent(VariableIdent VariableIdent) {
        this.VariableIdent=VariableIdent;
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
        if(VarDeclAddition!=null) VarDeclAddition.accept(visitor);
        if(VariableIdent!=null) VariableIdent.accept(visitor);
        if(OptBox!=null) OptBox.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclAddition!=null) VarDeclAddition.traverseTopDown(visitor);
        if(VariableIdent!=null) VariableIdent.traverseTopDown(visitor);
        if(OptBox!=null) OptBox.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclAddition!=null) VarDeclAddition.traverseBottomUp(visitor);
        if(VariableIdent!=null) VariableIdent.traverseBottomUp(visitor);
        if(OptBox!=null) OptBox.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclAdd(\n");

        if(VarDeclAddition!=null)
            buffer.append(VarDeclAddition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableIdent!=null)
            buffer.append(VariableIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptBox!=null)
            buffer.append(OptBox.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclAdd]");
        return buffer.toString();
    }
}
