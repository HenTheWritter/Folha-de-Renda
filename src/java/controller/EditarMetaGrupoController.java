package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MetaGrupo;
import model.Usuario;
import negocio.MetaGrupoNegocio;
import util.Flash;

@WebServlet(name = "EditarMetaGrupoController", urlPatterns = {"/EditarMetaGrupoController"})
public class EditarMetaGrupoController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(EditarMetaGrupoController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        int idGrupo = Integer.parseInt(request.getParameter("idGrupo"));

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            BigDecimal valorPoupado = new BigDecimal(request.getParameter("valorPoupado"));
            BigDecimal valorObjetivo = new BigDecimal(request.getParameter("valorObjetivo"));

            MetaGrupo meta = new MetaGrupo();
            meta.setId(id);
            meta.setIdGrupo(idGrupo);
            meta.setValorPoupado(valorPoupado);
            meta.setValorObjetivo(valorObjetivo);

            new MetaGrupoNegocio().atualizar(meta);
            Flash.sucesso(request, "Meta do grupo atualizada com sucesso!");

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Erro ao atualizar meta do grupo", e);
            Flash.erro(request, "Erro ao atualizar meta: " + e.getMessage());
        }

        Web.redirecionar(request, response, "DetalheGrupoController?idGrupo=" + idGrupo);
    }
}