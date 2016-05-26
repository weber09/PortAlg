package AST;

public class SPGreaterThanOp extends SPComparison {

    public SPGreaterThanOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    @Override
    public void codegen(CLEmitter output){

    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue) {
        System.err.println("Error in code generation");
    }
}
