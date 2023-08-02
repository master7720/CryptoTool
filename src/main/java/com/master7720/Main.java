package com.master7720;

import com.master7720.decrypter.*;
import com.master7720.encrypter.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ExecutorService executorService = Executors.newFixedThreadPool(2); // Create a thread pool with 2 threads

        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));

        switch (command) {
            case "encrypt":
                executorService.execute(() -> {
                    try {
                        String encryptedText = encrypt(type, text);
                        System.out.println("Encrypted: " + encryptedText);
                    } catch (Exception e) {
                        throw new RuntimeException("Error during encryption.", e);
                    }
                });
                break;

            case "decrypt":
                executorService.execute(() -> {
                    try {
                        String decryptedText = decrypt(type, text);
                        System.out.println("Decrypted: " + decryptedText);
                    } catch (Exception e) {
                        throw new RuntimeException("Error during decryption.", e);
                    }
                });
                break;

            default:
                System.out.println("Invalid command. Use 'encrypt' or 'decrypt'.");
                displayUsage();
                System.exit(1);
        }
    }

    public static String encrypt(String type, String text) throws Exception {
        EncryptionType encryptionType = EncryptionType.fromString(type);

        switch (encryptionType) {
            case BASE64:
                return Base64Encrypter.encrypt(text);
            case ROT13:
                return ROT13Encrypter.encrypt(text);
            case BASE16:
                return Base16Encrypter.encrypt(text);
            case BASE85:
                return Base85Encrypter.encrypt(text);
            case QUOTED_PRINTABLE:
                return QuotedPrintableEncrypter.encrypt(text);
            case ASCII85:
                return Ascii85Encrypter.encrypt(text);
            case Z85:
                return Z85Encrypter.encrypt(text);
            case AES:
                return AESEncrypter.encrypt(text);
            case BINARY:
                return BinaryEncrypter.encrypt(text);
            case RSA:
                return RSAEncrypter.encrypt(text);
            case TRIPLE_DES:
                return TripleDESEncrypter.encrypt(text);
            case DES:
                return DESEncrypter.encrypt(text);
            case HEXA:
                return HexaEncrypter.encrypt(text);
            case OCTAL:
                return OctalEncrypter.encrypt(text);
            case ARBITRARY_RADIX:
                return ArbitraryRadixEncrypter.encrypt(text);
            case VIGENERE:
                return VigenereEncrypter.encrypt(text, "SECRET_KEY");
            default:
                throw new IllegalArgumentException("Invalid Encryption type. Use 'base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des, octal, hexa, binary, arbitary_radix, triple_des'");
        }
    }

    public static String decrypt(String type, String encryptedText) throws Exception {
        EncryptionType encryptionType = EncryptionType.fromString(type);

        switch (encryptionType) {
            case BASE64:
                return Base64Decrypter.decrypt(encryptedText);
            case BASE16:
                return Base16Decrypter.decrypt(encryptedText);
            case BASE85:
                return Base85Decrypter.decrypt(encryptedText);
            case QUOTED_PRINTABLE:
                return QuotedPrintableDecrypter.decrypt(encryptedText);
            case ASCII85:
                return Ascii85Decrypter.decrypt(encryptedText);
            case Z85:
                return Z85Decrypter.decrypt(encryptedText);
            case AES:
                return AESDecrypter.decrypt(encryptedText);
            case RSA:
                return RSADecrypter.decrypt(encryptedText);
            case DES:
                return DESDecrypter.decrypt(encryptedText);
            case TRIPLE_DES:
                return TripleDESDecrypter.decrypt(encryptedText);
            case BINARY:
                return BinaryDecrypter.decrypt(encryptedText);
            case HEXA:
                return HexaDecrypter.decrypt(encryptedText);
            case OCTAL:
                return OctalDecrypter.decrypt(encryptedText);
            case ARBITRARY_RADIX:
                return ArbitraryRadixDecrypter.decrypt(encryptedText);
            case VIGENERE:
                return VigenereDecrypter.decrypt(encryptedText, "SECRET_KEY");
            default:
                throw new IllegalArgumentException("Invalid decryption type. Use 'base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des, octal, hexa, binary, arbitary_radix, triple_des'");
        }
    }

    public static void displayUsage() {
        System.out.println("Usage: java -jar <jar-file-name>.jar <command> <type> <text>");
        System.out.println("Commands: encrypt, decrypt, help");
        System.out.println("Types: base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des, octal, hexa, binary, arbitary_radix, triple_des");
    }

    public static void displayHelp() {
        displayUsage();
        System.out.println("\nDescription:");
        System.out.println("The EncryptionDecryption program allows you to encrypt or decrypt text using different encryption types.");
        System.out.println("\nExamples:");
        System.out.println("java -jar <jar-file-name>.jar encrypt base64 'Hello, World!'");
        System.out.println("java -jar <jar-file-name>.jar decrypt base64 'Khoor, Zruog!'");
    }

    private enum EncryptionType {
        BASE64,
        ROT13,
        BASE16,
        BASE85,
        QUOTED_PRINTABLE,
        ASCII85,
        Z85,
        VIGENERE,
        AES,
        RSA,
        DES,
        TRIPLE_DES,
        BINARY,
        HEXA,
        OCTAL,
        ARBITRARY_RADIX;

        public static EncryptionType fromString(String type) {
            return valueOf(type.toUpperCase());
        }
    }
}