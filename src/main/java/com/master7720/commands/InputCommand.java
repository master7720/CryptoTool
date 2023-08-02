package com.master7720.commands;

import com.master7720.Main;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputCommand {
    public static void execute(String type, String filePath) throws Exception {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        String encryptedText = new String(fileBytes, StandardCharsets.UTF_8);

        String decryptedText = Main.decrypt(type, encryptedText);
        System.out.println("Decrypted Input: " + decryptedText);
    }
}