package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MetaEconomia;
import model.Usuario;
import persistencia.MetaEconomiaP;

@WebServlet(name = "MetaController", urlPatterns = {"/MetaController"})
public class MetaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        
        if (usuario != null) 
        {
            String descricao = request.getParameter("descricao");
            double valorObjetivo = Double.parseDouble(request.getParameter("valorObjetivo"));
            double valorPoupado = Double.parseDouble(request.getParameter("valorPoupado"));
            
            MetaEconomia meta = new MetaEconomia();
            meta.setIdUsuario(usuario.getId());
            meta.setDescricao(descricao);
            meta.setValorObjetivo(valorObjetivo);
            meta.setValorPoupado(valorPoupado);
            
            MetaEconomiaP persistencia = new MetaEconomiaP();
            persistencia.salvar(meta);
            
            response.sendRedirect("dashboard.jsp?sucesso=true");
        } 
        else 
        {
            response.sendRedirect("login.jsp");
        }
    }
}