package com.master7720.encrypter;

public class VigenereEncrypter {
    public static String encrypt(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();
        int keyLength = key.length();
        int textLength = text.length();

        for (int i = 0; i < textLength; i++) {
            char c = text.charAt(i);
            char keyChar = key.charAt(i % keyLength);
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

    public static String encrypt(String s) {
        return s;
    }
}