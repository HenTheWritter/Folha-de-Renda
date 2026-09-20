package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Grupo;
import util.ConexaoDB;

public class GrupoP {

    public boolean salvar(Grupo grupo) 
    {
        String sql = "INSERT INTO grupo (nome, descricao) VALUES (?, ?)";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) 
        {
            stm.setString(1, grupo.getNome());
            stm.setString(2, grupo.getDescricao());
            
            stm.executeUpdate();
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean vincularUsuario(int idGrupo, int idUsuario) 
    {
        String sql = "INSERT INTO grupo_usuario (id_grupo, id_usuario) VALUES (?, ?)";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) 
        {
            stm.setInt(1, idGrupo);
            stm.setInt(2, idUsuario);
            
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