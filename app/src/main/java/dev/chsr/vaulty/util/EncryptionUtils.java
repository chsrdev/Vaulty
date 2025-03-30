package dev.chsr.vaulty.util;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class EncryptionUtils {
    private static SecretKey getSecretKey() throws GeneralSecurityException, IOException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (keyStore.containsAlias("MasterKey"))
            return (SecretKey) keyStore.getKey("MasterKey", null);
        else {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder("MasterKey",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build());
            return keyGenerator.generateKey();
        }
    }

    public static String decrypt(String encrypted, String IV)
            throws GeneralSecurityException, IOException {
        SecretKey secretKey = getSecretKey();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        byte[] IVBytes = Base64.getDecoder().decode(IV);
        byte[] encryptedBytes = Base64.getDecoder().decode(encrypted);

        GCMParameterSpec spec = new GCMParameterSpec(128, IVBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

        byte[] decryptedData = cipher.doFinal(encryptedBytes);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public static String[] encrypt(String decrypted)
            throws GeneralSecurityException, IOException {
        SecretKey secretKey = getSecretKey();
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        String IV = Base64.getEncoder().encodeToString(cipher.getIV());
        String encryptedData = Base64.getEncoder().encodeToString(
                cipher.doFinal(decrypted.getBytes(StandardCharsets.UTF_8))
        );
        return new String[]{encryptedData, IV};
    }

    public static String generatePassword() {
        StringBuilder pwd = new StringBuilder();
        Random rnd = new Random();
        int length = rnd.nextInt(12) + 12;
        for (int i = 0; i < length; i++) {
            pwd.append((char) (rnd.nextInt(126 - 33) + 33));
        }
        return pwd.toString();
    }

    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
