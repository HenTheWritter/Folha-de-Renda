package negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import model.MetaEconomia;
import model.Usuario;
import persistencia.MetaEconomiaP;

public class MetaEconomiaNegocio {

    private static final Logger LOG = Logger.getLogger(MetaEconomiaNegocio.class.getName());

    private final MetaEconomiaP metaP = new MetaEconomiaP();

    public void salvarMeta(MetaEconomia meta) throws NegocioException {
        meta.setDescricao(Regras.texto(meta.getDescricao(), "a descrição da meta", true));
        meta.setValorObjetivo(Regras.dinheiro(meta.getValorObjetivo(), "objetivo", false));
        meta.setValorPoupado(Regras.dinheiro(meta.getValorPoupado(), "valor poupado", true));

        try {
            metaP.salvar(meta);
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }

    public List<MetaEconomia> listar(Usuario usuario) throws NegocioException {
        try {
            return metaP.listarPorUsuario(usuario.getId());
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
    
    public void atualizar(model.MetaEconomia meta) throws Exception {
        if (meta.getValorPoupado().compareTo(java.math.BigDecimal.ZERO) < 0 || 
            meta.getValorObjetivo().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new Exception("Os valores informados não são válidos.");
        }
        
        persistencia.MetaEconomiaP persistencia = new persistencia.MetaEconomiaP();
        persistencia.atualizar(meta);
    }
}
