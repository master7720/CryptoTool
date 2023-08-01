# JavaEncrypterDecrypter
uses base64 and caesar to encrypted and decrypted

# How to use(command line only)

# Encrypt using base64
java -jar Base64EncryptDecrypt.jar encrypt base64 "Hello, World!"

# Encrypt using caesar cipher
java -jar Base64EncryptDecrypt.jar encrypt caesar "Hello, World!"

# Decrypt using base64
java -jar Base64EncryptDecrypt.jar decrypt base64 "SGVsbG8sIFdvcmxkIQ=="

# Decrypt using caesar cipher
java -jar Base64EncryptDecrypt.jar decrypt caesar "Khoor, Zruog!"
