package AST;

import static AST.CLConstants.*;

/**
 * Created by Gabriel on 28/03/2016.
 */

abstract class SPBooleanBinaryExpression extends SPBinaryExpression {
    boolean jumpOnTrue;

    public boolean getJumOnTrue(){
        return jumpOnTrue;
    }

    protected SPBooleanBinaryExpression(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    public void codegen(CLEmitter output) {
        String elseLabel = output.createLabel();
        String endIfLabel = output.createLabel();
        this.codegen(output, elseLabel, jumpOnTrue);
        output.addNoArgInstruction(ICONST_1); // true
        output.addBranchInstruction(GOTO, endIfLabel);
        output.addLabel(elseLabel);
        output.addNoArgInstruction(ICONST_0); // false
        output.addLabel(endIfLabel);
    }
}

