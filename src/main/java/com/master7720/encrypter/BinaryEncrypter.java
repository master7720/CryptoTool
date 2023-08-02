package com.master7720.encrypter;

public class BinaryEncrypter {
    public static String encrypt(String text) {
        StringBuilder binaryText = new StringBuilder();
        for (char c : text.toCharArray()) {
            String binaryString = Integer.toBinaryString(c);
            while (binaryString.length() < 8) {
                binaryString = "0" + binaryString;
            }
            binaryText.append(binaryString);
        }
        return binaryText.toString();
    }
}