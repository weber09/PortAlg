package AST;

import static AST.CLConstants.*;

public class SPGreaterThanOp extends SPComparison {

    public SPGreaterThanOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        lhs.codegen(output);
        rhs.codegen(output);
        output.addBranchInstruction(onTrue ? IF_ICMPGT : IF_ICMPLT, targetLabel);
    }
}
