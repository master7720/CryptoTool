package com.master7720.encrypter;

public class Ascii85Encrypter {
    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        StringBuilder encryptedText = new StringBuilder();
        int count = 0;
        int value = 0;

        for (char c : text.toCharArray()) {
            value = value * 256 + c;
            count++;

            if (count == 4) {
                encodeBlock(encryptedText, value);
                count = 0;
                value = 0;
            }
        }

        if (count > 0) {
            while (count < 4) {
                value = value * 256;
                count++;
            }
            encodeBlock(encryptedText, value);
        }

        encryptedText.append('~'); // Add the delimiter at the end
        return encryptedText.toString();
    }

    private static void encodeBlock(StringBuilder encryptedText, int value) {
        for (int i = 4; i >= 0; i--) {
            int remainder = value % 85;
            value /= 85;
            char c = (char) (remainder + '!');
            encryptedText.insert(0, c);
        }
    }
}