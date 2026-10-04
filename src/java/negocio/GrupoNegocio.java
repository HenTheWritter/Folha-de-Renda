package negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import model.Grupo;
import model.Usuario;
import persistencia.GrupoP;

public class GrupoNegocio {

    private static final Logger LOG = Logger.getLogger(GrupoNegocio.class.getName());

    private final GrupoP grupoP = new GrupoP();

    /** Cria o grupo e adiciona o criador como primeiro membro. */
    public void criarGrupo(Grupo grupo, Usuario criador) throws NegocioException {
        grupo.setNome(Regras.texto(grupo.getNome(), "o nome do grupo", true));
        grupo.setDescricao(Regras.texto(grupo.getDescricao(), "a descrição do grupo", false));

        try {
            grupoP.criarComMembro(grupo, criador.getId());
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }

    public List<Grupo> listar(Usuario usuario) throws NegocioException {
        try {
            return grupoP.listarPorUsuario(usuario.getId());
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
}
