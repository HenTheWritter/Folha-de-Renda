package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Gasto;
import util.ConexaoDB;

public class GastoP {

    public boolean salvar(Gasto gasto) 
    {
        String sql = "INSERT INTO gasto (id_usuario, descricao, valor, data_gasto) VALUES (?, ?, ?, ?)";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) 
        {
            stm.setInt(1, gasto.getIdUsuario());
            stm.setString(2, gasto.getDescricao());
            stm.setDouble(3, gasto.getValor());
            stm.setDate(4, gasto.getDataGasto());
            
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