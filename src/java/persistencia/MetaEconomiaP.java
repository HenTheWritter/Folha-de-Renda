package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MetaEconomia;
import util.ConexaoDB;

public class MetaEconomiaP {

    public void salvar(MetaEconomia meta) throws SQLException {
        String sql = "INSERT INTO meta_economia (id_usuario, descricao, valorObjetivo, valorPoupado) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, meta.getIdUsuario());
            stm.setString(2, meta.getDescricao());
            stm.setBigDecimal(3, meta.getValorObjetivo());
            stm.setBigDecimal(4, meta.getValorPoupado());
            stm.executeUpdate();
        }
    }

    public List<MetaEconomia> listarPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT id, id_usuario, descricao, valorObjetivo, valorPoupado "
                   + "FROM meta_economia WHERE id_usuario = ? ORDER BY id DESC";
        List<MetaEconomia> lista = new ArrayList<>();

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    MetaEconomia m = new MetaEconomia();
                    m.setId(rs.getInt("id"));
                    m.setIdUsuario(rs.getInt("id_usuario"));
                    m.setDescricao(rs.getString("descricao"));
                    m.setValorObjetivo(rs.getBigDecimal("valorObjetivo"));
                    m.setValorPoupado(rs.getBigDecimal("valorPoupado"));
                    lista.add(m);
                }
            }
        }
        return lista;
    }
}
