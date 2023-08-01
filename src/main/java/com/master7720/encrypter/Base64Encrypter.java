package com.master7720.encrypter;

import java.util.Base64;

public class Base64Encrypter {
    public static String encrypt(String text) {
        byte[] encodedBytes = Base64.getEncoder().encode(text.getBytes());
        return new String(encodedBytes);
    }
}