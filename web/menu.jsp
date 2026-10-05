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
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: #121212; color: #e0e0e0; }

        .navbar { background-color: #0a0a0a; display: flex; align-items: center; justify-content: space-between; gap: 20px; padding: 0 30px; min-height: 70px; flex-wrap: wrap; border-bottom: 3px solid transparent; border-image: linear-gradient(90deg, #4CAF50, #FFC107) 1; }
        .nav-logo { font-size: 24px; font-weight: bold; letter-spacing: 2px; text-decoration: none; color: #4CAF50; text-shadow: 0 0 8px rgba(76, 175, 80, 0.3); }
        .nav-links { display: flex; gap: 30px; flex-wrap: wrap; }
        .nav-links a { color: #ccc; text-decoration: none; font-size: 17px; font-weight: 300; padding: 6px 0; border-bottom: 2px solid transparent; transition: color 0.3s, border-color 0.3s; }
        .nav-links a:hover { color: #fff; }
        .nav-links a.ativo { color: #fff; border-bottom-color: #4CAF50; } 
        .nav-user { display: flex; align-items: center; gap: 14px; color: #ccc; font-size: 15px; }
        .nav-user form { margin: 0; }
        .btn-sair { background: transparent; color: #ccc; border: 1px solid #444; border-radius: 4px; padding: 7px 14px; font-size: 14px; cursor: pointer; width: auto; transition: 0.3s; }
        .btn-sair:hover { border-color: #FFC107; color: #FFC107; }
        a:focus-visible, button:focus-visible, input:focus-visible { outline: 2px solid #FFC107; outline-offset: 2px; }

        .pagina { max-width: 1100px; margin: 0 auto; padding: 24px 20px 50px; }
        
        .saldo { background: #1a1a1a; border-left: 4px solid #4CAF50; border-radius: 8px; padding: 22px 26px; margin-bottom: 22px; box-shadow: 0 4px 10px rgba(0,0,0,0.5); }
        .saldo small { display: block; color: #888; font-size: 14px; margin-bottom: 4px; }
        .saldo strong { font-size: 34px; color: #fff; }
        .saldo strong.negativo { color: #ef4444; } 

        .grade { display: flex; gap: 20px; flex-wrap: wrap; margin-bottom: 20px; }
        .card { background: #1a1a1a; padding: 22px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.5); flex: 1; min-width: 280px; border-top: 2px solid #4CAF50; }
        .card.amarelo { border-top-color: #FFC107; }
        .card h2 { font-size: 18px; margin-bottom: 14px; color: #fff; font-weight: 400; }
        label { display: block; font-size: 14px; font-weight: bold; color: #aaa; margin-bottom: 4px; }

        input[type=text], input[type=number], input[type=date] { width: 100%; padding: 10px; margin-bottom: 14px; border: 1px solid #333; border-radius: 4px; background-color: #0f0f0f; color: #fff; font-size: 14px; transition: border-color 0.3s, box-shadow 0.3s; }
        input:focus { outline: none; border-color: #4CAF50; box-shadow: 0 0 5px rgba(76, 175, 80, 0.3); }

        .btn { width: 100%; padding: 11px; border: none; border-radius: 4px; font-size: 15px; font-weight: bold; cursor: pointer; background: #4CAF50; color: #fff; transition: background 0.3s, box-shadow 0.3s; margin-top: 5px;}
        .btn:hover { background: #388E3C; box-shadow: 0 0 8px rgba(76, 175, 80, 0.4); }
        .btn.amarelo { background: #FFC107; color: #121212; }
        .btn.amarelo:hover { background: #FFA000; box-shadow: 0 0 8px rgba(255, 193, 7, 0.4); }

        .btn-acao { background: transparent; border: 1px solid #555; color: #aaa; border-radius: 4px; padding: 4px 10px; font-size: 11px; cursor: pointer; text-decoration: none; transition: 0.3s; text-transform: uppercase; font-weight: bold; letter-spacing: 1px; }
        .btn-acao:hover { border-color: #FFC107; color: #FFC107; background: rgba(255, 193, 7, 0.1); }

        .alerta { padding: 12px 14px; border-radius: 4px; margin-bottom: 18px; font-size: 14px; }
        .alerta.erro { background: rgba(220, 38, 38, 0.1); color: #ef4444; border: 1px solid #dc2626; }
        .alerta.ok { background: rgba(22, 163, 74, 0.1); color: #4ade80; border: 1px solid #16a34a; }

        table { width: 100%; border-collapse: collapse; font-size: 14px; }
        th, td { text-align: left; padding: 12px 6px; border-bottom: 1px solid #333; }
        th { color: #888; font-weight: 400; text-transform: uppercase; font-size: 12px; letter-spacing: 1px; }
        td.valor, th.valor { text-align: right; white-space: nowrap; }
        .vazio { color: #555; font-size: 14px; padding: 10px 0; font-style: italic; }

        .meta { padding: 14px 0; border-bottom: 1px solid #333; }
        .meta:last-child { border-bottom: none; padding-bottom: 0; }
        .meta-topo { display: flex; justify-content: space-between; align-items: center; gap: 10px; font-size: 14px; margin-bottom: 10px; color: #ddd; }
        .meta-titulo-container { display: flex; align-items: center; gap: 12px; }
        .barra { background: #333; border-radius: 6px; height: 8px; overflow: hidden; margin-bottom: 6px; }
        .barra > span { display: block; height: 100%; background: #4CAF50; box-shadow: 0 0 5px rgba(76, 175, 80, 0.6); }
        .meta small { color: #888; font-size: 13px; display: block; }

        @media (max-width: 600px) { .navbar { padding: 10px 16px; } .nav-links { gap: 18px; } }
    </style>
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
                            <div class="meta-topo">
                                <div class="meta-titulo-container">
                                    <strong><%= Html.esc(m.getDescricao()) %></strong>
                                    <!-- Botão que direciona para a página de edição da meta -->
                                    <a href="editar_meta.jsp?id=<%= m.getId() %>" class="btn-acao">Editar</a>
                                </div>
                                <span><%= pct %>%</span>
                            </div>
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