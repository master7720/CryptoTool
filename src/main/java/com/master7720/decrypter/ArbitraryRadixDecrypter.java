package com.master7720.decrypter;

import java.util.StringJoiner;

public class ArbitraryRadixDecrypter {
    private static int radix;

    public ArbitraryRadixDecrypter(int radix) {
        this.radix = radix;
    }

    public static String decrypt(String encryptedText) {
        int textLength = encryptedText.length();
        StringJoiner decryptedTextJoiner = new StringJoiner("");

        for (int i = 0; i < textLength; i++) {
            final char encryptedChar = encryptedText.charAt(i);
            int decimalValue = Integer.parseInt(Character.toString(encryptedChar), radix);
            decryptedTextJoiner.add(String.valueOf((char) decimalValue));
        }

        return decryptedTextJoiner.toString();
    }
}