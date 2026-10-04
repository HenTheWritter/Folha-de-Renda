package util;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/** Formatação de valores para exibição (padrão brasileiro). */
public final class Formato {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private Formato() {}

    /** 1234.5 -> "R$ 1.234,50" */
    public static String moeda(BigDecimal valor) {
        if (valor == null) {
            valor = BigDecimal.ZERO;
        }
        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(PT_BR));
        return "R$ " + df.format(valor);
    }

    /** java.sql.Date -> "31/12/2026" */
    public static String data(Date data) {
        if (data == null) {
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }

    /** Percentual inteiro entre 0 e 100 (para barras de progresso). */
    public static int percentual(BigDecimal parte, BigDecimal total) {
        if (parte == null || total == null || total.signum() <= 0) {
            return 0;
        }
        BigDecimal pct = parte.multiply(BigDecimal.valueOf(100)).divide(total, 0, java.math.RoundingMode.DOWN);
        return Math.max(0, Math.min(100, pct.intValue()));
    }
}
