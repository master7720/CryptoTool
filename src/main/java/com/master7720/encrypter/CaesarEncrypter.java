package com.master7720.encrypter;

public class CaesarEncrypter {
    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                int shift = 0;
                ch = (char) (base + (ch - base + shift) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }
}