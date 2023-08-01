package com.master7720.encrypter;

public class Z85Encrypter {
    public static String encrypt(String text) {
        byte[] data = text.getBytes();
        int length = data.length;

        // Pad the input data to a multiple of 4 bytes
        int remainder = length % 4;
        if (remainder != 0) {
            byte[] paddedData = new byte[length + 4 - remainder];
            System.arraycopy(data, 0, paddedData, 0, length);
            data = paddedData;
            length = data.length;
        }

        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < length; i += 4) {
            long value = ((data[i] & 0xFFL) << 24) | ((data[i + 1] & 0xFFL) << 16) | ((data[i + 2] & 0xFFL) << 8) | (data[i + 3] & 0xFFL);
            for (int j = 4; j >= 0; j--) {
                int index = (int) (value % 85);
                encryptedText.append((char) (index + 33));
                value /= 85;
            }
        }
        return encryptedText.toString();
    }
}