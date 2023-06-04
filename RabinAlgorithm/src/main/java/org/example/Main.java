package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Main {

   public static void main(String[] args) {

       KeyPair keyPair = generateKeys();

       // Message to be encrypted
       BigInteger message = new BigInteger("1223456789");

       // Encrypt the message
       BigInteger ciphertext = encrypt(message, keyPair.getPublicKey());
       System.out.println("Ciphertext: " + ciphertext);

       // Decrypt the ciphertext
       BigInteger[] decryptedMessages = decrypt(ciphertext, keyPair.getPrivateKey());
       System.out.println("Decrypted messages: ");
       for (BigInteger decryptedMessage : decryptedMessages) {
           System.out.println(decryptedMessage);
       }
   }

    static class KeyPair {
        private final BigInteger publicKey;
        private final BigInteger[] privateKeys;

        public KeyPair(BigInteger publicKey, BigInteger[] privateKeys) {
            this.publicKey = publicKey;
            this.privateKeys = privateKeys;
        }

        public BigInteger getPublicKey() {
            return publicKey;
        }

        public BigInteger[] getPrivateKey() {
            return privateKeys;
        }
    }

    public static KeyPair generateKeys() {
        SecureRandom random = new SecureRandom();

        // Generate two distinct prime numbers p and q
        BigInteger p = BigInteger.probablePrime(512, random);
        BigInteger q;
        do {
            q = BigInteger.probablePrime(512, random);
        } while (q.equals(p)); // Ensure p and q are distinct

        BigInteger n = p.multiply(q); // Calculate the modulus

        return new KeyPair(n, new BigInteger[]{p, q});
    }

    public static BigInteger encrypt(BigInteger message, BigInteger publicKey) {

        BigInteger n = publicKey;
        return message.modPow(new BigInteger("2"), n);
    }


    public static BigInteger[] decrypt(BigInteger ciphertext, BigInteger[] privateKeys) {
        BigInteger p = privateKeys[0];
        BigInteger q = privateKeys[1];

        BigInteger n = p.multiply(q);

        BigInteger mp = ciphertext.modPow(p.add(BigInteger.ONE).divide(new BigInteger("4")), p);
        BigInteger mq = ciphertext.modPow(q.add(BigInteger.ONE).divide(new BigInteger("4")), q);

        BigInteger yp = mq.multiply(p).multiply(p.modInverse(q)).add(mp.multiply(q).multiply(q.modInverse(p))).mod(n);
        BigInteger yq = mq.multiply(p).multiply(p.modInverse(q)).subtract(mp.multiply(q).multiply(q.modInverse(p))).mod(n);

        BigInteger[] decryptedMessages = new BigInteger[2];
        decryptedMessages[0] = yp;
        decryptedMessages[1] = yq;

        return decryptedMessages;
    }

}