package com.master7720.decrypter;

import java.util.Base64;

public class base64Decrypter {
    public static String decrypt(String encryptedText) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        return new String(decodedBytes);
    }
}