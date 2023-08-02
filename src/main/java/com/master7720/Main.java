package com.master7720;

import com.master7720.decrypter.*;
import com.master7720.encrypter.*;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            displayUsage();
            System.exit(1);
        }

        String type = args[0].toLowerCase();
        String text = args[1];

        String encryptedText = encrypt(type, text);
        String decryptedText = decrypt(type, encryptedText);

        System.out.println("Original Text: " + text);
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("Decrypted: " + decryptedText);
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
            case ROT13:
                return ROT13Decrypter.decrypt(encryptedText);
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
        System.out.println("Usage: java -jar <jar-file-name>.jar <type> <text>");
        System.out.println("Types: base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des, octal, hexa, binary, arbitary_radix, triple_des");
    }

    public static void displayHelp() {
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