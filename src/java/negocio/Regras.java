package negocio;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Validações e utilidades compartilhadas pela camada de negócio. */
final class Regras {

    static final int MAX_TEXTO = 100;
    /** Maior valor que cabe em DECIMAL(10,2). */
    static final BigDecimal VALOR_MAXIMO = new BigDecimal("99999999.99");

    private Regras() {}

    /** Remove espaços e valida tamanho. Texto opcional vazio vira "". */
    static String texto(String valor, String campo, boolean obrigatorio) throws NegocioException {
        String s = valor == null ? "" : valor.trim();
        if (obrigatorio && s.isEmpty()) {
            throw new NegocioException("Informe " + campo + ".");
        }
        if (s.length() > MAX_TEXTO) {
            throw new NegocioException("O campo " + campo + " aceita no máximo " + MAX_TEXTO + " caracteres.");
        }
        return s;
    }

    /** Valida um valor em dinheiro: positivo (ou zero, se permitido), até 2 casas, dentro do limite do banco. */
    static BigDecimal dinheiro(BigDecimal valor, String campo, boolean aceitaZero) throws NegocioException {
        if (valor == null) {
            throw new NegocioException("Informe " + campo + ".");
        }
        if (valor.signum() < 0 || (valor.signum() == 0 && !aceitaZero)) {
            throw new NegocioException(
                    aceitaZero ? "O valor de " + campo + " não pode ser negativo."
                               : "O valor de " + campo + " deve ser maior que zero.");
        }
        if (valor.stripTrailingZeros().scale() > 2) {
            throw new NegocioException("O valor de " + campo + " deve ter no máximo 2 casas decimais.");
        }
        if (valor.compareTo(VALOR_MAXIMO) > 0) {
            throw new NegocioException("O valor de " + campo + " é grande demais (máximo R$ 99.999.999,99).");
        }
        return valor.setScale(2);
    }

    /** Registra o erro técnico no log e devolve uma mensagem genérica para o usuário. */
    static NegocioException erroBanco(Logger log, SQLException e) {
        if ("22003".equals(e.getSQLState())) { // numeric value out of range
            return new NegocioException("O valor ultrapassa o limite permitido (R$ 99.999.999,99).");
        }
        log.log(Level.SEVERE, "Erro de banco de dados (SQLState " + e.getSQLState() + ")", e);
        return new NegocioException("Não foi possível concluir a operação agora. Tente novamente em instantes.");
    }
}
