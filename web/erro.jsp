<%@page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Object codigo = request.getAttribute("javax.servlet.error.status_code");
    int status = codigo instanceof Integer ? (Integer) codigo : 500;
    String titulo;
    String texto;
    if (status == 404) {
        titulo = "Página não encontrada";
        texto = "O endereço que você tentou abrir não existe.";
    } else if (status == 403) {
        titulo = "Ação não permitida";
        texto = "Não foi possível validar esta ação. Volte ao painel e tente novamente.";
    } else {
        titulo = "Algo deu errado";
        texto = "Ocorreu um erro inesperado. Tente novamente em instantes.";
    }
    response.setStatus(status);
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= titulo %> - Folha de Renda</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilo.css">
</head>
<body>
    <nav class="navbar">
        <a class="nav-logo" href="<%= request.getContextPath() %>/index.html">Folha de Renda</a>
    </nav>
    <main class="pagina">
        <section class="card amarelo">
            <h2><%= titulo %></h2>
            <p class="vazio" style="margin-bottom: 16px;"><%= texto %></p>
            <a class="btn" style="display: block; text-align: center; text-decoration: none;"
               href="<%= request.getContextPath() %>/PainelController">Ir para o painel</a>
        </section>
    </main>
</body>
</html>
