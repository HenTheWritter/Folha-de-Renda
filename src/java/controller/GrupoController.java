package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Grupo;
import negocio.GrupoNegocio;

@WebServlet(name = "GrupoController", urlPatterns = {"/GrupoController"})
public class GrupoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String nome = request.getParameter("nome").trim();
        String descricao = request.getParameter("descricao").trim();
        
        Grupo grupo = new Grupo();
        grupo.setNome(nome);
        grupo.setDescricao(descricao);
        
        GrupoNegocio negocio = new GrupoNegocio();
        
        try 
        {
            negocio.criarGrupo(grupo);
            response.sendRedirect("grupos.jsp?sucesso=true");
        } 
        catch (Exception e) 
        {
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("grupos.jsp").forward(request, response);
        }
    }
}