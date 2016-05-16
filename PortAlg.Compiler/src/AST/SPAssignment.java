package AST;

/**
 * Created by Gabriel on 08/03/2016.
 */

abstract public class SPAssignment extends SPBinaryExpression {

    public SPAssignment(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }
}

