package com.master7720.encrypter;

public class QuotedPrintableEncrypter {
    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        StringBuilder encryptedText = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int asciiValue = c;

            if ((asciiValue < 33 || asciiValue > 126 || c == '=') && !Character.isWhitespace(c)) {
                encryptedText.append('=');
                encryptedText.append(String.format("%02X", asciiValue));
            } else {
                encryptedText.append(c);
            }
        }

        return encryptedText.toString();
    }
}