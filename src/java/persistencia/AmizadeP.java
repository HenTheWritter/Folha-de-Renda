package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import util.ConexaoDB;

public class AmizadeP {

    /** Lança SQLException com SQLState 23505 se a amizade já existir (índice único do migracao.sql). */
    public void adicionar(int idUsuario, int idAmigo) throws SQLException {
        String sql = "INSERT INTO amizade (id_usuario1, id_usuario2) VALUES (?, ?)";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            stm.setInt(2, idAmigo);
            stm.executeUpdate();
        }
    }

    /** A amizade vale nos dois sentidos: (1,2) e (2,1) são a mesma. */
    public boolean existe(int idA, int idB) throws SQLException {
        String sql = "SELECT 1 FROM amizade WHERE (id_usuario1 = ? AND id_usuario2 = ?) "
                   + "OR (id_usuario1 = ? AND id_usuario2 = ?)";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idA);
            stm.setInt(2, idB);
            stm.setInt(3, idB);
            stm.setInt(4, idA);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** Amigos do usuário (id, nome e e-mail apenas). */
    public List<Usuario> listarAmigos(int idUsuario) throws SQLException {
        String sql = "SELECT u.id, u.nome, u.email FROM amizade a "
                   + "JOIN usuario u ON u.id = CASE WHEN a.id_usuario1 = ? THEN a.id_usuario2 ELSE a.id_usuario1 END "
                   + "WHERE a.id_usuario1 = ? OR a.id_usuario2 = ? ORDER BY u.nome";
        List<Usuario> lista = new ArrayList<>();

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            stm.setInt(2, idUsuario);
            stm.setInt(3, idUsuario);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    lista.add(u);
                }
            }
        }
        return lista;
    }
}
