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
import negocio.NegocioException;
import util.Flash;

@WebServlet(name = "CriarMetaGrupoController", urlPatterns = {"/CriarMetaGrupoController"})
public class CriarMetaGrupoController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(CriarMetaGrupoController.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        String idGrupoStr = request.getParameter("idGrupo");
        if (idGrupoStr == null || idGrupoStr.isEmpty()) {
            Flash.erro(request, "Grupo não identificado.");
            Web.redirecionar(request, response, "GrupoController");
            return;
        }

        int idGrupo = Integer.parseInt(idGrupoStr);

        try {
            MetaGrupo meta = new MetaGrupo();
            meta.setIdGrupo(idGrupo);
            meta.setDescricao(request.getParameter("descricao"));
            
            String objStr = request.getParameter("valorObjetivo");
            if (objStr != null && !objStr.isEmpty()) {
                meta.setValorObjetivo(new java.math.BigDecimal(objStr));
            }
            
            String poupadoStr = request.getParameter("valorPoupado");
            if (poupadoStr != null && !poupadoStr.isEmpty()) {
                meta.setValorPoupado(new java.math.BigDecimal(poupadoStr));
            } else {
                meta.setValorPoupado(java.math.BigDecimal.ZERO); 
            }

            new MetaGrupoNegocio().salvar(meta);
            Flash.sucesso(request, "Meta do grupo criada com sucesso!");

        } catch (negocio.NegocioException e) {
            Flash.erro(request, e.getMessage());
        } catch (Exception e) {
            LOG.log(java.util.logging.Level.SEVERE, "Erro ao salvar meta do grupo", e);
            Flash.erro(request, "Erro ao salvar a meta.");
        }

        Web.redirecionar(request, response, "DetalheGrupoController?idGrupo=" + idGrupo);
    }
}