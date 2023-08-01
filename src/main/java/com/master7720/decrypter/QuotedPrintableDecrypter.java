package com.master7720.decrypter;

public class QuotedPrintableDecrypter {
    public static String decrypt(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();
        int i = 0;
        while (i < encryptedText.length()) {
            char c = encryptedText.charAt(i);
            if (c == '=' && i + 2 < encryptedText.length()) {
                String hexValue = encryptedText.substring(i + 1, i + 3);
                int asciiValue = Integer.parseInt(hexValue, 16); // Convert hexadecimal to int
                decryptedText.append((char) asciiValue);
                i += 3;
            } else {
                decryptedText.append(c);
                i++;
            }
        }
        return decryptedText.toString();
    }
}