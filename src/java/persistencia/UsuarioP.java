package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Usuario;
import util.ConexaoDB;

public class UsuarioP {
    
    public boolean salvar(Usuario usuario) 
    {
        String sql = "INSERT INTO usuario (nome, email, senha, quantiaUsuario) VALUES (?, ?, ?, ?)";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) 
        {
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getEmail());
            stm.setString(3, usuario.getSenha());
            stm.setInt(4, usuario.getQuantiaUsuario());
            
            stm.executeUpdate();
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
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
                    usuario.setQuantiaUsuario(rs.getInt("quantiaUsuario")); // <-- ADICIONADO AQUI
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