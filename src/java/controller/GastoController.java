package controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Gasto;
import model.Usuario;
import negocio.GastoNegocio;

@WebServlet(name = "GastoController", urlPatterns = {"/GastoController"})
public class GastoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        
        if (usuario != null) 
        {
            String descricao = request.getParameter("descricao");
            double valor = Double.parseDouble(request.getParameter("valor"));
            Date dataGasto = Date.valueOf(request.getParameter("dataGasto")); 
            
            Gasto gasto = new Gasto();
            gasto.setIdUsuario(usuario.getId());
            gasto.setDescricao(descricao);
            gasto.setValor(valor);
            gasto.setDataGasto(dataGasto);
            
            GastoNegocio negocio = new GastoNegocio();
            
            try 
            {
                negocio.registrarNovoGasto(usuario, gasto);
                response.sendRedirect("menu.jsp?sucesso=true");
            } 
            catch (Exception e) 
            {
                request.setAttribute("mensagemErro", e.getMessage());
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }
        } 
        else 
        {
            response.sendRedirect("login.jsp");
        }
    }
}