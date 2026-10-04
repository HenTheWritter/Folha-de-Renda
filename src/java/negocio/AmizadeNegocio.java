package negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import model.Usuario;
import persistencia.AmizadeP;
import persistencia.UsuarioP;

public class AmizadeNegocio {

    private static final Logger LOG = Logger.getLogger(AmizadeNegocio.class.getName());

    private final AmizadeP amizadeP = new AmizadeP();
    private final UsuarioP usuarioP = new UsuarioP();

    public void adicionarPorEmail(Usuario usuario, String emailAmigo) throws NegocioException {
        String email = Regras.texto(emailAmigo, "o e-mail do amigo", true);

        try {
            Usuario amigo = usuarioP.buscarPorEmail(email);
            if (amigo == null) {
                throw new NegocioException("Nenhum usuário encontrado com esse e-mail.");
            }
            if (amigo.getId() == usuario.getId()) {
                throw new NegocioException("Você não pode adicionar a si mesmo como amigo.");
            }
            if (amizadeP.existe(usuario.getId(), amigo.getId())) {
                throw new NegocioException("Vocês já são amigos.");
            }
            amizadeP.adicionar(usuario.getId(), amigo.getId());
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) { // duas requisições ao mesmo tempo
                throw new NegocioException("Vocês já são amigos.");
            }
            throw Regras.erroBanco(LOG, e);
        }
    }

    public List<Usuario> listar(Usuario usuario) throws NegocioException {
        try {
            return amizadeP.listarAmigos(usuario.getId());
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
}
