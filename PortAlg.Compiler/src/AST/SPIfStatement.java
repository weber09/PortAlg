package AST;

import static AST.CLConstants.*;

public class SPIfStatement extends SPStatement {

    private SPExpression condition;
    
    private SPStatement thenPart;
    
    private SPStatement elsePart;

    public SPIfStatement(int line, SPExpression condition, SPStatement thenPart,
                        SPStatement elsePart) {
        super(line);
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    public SPStatement analyze(Context context) {
        condition = (SPExpression) condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        thenPart = (SPStatement) thenPart.analyze(context);
        if (elsePart != null) {
            elsePart = (SPStatement) elsePart.analyze(context);
        }
        return this;
    }

    public void codegen(CLEmitter output) {
        String elseLabel = output.createLabel();
        String endLabel = output.createLabel();

        if(elsePart == null)
            elseLabel = endLabel;

        condition.codegen(output, elseLabel, false);
        thenPart.codegen(output);
        output.addBranchInstruction(GOTO, endLabel);
        if (elsePart != null) {
            output.addLabel(elseLabel);
            elsePart.codegen(output);
        }
        output.addLabel(endLabel);
    }

}
