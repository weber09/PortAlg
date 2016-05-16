package AST;

/**
 * Created by Gabriel on 08/03/2016.
 */

abstract public class SPLiteral extends SPExpression {

    String text;

    SPLiteral(int line, String text)
    {
        super(line);
        this.text = text;
    }
}


class Util {

    public static String escapeSpecialXMLChars(String s) {
        String escapedString = s.replaceAll("&", "&amp;");
        escapedString = escapedString.replaceAll("<", "&lt;");
        escapedString = escapedString.replaceAll(">", "&gt;");
        escapedString = escapedString.replaceAll("\"", "&quot;");
        escapedString = escapedString.replaceAll("'", "&#39;");
        return escapedString;
    }

    public static String unescape(String s) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                i++;
                if (i >= s.length()) {
                    break;
                }
                c = s.charAt(i);
                switch (c) {
                    case 'b':
                        b.append('\b');
                        break;
                    case 't':
                        b.append('\t');
                        break;
                    case 'n':
                        b.append('\n');
                        break;
                    case 'f':
                        b.append('\f');
                        break;
                    case 'r':
                        b.append('\r');
                        break;
                    case '"':
                        b.append('"');
                        break;
                    case '\'':
                        b.append('\'');
                        break;
                    case '\\':
                        b.append('\\');
                        break;
                }
            } else {
                b.append(c);
            }
        }
        return b.toString();
    }

}

