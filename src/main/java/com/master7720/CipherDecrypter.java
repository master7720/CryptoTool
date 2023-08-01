package com.master7720;

public class CipherDecrypter {
    public static String decrypt(String text, int shift) {
        return CipherEncrypter.encrypt(text, 26 - shift);
    }
}