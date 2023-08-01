package com.master7720.decrypter;

public class QuotedPrintableDecrypter {
    public static String decrypt(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();
        int i = 0;
        while (i < encryptedText.length()) {
            char c = encryptedText.charAt(i);
            if (c == '=' && i + 2 < encryptedText.length()) {
                char firstDigit = encryptedText.charAt(i + 1);
                char secondDigit = encryptedText.charAt(i + 2);
                int asciiValue = hexToDecimal(firstDigit) * 16 + hexToDecimal(secondDigit);
                decryptedText.append((char) asciiValue);
                i += 3;
            } else {
                decryptedText.append(c);
                i++;
            }
        }
        return decryptedText.toString();
    }

    private static int hexToDecimal(char digit) {
        if (digit >= '0' && digit <= '9') {
            return digit - '0';
        } else if (digit >= 'A' && digit <= 'F') {
            return digit - 'A' + 10;
        } else if (digit >= 'a' && digit <= 'f') {
            return digit - 'a' + 10;
        }
        throw new IllegalArgumentException("Invalid hexadecimal digit: " + digit);
    }
}