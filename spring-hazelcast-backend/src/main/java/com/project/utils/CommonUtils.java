package com.project.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {

    private static final Logger log = Logger.getLogger(CommonUtils.class);

    public static String hashPassword(String text) {
        return toHexString(md5(text));
    }

    public static String toHexString(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("byte array must not be null");
        }
        StringBuffer hex = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            hex.append(Character.forDigit((bytes[i] & 0XF0) >> 4, 16));
            hex.append(Character.forDigit((bytes[i] & 0X0F), 16));
        }
        return hex.toString();
    }

    public static byte[] fromHexString(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("Hex string must not be null");
        }
        if (hex.length() % 2 == 1) {
            throw new IllegalArgumentException("Hex string length is not even : " + hex.length());
        }
        int index = 0;
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            char chigh = hex.charAt(index++);
            int high = Character.digit(chigh, 16);
            if (high == -1) {
                throw new IllegalArgumentException("Hex string contains a bad char : " + chigh);
            }
            char clow = hex.charAt(index++);
            int low = Character.digit(clow, 16);
            if (low == -1) {
                throw new IllegalArgumentException("Hex string contains a bad char : " + clow);
            }
            byte value = (byte) ((high << 4) + low);
            bytes[i] = value;
        }
        return bytes;
    }
    //here we go:))

    public static byte[] md5(String text) {
        // arguments check
        if (text == null) {
            throw new NullPointerException("null text");        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            log.error("Cannot find MD5 algorithm", e);
            throw new RuntimeException("Cannot find MD5 algorithm");
        }
    }
}
