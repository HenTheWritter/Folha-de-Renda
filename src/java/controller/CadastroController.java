package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import negocio.NegocioException;
import negocio.UsuarioNegocio;

@WebServlet(name = "CadastroController", urlPatterns = {"/CadastroController"})
public class CadastroController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CadastroController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = Web.texto(request, "nome");
        String email = Web.texto(request, "email");

        try {
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(request.getParameter("senha")); // texto puro; o negócio troca por hash

            new UsuarioNegocio().cadastrarUsuario(usuario);

            response.sendRedirect(request.getContextPath() + "/login.jsp?sucesso=true");
        } catch (NegocioException e) {
            voltar(request, response, e.getMessage(), nome, email);
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Falha inesperada no cadastro", e);
            voltar(request, response, "Erro inesperado. Tente novamente.", nome, email);
        }
    }

    private void voltar(HttpServletRequest request, HttpServletResponse response,
                        String mensagem, String nome, String email) throws ServletException, IOException {
        request.setAttribute("erroRegisto", mensagem);
        request.setAttribute("nomeForm", nome);
        request.setAttribute("emailForm", email);
        request.getRequestDispatcher("cadastro.jsp").forward(request, response);
    }
}
