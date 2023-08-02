package com.master7720.commands;

import com.master7720.Main;

public class EncryptCommand {
    public static String execute(String type, String text) {
        try {
            String encryptedText = Main.encrypt(type, text);
            System.out.println("Encrypted: " + encryptedText);
        } catch (Exception e) {
            throw new RuntimeException("Error during encryption.", e);
        }
        return type;
    }
}