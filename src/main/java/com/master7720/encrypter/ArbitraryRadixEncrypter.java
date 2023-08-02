package com.master7720.encrypter;

public class ArbitraryRadixEncrypter {
    private static int radix;

    public ArbitraryRadixEncrypter(int radix) {
        this.radix = radix;
    }

    public static String encrypt(String text) {
        StringBuilder encryptedTextBuilder = new StringBuilder();
        for (char c : text.toCharArray()) {
            String encryptedChunk = Integer.toString(c, radix);
            encryptedTextBuilder.append(encryptedChunk);
        }
        return encryptedTextBuilder.toString();
    }
}