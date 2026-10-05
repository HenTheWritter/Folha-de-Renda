<%@page import="util.Html"%>
<%@page import="util.Csrf"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Criar Conta - Folha de Renda</title>
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
            border-top: 2px solid #FFC107;
        }
        .form-box h2 { color: #fff; margin-bottom: 25px; font-size: 22px; text-align: center; font-weight: 400; }
        
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #aaa; font-weight: bold; font-size: 14px; }
        
        .form-group input { 
            width: 100%; padding: 12px; border: 1px solid #333; border-radius: 4px; 
            background-color: #0f0f0f; color: #fff; font-size: 14px; transition: border-color 0.3s, box-shadow 0.3s; 
        }
        .form-group input:focus { outline: none; border-color: #FFC107; box-shadow: 0 0 5px rgba(255, 193, 7, 0.3); }

        .btn-yellow { 
            width: 100%; padding: 12px; border: none; border-radius: 4px; font-size: 16px; 
            font-weight: bold; cursor: pointer; background-color: #FFC107; color: #121212; 
            transition: background 0.3s, box-shadow 0.3s; margin-top: 10px; 
        }
        .btn-yellow:hover { background-color: #FFA000; box-shadow: 0 0 8px rgba(255, 193, 7, 0.4); }

        .alert-error { background: rgba(220, 38, 38, 0.1); color: #ef4444; border: 1px solid #dc2626; padding: 12px; border-radius: 4px; margin-bottom: 20px; text-align: center; font-size: 14px; }
        
        .link-rodape { text-align: center; margin-top: 20px; font-size: 14px; color: #aaa; }
        .link-rodape a { color: #4CAF50; font-weight: bold; text-decoration: none; transition: text-shadow 0.3s; }
        .link-rodape a:hover { text-shadow: 0 0 5px rgba(76, 175, 80, 0.5); }
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
            <h2>Criar conta</h2>
            
            <% if (request.getAttribute("erroRegisto") != null) { %>
                <div class="alert-error" role="alert"><%= Html.esc(request.getAttribute("erroRegisto")) %></div>
            <% } %>

            <form action="CadastroController" method="POST">
                <%= Csrf.campo(session) %>
                <div class="form-group">
                    <label>Nome</label>
                    <input type="text" name="nome" required maxlength="100" placeholder="Como quer ser chamado" value="<%= Html.esc(request.getAttribute("nomeForm")) %>">
                </div>
                <div class="form-group">
                    <label>E-mail</label>
                    <input type="email" name="email" required maxlength="100" placeholder="Um e-mail válido" value="<%= Html.esc(request.getAttribute("emailForm")) %>">
                </div>
                <div class="form-group">
                    <label>Senha</label>
                    <input type="password" name="senha" required minlength="8" maxlength="128" placeholder="Mínimo de 8 caracteres">
                </div>
                <button type="submit" class="btn-yellow">Criar conta</button>
            </form>

            <div class="link-rodape">
                Já tem uma conta? <a href="login.jsp">Entrar</a>
            </div>
        </div>
    </div>

</body>
</html>