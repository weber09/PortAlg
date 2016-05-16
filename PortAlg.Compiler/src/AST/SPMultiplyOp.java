package AST;

import static AST.CLConstants.*;

public class SPMultiplyOp extends SPBinaryExpression {


    public SPMultiplyOp(int line, SPExpression lhs, SPExpression rhs) {
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
            opcode = IMUL;
        } else if(baseLhsType == Type.DECIMAL || baseRhsType == Type.DECIMAL) {
            type = Type.DECIMAL;
            opcode = DMUL;
        } else if(baseLhsType == Type.LONG && baseRhsType == Type.LONG){
            type = Type.LONG;
            opcode = LMUL;
        } else {
            type = Type.ANY;
            SPAST.compilationUnit.reportSemanticError(line(), "Invalid operand types for *");
        }
        return this;
    }

    private void convert(CLEmitter output, SPExpression exp){
        Type baseType = type;
        if(baseType.isArray()){
            baseType = baseType.getBaseType();
        }

        if(baseType != Type.DECIMAL)
            return;

        Type expBaseType = exp.type();
        if(expBaseType.isArray()){
            expBaseType = expBaseType.getBaseType();
        }

        if(expBaseType == Type.DECIMAL)
            return;

        int convertOpCode = I2D;
        if(expBaseType == Type.LONG)
            convertOpCode = L2D;

        output.addNoArgInstruction(convertOpCode);
    }


    public void codegen(CLEmitter output) {

        lhs.codegen(output);

        convert(output, lhs);

        rhs.codegen(output);

        convert(output, rhs);

        output.addNoArgInstruction(opcode);
    }
}
