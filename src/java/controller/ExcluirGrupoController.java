package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import negocio.GrupoNegocio;
import util.Flash;

@WebServlet(name = "ExcluirGrupoController", urlPatterns = {"/ExcluirGrupoController"})
public class ExcluirGrupoController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) { Web.redirecionar(request, response, "login.jsp"); return; }

        try {
            int idGrupo = Integer.parseInt(request.getParameter("idGrupo"));
            new GrupoNegocio().excluir(idGrupo, usuario);
            Flash.sucesso(request, "Grupo apagado permanentemente!");
        } catch (Exception e) {
            Flash.erro(request, "Erro ao tentar apagar o grupo.");
        }
        Web.redirecionar(request, response, "GrupoController");
    }
}