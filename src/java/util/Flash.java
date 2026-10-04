package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Mensagens de uma só exibição (sucesso/erro) que sobrevivem a um redirect.
 * O controller grava com sucesso()/erro(); o doGet que carrega a página chama paraRequest().
 */
public final class Flash {

    private static final String SUCESSO = "flashSucesso";
    private static final String ERRO = "flashErro";

    private Flash() {}

    public static void sucesso(HttpServletRequest request, String mensagem) {
        request.getSession().setAttribute(SUCESSO, mensagem);
    }

    public static void erro(HttpServletRequest request, String mensagem) {
        request.getSession().setAttribute(ERRO, mensagem);
    }

    /** Move as mensagens da sessão para atributos do request (mensagemSucesso / mensagemErro). */
    public static void paraRequest(HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        if (sessao == null) {
            return;
        }
        Object ok = sessao.getAttribute(SUCESSO);
        Object erro = sessao.getAttribute(ERRO);
        sessao.removeAttribute(SUCESSO);
        sessao.removeAttribute(ERRO);
        if (ok != null) {
            request.setAttribute("mensagemSucesso", ok);
        }
        if (erro != null) {
            request.setAttribute("mensagemErro", erro);
        }
    }
}
