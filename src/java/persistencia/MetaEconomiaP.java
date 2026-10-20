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
    
    public void atualizar(MetaEconomia meta) throws SQLException {
        String sql = "UPDATE meta_economia SET valorPoupado = ?, valorObjetivo = ? WHERE id = ? AND id_usuario = ?";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setBigDecimal(1, meta.getValorPoupado());
            stm.setBigDecimal(2, meta.getValorObjetivo());
            stm.setInt(3, meta.getId());
            stm.setInt(4, meta.getIdUsuario());
            
            stm.executeUpdate();
        }
    }
    
    public List<MetaEconomia> buscarPorDescricao(int idUsuario, String termo) throws SQLException {
        String sql = "SELECT id, id_usuario, descricao, valorObjetivo, valorPoupado "
                   + "FROM meta_economia WHERE id_usuario = ? AND descricao ILIKE ? ORDER BY id DESC";
        List<MetaEconomia> lista = new ArrayList<>();

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            stm.setString(2, "%" + termo + "%");
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
    
    public void excluir(int id, int idUsuario) throws SQLException {
        String sql = "DELETE FROM meta_economia WHERE id = ? AND id_usuario = ?";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.setInt(2, idUsuario);
            stm.executeUpdate();
        }
    }
}