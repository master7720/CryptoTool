package com.master7720.encrypter;

public class ROT13Encrypter {

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        int textLength = text.length();
        StringBuilder encryptedText = new StringBuilder(textLength);

        for (int i = 0; i < textLength; i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                int shift = 13;
                ch = (char) (base + (ch - base + shift) % 26);
            }
            encryptedText.append(ch);
        }

        return encryptedText.toString();
    }
}