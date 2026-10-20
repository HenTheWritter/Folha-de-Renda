package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import negocio.GastoNegocio;
import util.Flash;

@WebServlet(name = "ExcluirGastoController", urlPatterns = {"/ExcluirGastoController"})
public class ExcluirGastoController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) { Web.redirecionar(request, response, "login.jsp"); return; }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            new GastoNegocio().excluir(id, usuario);
            Flash.sucesso(request, "Gasto excluído e saldo estornado com sucesso!");
        } catch (Exception e) {
            Flash.erro(request, "Erro ao excluir o gasto.");
        }
        Web.redirecionar(request, response, "PainelController");
    }
}