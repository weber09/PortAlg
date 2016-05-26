package AST;

import static AST.CLConstants.*;

public class SPGreaterEqualOp extends SPComparison {

    public SPGreaterEqualOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addBranchInstruction(onTrue ? IF_ICMPGE : IF_ICMPLT, targetLabel);
    }
}
