package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;
import negocio.UsuarioNegocio;

@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        UsuarioNegocio negocio = new UsuarioNegocio();
        
        try 
        {
            Usuario usuario = negocio.realizarLogin(email, senha);
            
            HttpSession sessao = request.getSession();
            sessao.setAttribute("usuarioLogado", usuario);
            
            response.sendRedirect("menu.jsp");
        } 
        catch (Exception e) 
        {
            request.setAttribute("mensagemErro", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}