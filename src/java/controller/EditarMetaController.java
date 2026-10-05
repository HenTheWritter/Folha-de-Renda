package controller;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.MetaEconomia;
import model.Usuario;
import negocio.MetaEconomiaNegocio;

@WebServlet(name = "EditarMetaController", urlPatterns = {"/EditarMetaController"})
public class EditarMetaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            BigDecimal valorPoupado = new BigDecimal(request.getParameter("valorPoupado"));
            BigDecimal valorObjetivo = new BigDecimal(request.getParameter("valorObjetivo"));

            MetaEconomia meta = new MetaEconomia();
            meta.setId(id);
            meta.setValorPoupado(valorPoupado);
            meta.setValorObjetivo(valorObjetivo);
            
            meta.setIdUsuario(usuarioLogado.getId()); 

            MetaEconomiaNegocio negocio = new MetaEconomiaNegocio();
            negocio.atualizar(meta);

            session.setAttribute("mensagemSucesso", "Meta atualizada com sucesso!");
            response.sendRedirect("PainelController");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("mensagemErro", "Erro ao atualizar a meta: " + e.getMessage());
            response.sendRedirect("PainelController");
        }
    }
}