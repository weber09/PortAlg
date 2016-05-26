package AST;

import static AST.CLConstants.*;

public class SPLessThanOp extends SPComparison {

    public SPLessThanOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addBranchInstruction(onTrue ? IF_ICMPLT : IF_ICMPGE, targetLabel);
    }
}
