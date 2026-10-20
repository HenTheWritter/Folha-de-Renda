package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MetaGrupo;
import util.ConexaoDB;

public class MetaGrupoP {

    public void salvar(MetaGrupo meta) throws SQLException {
        String sql = "INSERT INTO meta_grupo (id_grupo, descricao, valor_objetivo, valor_poupado) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, meta.getIdGrupo());
            stm.setString(2, meta.getDescricao());
            stm.setBigDecimal(3, meta.getValorObjetivo());
            stm.setBigDecimal(4, meta.getValorPoupado());
            stm.executeUpdate();
        }
    }

    public List<MetaGrupo> listarPorGrupo(int idGrupo) throws SQLException {
        String sql = "SELECT id, id_grupo, descricao, valor_objetivo, valor_poupado "
                   + "FROM meta_grupo WHERE id_grupo = ? ORDER BY id DESC";
        List<MetaGrupo> lista = new ArrayList<>();

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idGrupo);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    MetaGrupo m = new MetaGrupo();
                    m.setId(rs.getInt("id"));
                    m.setIdGrupo(rs.getInt("id_grupo"));
                    m.setDescricao(rs.getString("descricao"));
                    m.setValorObjetivo(rs.getBigDecimal("valor_objetivo"));
                    m.setValorPoupado(rs.getBigDecimal("valor_poupado"));
                    lista.add(m);
                }
            }
        }
        return lista;
    }

    public void atualizar(MetaGrupo meta) throws SQLException {
        String sql = "UPDATE meta_grupo SET valor_poupado = ?, valor_objetivo = ? WHERE id = ? AND id_grupo = ?";
        
        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setBigDecimal(1, meta.getValorPoupado());
            stm.setBigDecimal(2, meta.getValorObjetivo());
            stm.setInt(3, meta.getId());
            stm.setInt(4, meta.getIdGrupo());
            
            stm.executeUpdate();
        }
    }
    
    public void excluir(int id, int idGrupo) throws SQLException {
        String sql = "DELETE FROM meta_grupo WHERE id = ? AND id_grupo = ?";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.setInt(2, idGrupo);
            stm.executeUpdate();
        }
    }
}