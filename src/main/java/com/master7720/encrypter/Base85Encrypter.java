package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class Base85Encrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

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

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<String>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, length);
                byte[] chunk = new byte[endIndex - i];
                System.arraycopy(data, i, chunk, 0, chunk.length);
                Future<String> future = executorService.submit(() -> encryptChunk(chunk));
                futures.add(future);
            }

            // Combine the results and concatenate the encoded chunks
            StringBuilder encryptedText = new StringBuilder();
            for (Future<String> future : futures) {
                encryptedText.append(future.get());
            }

            return encryptedText.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private static String encryptChunk(byte[] dataChunk) {
        StringBuilder encryptedText = new StringBuilder();
        int chunkLength = dataChunk.length;

        for (int i = 0; i < chunkLength; i += 4) {
            int value = ((dataChunk[i] & 0xFF) << 24) | ((dataChunk[i + 1] & 0xFF) << 16) | ((dataChunk[i + 2] & 0xFF) << 8) | (dataChunk[i + 3] & 0xFF);
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