package com.master7720;

import java.util.Base64;

public class Encrypter {
    public static String encrypt(String text) {
        byte[] encodedBytes = Base64.getEncoder().encode(text.getBytes());
        return new String(encodedBytes);
    }
}