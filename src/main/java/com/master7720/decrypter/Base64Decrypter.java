package com.master7720.decrypter;

import java.util.Base64;

public class Base64Decrypter {
    public static String decrypt(String encryptedText) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 encoded text: " + e.getMessage());
        }
    }
}