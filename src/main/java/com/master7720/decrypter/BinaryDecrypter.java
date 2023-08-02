package com.master7720.decrypter;

public class BinaryDecrypter {

    public static String decrypt(String binaryText) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < binaryText.length(); i += 8) {
            final String binaryChunk = binaryText.substring(i, Math.min(i + 8, binaryText.length()));
            int decimalValue = Integer.parseInt(binaryChunk, 2);
            decryptedText.append((char) decimalValue);
        }

        return decryptedText.toString();
    }
}