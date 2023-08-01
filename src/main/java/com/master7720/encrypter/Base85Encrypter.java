package com.master7720.encrypter;

import jdk.internal.dynalink.support.NameCodec;

import java.util.Arrays;

public class Base85Encrypter {
    public static String encrypt(String text) {
        byte[] bytesToEncrypt = text.getBytes();
        NameCodec Base85 = null;
        byte[] encryptedBytes = NameCodec.encode(Arrays.toString(bytesToEncrypt)).getBytes();
        return new String(encryptedBytes);
    }
}