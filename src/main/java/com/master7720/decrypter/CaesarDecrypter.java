package com.master7720.decrypter;

import com.master7720.encrypter.CaesarEncrypter;

public class CaesarDecrypter {
    public static String decrypt(String text, int shift) {
        return CaesarEncrypter.encrypt(text);
    }
}