package AST;

import static AST.CLConstants.*;

public class SPAssignOp extends SPAssignment {

    public void setLhs(SPExpression lhs)
    {
        this.lhs = lhs;
    }

    public void setRhs(SPExpression rhs)
    {
        this.rhs = rhs;
    }

    public SPAssignOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    private boolean mustMatchTypes(){
        Type baseTypeLhs = lhs.type();
        if(lhs.type().isArray()){
                baseTypeLhs = baseTypeLhs.getBaseType();
        }

        Type baseTypeRhs = rhs.type();
        if(rhs.type().isArray()){
            baseTypeRhs = baseTypeRhs.getBaseType();
        }

        if(baseTypeRhs.matchesExpected(baseTypeLhs)) {
            return true;
        }

        if(baseTypeLhs == Type.DECIMAL && (baseTypeRhs == Type.LONG || baseTypeRhs == Type.INT))
            return true;

       return baseTypeLhs == Type.LONG && baseTypeRhs == Type.INT;
    }

    public SPExpression analyze(Context context) {
        if (!(lhs instanceof SPLhs)) {
            SPAST.compilationUnit.reportSemanticError(line(), "Illegal lhs for assignment");
        } else {
            lhs = ((SPLhs) lhs).analyzeLhs(context);
        }
        rhs = rhs.analyze(context);

        if(!mustMatchTypes()){
            SPAST.compilationUnit.reportSemanticError(line, "Type %s doesn't match type %s", lhs.type(), rhs.type());
        }

        Type baseTypeRhs = rhs.type();
        if(rhs.type().isArray()){
            baseTypeRhs = baseTypeRhs.getBaseType();
        }

        type = baseTypeRhs;
        /*if (lhs instanceof SPVariable) {
            IDefn defn = ((SPVariable) lhs).iDefn();
            if (defn != null) {
                // Local variable; consider it to be initialized now.
                ((LocalVariableDefn) defn).initialize();
            }
        }*/
        return this;
    }

    public void codegen(CLEmitter output) {
        ((SPLhs) lhs).codegenLoadLhsLvalue(output);
        rhs.codegen(output);
        if (!isStatementExpression) {
            // Generate code to leave the Rvalue atop stack
            ((SPLhs) lhs).codegenDuplicateRvalue(output);
        }

        Type baseType = lhs.type();
        if(baseType.isArray()){
            baseType = baseType.getBaseType();
        }

        Type baseTypeRhs = rhs.type();
        if(baseTypeRhs.isArray()){
            baseTypeRhs = baseTypeRhs.getBaseType();
        }

        if(baseType == Type.DECIMAL){
            if(baseTypeRhs == Type.INT)
                output.addNoArgInstruction(I2D);
            else if(baseTypeRhs == Type.LONG)
                output.addNoArgInstruction(L2D);
        } else if(baseType == Type.LONG) {
            output.addNoArgInstruction(I2L);
        }

        ((SPLhs) lhs).codegenStore(output);
    }

}
