package util;

/** Escape de HTML para qualquer texto vindo do usuário que seja impresso em JSP. */
public final class Html {

    private Html() {}

    public static String esc(Object valor) {
        if (valor == null) {
            return "";
        }
        String s = String.valueOf(valor);
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&':  sb.append("&amp;");  break;
                case '<':  sb.append("&lt;");   break;
                case '>':  sb.append("&gt;");   break;
                case '"':  sb.append("&quot;"); break;
                case '\'': sb.append("&#39;");  break;
                default:   sb.append(c);
            }
        }
        return sb.toString();
    }
}
