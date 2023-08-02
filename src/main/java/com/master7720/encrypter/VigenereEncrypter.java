package com.master7720.encrypter;

public class VigenereEncrypter {
    public static String encrypt(String text, String key) {
        if (text == null || text.isEmpty() || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Input text and key cannot be null or empty.");
        }

        int textLength = text.length();
        StringBuilder encryptedText = new StringBuilder(textLength);

        for (int i = 0; i < textLength; i++) {
            char c = text.charAt(i);
            char keyChar = key.charAt(i % key.length());
            encryptedText.append(encryptChar(c, keyChar));
        }

        return encryptedText.toString();
    }

    private static char encryptChar(char c, char keyChar) {
        if (Character.isLetter(c)) {
            char base = Character.isLowerCase(c) ? 'a' : 'A';
            int shift = keyChar - 'a';
            c = (char) (base + (c - base + shift) % 26);
        }
        return c;
    }
}