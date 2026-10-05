<%@page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Object codigo = request.getAttribute("javax.servlet.error.status_code");
    int status = codigo instanceof Integer ? (Integer) codigo : 500;
    String titulo;
    String texto;
    if (status == 404) {
        titulo = "Página não encontrada";
        texto = "O endereço que tentou aceder não existe.";
    } else if (status == 403) {
        titulo = "Ação não permitida";
        texto = "Não foi possível validar esta ação. Volte ao painel e tente novamente.";
    } else {
        titulo = "Algo correu mal";
        texto = "Ocorreu um erro inesperado. Tente novamente em instantes.";
    }
    response.setStatus(status);
%>
<!DOCTYPE html>
<html lang="pt-PT">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= titulo %> - Folha de Renda</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: #121212; color: #e0e0e0; }

        .navbar { 
            background-color: #0a0a0a; 
            display: flex; 
            align-items: center; 
            justify-content: flex-start; 
            padding: 0 30px; 
            height: 70px; 
            border-bottom: 3px solid transparent; 
            border-image: linear-gradient(90deg, #4CAF50, #FFC107) 1; 
        }
        .nav-logo { 
            color: #4CAF50; 
            font-size: 24px; 
            font-weight: bold; 
            letter-spacing: 2px; 
            text-transform: uppercase; 
            text-shadow: 0 0 8px rgba(76, 175, 80, 0.3); 
            text-decoration: none; 
        }

        .pagina { 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            height: calc(100vh - 70px); 
            padding: 20px; 
        }
        
        .card { 
            background: #1a1a1a; 
            padding: 40px; 
            border-radius: 8px; 
            box-shadow: 0 4px 15px rgba(0,0,0,0.5); 
            width: 100%; 
            max-width: 450px; 
            text-align: center; 
            border-top: 2px solid #4CAF50; 
        }
        .card.amarelo { border-top-color: #FFC107; }
        .card h2 { font-size: 24px; margin-bottom: 16px; color: #fff; font-weight: 400; }
        
        .vazio { color: #aaa; font-size: 16px; margin-bottom: 24px; line-height: 1.5; }
        
        .btn { 
            display: inline-block; 
            width: 100%; 
            padding: 12px; 
            border: none; 
            border-radius: 4px; 
            font-size: 16px; 
            font-weight: bold; 
            cursor: pointer; 
            background: #4CAF50; 
            color: #fff; 
            transition: background 0.3s, box-shadow 0.3s; 
            text-decoration: none; 
        }
        .btn:hover { background: #388E3C; box-shadow: 0 0 8px rgba(76, 175, 80, 0.4); }
    </style>
</head>
<body>
    <nav class="navbar">
        <a class="nav-logo" href="<%= request.getContextPath() %>/index.html">Folha de Renda</a>
    </nav>
    <main class="pagina">
        <section class="card amarelo">
            <h2><%= titulo %></h2>
            <p class="vazio"><%= texto %></p>
            <a class="btn" href="<%= request.getContextPath() %>/PainelController">Ir para o painel</a>
        </section>
    </main>
</body>
</html>