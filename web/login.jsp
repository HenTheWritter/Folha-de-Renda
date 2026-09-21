<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema Financeiro</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-box { background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 320px; }
        .login-box h2 { text-align: center; color: #333; margin-top: 0; }
        input { width: 100%; padding: 10px; margin: 10px 0 20px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background-color: #0288d1; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background-color: #02669c; }
        .error { color: #d32f2f; font-size: 0.9em; margin-bottom: 15px; text-align: center; }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>Logar na Conta</h2>
        
        <% if (request.getAttribute("mensagemErro") != null) { %>
            <div class="error"><%= request.getAttribute("mensagemErro") %></div>
        <% } %>
        
        <form action="UsuarioController" method="POST">
            <label>E-mail:</label>
            <input type="email" name="email" placeholder="INSIRA EMAIL:" required>
            
            <label>Palavra-passe:</label>
            <input type="password" name="senha" placeholder="INSIRA SENHA:" required>
            
            <button type="submit">Entrar</button>
        </form>
    </div>
</body>
</html>