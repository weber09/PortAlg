package AST;

public class SPLiteralDecimal extends SPLiteral{
    public SPLiteralDecimal(int line, String text) {
        super(line, text);
    }

    public SPExpression analyze(Context context) {
        type = Type.DECIMAL;
        return this;
    }

    public void codegen(CLEmitter output) {
        output.addLDCInstruction(Double.parseDouble(text));
    }

}
