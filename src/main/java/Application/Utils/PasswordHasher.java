package Application.Utils;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class PasswordHasher {

    public static String PasswordHash(String password) {
        String salt = BCrypt.gensalt();
        Objects.requireNonNull(salt, "Salt cannot be null");
        return BCrypt.hashpw(password, salt);
    }

    public static Boolean PasswordVerify(String password, String hashedPassword) {
        String salt = hashedPassword.substring(0, 29);
        Objects.requireNonNull(salt, "Salt cannot be null");
        return BCrypt.checkpw(password, hashedPassword);
    }
}
