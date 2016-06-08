package AST;

import static AST.CLConstants.*;

/**
 * Created by Gabriel on 07/06/2016.
 */

public class SPRestDivisionOp extends SPBinaryExpression {


    public SPRestDivisionOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public SPExpression analyze(Context context) {
        lhs = lhs.analyze(context);
        rhs = rhs.analyze(context);

        Type baseLhsType = lhs.type();
        if(baseLhsType.isArray()){
            baseLhsType = baseLhsType.getBaseType();
        }

        Type baseRhsType = rhs.type();
        if(baseRhsType.isArray()){
            baseRhsType = baseRhsType.getBaseType();
        }

        if (baseLhsType == Type.INT && baseRhsType == Type.INT) {
            type = Type.INT;
        } else {
            type = Type.ANY;
            SPAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for %");
        }
        return this;
    }
    public void codegen(CLEmitter output) {

        lhs.codegen(output);

        rhs.codegen(output);

        output.addNoArgInstruction(IREM);
    }
}
