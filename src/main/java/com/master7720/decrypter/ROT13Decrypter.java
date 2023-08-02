package com.master7720.decrypter;

public class ROT13Decrypter {

    public static String decrypt(String text) {
        StringBuilder decryptedText = new StringBuilder(text.length());

        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                int shift = 13;
                ch = (char) (base + (ch - base - shift + 26) % 26);
            }
            decryptedText.append(ch);
        }

        return decryptedText.toString();
    }
}