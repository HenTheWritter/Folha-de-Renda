<%@page import="java.util.List"%>
<%@page import="model.Usuario"%>
<%@page import="util.Html"%>
<%@page import="util.Csrf"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    if (request.getAttribute("amigos") == null) {
        response.sendRedirect("AmizadeController");
        return;
    }
    @SuppressWarnings("unchecked")
    List<Usuario> amigos = (List<Usuario>) request.getAttribute("amigos");
    String paginaAtiva = "amigos";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Amigos - Folha de Renda</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <%@include file="/WEB-INF/jspf/navbar.jspf" %>

    <main class="pagina">
        <h1>Amigos</h1>
        <%@include file="/WEB-INF/jspf/mensagens.jspf" %>

        <div class="grade">
            <section class="card">
                <h2>Adicionar amigo</h2>
                <form action="AmizadeController" method="POST">
                    <%= Csrf.campo(session) %>
                    <label for="email">E-mail do amigo</label>
                    <input type="email" id="email" name="email" required maxlength="100" placeholder="amigo@exemplo.com">
                    <button type="submit" class="btn">Adicionar</button>
                </form>
            </section>

            <section class="card amarelo">
                <h2>Seus amigos</h2>
                <% if (amigos.isEmpty()) { %>
                    <p class="vazio">Você ainda não adicionou ninguém. Informe o e-mail de quem já tem conta.</p>
                <% } else { %>
                    <ul class="lista-simples">
                        <% for (Usuario a : amigos) { %>
                            <li><%= Html.esc(a.getNome()) %><small><%= Html.esc(a.getEmail()) %></small></li>
                        <% } %>
                    </ul>
                <% } %>
            </section>
        </div>
    </main>
</body>
</html>
