package AST;

import static AST.CLConstants.GOTO;
import static AST.CLConstants.ICONST_1;

public class SPLiteralTrue extends SPLiteral {

    public SPLiteralTrue(int line) {
        super(line, "");
    }

    public SPExpression analyze(Context context) {
        type = Type.BOOLEAN;
        return this;
    }

    public void codegen(CLEmitter output) {
        output.addNoArgInstruction(ICONST_1);
    }


    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        if (onTrue) {
            output.addBranchInstruction(GOTO, targetLabel);
        }
    }

}
