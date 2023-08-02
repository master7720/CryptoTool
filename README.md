# CryptoTool

**WARNING: This software is in alpha; some features might break or not work as expected. Feel free to report any issues or contribute through issues and pull requests.**

CryptoTool is a modern encryption and decryption tool that supports various commercial encryptions.

## Supported Encrypters

1. AES (Advanced Encryption Standard)
2. Triple DES (Data Encryption Standard)
3. RSA (Rivest–Shamir–Adleman)
4. Base64 Encoding
5. Base85 Encoding
6. ROT13 Encoding
7. Vigenere Cipher
8. Z85 Encoding
9. Ascii85 Encoding

## How to Use (Command Line)

### Encrypt

To encrypt a message, use the following command:
java -jar CryptoTool.jar <any supported encrypter> encrypt "Add Your Text Here"

### Decrypt

To decrypt a message, use the following command:
java -jar CryptoTool.jar <any supported decrypter> decrypt "Add Your Text Here"
