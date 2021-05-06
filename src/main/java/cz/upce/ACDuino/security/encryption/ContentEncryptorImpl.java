package cz.upce.ACDuino.security.encryption;

import cz.upce.ACDuino.AcDuinoApplication;
import cz.upce.ACDuino.config.AesSecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class ContentEncryptorImpl implements ContentEncryptor {

    private static final Logger logger = LoggerFactory.getLogger(AcDuinoApplication.class);
    private static final int IV_SIZE = 16;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private final AesSecurityProperties properties;

    public ContentEncryptorImpl(AesSecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getKey() {
        try {
            return new String(getKeyFromPassword().getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Error in aes key generation.", e);
            return null;
        }
    }

    @Override
    public byte[] encrypt(String input) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec iv = generateIv();
        cipher.init(Cipher.ENCRYPT_MODE, getKeyFromPassword(), iv);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(iv.getIV());

        try (final CipherOutputStream cos = new CipherOutputStream(baos, cipher)) {
            cos.write(input.getBytes(StandardCharsets.UTF_8));
        }
        return Base64.getEncoder().encode(baos.toByteArray());
    }

    @Override
    public byte[] decrypt(String encrypted) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(
                Base64.getDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)));

        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        final IvParameterSpec iv = readIV(bais);
        cipher.init(Cipher.DECRYPT_MODE, getKeyFromPassword(), iv);

        final byte[] buf = new byte[1_024];
        try (final CipherInputStream cis = new CipherInputStream(bais, cipher);
             final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int read;
            while ((read = cis.read(buf)) != -1) {
                baos.write(buf, 0, read);
            }
            return baos.toByteArray();
        }
    }

    private SecretKey getKeyFromPassword()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(properties.getPassword().toCharArray(),
                properties.getSalt().getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }

    private IvParameterSpec generateIv() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    private IvParameterSpec readIV(final InputStream is) throws IOException {
        final byte[] iv = new byte[IV_SIZE];
        int offset = 0;
        while (offset < IV_SIZE) {
            final int read = is.read(iv, offset, IV_SIZE - offset);
            if (read == -1) {
                throw new IOException("Too few bytes for IV in input stream");
            }
            offset += read;
        }
        return new IvParameterSpec(iv);
    }
}
