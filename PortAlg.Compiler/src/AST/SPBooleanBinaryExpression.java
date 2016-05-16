package AST;

/**
 * Created by Gabriel on 28/03/2016.
 */

abstract class SPBooleanBinaryExpression extends SPBinaryExpression {
    protected SPBooleanBinaryExpression(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }
}

