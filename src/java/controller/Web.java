package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;
import negocio.NegocioException;

/** Utilidades compartilhadas pelos controllers (leitura segura de parâmetros e redirect). */
final class Web {

    private Web() {}

    static String texto(HttpServletRequest request, String nome) {
        String v = request.getParameter(nome);
        return v == null ? "" : v.trim();
    }

    /** Lê um valor em dinheiro; aceita "10,50" e "10.50". Rejeita NaN, Infinity e lixo. */
    static BigDecimal valor(HttpServletRequest request, String nome, String campo) throws NegocioException {
        String s = texto(request, nome).replace(',', '.');
        if (s.isEmpty()) {
            throw new NegocioException("Informe o valor de " + campo + ".");
        }
        if (s.length() > 30) {
            throw new NegocioException("O valor de " + campo + " é inválido.");
        }
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new NegocioException("O valor de " + campo + " é inválido.");
        }
    }

    /** Lê uma data no formato yyyy-MM-dd (o que o input type="date" envia). */
    static Date data(HttpServletRequest request, String nome) throws NegocioException {
        String s = texto(request, nome);
        try {
            return Date.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new NegocioException("Informe uma data válida.");
        }
    }

    static Usuario usuarioLogado(HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        return sessao == null ? null : (Usuario) sessao.getAttribute("usuarioLogado");
    }

    static void redirecionar(HttpServletRequest request, HttpServletResponse response, String destino)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/" + destino);
    }
}
