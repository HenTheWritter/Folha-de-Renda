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
import model.Grupo;
import model.Usuario;
import negocio.GrupoNegocio;
import negocio.AmizadeNegocio; 
import negocio.NegocioException;
import util.Flash;

@WebServlet(name = "GrupoController", urlPatterns = {"/GrupoController"})
public class GrupoController extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(GrupoController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        Flash.paraRequest(request);
        List<Grupo> grupos = new ArrayList<>();
        List<Usuario> amigos = new ArrayList<>();
        
        try {
            grupos = new GrupoNegocio().listar(usuario);
            
            amigos = new AmizadeNegocio().listar(usuario); 
            
        } catch (NegocioException e) {
            request.setAttribute("mensagemErro", e.getMessage());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Erro ao listar grupos ou amigos", e);
        }
        
        request.setAttribute("grupos", grupos);
        request.setAttribute("amigos", amigos); // Envia os amigos para a página
        request.getRequestDispatcher("grupos.jsp").forward(request, response);
    }

    /** Cria um grupo; o usuário logado e os amigos marcados entram no grupo. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        try {
            Grupo grupo = new Grupo();
            grupo.setNome(Web.texto(request, "nome"));
            grupo.setDescricao(Web.texto(request, "descricao"));

            String[] amigosIds = request.getParameterValues("amigosIds");

            new GrupoNegocio().criarGrupoComMembros(grupo, usuario, amigosIds);
            
            Flash.sucesso(request, "Grupo criado com sucesso!");
        } catch (NegocioException e) {
            Flash.erro(request, e.getMessage());
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "Falha inesperada ao criar grupo", e);
            Flash.erro(request, "Erro inesperado. Tente novamente.");
        }
        Web.redirecionar(request, response, "GrupoController");
    }
}