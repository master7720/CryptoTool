package com.master7720.encrypter;

public class Base85Encrypter {

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        byte[] data = text.getBytes();
        int length = data.length;

        // Pad the input data to a multiple of 4 bytes if needed
        int remainder = length % 4;
        if (remainder != 0) {
            byte[] paddedData = new byte[length + 4 - remainder];
            System.arraycopy(data, 0, paddedData, 0, length);
            data = paddedData;
            length = data.length;
        }

        StringBuilder encryptedText = new StringBuilder();
        int chunkLength = data.length;

        for (int i = 0; i < chunkLength; i += 4) {
            int value = ((data[i] & 0xFF) << 24) | ((data[i + 1] & 0xFF) << 16) | ((data[i + 2] & 0xFF) << 8) | (data[i + 3] & 0xFF);
            for (int j = 4; j >= 0; j--) {
                int index = value % 85;
                encryptedText.append(base85Character(index));
                value /= 85;
            }
        }

        return encryptedText.toString();
    }

    private static char base85Character(int value) {
        return (char) (value + 33);
    }
}