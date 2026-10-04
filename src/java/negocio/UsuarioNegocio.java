package negocio;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import model.Usuario;
import persistencia.UsuarioP;
import util.Senha;

public class UsuarioNegocio {

    private static final Logger LOG = Logger.getLogger(UsuarioNegocio.class.getName());
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final int SENHA_MIN = 8;
    private static final int SENHA_MAX = 128;
    /** Usado para gastar o mesmo tempo quando o e-mail não existe (evita descobrir e-mails cadastrados pelo tempo de resposta). */
    private static final String HASH_FALSO = Senha.gerarHash("senha-falsa-para-igualar-tempo");

    private final UsuarioP usuarioP = new UsuarioP();

    public Usuario realizarLogin(String email, String senha) throws NegocioException {
        if (email == null || email.trim().isEmpty()) {
            throw new NegocioException("O e-mail não pode ser vazio.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new NegocioException("A senha não pode ser vazia.");
        }

        try {
            Usuario usuario = usuarioP.buscarPorEmail(email.trim());
            boolean ok = Senha.confere(senha, usuario != null ? usuario.getSenha() : HASH_FALSO);

            if (usuario == null || !ok) {
                throw new NegocioException("E-mail ou senha inválidos.");
            }
            usuario.setSenha(null); // o hash não precisa ficar na sessão
            return usuario;
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }

    /** Recebe a senha em texto puro em {@code usuario.getSenha()} e grava somente o hash. */
    public void cadastrarUsuario(Usuario usuario) throws NegocioException {
        String nome = Regras.texto(usuario.getNome(), "o nome", true);
        String email = Regras.texto(usuario.getEmail(), "o e-mail", true).toLowerCase(Locale.ROOT);
        String senha = usuario.getSenha();

        if (!EMAIL.matcher(email).matches()) {
            throw new NegocioException("Informe um e-mail válido.");
        }
        if (senha == null || senha.length() < SENHA_MIN) {
            throw new NegocioException("A senha deve ter pelo menos " + SENHA_MIN + " caracteres.");
        }
        if (senha.length() > SENHA_MAX) {
            throw new NegocioException("A senha deve ter no máximo " + SENHA_MAX + " caracteres.");
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(Senha.gerarHash(senha));
        usuario.setQuantiaUsuario(BigDecimal.ZERO);

        try {
            usuarioP.salvar(usuario);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new NegocioException("Este e-mail já está cadastrado. Tente fazer login.");
            }
            throw Regras.erroBanco(LOG, e);
        }
    }

    /** Atualiza o saldo do objeto da sessão com o valor real do banco. */
    public void atualizarSaldo(Usuario usuario) throws NegocioException {
        try {
            usuario.setQuantiaUsuario(usuarioP.buscarSaldo(usuario.getId()));
        } catch (SQLException e) {
            throw Regras.erroBanco(LOG, e);
        }
    }
}
