package com.master7720.decrypter;

public class HexaDecrypter {

    public static String decrypt(String hexaText) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < hexaText.length(); i += 2) {
            final String hexaChunk = hexaText.substring(i, Math.min(i + 2, hexaText.length()));
            char decryptedChar = (char) Integer.parseInt(hexaChunk, 16);
            decryptedText.append(decryptedChar);
        }

        return decryptedText.toString();
    }

    public static String encrypt(String text) {
        return text;
    }
}