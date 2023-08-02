package com.master7720.decrypter;

public class VigenereDecrypter {
    public static String decrypt(String encryptedText, String key) {
        int textLength = encryptedText.length();
        StringBuilder decryptedText = new StringBuilder(textLength);
        int keyLength = key.length();

        for (int i = 0; i < textLength; i++) {
            char c = encryptedText.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            decryptedText.append(decryptChar(c, keyChar));
        }

        return decryptedText.toString();
    }

    private static char decryptChar(char c, char keyChar) {
        if (Character.isLetter(c)) {
            char base = Character.isLowerCase(c) ? 'a' : 'A';
            int shift = keyChar - 'a';
            c = (char) (base + (c - base - shift + 26) % 26);
        }
        return c;
    }
}