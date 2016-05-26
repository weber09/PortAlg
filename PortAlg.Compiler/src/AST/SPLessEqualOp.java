package AST;

import static AST.CLConstants.*;

public class SPLessEqualOp extends SPComparison {

    public SPLessEqualOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addBranchInstruction(onTrue ? IF_ICMPLE : IF_ICMPGE, targetLabel);
    }
}
