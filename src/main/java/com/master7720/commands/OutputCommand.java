package com.master7720.commands;

import com.master7720.Main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutputCommand {
    public static void execute(String type, String text, String filePath) throws Exception {
        String outputText = Main.encrypt(type, text);

        File outputFile = new File(filePath);
        Files.write(Paths.get(outputFile.toURI()), outputText.getBytes());

        System.out.println("Output saved to: " + filePath);
    }
}