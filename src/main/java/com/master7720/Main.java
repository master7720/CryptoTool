package com.master7720;

import com.master7720.decrypter.*;
import com.master7720.encrypter.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String, Encrypter> ENCRYPTERS = new HashMap<>();
    private static final Map<String, Decrypter> DECRYPTERS = new HashMap<>();

    static {//has to be a better way of doing this
        ENCRYPTERS.put(EncryptionType.BASE64, Base64Encrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.ROT13, ROT13Encrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.BASE16, Base16Encrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.BASE85, Base85Encrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.QUOTED_PRINTABLE, QuotedPrintableEncrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.ASCII85, Ascii85Encrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.Z85, Z85Encrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.VIGENERE, VigenereEncrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.AES, AESEncrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.RSA, RSAEncrypter::encrypt);
        ENCRYPTERS.put(EncryptionType.DES, DESEncrypter::encrypt);

        DECRYPTERS.put(EncryptionType.BASE64, Base64Decrypter::decrypt);
        DECRYPTERS.put(EncryptionType.ROT13, ROT13Decrypter::decrypt);
        DECRYPTERS.put(EncryptionType.BASE16, Base16Decrypter::decrypt);
        DECRYPTERS.put(EncryptionType.BASE85, Base85Decrypter::decrypt);
        DECRYPTERS.put(EncryptionType.QUOTED_PRINTABLE, QuotedPrintableDecrypter::decrypt);
        DECRYPTERS.put(EncryptionType.ASCII85, Ascii85Decrypter::decrypt);
        DECRYPTERS.put(EncryptionType.Z85, Z85Decrypter::decrypt);
        DECRYPTERS.put(EncryptionType.VIGENERE, VigenereDecrypter::decrypt);
        DECRYPTERS.put(EncryptionType.AES, AESDecrypter::decrypt);
        DECRYPTERS.put(EncryptionType.RSA, RSADecrypter::decrypt);
        DECRYPTERS.put(EncryptionType.DES, DESDecrypter::decrypt);
    }

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
                if (ENCRYPTERS.containsKey(type)) {
                    String encryptedText = ENCRYPTERS.get(type).encrypt(text);
                    System.out.println("Encrypted: " + encryptedText);
                } else {
                    System.out.println("Invalid encryption type. Use 'base64', 'caesar', 'base16', 'base85', 'quoted_printable', 'ascii85', 'z85'.");
                    displayUsage();
                    System.exit(1);
                }
                break;
            case "decrypt":
                if (DECRYPTERS.containsKey(type)) {
                    String decryptedText = DECRYPTERS.get(type).decrypt(text);
                    System.out.println("Decrypted: " + decryptedText);
                } else {
                    System.out.println("Invalid decryption type. Use 'base64', 'caesar', 'base16', 'base85', 'quoted_printable', 'ascii85', 'z85'.");
                    displayUsage();
                    System.exit(1);
                }
                break;
            default:
                System.out.println("Invalid command. Use 'encrypt' or 'decrypt'.");
                displayUsage();
                System.exit(1);
        }
    }

    private interface Encrypter {
        String encrypt(String text) throws Exception;
    }

    private interface Decrypter {
        String decrypt(String encryptedText) throws Exception;
    }

    private static void displayUsage() {
        System.out.println("Usage: java -jar EncryptionDecryption.jar <command> <type> <text>");
        System.out.println("Commands: encrypt, decrypt, help");
        System.out.println("Types: base64, caesar, base16, base85, quoted_printable, ascii85, z85");
    }
    private static void displayHelp() {
        displayUsage();
        System.out.println("\nDescription:");
        System.out.println("The EncryptionDecryption program allows you to encrypt or decrypt text using different encryption types.");
        System.out.println("\nExamples:");
        System.out.println("java -jar JavaEncryptionDecryption.jar encrypt base64 'Hello, World!'");
        System.out.println("java -jar JavaEncryptionDecryption.jar decrypt base64 'Khoor, Zruog!'");
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