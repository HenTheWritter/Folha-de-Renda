<%@page import="java.util.List"%>
<%@page import="model.Usuario"%>
<%@page import="model.MetaGrupo"%>
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
    
    if (request.getAttribute("metasGrupo") == null || request.getAttribute("idGrupo") == null) {
        response.sendRedirect("GrupoController");
        return;
    }
    
    @SuppressWarnings("unchecked")
    List<MetaGrupo> metas = (List<MetaGrupo>) request.getAttribute("metasGrupo");
    int idGrupo = (Integer) request.getAttribute("idGrupo");
    String paginaAtiva = "grupos";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Painel do Grupo - Folha de Renda</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: #121212; color: #e0e0e0; }

        .navbar { background-color: #0a0a0a; display: flex; align-items: center; justify-content: space-between; gap: 20px; padding: 0 30px; min-height: 70px; flex-wrap: wrap; border-bottom: 3px solid transparent; border-image: linear-gradient(90deg, #4CAF50, #FFC107) 1; }
        .nav-logo { font-size: 24px; font-weight: bold; letter-spacing: 2px; text-decoration: none; color: #4CAF50; text-shadow: 0 0 8px rgba(76, 175, 80, 0.3); }
        .nav-links { display: flex; gap: 30px; flex-wrap: wrap; }
        .nav-links a { color: #ccc; text-decoration: none; font-size: 17px; transition: 0.3s; padding: 6px 0; border-bottom: 2px solid transparent;}
        .nav-links a:hover, .nav-links a.ativo { color: #fff; border-bottom-color: #4CAF50; }

        .pagina { max-width: 1100px; margin: 0 auto; padding: 24px 20px 50px; }
        .header-grupo { display: flex; justify-content: space-between; align-items: center; margin-bottom: 22px; }
        h1 { font-size: 24px; color: #fff; font-weight: 400; }
        
        .btn-voltar { color: #aaa; text-decoration: none; font-size: 14px; border: 1px solid #444; padding: 6px 12px; border-radius: 4px; transition: 0.3s; }
        .btn-voltar:hover { color: #FFC107; border-color: #FFC107; }

        .grade { display: flex; gap: 20px; flex-wrap: wrap; }
        .card { background: #1a1a1a; padding: 22px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.5); flex: 1; min-width: 280px; border-top: 2px solid #4CAF50; }
        .card.amarelo { border-top-color: #FFC107; }
        .card h2 { font-size: 18px; margin-bottom: 14px; color: #fff; font-weight: 400; }
        
        label { display: block; font-size: 14px; font-weight: bold; color: #aaa; margin-bottom: 4px; }
        input[type=text], input[type=number] { width: 100%; padding: 10px; margin-bottom: 14px; border: 1px solid #333; border-radius: 4px; background-color: #0f0f0f; color: #fff; font-size: 14px; transition: border-color 0.3s; }
        input:focus { outline: none; border-color: #4CAF50; box-shadow: 0 0 5px rgba(76, 175, 80, 0.3); }

        .btn { width: 100%; padding: 11px; border: none; border-radius: 4px; font-size: 15px; font-weight: bold; cursor: pointer; background: #4CAF50; color: #fff; transition: 0.3s; margin-top: 5px; }
        .btn:hover { background: #388E3C; box-shadow: 0 0 8px rgba(76, 175, 80, 0.4); }

        .vazio { color: #555; font-size: 14px; padding: 10px 0; font-style: italic; }

        /* Barras de Metas */
        .meta { padding: 14px 0; border-bottom: 1px solid #333; }
        .meta:last-child { border-bottom: none; padding-bottom: 0; }
        .meta-topo { display: flex; justify-content: space-between; align-items: center; gap: 10px; font-size: 14px; margin-bottom: 10px; color: #ddd; }
        .meta-titulo-container { display: flex; align-items: center; gap: 12px; }
        
        .btn-acao { background: transparent; border: 1px solid #555; color: #aaa; border-radius: 4px; padding: 4px 10px; font-size: 11px; cursor: pointer; text-decoration: none; transition: 0.3s; text-transform: uppercase; font-weight: bold; }
        .btn-acao:hover { border-color: #FFC107; color: #FFC107; background: rgba(255, 193, 7, 0.1); }

        .barra { background: #333; border-radius: 6px; height: 8px; overflow: hidden; margin-bottom: 6px; }
        .barra > span { display: block; height: 100%; background: #4CAF50; box-shadow: 0 0 5px rgba(76, 175, 80, 0.6); }
        .meta small { color: #888; font-size: 13px; display: block; }
    </style>
</head>
<body>
    <%@include file="/WEB-INF/jspf/navbar.jspf" %>

    <main class="pagina">
        <div class="header-grupo">
            <h1>Painel do Grupo</h1>
            <a href="GrupoController" class="btn-voltar">← Voltar aos meus grupos</a>
        </div>
        
        <%@include file="/WEB-INF/jspf/mensagens.jspf" %>

        <section class="card">
            <h2>Nova Meta Partilhada</h2>
            <form action="CriarMetaGrupoController" method="POST">
                <%= Csrf.campo(session) %>

                <!-- ISTO É O MAIS IMPORTANTE: Passar o ID do grupo silenciosamente -->
                <input type="hidden" name="idGrupo" value="<%= idGrupo %>">

                <label for="descricao">Descrição da Meta</label>
                <input type="text" id="descricao" name="descricao" required maxlength="100" placeholder="Ex.: Bilhetes de Avião">

                <label for="valorObjetivo">Valor a Alcançar (R$)</label>
                <input type="number" id="valorObjetivo" name="valorObjetivo" step="0.01" min="0.01" required placeholder="0.00">

                <label for="valorPoupado">Valor Já Guardado (R$) (Opcional)</label>
                <input type="number" id="valorPoupado" name="valorPoupado" step="0.01" min="0" placeholder="0.00">

                <button type="submit" class="btn">Criar Meta</button>
            </form>
        </section>

            <section class="card amarelo">
                <h2>Metas do Grupo</h2>
                <% if (metas.isEmpty()) { %>
                    <p class="vazio">O grupo ainda não tem nenhuma meta partilhada.</p>
                <% } else { %>
                    <% for (MetaGrupo m : metas) {
                           int pct = Formato.percentual(m.getValorPoupado(), m.getValorObjetivo()); %>
                        <div class="meta">
                            <div class="meta-topo">
                                <div class="meta-titulo-container">
                                    <strong><%= Html.esc(m.getDescricao()) %></strong>
                                    <a href="editar_meta_grupo.jsp?id=<%= m.getId() %>&idGrupo=<%= idGrupo %>" class="btn-acao">Editar</a>

                                    <form action="ExcluirMetaGrupoController" method="POST" style="display:inline;" onsubmit="return confirm('Deseja realmente excluir esta meta?');">
                                        <%= Csrf.campo(session) %>
                                        <input type="hidden" name="id" value="<%= m.getId() %>">
                                        <input type="hidden" name="idGrupo" value="<%= idGrupo %>">
                                        <button type="submit" class="btn-acao" style="border-color: #ef4444; color: #ef4444; background: transparent;">Excluir</button>
                                    </form>
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