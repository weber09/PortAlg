package AST;

public class SPLiteralString extends SPLiteral {

    public SPLiteralString(int line, String text) {
        super(line, text);
        this.text = text;
    }

    public SPExpression analyze(Context context) {
        type = Type.STRING;
        return this;
    }

    public void codegen(CLEmitter output) {
        String s = Util.unescape(text);

        String literal = s.substring(1, s.length() - 1);
        output.addLDCInstruction(literal);
    }
}
