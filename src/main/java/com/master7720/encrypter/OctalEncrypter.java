package com.master7720.encrypter;

public class OctalEncrypter {
    public static String encrypt(String text) {
        StringBuilder octalText = new StringBuilder();
        for (char c : text.toCharArray()) {
            octalText.append(Integer.toOctalString((int) c));
        }
        return octalText.toString();
    }
}