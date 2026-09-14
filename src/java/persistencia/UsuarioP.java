package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Usuario;
import util.ConexaoDB;

public class UsuarioP {
    
    public Usuario autenticar(String email, String senha) 
    {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        Usuario usuario = null;

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) 
        {
            stm.setString(1, email);
            stm.setString(2, senha);
            
            try (ResultSet rs = stm.executeQuery()) 
            {
                if (rs.next()) 
                {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setQuantiaUsuario(rs.getInt("quantiaUsuario"));
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return usuario;
    }
}