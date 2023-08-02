package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class Z85Encrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int DEFAULT_CHUNK_SIZE = 1000;

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

        int chunkSize = Math.max(length / NUM_THREADS, DEFAULT_CHUNK_SIZE);
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<String>> futures = new ArrayList<>();

        try {
            StringBuilder encryptedText = new StringBuilder(length + (length / 4) + 1);

            for (int i = 0; i < length; i += chunkSize) {
                int endIndex = Math.min(i + chunkSize, length);
                byte[] chunk = new byte[endIndex - i];
                System.arraycopy(data, i, chunk, 0, chunk.length);
                Future<String> future = executorService.submit(() -> encryptChunk(chunk));
                futures.add(future);
            }

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
        StringBuilder encryptedChunk = new StringBuilder(dataChunk.length + (dataChunk.length / 4) + 1);

        for (int i = 0; i < dataChunk.length; i += 4) {
            long value = ((dataChunk[i] & 0xFFL) << 24) | ((dataChunk[i + 1] & 0xFFL) << 16) | ((dataChunk[i + 2] & 0xFFL) << 8) | (dataChunk[i + 3] & 0xFFL);
            for (int j = 4; j >= 0; j--) {
                int index = (int) (value % 85);
                encryptedChunk.append((char) (index + 33));
                value /= 85;
            }
        }

        return encryptedChunk.toString();
    }
}