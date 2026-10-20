package negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import model.MetaGrupo;
import persistencia.MetaGrupoP;

public class MetaGrupoNegocio {

    private static final Logger LOG = Logger.getLogger(MetaGrupoNegocio.class.getName());
    
    private final MetaGrupoP metaP = new MetaGrupoP();

    public void salvar(MetaGrupo meta) throws NegocioException {
        if (meta.getDescricao() == null || meta.getDescricao().trim().isEmpty()) {
            throw new NegocioException("A descrição da meta é obrigatória.");
        }
        if (meta.getValorObjetivo() == null || meta.getValorObjetivo().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new NegocioException("O valor objetivo deve ser maior que zero.");
        }
        if (meta.getValorPoupado() == null || meta.getValorPoupado().compareTo(java.math.BigDecimal.ZERO) < 0) {
            meta.setValorPoupado(java.math.BigDecimal.ZERO); 
        }
        
        try {
            metaP.salvar(meta); 
        } catch (java.sql.SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }

    public List<MetaGrupo> listar(int idGrupo) throws NegocioException {
        try {
            return metaP.listarPorGrupo(idGrupo);
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
    
    public void atualizar(MetaGrupo meta) throws Exception {
        if (meta.getValorPoupado().compareTo(java.math.BigDecimal.ZERO) < 0 || 
            meta.getValorObjetivo().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new Exception("Os valores informados não são válidos.");
        }
        
        try {
            metaP.atualizar(meta);
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
    
    public void excluir(int id, int idGrupo) throws NegocioException {
        try {
            metaP.excluir(id, idGrupo);
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
}