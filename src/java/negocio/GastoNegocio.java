package negocio;

import model.Gasto;
import model.Usuario;
import persistencia.GastoP;
import persistencia.UsuarioP;

public class GastoNegocio {
    
    private GastoP gastoP = new GastoP();
    private UsuarioP usuarioP = new UsuarioP();

    public boolean registrarNovoGasto(Usuario usuario, Gasto gasto) throws Exception 
    {
        if (gasto.getValor() <= 0) 
        {
            throw new Exception("O valor do gasto deve ser maior que zero.");
        }
        
        if (usuario.getQuantiaUsuario() < gasto.getValor()) 
        {
            throw new Exception("Saldo insuficiente para este gasto.");
        }

        boolean gastoSalvo = gastoP.salvar(gasto);
        
        if (gastoSalvo) 
        {
            double novoSaldo = usuario.getQuantiaUsuario() - (int) gasto.getValor();
            
            usuario.setQuantiaUsuario(novoSaldo);
            
            return usuarioP.atualizarQuantia(usuario.getId(), novoSaldo);
        }
        
        return false;
    }
    
    public boolean adicionarRenda(Usuario usuario, int valorRenda) throws Exception 
    {
        if (valorRenda <= 0) 
        {
            throw new Exception("A renda adicionada deve ser maior que zero.");
        }
        
        double novoSaldo = usuario.getQuantiaUsuario() + valorRenda;
        
        usuario.setQuantiaUsuario(novoSaldo);
        
        return usuarioP.atualizarQuantia(usuario.getId(), novoSaldo);
    }
}