package com.master7720;

import com.master7720.decrypter.*;
import com.master7720.encrypter.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            displayUsage();
            System.exit(1);
        }

        String command = args[0].toLowerCase();
        if (command.equals("help")) {
            displayHelp();
            System.exit(0);
        }

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
                displayUsage();
                System.exit(1);
        }
    }

    public static String encrypt(String type, String text) {
        switch (type) {
            case EncryptionType.BASE64:
                return Base64Encrypter.encrypt(text);
            case EncryptionType.CAESAR:
                return CaesarEncrypter.encrypt(text);
            case EncryptionType.BASE16:
                return Base16Encrypter.encrypt(text);
            case EncryptionType.BASE85:
                return Base85Encrypter.encrypt(text);
            case EncryptionType.QUOTED_PRINTABLE:
                return QuotedPrintableEncrypter.encrypt(text);
            case EncryptionType.ASCII85:
                return Ascii85Encrypter.encrypt(text);
            case EncryptionType.Z85:
                return Z85Encrypter.encrypt(text);
            default:
                throw new IllegalArgumentException("Invalid decryption type. Use 'base64', 'caesar', 'base16', 'base85', 'quoted_printable', 'ascii85', 'z85'.");
        }
    }

    public static String decrypt(String type, String encryptedText) {
        switch (type) {
            case EncryptionType.BASE64:
                return base64Decrypter.decrypt(encryptedText);
            case EncryptionType.CAESAR:
                return CaesarDecrypter.decrypt(encryptedText, 3);
            case EncryptionType.BASE16:
                return Base16Decrypter.decrypt(encryptedText);
            case EncryptionType.BASE85:
                return Base85Decrypter.decrypt(encryptedText);
            case EncryptionType.QUOTED_PRINTABLE:
                return QuotedPrintableDecrypter.decrypt(encryptedText);
            case EncryptionType.ASCII85:
                return Ascii85Decrypter.decrypt(encryptedText);
            case EncryptionType.Z85:
                return Z85Decrypter.decrypt(encryptedText);
            default:
                throw new IllegalArgumentException("Invalid decryption type. Use 'base64', 'caesar', 'base16', 'base85', 'quoted_printable', 'ascii85', 'z85'.");
        }
    }

    public static void displayUsage() {
        System.out.println("Usage: java -jar EncryptionDecryption.jar <command> <type> <text>");
        System.out.println("Commands: encrypt, decrypt, help");
        System.out.println("Types: base64, caesar, base16, base85, quoted_printable, ascii85, z85");
    }

    public static void displayHelp() {
        displayUsage();
        System.out.println("\nDescription:");
        System.out.println("The EncryptionDecryption program allows you to encrypt or decrypt text using different encryption types.");
        System.out.println("\nExamples:");
        System.out.println("java -jar EncryptionDecryption.jar encrypt base64 'Hello, World!'");//TODO: need to add the rest of this shit for the help command
        System.out.println("java -jar EncryptionDecryption.jar decrypt caesar 'Khoor, Zruog!'");
    }

    private static class EncryptionType {
        public static final String BASE64 = "base64";
        public static final String CAESAR = "caesar";
        public static final String BASE16 = "base16";
        public static final String BASE85 = "base85";
        public static final String QUOTED_PRINTABLE = "quoted_printable";
        public static final String ASCII85 = "ascii85";
        public static final String Z85 = "z85";

    }
}