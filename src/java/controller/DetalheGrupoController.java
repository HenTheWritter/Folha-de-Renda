package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

@WebServlet(name = "DetalheGrupoController", urlPatterns = {"/DetalheGrupoController"})
public class DetalheGrupoController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DetalheGrupoController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        String idGrupoStr = request.getParameter("idGrupo");
        if (idGrupoStr == null || idGrupoStr.trim().isEmpty()) {
            Web.redirecionar(request, response, "GrupoController");
            return;
        }

        int idGrupo = Integer.parseInt(idGrupoStr);
        List<MetaGrupo> metasGrupo = new ArrayList<>();

        try {
            metasGrupo = new MetaGrupoNegocio().listar(idGrupo);
        } catch (NegocioException e) {
            request.setAttribute("mensagemErro", e.getMessage());
        }

        request.setAttribute("idGrupo", idGrupo);
        request.setAttribute("metasGrupo", metasGrupo);
        request.getRequestDispatcher("detalhes_grupo.jsp").forward(request, response);
    }

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
            MetaGrupo meta = new MetaGrupo();
            meta.setIdGrupo(idGrupo);
            meta.setDescricao(Web.texto(request, "descricao"));
            meta.setValorObjetivo(new BigDecimal(request.getParameter("valorObjetivo")));
            meta.setValorPoupado(new BigDecimal(request.getParameter("valorPoupado")));

            new MetaGrupoNegocio().salvar(meta); 
            Flash.sucesso(request, "Meta do grupo criada com sucesso!");

        } catch (NegocioException e) { 
            Flash.erro(request, e.getMessage());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Erro ao salvar meta do grupo", e);
            Flash.erro(request, "Erro inesperado ao salvar a meta.");
        }

        Web.redirecionar(request, response, "DetalheGrupoController?idGrupo=" + idGrupo);
    }
}