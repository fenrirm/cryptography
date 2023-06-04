package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Main {
    static BigInteger p, g, y;
    static BigInteger a, b, k, EC, M;
    static BigInteger secretKey = new BigInteger("1234");
    static Random sc = new SecureRandom();

    public static BigInteger run(BigInteger num) {
        publicKeyCalculation();
        encryption(num);
        return decryption();
    }

    private static void publicKeyCalculation() {
        p = BigInteger.probablePrime(64, sc);
        g = new BigInteger("3");
        y = g.modPow(secretKey, p);
    }

    private static void encryption(BigInteger X) {
        k = new BigInteger(64, sc);
        EC = X.multiply(y.modPow(k, p)).mod(p);

        a = g.modPow(k, p);
        b = EC.mod(p);
    }

    private static BigInteger decryption() {
        BigInteger d = a.modPow(secretKey.negate(), p);
        M = d.multiply(b).mod(p);
        return M;
    }

    public static void main(String[] args) {
        BigInteger plaintext = new BigInteger("12345");

        BigInteger decryptedText = run(plaintext);
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Decrypted text: " + decryptedText);
    }
}
