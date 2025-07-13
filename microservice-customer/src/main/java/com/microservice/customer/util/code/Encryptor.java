package com.microservice.customer.util.code;

import com.microservice.customer.exception.ApiErrorEnum;
import com.microservice.customer.exception.CustomerException;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Encryptor {

    static Logger LOGGER = LoggerFactory.getLogger(Encryptor.class);

    private static final String SECRET_KEY = "1234567890123456";

    private static final String ALGORITHM = "AES";

    public static String encrypt(String input) {
        LOGGER.info("Starting encryption process.");
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(input.getBytes());
            String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(encrypted);
            LOGGER.info("Encryption completed successfully.");
            return encoded;
        } catch (Exception e) {
            LOGGER.error("An error occurred during encryption: {}", e.getMessage(), e);
            throw new CustomerException(ApiErrorEnum.ENCRYPTING_ERROR);
        }
    }

    public static String decrypt(String encrypted) {
        LOGGER.info("Starting decryption process {}", encrypted);
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = Base64.getUrlDecoder().decode(encrypted);
            String decrypted = new String(cipher.doFinal(decoded));
            LOGGER.info("Decryption completed successfully.");
            return decrypted;
        } catch (Exception e) {
            LOGGER.error("An error occurred during decryption: {}", e.getMessage(), e);
            throw new CustomerException(ApiErrorEnum.ENCRYPTING_ERROR);
        }
    }
}
