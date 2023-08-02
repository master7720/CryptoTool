package com.master7720.encrypter;

import java.security.*;
import javax.crypto.*;
import java.util.Base64;

public class RSAEncrypter {

    private static PublicKey publicKey;
    private static final Object lock = new Object();

    public RSAEncrypter() {
        // No need to generate the RSA key pair here, we will do it lazily when needed
    }

    private static void generateKeyPairIfNeeded() {
        if (publicKey == null) {
            synchronized (lock) {
                if (publicKey == null) {
                    try {
                        // Generate RSA key pair only if not already generated
                        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
                        keyPairGenerator.initialize(2048, secureRandom);
                        KeyPair keyPair = keyPairGenerator.generateKeyPair();
                        publicKey = keyPair.getPublic();
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException("Failed to generate RSA key pair. RSA algorithm or provider not available.", e);
                    }
                }
            }
        }
    }

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        generateKeyPairIfNeeded();

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Encryption failed. Unable to perform RSA encryption.", e);
        }
    }
}