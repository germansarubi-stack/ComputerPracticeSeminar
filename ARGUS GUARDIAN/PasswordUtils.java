import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtils {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtils() {
        // Clase de utilidad, no instanciar.
    }

    public static String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula.");
        }

        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        try {
            byte[] hash = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
            return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("No se pudo hashear la contraseña.", e);
        } finally {
            spec.clearPassword();
        }
    }

    public static boolean verifyPassword(String password, String storedHash) {
        if (password == null || storedHash == null) {
            return false;
        }

        String[] parts = storedHash.split(":");
        if (parts.length != 3) {
            return false;
        }

        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] hash = Base64.getDecoder().decode(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
        try {
            byte[] testHash = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
            if (testHash.length != hash.length) {
                return false;
            }
            int diff = 0;
            for (int i = 0; i < hash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return false;
        } finally {
            spec.clearPassword();
        }
    }
}
