package com.master7720.encrypter;

public class Z85Encrypter {

    public static String encrypt(String text) {
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

        StringBuilder encryptedText = new StringBuilder(length + (length / 4) + 1);

        for (int i = 0; i < length; i += 4) {
            byte[] chunk = new byte[4];
            System.arraycopy(data, i, chunk, 0, 4);
            String chunkResult = encryptChunk(chunk);
            encryptedText.append(chunkResult);
        }

        return encryptedText.toString();
    }

    private static String encryptChunk(byte[] dataChunk) {
        StringBuilder encryptedChunk = new StringBuilder(dataChunk.length + (dataChunk.length / 4) + 1);

        long value = ((dataChunk[0] & 0xFFL) << 24) | ((dataChunk[1] & 0xFFL) << 16) | ((dataChunk[2] & 0xFFL) << 8) | (dataChunk[3] & 0xFFL);
        for (int j = 4; j >= 0; j--) {
            int index = (int) (value % 85);
            encryptedChunk.append((char) (index + 33));
            value /= 85;
        }

        return encryptedChunk.toString();
    }
}