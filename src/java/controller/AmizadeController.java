package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import negocio.AmizadeNegocio;
import negocio.NegocioException;
import util.Flash;

@WebServlet(name = "AmizadeController", urlPatterns = {"/AmizadeController"})
public class AmizadeController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AmizadeController.class.getName());

    /** Mostra a lista de amigos (amigos.jsp). */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        Flash.paraRequest(request);
        List<Usuario> amigos = new ArrayList<>();
        try {
            amigos = new AmizadeNegocio().listar(usuario);
        } catch (NegocioException e) {
            request.setAttribute("mensagemErro", e.getMessage());
        }
        request.setAttribute("amigos", amigos);
        request.getRequestDispatcher("amigos.jsp").forward(request, response);
    }

    /** Adiciona um amigo pelo e-mail. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        try {
            new AmizadeNegocio().adicionarPorEmail(usuario, Web.texto(request, "email"));
            Flash.sucesso(request, "Amigo adicionado.");
        } catch (NegocioException e) {
            Flash.erro(request, e.getMessage());
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Falha inesperada ao adicionar amigo", e);
            Flash.erro(request, "Erro inesperado. Tente novamente.");
        }
        Web.redirecionar(request, response, "AmizadeController");
    }
}
