package com.master7720;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Config {
    private static final String CONFIG_FILE = "config.json";

    private static ConfigData configData;

    static {
        try {
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
            Gson gson = new Gson();
            configData = gson.fromJson(content, ConfigData.class);
        } catch (IOException e) {
            System.out.println("Config file not found. Creating a new one...");
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        configData = new ConfigData();
        configData.setInputFile("input.txt");
        configData.setOutputFile("output.txt");
        configData.setEncryptionType("base64");
        configData.setDecryptionType("base64");
        saveConfig();
    }

    public static String getInputFile() {
        return configData.getInputFile();
    }

    public static void setInputFile(String inputFile) {
        configData.setInputFile(inputFile);
        saveConfig();
    }

    public static String getOutputFile() {
        return configData.getOutputFile();
    }

    public static void setOutputFile(String outputFile) {
        configData.setOutputFile(outputFile);
        saveConfig();
    }

    public static String getEncryptionType() {
        return configData.getEncryptionType();
    }

    public static void setEncryptionType(String encryptionType) {
        configData.setEncryptionType(encryptionType);
        saveConfig();
    }

    public static String getDecryptionType() {
        return configData.getDecryptionType();
    }

    public static void setDecryptionType(String decryptionType) {
        configData.setDecryptionType(decryptionType);
        saveConfig();
    }

    private static void saveConfig() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String data = gson.toJson(configData);
            Files.write(Paths.get(CONFIG_FILE), data.getBytes());
        } catch (IOException e) {
            System.out.println("Error while saving the config file.");
            e.printStackTrace();
        }
    }

    private static class ConfigData {
        private String inputFile;
        private String outputFile;
        private String encryptionType;
        private String decryptionType;

        public String getInputFile() {
            return inputFile;
        }

        public void setInputFile(String inputFile) {
            this.inputFile = inputFile;
        }

        public String getOutputFile() {
            return outputFile;
        }

        public void setOutputFile(String outputFile) {
            this.outputFile = outputFile;
        }

        public String getEncryptionType() {
            return encryptionType;
        }

        public void setEncryptionType(String encryptionType) {
            this.encryptionType = encryptionType;
        }

        public String getDecryptionType() {
            return decryptionType;
        }

        public void setDecryptionType(String decryptionType) {
            this.decryptionType = decryptionType;
        }
    }
}