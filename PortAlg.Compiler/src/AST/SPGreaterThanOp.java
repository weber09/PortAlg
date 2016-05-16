package AST;

public class SPGreaterThanOp extends SPComparison {

    public SPGreaterThanOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    @Override
    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
    }
}
