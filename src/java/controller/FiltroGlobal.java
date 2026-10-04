package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Csrf;

/**
 * Filtro único (ordem garantida) que, para toda requisição:
 *   1. força UTF-8 (acentos corretos em todos os formulários);
 *   2. exige login em tudo que não for página pública;
 *   3. exige o token anti-CSRF em todo POST;
 *   4. acrescenta cabeçalhos de segurança.
 */
@WebFilter(filterName = "FiltroGlobal", urlPatterns = {"/*"})
public class FiltroGlobal implements Filter {

    private static final Set<String> PUBLICOS = new HashSet<>(Arrays.asList(
            "", "/", "/index.html", "/login.jsp", "/cadastro.jsp",
            "/UsuarioController", "/CadastroController"));

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");

        String caminho = request.getServletPath();
        boolean publico = PUBLICOS.contains(caminho) || caminho.startsWith("/css/");

        if (!publico) {
            HttpSession sessao = request.getSession(false);
            boolean logado = sessao != null && sessao.getAttribute("usuarioLogado") != null;
            if (!logado) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            // páginas autenticadas não devem ficar em cache (botão "voltar" após o logout)
            response.setHeader("Cache-Control", "no-store");
        }

        if ("POST".equalsIgnoreCase(request.getMethod()) && !Csrf.valido(request)) {
            if (publico) {
                // login/cadastro com a página aberta há muito tempo (sessão expirou)
                response.sendRedirect(request.getContextPath() + "/login.jsp?sessao=expirada");
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Requisição inválida (token de segurança).");
            }
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
