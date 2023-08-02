package com.master7720.decrypter;

public class Z85Decrypter {
    public static String decrypt(String encryptedText) {
        int length = encryptedText.length();
        if (length % 5 != 0) {
            throw new IllegalArgumentException("Invalid Z85 encoded text length.");
        }

        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < length; i += 5) {
            String chunk = encryptedText.substring(i, i + 5);
            decryptedText.append(decryptChunk(chunk));
        }

        return decryptedText.toString();
    }

    private static String decryptChunk(String chunk) {
        StringBuilder decryptedChunk = new StringBuilder(chunk.length());
        long value = 0;

        for (int j = 0; j < 5; j++) {
            char c = chunk.charAt(j);
            int code = c - 33;
            if (code < 0 || code > 84) {
                throw new IllegalArgumentException("Invalid character in Z85 encoded text.");
            }
            value = value * 85 + code;
        }

        for (int j = 0; j < 4; j++) {
            byte b = (byte) (value >> (3 - j) * 8);
            decryptedChunk.append((char) (b & 0xFF));
        }

        return decryptedChunk.toString();
    }
}