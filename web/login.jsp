<%@page import="util.Html"%>
<%@page import="util.Csrf"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - Folha de Renda</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: #121212; color: #e0e0e0; }

        .navbar {
            background-color: #0a0a0a; 
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 30px;
            height: 70px;
            border-bottom: 3px solid transparent;
            border-image: linear-gradient(90deg, #4CAF50, #FFC107) 1;
        }
        .nav-logo { color: #4CAF50; font-size: 24px; font-weight: bold; letter-spacing: 2px; text-transform: uppercase; text-shadow: 0 0 8px rgba(76, 175, 80, 0.3); }
        .nav-links { display: flex; gap: 30px; }
        .nav-links a { color: #ccc; text-decoration: none; font-size: 17px; font-weight: 300; transition: color 0.3s; }
        .nav-links a:hover { color: #fff; }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: calc(100vh - 70px);
            padding: 20px;
        }

        .form-box {
            background-color: #1a1a1a;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.5);
            width: 100%;
            max-width: 400px;
            border-top: 2px solid #4CAF50;
        }
        .form-box h2 { color: #fff; margin-bottom: 25px; font-size: 22px; text-align: center; font-weight: 400; }
        
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #aaa; font-weight: bold; font-size: 14px; }
        
        .form-group input { 
            width: 100%; padding: 12px; border: 1px solid #333; border-radius: 4px; 
            background-color: #0f0f0f; color: #fff; font-size: 14px; transition: border-color 0.3s, box-shadow 0.3s; 
        }
        .form-group input:focus { outline: none; border-color: #4CAF50; box-shadow: 0 0 5px rgba(76, 175, 80, 0.3); }

        .btn-green { 
            width: 100%; padding: 12px; border: none; border-radius: 4px; font-size: 16px; 
            font-weight: bold; cursor: pointer; background-color: #4CAF50; color: white; 
            transition: background 0.3s, box-shadow 0.3s; margin-top: 10px; 
        }
        .btn-green:hover { background-color: #388E3C; box-shadow: 0 0 8px rgba(76, 175, 80, 0.4); }

        .alert-error { background: rgba(220, 38, 38, 0.1); color: #ef4444; border: 1px solid #dc2626; padding: 12px; border-radius: 4px; margin-bottom: 20px; text-align: center; font-size: 14px; }
        .alert-success { background: rgba(22, 163, 74, 0.1); color: #4ade80; border: 1px solid #16a34a; padding: 12px; border-radius: 4px; margin-bottom: 20px; text-align: center; font-size: 14px; }
        
        .link-rodape { text-align: center; margin-top: 20px; font-size: 14px; color: #aaa; }
        .link-rodape a { color: #FFC107; font-weight: bold; text-decoration: none; transition: text-shadow 0.3s; }
        .link-rodape a:hover { text-shadow: 0 0 5px rgba(255, 193, 7, 0.5); }
    </style>
</head>
<body>

    <nav class="navbar">
        <div class="nav-logo">Folha de Renda</div>
        <div class="nav-links">
            <a href="index.html">Início</a>
        </div>
    </nav>

    <div class="container">
        <div class="form-box">
            <h2>Entrar na conta</h2>
            
            <% if (request.getAttribute("mensagemErro") != null) { %>
                <div class="alert-error" role="alert"><%= Html.esc(request.getAttribute("mensagemErro")) %></div>
            <% } %>
            <% if (request.getParameter("sucesso") != null) { %>
                <div class="alert-success" role="status">Conta criada com sucesso! Faça login.</div>
            <% } %>
            <% if (request.getParameter("sessao") != null) { %>
                <div class="alert-error" role="alert">Sua sessão expirou. Tente novamente.</div>
            <% } %>

            <form action="UsuarioController" method="POST">
                <%= Csrf.campo(session) %>
                <div class="form-group">
                    <label>E-mail</label>
                    <input type="email" name="email" required maxlength="100" placeholder="Seu e-mail" value="<%= Html.esc(request.getAttribute("emailForm")) %>">
                </div>
                <div class="form-group">
                    <label>Senha</label>
                    <input type="password" name="senha" required placeholder="Sua senha">
                </div>
                <button type="submit" class="btn-green">Entrar</button>
            </form>

            <div class="link-rodape">
                Ainda não tem conta? <a href="cadastro.jsp">Criar conta</a>
            </div>
        </div>
    </div>

</body>
</html>