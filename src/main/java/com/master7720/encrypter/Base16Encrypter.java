package com.master7720.encrypter;

public class Base16Encrypter {

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        StringBuilder encryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            String hexValue = String.format("%02x", (int) c); // Convert char to two-digit hexadecimal value
            encryptedText.append(hexValue);
        }

        return encryptedText.toString();
    }
}