<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Painel - Finanças Pessoais</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; }
        .header { background: #0288d1; color: white; padding: 20px; border-radius: 8px; display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .header h2, .header h3 { margin: 0; }
        .container { display: flex; gap: 20px; flex-wrap: wrap; }
        .card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); flex: 1; min-width: 300px; }
        input { width: 100%; padding: 8px; margin: 8px 0 15px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;}
        button { width: 100%; padding: 10px; background-color: #388e3c; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        button:hover { background-color: #2e7d32; }
        .success { color: green; background: #e8f5e9; padding: 10px; border-radius: 4px; margin-bottom: 15px; }
        .error { color: red; background: #ffebee; padding: 10px; border-radius: 4px; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="header">
        <h2>Bem-vindo, <%= usuario.getNome() %>!</h2>
        <h3>Saldo Atual: R$ <%= String.format("%.2f", usuario.getQuantiaUsuario()) %></h3>
    </div>
    <% if (request.getParameter("sucesso") != null) { %>
        <div class="success">Operação registrada com sucesso! O seu saldo foi atualizado.</div>
    <% } %>
    <% if (request.getAttribute("mensagemErro") != null) { %>
        <div class="error"><%= request.getAttribute("mensagemErro") %></div>
    <% } %>

    <div class="container">
        <div class="card">
            <h3>Registrar Novo Gasto</h3>
            <form action="GastoController" method="POST">
                <label>Descrição do Gasto:</label>
                <input type="text" name="descricao" required placeholder="Ex: Conta de Luz">
                
                <label>Valor (R$):</label>
                <input type="number" step="0.01" name="valor" required placeholder="0.00">
                
                <label>Data do Gasto:</label>
                <input type="date" name="dataGasto" required>
                
                <button type="submit">Salvar Gasto</button>
            </form>
        </div>

        <div class="card">
            <h3>Criar Meta de Economia</h3>
            <form action="MetaController" method="POST">
                <label>O que deseja alcançar?</label>
                <input type="text" name="descricao" required placeholder="Ex: Viagem para a Praia">
                
                <label>Valor Objetivo (R$):</label>
                <input type="number" step="0.01" name="valorObjetivo" required placeholder="0.00">
                
                <label>Valor Já Poupado (R$):</label>
                <input type="number" step="0.01" name="valorPoupado" value="0.00" required>
                
                <button type="submit" style="background-color: #f57c00;">Salvar Meta</button>
            </form>
        </div>
    </div>
</body>
</html>