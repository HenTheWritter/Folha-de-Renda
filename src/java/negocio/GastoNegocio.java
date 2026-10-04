package negocio;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import model.Gasto;
import model.Usuario;
import persistencia.GastoP;
import persistencia.UsuarioP;

public class GastoNegocio {

    private static final Logger LOG = Logger.getLogger(GastoNegocio.class.getName());

    private final GastoP gastoP = new GastoP();
    private final UsuarioP usuarioP = new UsuarioP();

    public void registrarNovoGasto(Usuario usuario, Gasto gasto) throws NegocioException {
        gasto.setDescricao(Regras.texto(gasto.getDescricao(), "a descrição do gasto", true));
        gasto.setValor(Regras.dinheiro(gasto.getValor(), "gasto", false));
        if (gasto.getDataGasto() == null) {
            throw new NegocioException("Informe a data do gasto.");
        }
        gasto.setIdUsuario(usuario.getId());

        try {
            if (!gastoP.registrarComDebito(gasto)) {
                throw new NegocioException("Saldo insuficiente para este gasto.");
            }
            usuario.setQuantiaUsuario(usuarioP.buscarSaldo(usuario.getId()));
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }

    public void adicionarRenda(Usuario usuario, BigDecimal valorRenda) throws NegocioException {
        BigDecimal valor = Regras.dinheiro(valorRenda, "renda", false);

        try {
            usuarioP.creditar(usuario.getId(), valor);
            usuario.setQuantiaUsuario(usuarioP.buscarSaldo(usuario.getId()));
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }

    public List<Gasto> listarRecentes(Usuario usuario, int limite) throws NegocioException {
        try {
            return gastoP.listarRecentes(usuario.getId(), limite);
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
}
