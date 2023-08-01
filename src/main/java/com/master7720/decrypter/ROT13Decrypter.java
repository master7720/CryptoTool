package com.master7720.decrypter;

public class ROT13Decrypter {
    public static String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                int shift = 13;
                ch = (char) (base + (ch - base - shift + 26) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }
}