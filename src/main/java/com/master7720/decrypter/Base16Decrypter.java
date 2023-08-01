package com.master7720.decrypter;

public class Base16Decrypter {
    public static String decrypt(String encryptedText) {
        if (encryptedText.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid Base16 encoded text length.");
        }

        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedText.length() - 1; i += 2) {
            String hexValue = encryptedText.substring(i, i + 2);
            try {
                char decryptedChar = (char) Integer.parseInt(hexValue, 16);
                decryptedText.append(decryptedChar);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid character in Base16 encoded text: " + e.getMessage());
            }
        }
        return decryptedText.toString();
    }
}