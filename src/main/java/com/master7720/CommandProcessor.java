package com.master7720;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.master7720.commands.DecryptCommand;
import com.master7720.commands.EncryptCommand;
import com.master7720.commands.HelpCommand;
import com.master7720.commands.InputCommand;
import com.master7720.commands.OutputCommand;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.master7720.Main.displayHelp;
import static com.master7720.Main.displayUsage;

public class CommandProcessor {

    private static final String CONFIG_FILE = "config.json";

    public static void processCommand(String[] args) {
        if (args.length < 1) {
            displayUsage();
            System.exit(1);
        }

        String command = args[0].toLowerCase();

        switch (command) {
            case "encrypt":
                String encryptionType = Config.getEncryptionType();
                String inputText = getInputText(args);
                String encryptedText = EncryptCommand.execute(encryptionType, inputText);
                addToConfigFile(command, encryptionType, inputText, encryptedText);
                break;

            case "decrypt":
                String decryptionType = Config.getDecryptionType();
                String encryptedTextForDecryption = getInputText(args);
                String decryptedText = DecryptCommand.execute(decryptionType, encryptedTextForDecryption);
                addToConfigFile(command, decryptionType, encryptedTextForDecryption, decryptedText);
                break;

            case "config":
                handleConfigCommand(args);
                break;

            case "help":
                displayHelp();
                break;

            default:
                System.out.println("Invalid command. Use 'encrypt', 'decrypt', 'config', or 'help'.");
                displayUsage();
                System.exit(1);
        }
    }

    private static void handleConfigCommand(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide a config option.");
            displayUsage();
            System.exit(1);
        }

        String configOption = args[1].toLowerCase();

        switch (configOption) {
            case "input":
                if (args.length < 3) {
                    System.out.println("Please provide the input file path.");
                    displayUsage();
                    System.exit(1);
                }
                String inputFile = args[2];
                Config.setInputFile(inputFile);
                System.out.println("Input file path updated successfully.");
                break;

            case "output":
                if (args.length < 3) {
                    System.out.println("Please provide the output file path.");
                    displayUsage();
                    System.exit(1);
                }
                String outputFile = args[2];
                Config.setOutputFile(outputFile);
                System.out.println("Output file path updated successfully.");
                break;

            case "encryption":
                if (args.length < 3) {
                    System.out.println("Please provide the encryption type.");
                    displayUsage();
                    System.exit(1);
                }
                String encryptionType = args[2];
                Config.setEncryptionType(encryptionType);
                System.out.println("Encryption type updated successfully.");
                break;

            case "decryption":
                if (args.length < 3) {
                    System.out.println("Please provide the decryption type.");
                    displayUsage();
                    System.exit(1);
                }
                String decryptionType = args[2];
                Config.setDecryptionType(decryptionType);
                System.out.println("Decryption type updated successfully.");
                break;

            default:
                System.out.println("Invalid config option. Use 'input', 'output', 'encryption', or 'decryption'.");
                displayUsage();
                System.exit(1);
        }
    }

    private static String getInputText(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide the input text.");
            displayUsage();
            System.exit(1);
        }
        return args[1];
    }

    private static void addToConfigFile(String command, String type, String inputText, String outputText) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ConfigData configData = new ConfigData(command, type, inputText, outputText);
            String data = gson.toJson(configData) + "\n";
            Files.write(Paths.get(CONFIG_FILE), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Error while updating the config file.");
            e.printStackTrace();
        }
    }

    private static class ConfigData {

        public ConfigData(String command, String type, String inputText, String outputText) {
        }
    }
}