package com.master7720;

import com.master7720.decrypter.*;
import com.master7720.encrypter.*;

public class Main {
    public static void main(String[] args) throws Exception {
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

    public static String encrypt(String type, String text) throws Exception {
        switch (type) {
            case EncryptionType.BASE64:
                return Base64Encrypter.encrypt(text);
            case EncryptionType.ROT13:
                return ROT13Encrypter.encrypt(text);
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
            case EncryptionType.AES:
                return AESEncrypter.encrypt(text);
            case EncryptionType.RSA:
                return RSAEncrypter.encrypt(text);
            case EncryptionType.DES:
                return DESEncrypter.encrypt(text);
            case EncryptionType.VIGENERE:
                return VigenereEncrypter.encrypt(text, "SECRET_KEY");
            default:
                throw new IllegalArgumentException("Invalid decryption type. Use 'base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des");
        }
    }

    public static String decrypt(String type, String encryptedText) throws Exception {
        switch (type) {
            case EncryptionType.BASE64:
                return Base64Decrypter.decrypt(encryptedText);
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
            case EncryptionType.AES:
                return AESDecrypter.decrypt(encryptedText);
            case EncryptionType.RSA:
                return RSADecrypter.decrypt(encryptedText);
            case EncryptionType.DES:
                return DESDecrypter.decrypt(encryptedText);
            case EncryptionType.VIGENERE:
                return VigenereDecrypter.decrypt(encryptedText, "SECRET_KEY");
            default:
                throw new IllegalArgumentException("Invalid decryption type. Use 'base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des");
        }
    }

    public static void displayUsage() {
        System.out.println("Usage: java -jar <jar-file-name>.jar <command> <type> <text>");
        System.out.println("Commands: encrypt, decrypt, help");
        System.out.println("Types: base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des");
    }

    public static void displayHelp() {
        displayUsage();
        System.out.println("\nDescription:");
        System.out.println("The EncryptionDecryption program allows you to encrypt or decrypt text using different encryption types.");
        System.out.println("\nExamples:");
        System.out.println("java -jar <jar-file-name>.jar encrypt base64 'Hello, World!'");
        System.out.println("java -jar <jar-file-name>.jar decrypt base64 'Khoor, Zruog!'");
    }

    private static class EncryptionType {
        public static final String BASE64 = "base64";
        public static final String BASE16 = "base16";
        public static final String BASE85 = "base85";
        public static final String QUOTED_PRINTABLE = "quoted_printable";
        public static final String ASCII85 = "ascii85";
        public static final String Z85 = "z85";
        public static final String ROT13 = "rot13";
        public static final String VIGENERE = "vigenere";
        public static final String AES = "aes";
        public static final String RSA = "rsa";
        public static final String DES = "des";
    }
}