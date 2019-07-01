package inc.sebek.securityservice.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

@Component
public class PBKDF2Encoder implements PasswordEncoder {
    @Value("${spring.password.encoder.secret}")
    private String secret;

    @Value("${spring.password.encoder.iteration}")
    private Integer iteration;

    @Value("${spring.password.encoder.keylength}")
    private Integer keylength;

    @SneakyThrows
    @Override
    public String encode(CharSequence rawPassword) {
        var encodedPassword = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                                              .generateSecret(new PBEKeySpec(rawPassword.toString().toCharArray(),
                                                                             secret.getBytes(), iteration, keylength))
                                              .getEncoded();
        return Base64.getEncoder().encodeToString(encodedPassword);


    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
