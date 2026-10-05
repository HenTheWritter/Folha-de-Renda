<%@page import="util.Html"%>
<%@page import="util.Csrf"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("usuarioLogado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String idMeta = request.getParameter("id");
    if (idMeta == null || idMeta.trim().isEmpty()) {
        response.sendRedirect("PainelController");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Editar Meta - Folha de Renda</title>
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        body { background-color: #121212; color: #e0e0e0; }

        .navbar { background-color: #0a0a0a; display: flex; align-items: center; justify-content: space-between; padding: 0 30px; height: 70px; border-bottom: 3px solid transparent; border-image: linear-gradient(90deg, #4CAF50, #FFC107) 1; }
        .nav-logo { color: #4CAF50; font-size: 24px; font-weight: bold; letter-spacing: 2px; text-transform: uppercase; text-decoration: none; text-shadow: 0 0 8px rgba(76, 175, 80, 0.3); }

        .container { display: flex; justify-content: center; align-items: center; height: calc(100vh - 70px); padding: 20px; }
        
        .form-box { background-color: #1a1a1a; padding: 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); width: 100%; max-width: 400px; border-top: 2px solid #FFC107; }
        .form-box h2 { color: #fff; margin-bottom: 25px; font-size: 22px; text-align: center; font-weight: 400; }
        
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #aaa; font-weight: bold; font-size: 14px; }
        .form-group input { width: 100%; padding: 12px; border: 1px solid #333; border-radius: 4px; background-color: #0f0f0f; color: #fff; font-size: 14px; transition: border-color 0.3s, box-shadow 0.3s; }
        .form-group input:focus { outline: none; border-color: #FFC107; box-shadow: 0 0 5px rgba(255, 193, 7, 0.3); }

        .btn-yellow { width: 100%; padding: 12px; border: none; border-radius: 4px; font-size: 16px; font-weight: bold; cursor: pointer; background-color: #FFC107; color: #121212; transition: 0.3s; margin-top: 10px; }
        .btn-yellow:hover { background-color: #FFA000; box-shadow: 0 0 8px rgba(255, 193, 7, 0.4); }

        .btn-cancelar { display: block; width: 100%; text-align: center; padding: 12px; margin-top: 10px; font-size: 14px; color: #aaa; text-decoration: none; transition: 0.3s; }
        .btn-cancelar:hover { color: #ef4444; }
    </style>
</head>
<body>

    <nav class="navbar">
        <a href="PainelController" class="nav-logo">Folha de Renda</a>
    </nav>

    <div class="container">
        <div class="form-box">
            <h2>Atualizar Meta</h2>
            
            <form action="EditarMetaController" method="POST">
                <%= Csrf.campo(session) %>
                
                <input type="hidden" name="id" value="<%= Html.esc(idMeta) %>">
                
                <div class="form-group">
                    <label>Novo Valor Já Poupado (R$)</label>
                    <input type="number" name="valorPoupado" step="0.01" min="0" required placeholder="Ex: 150.50">
                </div>
                
                <div class="form-group">
                    <label>Ajustar Valor do Objetivo (R$)</label>
                    <input type="number" name="valorObjetivo" step="0.01" min="0.01" required placeholder="Novo total ou repita o antigo">
                </div>
                
                <button type="submit" class="btn-yellow">Guardar Alterações</button>
                <a href="PainelController" class="btn-cancelar">Cancelar e voltar</a>
            </form>
        </div>
    </div>

</body>
</html>