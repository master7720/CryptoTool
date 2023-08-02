package com.master7720.decrypter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RSADecrypter {

    private static PrivateKey privateKey;
    private static ExecutorService executorService;

    public RSADecrypter(byte[] privateKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        // Load private key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        privateKey = keyFactory.generatePrivate(keySpec);
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public static String decrypt(String encryptedText) throws Exception {
        return decrypt(encryptedText.getBytes(), null);
    }

    public static String decrypt(byte[] encryptedText, Callback callback) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        int chunkSize = (encryptedText.length + numThreads - 1) / numThreads; // Ceil division

        Future<String>[] futures = new Future[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = Math.min((i + 1) * chunkSize, encryptedText.length);
            byte[] chunk = new byte[endIndex - startIndex];
            System.arraycopy(encryptedText, startIndex, chunk, 0, chunk.length);
            futures[i] = executorService.submit(() -> {
                try {
                    return decryptChunk(chunk);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        StringBuilder decryptedText = new StringBuilder();
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                decryptedText.append(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (callback != null) {
            callback.onComplete();
        }

        return decryptedText.toString();
    }

    private static String decryptChunk(byte[] encryptedChunk) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedChunk);
        return new String(decryptedBytes);
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public interface Callback {
        void onComplete();
    }
}