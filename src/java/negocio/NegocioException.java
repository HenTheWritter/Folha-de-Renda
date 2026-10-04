package negocio;

/**
 * Erro cuja mensagem é segura para mostrar ao usuário (regra de negócio violada,
 * dado inválido etc.). Qualquer outra exceção deve ser registrada no log e
 * mostrada como mensagem genérica.
 */
public class NegocioException extends Exception {
    private static final long serialVersionUID = 1L;

    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
