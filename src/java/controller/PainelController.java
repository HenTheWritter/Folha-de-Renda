package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Gasto;
import model.MetaEconomia;
import model.Usuario;
import negocio.GastoNegocio;
import negocio.MetaEconomiaNegocio;
import negocio.NegocioException;
import negocio.UsuarioNegocio;
import util.Flash;

/** Carrega os dados do painel (saldo, últimos gastos, metas) e mostra o menu.jsp. */
@WebServlet(name = "PainelController", urlPatterns = {"/PainelController"})
public class PainelController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = Web.usuarioLogado(request);
        if (usuario == null) {
            Web.redirecionar(request, response, "login.jsp");
            return;
        }

        Flash.paraRequest(request);
        List<Gasto> gastos = new ArrayList<>();
        List<MetaEconomia> metas = new ArrayList<>();

        try {
            new UsuarioNegocio().atualizarSaldo(usuario);
            gastos = new GastoNegocio().listarRecentes(usuario, 10);
            metas = new MetaEconomiaNegocio().listar(usuario);
        } catch (NegocioException e) {
            request.setAttribute("mensagemErro", e.getMessage());
        }

        request.setAttribute("gastos", gastos);
        request.setAttribute("metas", metas);
        request.getRequestDispatcher("menu.jsp").forward(request, response);
    }
}
