package com.master7720.commands;

import com.master7720.Main;

public class DecryptCommand {
    public static String execute(String type, String text) {
        try {
            String decryptedText = Main.decrypt(type, text);
            System.out.println("Decrypted: " + decryptedText);
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption.", e);
        }
        return type;
    }
}