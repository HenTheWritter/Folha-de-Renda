package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Gasto;
import util.ConexaoDB;

public class GastoP {

    /**
     * Debita o saldo e grava o gasto na MESMA transação.
     * O débito só acontece se houver saldo suficiente (checado pelo próprio UPDATE),
     * então duas requisições simultâneas não conseguem gastar o mesmo dinheiro.
     *
     * @return false se o saldo era insuficiente (nada é gravado)
     */
    public boolean registrarComDebito(Gasto gasto) throws SQLException {
        String debito = "UPDATE usuario SET quantiaUsuario = quantiaUsuario - ? "
                      + "WHERE id = ? AND quantiaUsuario >= ?";
        String insercao = "INSERT INTO gasto (id_usuario, descricao, valor, data_gasto) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexaoDB.conectar()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement up = con.prepareStatement(debito)) {
                    up.setBigDecimal(1, gasto.getValor());
                    up.setInt(2, gasto.getIdUsuario());
                    up.setBigDecimal(3, gasto.getValor());
                    if (up.executeUpdate() == 0) {
                        con.rollback();
                        return false;
                    }
                }
                try (PreparedStatement ins = con.prepareStatement(insercao)) {
                    ins.setInt(1, gasto.getIdUsuario());
                    ins.setString(2, gasto.getDescricao());
                    ins.setBigDecimal(3, gasto.getValor());
                    ins.setDate(4, gasto.getDataGasto());
                    ins.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<Gasto> listarRecentes(int idUsuario, int limite) throws SQLException {
        String sql = "SELECT id, id_usuario, descricao, valor, data_gasto FROM gasto "
                   + "WHERE id_usuario = ? ORDER BY data_gasto DESC, id DESC LIMIT ?";
        List<Gasto> lista = new ArrayList<>();

        try (Connection con = ConexaoDB.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, idUsuario);
            stm.setInt(2, limite);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Gasto g = new Gasto();
                    g.setId(rs.getInt("id"));
                    g.setIdUsuario(rs.getInt("id_usuario"));
                    g.setDescricao(rs.getString("descricao"));
                    g.setValor(rs.getBigDecimal("valor"));
                    g.setDataGasto(rs.getDate("data_gasto"));
                    lista.add(g);
                }
            }
        }
        return lista;
    }
}
