package persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;
import util.ConexaoDB;

public class UsuarioP {

    private static final String COLUNAS = "id, nome, email, senha, quantiaUsuario";

    /** Insere o usuário. Lança SQLException com SQLState 23505 se o e-mail já existir. */
    public void salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, quantiaUsuario) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getEmail());
            stm.setString(3, usuario.getSenha());
            stm.setBigDecimal(4, usuario.getQuantiaUsuario());
            stm.executeUpdate();
        }
    }

    /** Busca por e-mail (sem diferenciar maiúsculas). Retorna null se não existir. */
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM usuario WHERE LOWER(email) = LOWER(?)";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /** Saldo atual direto do banco (a cópia da sessão pode estar desatualizada). */
    public BigDecimal buscarSaldo(int idUsuario) throws SQLException {
        String sql = "SELECT quantiaUsuario FROM usuario WHERE id = ?";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
            }
        }
    }

    /** Soma o valor ao saldo de forma atômica (feita pelo banco, sem ler-e-gravar). */
    public void creditar(int idUsuario, BigDecimal valor) throws SQLException {
        String sql = "UPDATE usuario SET quantiaUsuario = quantiaUsuario + ? WHERE id = ?";

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setBigDecimal(1, valor);
            stm.setInt(2, idUsuario);
            stm.executeUpdate();
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));
        u.setQuantiaUsuario(rs.getBigDecimal("quantiaUsuario"));
        return u;
    }
}
