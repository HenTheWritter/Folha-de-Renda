package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Grupo;
import util.ConexaoDB;

public class GrupoP {

    /**
     * Cria o grupo e já vincula o criador como membro, numa única transação
     * (antes o grupo era criado sem nenhum membro).
     *
     * @return id do grupo criado
     */
    public int criarComMembro(Grupo grupo, int idCriador) throws SQLException {
        String insGrupo = "INSERT INTO grupo (nome, descricao) VALUES (?, ?) RETURNING id";

        try (Connection con = ConexaoDB.conectar()) {
            con.setAutoCommit(false);
            try {
                int idGrupo;
                try (PreparedStatement stm = con.prepareStatement(insGrupo)) {
                    stm.setString(1, grupo.getNome());
                    stm.setString(2, grupo.getDescricao());
                    try (ResultSet rs = stm.executeQuery()) {
                        rs.next();
                        idGrupo = rs.getInt(1);
                    }
                }
                vincularUsuario(con, idGrupo, idCriador);
                con.commit();
                return idGrupo;
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public void vincularUsuario(int idGrupo, int idUsuario) throws SQLException {
        try (Connection con = ConexaoDB.conectar()) {
            vincularUsuario(con, idGrupo, idUsuario);
        }
    }

    private void vincularUsuario(Connection con, int idGrupo, int idUsuario) throws SQLException {
        String sql = "INSERT INTO grupo_usuario (id_grupo, id_usuario) VALUES (?, ?)";
        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idGrupo);
            stm.setInt(2, idUsuario);
            stm.executeUpdate();
        }
    }

    public List<Grupo> listarPorUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT g.id, g.nome, g.descricao FROM grupo g "
                   + "JOIN grupo_usuario gu ON gu.id_grupo = g.id "
                   + "WHERE gu.id_usuario = ? ORDER BY g.nome";
        List<Grupo> lista = new ArrayList<>();

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Grupo g = new Grupo();
                    g.setId(rs.getInt("id"));
                    g.setNome(rs.getString("nome"));
                    g.setDescricao(rs.getString("descricao"));
                    lista.add(g);
                }
            }
        }
        return lista;
    }
}
