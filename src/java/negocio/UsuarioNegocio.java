package negocio;

import model.Usuario;
import persistencia.UsuarioP;

public class UsuarioNegocio {
    private UsuarioP usuarioP = new UsuarioP();

    public Usuario realizarLogin(String email, String senha) throws Exception 
    {
        if (email == null || email.trim().isEmpty()) 
        {
            throw new Exception("O e-mail não pode ser vazio.");
        }
        
        if (senha == null || senha.trim().isEmpty()) 
        {
            throw new Exception("A senha não pode ser vazia.");
        }

        Usuario usuario = usuarioP.autenticar(email, senha);
        
        if (usuario == null) 
        {
            throw new Exception("E-mail ou senha inválidos.");
        }
        
        return usuario;
    }
}