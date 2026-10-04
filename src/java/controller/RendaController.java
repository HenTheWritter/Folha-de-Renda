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
import negocio.GastoNegocio;
import negocio.NegocioException;
import util.Flash;

/** Adiciona renda (entrada de dinheiro) ao saldo do usuário. */
@WebServlet(name = "RendaController", urlPatterns = {"/RendaController"})
public class RendaController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(RendaController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        try {
            new GastoNegocio().adicionarRenda(usuario, Web.valor(request, "valorRenda", "renda"));
            Flash.sucesso(request, "Renda adicionada. O saldo foi atualizado.");
        } catch (NegocioException e) {
            Flash.erro(request, e.getMessage());
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Falha inesperada ao adicionar renda", e);
            Flash.erro(request, "Erro inesperado. Tente novamente.");
        }
        Web.redirecionar(request, response, "PainelController");
    }
}
