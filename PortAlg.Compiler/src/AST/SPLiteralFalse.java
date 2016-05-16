package AST;

import static AST.CLConstants.GOTO;
import static AST.CLConstants.ICONST_0;

public class SPLiteralFalse extends SPLiteral {

    public SPLiteralFalse(int line) {
        super(line, "");
    }

    public SPExpression analyze(Context context) {
        type = Type.BOOLEAN;
        return this;
    }

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(ICONST_0);
    }


    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        if (onTrue) {
            output.addBranchInstruction(GOTO, targetLabel);
        }
    }

}
