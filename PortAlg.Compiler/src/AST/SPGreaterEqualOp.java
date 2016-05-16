package AST;

public class SPGreaterEqualOp extends SPComparison {

    public SPGreaterEqualOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    @Override
    public void codegen(CLEmitter output) {
        lhs.codegen(output);
        rhs.codegen(output);
    }
}
