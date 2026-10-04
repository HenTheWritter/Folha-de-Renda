package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Gasto;
import model.Usuario;
import negocio.GastoNegocio;
import negocio.NegocioException;
import util.Flash;

@WebServlet(name = "GastoController", urlPatterns = {"/GastoController"})
public class GastoController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(GastoController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        try {
            Gasto gasto = new Gasto();
            gasto.setDescricao(Web.texto(request, "descricao"));
            gasto.setValor(Web.valor(request, "valor", "gasto"));
            gasto.setDataGasto(Web.data(request, "dataGasto"));

            new GastoNegocio().registrarNovoGasto(usuario, gasto);
            Flash.sucesso(request, "Gasto registrado. O saldo foi atualizado.");
        } catch (NegocioException e) {
            Flash.erro(request, e.getMessage());
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Falha inesperada ao registrar gasto", e);
            Flash.erro(request, "Erro inesperado. Tente novamente.");
        }
        Web.redirecionar(request, response, "PainelController");
    }
}
