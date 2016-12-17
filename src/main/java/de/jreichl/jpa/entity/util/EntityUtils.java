package de.jreichl.jpa.entity.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class EntityUtils {

    private static final String algorithm = "SHA-256";
    private static final Random random = new Random(System.currentTimeMillis());
    
    public static String hashPassword(String password, String salt) throws EntityUtilException {
        try {
            MessageDigest hashAlgo = MessageDigest.getInstance(algorithm);
            String toHash = salt + "#" + password;
            byte[] output = hashAlgo.digest(toHash.getBytes("UTF-8"));
            StringBuilder passwordBuilder = new StringBuilder();
            for(byte b : output)
                passwordBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return passwordBuilder.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new EntityUtilException("Could not hash password", ex);
        }
    }
    
    public static String createRandomString(int byteLength) {
        SecureRandom zufallsgenerator = new SecureRandom();
        byte[] bytes = new byte[byteLength];
        zufallsgenerator.nextBytes(bytes);
        BigInteger zufallszahl = new BigInteger(bytes);
        return zufallszahl.abs().toString(32).toUpperCase();
    }
    
    public static String createRandomUUID() {
        return UUID.randomUUID().toString();
    } 
    
    
    public static class EntityUtilException extends Exception {
        public EntityUtilException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    public static String createRandomIBAN() {        
        StringBuilder iban = new StringBuilder("DE");
        for(int i = 0; i<20; i++)
            iban.append(random.nextInt(10));

        return iban.toString();
    }
}
