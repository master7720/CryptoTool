package com.master7720.decrypter;

public class OctalDecrypter {

    public static String decrypt(String octalText) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < octalText.length(); i += 3) {
            final String octalChunk = octalText.substring(i, Math.min(i + 3, octalText.length()));
            int decimalValue = Integer.parseInt(octalChunk, 8);
            decryptedText.append((char) decimalValue);
        }

        return decryptedText.toString();
    }
}