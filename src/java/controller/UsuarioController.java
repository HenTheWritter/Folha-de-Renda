package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;
import negocio.NegocioException;
import negocio.UsuarioNegocio;

/** Login. */
@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UsuarioController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = Web.texto(request, "email");
        String senha = request.getParameter("senha");

        try {
            Usuario usuario = new UsuarioNegocio().realizarLogin(email, senha);

            // Evita "session fixation": descarta a sessão anterior e cria uma nova após o login.
            HttpSession antiga = request.getSession(false);
            if (antiga != null) {
                antiga.invalidate();
            }
            request.getSession(true).setAttribute("usuarioLogado", usuario);

            Web.redirecionar(request, response, "PainelController");
        } catch (NegocioException e) {
            request.setAttribute("mensagemErro", e.getMessage());
            request.setAttribute("emailForm", email);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Falha inesperada no login", e);
            request.setAttribute("mensagemErro", "Erro inesperado. Tente novamente.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
