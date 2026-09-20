package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.ConexaoDB;

public class AmizadeP {

    public boolean adicionarAmigo(int idUsuarioPrincipal, int idAmigo) 
    {
        String sql = "INSERT INTO amizade (id_usuario1, id_usuario2) VALUES (?, ?)";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) 
        {
            stm.setInt(1, idUsuarioPrincipal);
            stm.setInt(2, idAmigo);
            
            stm.executeUpdate();
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}