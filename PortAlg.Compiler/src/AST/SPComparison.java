package AST;

/**
 * Created by Gabriel on 28/03/2016.
 */

abstract class SPComparison extends SPBooleanBinaryExpression {

    protected SPComparison(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }
    
    public SPExpression analyze(Context context) {
        lhs = lhs.analyze(context);
        rhs = rhs.analyze(context);
        //lhs.type().mustMatchExpected(line(), Type.INT);
        //rhs.type().mustMatchExpected(line(), lhs.type());
        type = Type.BOOLEAN;
        return this;
    }
}

