package AST;

import static AST.CLConstants.*;

public class SPNotEqualOp extends SPBooleanBinaryExpression {

    public SPNotEqualOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public SPExpression analyze(Context context) {
        lhs = lhs.analyze(context);
        rhs = rhs.analyze(context);

        Type lhsBaseType = lhs.type();
        if(lhsBaseType.isArray()){
            lhsBaseType = lhsBaseType.getBaseType();
        }

        Type rhsBaseType = rhs.type();
        if(rhsBaseType.isArray()){
            rhsBaseType = rhsBaseType.getBaseType();
        }

        if ((lhsBaseType == Type.INT && rhsBaseType == Type.INT) ||
                lhsBaseType == Type.DECIMAL || rhsBaseType == Type.DECIMAL ||
                (lhsBaseType == Type.LONG && rhsBaseType == Type.LONG) ||
                (lhsBaseType == Type.BOOLEAN && rhsBaseType == Type.BOOLEAN)){
            type = Type.BOOLEAN;
        } else {
            type = Type.ANY;
            SPAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for =");
        }

        return this;
    }

    @Override
    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        Type lhsBaseType = lhs.type();
        if(lhsBaseType.isArray()){
            lhsBaseType = lhsBaseType.getBaseType();
        }

        Type rhsBaseType = rhs.type();
        if(rhsBaseType.isArray()){
            rhsBaseType = rhsBaseType.getBaseType();
        }

        lhs.codegen(output);

        if(lhsBaseType == Type.INT && rhsBaseType == Type.DECIMAL){
            output.addNoArgInstruction(I2D);
        }

        rhs.codegen(output);

        if(rhsBaseType == Type.INT && lhsBaseType == Type.DECIMAL){
            output.addNoArgInstruction(I2D);
        }

    }
}
