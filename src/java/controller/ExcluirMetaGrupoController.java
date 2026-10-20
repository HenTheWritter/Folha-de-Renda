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
import negocio.MetaGrupoNegocio;
import negocio.NegocioException;
import util.Flash;

@WebServlet(name = "ExcluirMetaGrupoController", urlPatterns = {"/ExcluirMetaGrupoController"})
public class ExcluirMetaGrupoController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ExcluirMetaGrupoController.class.getName());

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
            new MetaGrupoNegocio().excluir(id, idGrupo);
            Flash.sucesso(request, "Meta excluída com sucesso!");
        } catch (NegocioException e) {
            Flash.erro(request, e.getMessage());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Erro ao excluir meta do grupo", e);
            Flash.erro(request, "Erro ao excluir a meta.");
        }

        Web.redirecionar(request, response, "DetalheGrupoController?idGrupo=" + idGrupo);
    }
}