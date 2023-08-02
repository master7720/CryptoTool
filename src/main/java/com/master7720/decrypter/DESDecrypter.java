package com.master7720.decrypter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class DESDecrypter {

    private static SecretKey secretKey;

    public DESDecrypter(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public static String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}