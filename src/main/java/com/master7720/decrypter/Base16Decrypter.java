package com.master7720.decrypter;

public class Base16Decrypter {
    public static String decrypt(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedText.length() - 1; i += 2) {
            String hexValue = encryptedText.substring(i, i + 2);
            char decryptedChar = (char) Integer.parseInt(hexValue, 16); // Convert hexadecimal to char
            decryptedText.append(decryptedChar);
        }
        return decryptedText.toString();
    }
}