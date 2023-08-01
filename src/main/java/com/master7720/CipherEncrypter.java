package com.master7720;

public class CipherEncrypter {
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) (base + (ch - base + shift) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }
}