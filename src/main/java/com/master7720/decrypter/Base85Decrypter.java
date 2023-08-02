package com.master7720.decrypter;

import java.nio.charset.StandardCharsets;

public class Base85Decrypter {

    public static String decrypt(String encryptedText) {
        int length = encryptedText.length();
        if (length % 5 != 0) {
            throw new IllegalArgumentException("Invalid Base85 encoded text length.");
        }

        byte[] decryptedData = new byte[length * 4 / 5];

        // Remove padding bytes if any
        int padding = 0;
        for (int i = length - 1; i >= length - 5; i--) {
            if (encryptedText.charAt(i) == 'z') {
                padding++;
            } else {
                break;
            }
        }

        int dataIndex = 0;
        for (int i = 0; i < length; i += 5) {
            int value = 0;
            for (int j = 0; j < 5; j++) {
                char c = encryptedText.charAt(i + j);
                if (c < 33 || c > 117) {
                    throw new IllegalArgumentException("Invalid character in Base85 encoded text.");
                }
                value = value * 85 + (c - 33);
            }
            for (int j = 3; j >= 0; j--) {
                decryptedData[dataIndex++] = (byte) (value >> (j * 8) & 0xFF);
            }
        }

        // Create resultData by removing padding bytes
        byte[] resultData = new byte[decryptedData.length - padding];
        System.arraycopy(decryptedData, 0, resultData, 0, resultData.length);

        // Convert decrypted bytes to String using UTF-8 encoding
        return new String(resultData, StandardCharsets.UTF_8);
    }
}