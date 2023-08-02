package com.master7720;

import com.master7720.commands.*;

import static com.master7720.Main.displayHelp;

public class CommandProcessor {

    public static void processCommand(String[] args) throws Exception {
        if (args.length < 3) {
            displayUsage();
            System.exit(1);
        }

        String command = args[0].toLowerCase();
        String type = args[1].toLowerCase();
        String text = args[2];

        switch (command) {
            case "encrypt":
                EncryptCommand.execute(type, text);
                break;

            case "decrypt":
                DecryptCommand.execute(type, text);
                break;

            case "input":
                if (args.length < 4) {
                    System.out.println("Please provide the input file path.");
                    displayUsage();
                    System.exit(1);
                }
                String inputFile = args[3];
                InputCommand.execute(type, inputFile);
                break;

            case "output":
                if (args.length < 4) {
                    System.out.println("Please provide the output file path.");
                    displayUsage();
                    System.exit(1);
                }
                String outputFile = args[3];
                OutputCommand.execute(type, text, outputFile);
                break;

            case "help":
                displayHelp();
                break;

            default:
                System.out.println("Invalid command. Use 'encrypt', 'decrypt', 'input', 'output', or 'help'.");
                displayUsage();
                System.exit(1);
        }
    }

    public static void displayUsage() {
        System.out.println("Usage: java -jar <jar-file-name>.jar <command> <type> <text>");
        System.out.println("Commands: encrypt, decrypt, help");
        System.out.println("Types: base64, rot13, base16, base85, quoted_printable, ascii85, z85, vigenere, aes, rsa, des, octal, hexa, binary, arbitary_radix, triple_des");
    }
}