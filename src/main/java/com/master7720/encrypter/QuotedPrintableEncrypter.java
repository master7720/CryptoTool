package com.master7720.encrypter;

public class QuotedPrintableEncrypter  {
    public static String encrypt(String text) {
        StringBuilder encryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            int asciiValue = (int) c;
            if (asciiValue < 33 || asciiValue > 126 || c == '=') {
                encryptedText.append('=');
                encryptedText.append(String.format("%02X", asciiValue)); // Convert char to two-digit hexadecimal value
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }
}