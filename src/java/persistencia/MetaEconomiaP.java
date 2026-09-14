package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.MetaEconomia;
import util.ConexaoDB;

public class MetaEconomiaP {

    public boolean salvar(MetaEconomia meta) 
    {
        String sql = "INSERT INTO metas_economia (id_usuario, descricao, valor_objetivo, valor_poupado) VALUES (?, ?, ?, ?)";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) 
        {
            stm.setInt(1, meta.getIdUsuario());
            stm.setString(2, meta.getDescricao());
            stm.setDouble(3, meta.getValorObjetivo());
            stm.setDouble(4, meta.getValorPoupado());
            
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