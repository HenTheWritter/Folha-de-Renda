package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Hash de senha com PBKDF2-HMAC-SHA256 e salt aleatório (somente JDK, sem bibliotecas extras).
 * Formato salvo no banco:  pbkdf2$<iterações>$<salt em base64>$<hash em base64>
 */
public final class Senha {

    private static final String PREFIXO = "pbkdf2";
    private static final int ITERACOES = 120_000;
    private static final int TAM_SALT = 16;
    private static final int TAM_HASH_BITS = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private Senha() {}

    public static String gerarHash(String senha) {
        byte[] salt = new byte[TAM_SALT];
        RANDOM.nextBytes(salt);
        byte[] hash = pbkdf2(senha.toCharArray(), salt, ITERACOES);
        Base64.Encoder b64 = Base64.getEncoder();
        return PREFIXO + "$" + ITERACOES + "$" + b64.encodeToString(salt) + "$" + b64.encodeToString(hash);
    }

    public static boolean confere(String senhaDigitada, String armazenado) {
        if (senhaDigitada == null || armazenado == null) {
            return false;
        }
        String[] partes = armazenado.split("\\$");
        if (partes.length != 4 || !PREFIXO.equals(partes[0])) {
            return false; // formato desconhecido (ex.: senha antiga em texto puro)
        }
        try {
            int iteracoes = Integer.parseInt(partes[1]);
            byte[] salt = Base64.getDecoder().decode(partes[2]);
            byte[] esperado = Base64.getDecoder().decode(partes[3]);
            byte[] calculado = pbkdf2(senhaDigitada.toCharArray(), salt, iteracoes);
            return MessageDigest.isEqual(esperado, calculado); // comparação em tempo constante
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] pbkdf2(char[] senha, byte[] salt, int iteracoes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(senha, salt, iteracoes, TAM_HASH_BITS);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException("PBKDF2 indisponível nesta JVM.", e);
        }
    }
}
