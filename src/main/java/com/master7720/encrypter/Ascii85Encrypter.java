package com.master7720.encrypter;

public class Ascii85Encrypter {
    private static final int CHUNK_SIZE = 100;

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        StringBuilder encryptedText = new StringBuilder();
        int textLength = text.length();

        for (int i = 0; i < textLength; i += CHUNK_SIZE) {
            int endIndex = Math.min(i + CHUNK_SIZE, textLength);
            String chunk = text.substring(i, endIndex);
            String encryptedChunk = encryptChunk(chunk);
            encryptedText.append(encryptedChunk);
        }

        return encryptedText.toString();
    }

    private static String encryptChunk(String chunk) {
        StringBuilder encryptedChunk = new StringBuilder();
        int count = 0;
        int value = 0;

        for (char c : chunk.toCharArray()) {
            value = value * 256 + c;
            count++;

            if (count == 4) {
                encodeBlock(encryptedChunk, value);
                count = 0;
                value = 0;
            }
        }

        while (count < 4) {
            value = value * 256;
            count++;
        }
        encodeBlock(encryptedChunk, value);

        return encryptedChunk.toString();
    }

    private static void encodeBlock(StringBuilder encryptedChunk, int value) {
        for (int i = 4; i >= 0; i--) {
            int remainder = value % 85;
            value /= 85;
            char c = (char) (remainder + '!');
            encryptedChunk.insert(0, c);
        }
    }
}