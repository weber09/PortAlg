package AST;

/**
 * Created by Gabriel on 08/03/2016.
 */
abstract public class SPBinaryExpression extends SPExpression {

    protected SPExpression lhs;

    protected SPExpression rhs;

    public void setLhs( SPExpression lhs){
        this.lhs = lhs;
    }

    public void setRhs( SPExpression rhs){
        this.rhs = rhs;
    }

    protected int opcode;

    protected SPBinaryExpression(int line, SPExpression lhs, SPExpression rhs) {
        super(line);
        this.lhs = lhs;
        this.rhs = rhs;
    }
}

