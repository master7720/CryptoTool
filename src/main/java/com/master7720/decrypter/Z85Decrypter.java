package com.master7720.decrypter;

public class Z85Decrypter {
    public static String decrypt(String encryptedText) {
        int length = encryptedText.length();
        if (length % 5 != 0) {
            throw new IllegalArgumentException("Invalid Z85 encoded text length.");
        }

        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < length; i += 5) {
            long value = 0;
            for (int j = 0; j < 5; j++) {
                char c = encryptedText.charAt(i + j);
                if (c < 33 || c > 117) {
                    throw new IllegalArgumentException("Invalid character in Z85 encoded text.");
                }
                value = value * 85 + (c - 33);
            }
            for (int j = 0; j < 4; j++) {
                byte b = (byte) (value >> (3 - j) * 8);
                decryptedText.append((char) (b & 0xFF));
            }
        }
        return decryptedText.toString();
    }
}