<%@page import="java.util.List"%>
<%@page import="model.Usuario"%>
<%@page import="model.Gasto"%>
<%@page import="model.MetaEconomia"%>
<%@page import="util.Html"%>
<%@page import="util.Csrf"%>
<%@page import="util.Formato"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    // Esta página precisa dos dados carregados pelo PainelController.
    if (request.getAttribute("gastos") == null || request.getAttribute("metas") == null) {
        response.sendRedirect("PainelController");
        return;
    }
    @SuppressWarnings("unchecked")
    List<Gasto> gastos = (List<Gasto>) request.getAttribute("gastos");
    @SuppressWarnings("unchecked")
    List<MetaEconomia> metas = (List<MetaEconomia>) request.getAttribute("metas");
    String paginaAtiva = "painel";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Painel - Folha de Renda</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <%@include file="/WEB-INF/jspf/navbar.jspf" %>

    <main class="pagina">
        <%@include file="/WEB-INF/jspf/mensagens.jspf" %>

        <section class="saldo">
            <small>Saldo atual</small>
            <strong class="<%= usuario.getQuantiaUsuario().signum() < 0 ? "negativo" : "" %>"><%= Formato.moeda(usuario.getQuantiaUsuario()) %></strong>
        </section>

        <div class="grade">
            <section class="card">
                <h2>Adicionar renda</h2>
                <form action="RendaController" method="POST">
                    <%= Csrf.campo(session) %>
                    <label for="valorRenda">Valor (R$)</label>
                    <input type="number" id="valorRenda" name="valorRenda" step="0.01" min="0.01" required placeholder="0,00">
                    <button type="submit" class="btn">Adicionar ao saldo</button>
                </form>
            </section>

            <section class="card amarelo">
                <h2>Registrar gasto</h2>
                <form action="GastoController" method="POST">
                    <%= Csrf.campo(session) %>
                    <label for="descricao">Descrição</label>
                    <input type="text" id="descricao" name="descricao" required maxlength="100" placeholder="Ex.: Conta de luz">
                    <label for="valor">Valor (R$)</label>
                    <input type="number" id="valor" name="valor" step="0.01" min="0.01" required placeholder="0,00">
                    <label for="dataGasto">Data</label>
                    <input type="date" id="dataGasto" name="dataGasto" required>
                    <button type="submit" class="btn amarelo">Salvar gasto</button>
                </form>
            </section>

            <section class="card">
                <h2>Criar meta de economia</h2>
                <form action="MetaController" method="POST">
                    <%= Csrf.campo(session) %>
                    <label for="metaDescricao">O que você quer alcançar?</label>
                    <input type="text" id="metaDescricao" name="descricao" required maxlength="100" placeholder="Ex.: Viagem para a praia">
                    <label for="valorObjetivo">Valor do objetivo (R$)</label>
                    <input type="number" id="valorObjetivo" name="valorObjetivo" step="0.01" min="0.01" required placeholder="0,00">
                    <label for="valorPoupado">Valor já poupado (R$)</label>
                    <input type="number" id="valorPoupado" name="valorPoupado" step="0.01" min="0" value="0.00" required>
                    <button type="submit" class="btn">Salvar meta</button>
                </form>
            </section>
        </div>

        <div class="grade">
            <section class="card amarelo">
                <h2>Últimos gastos</h2>
                <% if (gastos.isEmpty()) { %>
                    <p class="vazio">Nenhum gasto registrado ainda. Use o formulário acima para começar.</p>
                <% } else { %>
                    <table>
                        <thead><tr><th>Data</th><th>Descrição</th><th class="valor">Valor</th></tr></thead>
                        <tbody>
                        <% for (Gasto g : gastos) { %>
                            <tr>
                                <td><%= Formato.data(g.getDataGasto()) %></td>
                                <td><%= Html.esc(g.getDescricao()) %></td>
                                <td class="valor"><%= Formato.moeda(g.getValor()) %></td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                <% } %>
            </section>

            <section class="card">
                <h2>Metas de economia</h2>
                <% if (metas.isEmpty()) { %>
                    <p class="vazio">Você ainda não criou nenhuma meta.</p>
                <% } else { %>
                    <% for (MetaEconomia m : metas) {
                           int pct = Formato.percentual(m.getValorPoupado(), m.getValorObjetivo()); %>
                        <div class="meta">
                            <div class="meta-topo"><strong><%= Html.esc(m.getDescricao()) %></strong><span><%= pct %>%</span></div>
                            <div class="barra" role="progressbar" aria-valuenow="<%= pct %>" aria-valuemin="0" aria-valuemax="100"><span style="width: <%= pct %>%"></span></div>
                            <small><%= Formato.moeda(m.getValorPoupado()) %> de <%= Formato.moeda(m.getValorObjetivo()) %></small>
                        </div>
                    <% } %>
                <% } %>
            </section>
        </div>
    </main>
</body>
</html>
