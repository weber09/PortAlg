package AST;

import static AST.CLConstants.IADD;

public class SPPlusAssignOp extends SPAssignment {

    public void setLhs(SPExpression lhs)
    {
        this.lhs = lhs;
    }

    public void setRhs(SPExpression rhs)
    {
        this.rhs = rhs;
    }

    public SPPlusAssignOp(int line, SPExpression lhs, SPExpression rhs) {
        super(line, lhs, rhs);
    }

    /**
     * Analyze the lhs and rhs, rewrite rhs as lhs + rhs (string concatenation)
     * if lhs is a String, and set the result type.
     *
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public SPExpression analyze(Context context) {
        if (!(lhs instanceof SPLhs)) {
            SPAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
            return this;
        } else {
            lhs = (SPExpression) ((SPLhs) lhs).analyzeLhs(context);
        }
        rhs = (SPExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new SPStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else {
            SPAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;
    }

    public void codegen(CLEmitter output) {
        ((SPLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else {
            ((SPLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IADD);
        }
        if (!isStatementExpression) {
            ((SPLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((SPLhs) lhs).codegenStore(output);
    }
}
