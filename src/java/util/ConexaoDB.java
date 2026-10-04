package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Abre conexões com o PostgreSQL.
 *
 * As credenciais NÃO ficam no código. Ordem de busca (a primeira que existir vale):
 *   1. variáveis de ambiente  DB_URL, DB_USER, DB_PASS
 *   2. propriedades da JVM    -DDB_URL=... -DDB_USER=... -DDB_PASS=...
 *   3. arquivo db.properties no classpath (copie db.properties.example e renomeie)
 */
public class ConexaoDB {

    private static final String URL_PADRAO = "jdbc:postgresql://localhost:5432/bdueg202601";
    private static final String USER_PADRAO = "postgres";

    private static final Properties ARQUIVO = carregarArquivo();

    private static Properties carregarArquivo() {
        Properties p = new Properties();
        try (InputStream in = ConexaoDB.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                p.load(in);
            }
        } catch (Exception e) {
            // sem arquivo: segue com variáveis de ambiente / propriedades da JVM
        }
        return p;
    }

    private static String config(String chave, String padrao) {
        String valor = System.getenv(chave);
        if (valor == null || valor.isEmpty()) {
            valor = System.getProperty(chave);
        }
        if (valor == null || valor.isEmpty()) {
            valor = ARQUIVO.getProperty(chave);
        }
        return (valor == null || valor.isEmpty()) ? padrao : valor;
    }

    public static Connection conectar() throws SQLException {
        String senha = config("DB_PASS", null);
        if (senha == null) {
            throw new SQLException("Senha do banco não configurada. Defina DB_PASS "
                    + "(variável de ambiente, -DDB_PASS ou db.properties).");
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado no classpath.", e);
        }
        return DriverManager.getConnection(
                config("DB_URL", URL_PADRAO), config("DB_USER", USER_PADRAO), senha);
    }
}
