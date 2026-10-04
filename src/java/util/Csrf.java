package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/** Token anti-CSRF por sessão. Todo formulário POST deve incluir {@code Csrf.campo(session)}. */
public final class Csrf {

    public static final String PARAMETRO = "csrfToken";
    private static final String ATRIBUTO = "csrfToken";
    private static final SecureRandom RANDOM = new SecureRandom();

    private Csrf() {}

    public static String token(HttpSession sessao) {
        String token = (String) sessao.getAttribute(ATRIBUTO);
        if (token == null) {
            byte[] bytes = new byte[32];
            RANDOM.nextBytes(bytes);
            token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
            sessao.setAttribute(ATRIBUTO, token);
        }
        return token;
    }

    /** Campo hidden pronto para colar dentro de um {@code <form>}. */
    public static String campo(HttpSession sessao) {
        return "<input type=\"hidden\" name=\"" + PARAMETRO + "\" value=\"" + Html.esc(token(sessao)) + "\">";
    }

    public static boolean valido(HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        if (sessao == null) {
            return false;
        }
        String esperado = (String) sessao.getAttribute(ATRIBUTO);
        String recebido = request.getParameter(PARAMETRO);
        if (esperado == null || recebido == null) {
            return false;
        }
        return MessageDigest.isEqual(
                esperado.getBytes(StandardCharsets.UTF_8), recebido.getBytes(StandardCharsets.UTF_8));
    }
}
