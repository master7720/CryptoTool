package com.master7720.decrypter;

public class Ascii85Decrypter {

    public static String decrypt(String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();

        int value = 0;
        int count = 0;

        for (char c : encryptedText.toCharArray()) {
            if (c == '~') {
                break; // Stop at the delimiter
            }

            if (c == 'z') {
                decryptedText.append('\0');
                count++;
            } else if (c >= '!' && c <= 'u') {
                value = value * 85 + (c - '!');
                count++;

                if (count == 5) {
                    decodeBlock(decryptedText, value);
                    count = 0;
                    value = 0;
                }
            } else {
                throw new IllegalArgumentException("Invalid character in Ascii85 encoded text.");
            }
        }

        return decryptedText.toString();
    }

    private static void decodeBlock(StringBuilder decryptedText, int value) {
        for (int i = 3; i >= 0; i--) {
            char c = (char) (value % 256);
            value /= 256;
            decryptedText.insert(0, c);
        }
    }
}