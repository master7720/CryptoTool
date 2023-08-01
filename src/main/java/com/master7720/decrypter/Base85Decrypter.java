package com.master7720.decrypter;

import jdk.internal.dynalink.support.NameCodec;
import java.util.Arrays;

public class Base85Decrypter {
    public static String decrypt(String encryptedText) {
        byte[] encryptedBytes = encryptedText.getBytes();
        NameCodec Base85;
        byte[] decryptedBytes = NameCodec.decode(Arrays.toString(encryptedBytes)).getBytes();
        return new String(decryptedBytes);
    }
}