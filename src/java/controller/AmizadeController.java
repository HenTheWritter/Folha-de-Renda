package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;
import persistencia.AmizadeP;

@WebServlet(name = "AmizadeController", urlPatterns = {"/AmizadeController"})
public class AmizadeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        
        if (usuario != null) 
        {
            int idAmigo = Integer.parseInt(request.getParameter("idAmigo"));
            
            AmizadeP amizadeP = new AmizadeP();
            amizadeP.adicionarAmigo(usuario.getId(), idAmigo);
            
            response.sendRedirect("amigos.jsp?sucesso=true");
        } 
        else 
        {
            response.sendRedirect("login.jsp");
        }
    }
}