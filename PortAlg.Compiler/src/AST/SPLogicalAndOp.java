package AST;

public class SPLogicalAndOp extends SPBooleanBinaryExpression {

    public SPLogicalAndOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public SPExpression analyze(Context context) {
        lhs = lhs.analyze(context);
        rhs = rhs.analyze(context);
        lhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        rhs.type().mustMatchExpected(line(), Type.BOOLEAN);
        type = Type.BOOLEAN;
        return this;
    }

    public void codegen(CLEmitter output, String targetLabel, boolean onTrue){
        if (onTrue) {
            String falseLabel = output.createLabel();
            if(SPBooleanBinaryExpression.class.isInstance(lhs)){
                lhs.codegen(output, falseLabel, ((SPBooleanBinaryExpression)lhs).getJumOnTrue());
            }else {
                lhs.codegen(output, falseLabel, false);
            }

            if(SPBooleanBinaryExpression.class.isInstance(rhs)){
                rhs.codegen(output, targetLabel, !((SPBooleanBinaryExpression)rhs).getJumOnTrue());
            }else {
                rhs.codegen(output, targetLabel, true);
            }
            output.addLabel(falseLabel);
        } else {
            if(SPBooleanBinaryExpression.class.isInstance(lhs)){
                lhs.codegen(output, targetLabel, ((SPBooleanBinaryExpression)lhs).getJumOnTrue());
            }else {
                lhs.codegen(output, targetLabel, false);
            }

            if(SPBooleanBinaryExpression.class.isInstance(rhs)){
                rhs.codegen(output, targetLabel, ((SPBooleanBinaryExpression)rhs).getJumOnTrue());
            }else {
                rhs.codegen(output, targetLabel, false);
            }
        }
    }
}
