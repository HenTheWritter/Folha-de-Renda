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

    public void criarGrupoComMembros(Grupo grupo, Usuario criador, String[] amigosIds) throws NegocioException {
        grupo.setNome(Regras.texto(grupo.getNome(), "o nome do grupo", true));
        grupo.setDescricao(Regras.texto(grupo.getDescricao(), "a descrição do grupo", false));

        try {
            grupoP.criarComMembro(grupo, criador.getId(), amigosIds);
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
    
    public void excluir(int idGrupo, Usuario usuario) throws NegocioException {
        try {
            grupoP.excluir(idGrupo, usuario.getId());
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
}