<%@page import="java.util.List"%>
<%@page import="model.Usuario"%>
<%@page import="model.Grupo"%>
<%@page import="util.Html"%>
<%@page import="util.Csrf"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    if (request.getAttribute("grupos") == null) {
        response.sendRedirect("GrupoController");
        return;
    }
    @SuppressWarnings("unchecked")
    List<Grupo> grupos = (List<Grupo>) request.getAttribute("grupos");
    String paginaAtiva = "grupos";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Grupos - Folha de Renda</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <%@include file="/WEB-INF/jspf/navbar.jspf" %>

    <main class="pagina">
        <h1>Grupos</h1>
        <%@include file="/WEB-INF/jspf/mensagens.jspf" %>

        <div class="grade">
            <section class="card">
                <h2>Criar grupo</h2>
                <form action="GrupoController" method="POST">
                    <%= Csrf.campo(session) %>
                    <label for="nome">Nome do grupo</label>
                    <input type="text" id="nome" name="nome" required maxlength="100" placeholder="Ex.: Viagem de julho">
                    <label for="descricao">Descrição (opcional)</label>
                    <input type="text" id="descricao" name="descricao" maxlength="100" placeholder="Para que serve este grupo?">
                    <button type="submit" class="btn">Criar grupo</button>
                </form>
            </section>

            <section class="card amarelo">
                <h2>Seus grupos</h2>
                <% if (grupos.isEmpty()) { %>
                    <p class="vazio">Você ainda não participa de nenhum grupo.</p>
                <% } else { %>
                    <ul class="lista-simples">
                        <% for (Grupo g : grupos) { %>
                            <li><%= Html.esc(g.getNome()) %>
                                <% if (g.getDescricao() != null && !g.getDescricao().isEmpty()) { %>
                                    <small><%= Html.esc(g.getDescricao()) %></small>
                                <% } %>
                            </li>
                        <% } %>
                    </ul>
                <% } %>
            </section>
        </div>
    </main>
</body>
</html>
