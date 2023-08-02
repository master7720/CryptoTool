package com.master7720.encrypter;

public class HexaEncrypter {
    public static String encrypt(String text) {
        StringBuilder hexaText = new StringBuilder();
        for (char c : text.toCharArray()) {
            hexaText.append(String.format("%02X", (int) c));
        }
        return hexaText.toString();
    }
}