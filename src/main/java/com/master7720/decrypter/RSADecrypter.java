package com.master7720.decrypter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSADecrypter {

    private static PrivateKey privateKey;

    public RSADecrypter(byte[] privateKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        // Load private key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        privateKey = keyFactory.generatePrivate(keySpec);
    }

    public static String decrypt(String encryptedText) throws Exception {
        return decrypt(encryptedText.getBytes(), null);
    }

    public static String decrypt(byte[] encryptedText, Callback callback) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedText);
            return new String(decryptedBytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        } finally {
            if (callback != null) {
                callback.onComplete();
            }
        }
    }

    public interface Callback {
        void onComplete();
    }
}