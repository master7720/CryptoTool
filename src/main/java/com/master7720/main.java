package com.master7720;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -jar Base64EncryptDecrypt.jar <command> <type> <text>");
            System.out.println("Commands: encrypt, decrypt");
            System.out.println("Types: base64, caesar");
            System.exit(1);
        }

        String command = args[0].toLowerCase();
        String type = args[1].toLowerCase();
        String text = args[2];

        switch (command) {
            case "encrypt":
                String encryptedText = encrypt(type, text);
                System.out.println("Encrypted: " + encryptedText);
                break;
            case "decrypt":
                String decryptedText = decrypt(type, text);
                System.out.println("Decrypted: " + decryptedText);
                break;
            default:
                System.out.println("Invalid command. Use 'encrypt' or 'decrypt'.");
                break;
        }
    }

    public static String encrypt(String type, String text) {
        switch (type) {
            case "base64":
                return Encrypter.encrypt(text);
            case "caesar":
                return CipherEncrypter.encrypt(text, 3);
            default:
                System.out.println("Invalid encryption type. Use 'base64' or 'caesar'.");
                System.exit(1);
                return null; 
        }
    }

    public static String decrypt(String type, String encryptedText) {
        switch (type) {
            case "base64":
                return Decrypter.decrypt(encryptedText);
            case "caesar":
                return CipherDecrypter.decrypt(encryptedText, 3);
            default:
                System.out.println("Invalid encryption type. Use 'base64' or 'caesar'.");
                System.exit(1);
                return null; 
        }
    }
}