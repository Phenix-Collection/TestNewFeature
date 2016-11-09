package com.example.wenjunzhong.testnewfeature.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by eric on 16/8/7.
 */

public final class RC4 {

    public static final int OUT_KEY_LENGTH = 256;

    private RC4() {}


    private static void initKey(byte[] inKey, byte[] outKey) throws IllegalArgumentException {
        if (inKey == null || inKey.length == 0 || outKey == null) {
            throw new IllegalArgumentException("inKey == null || outKey == null");
        }

        for (int i = 0; i < OUT_KEY_LENGTH; i++) {
            outKey[i] = (byte) i;
        }

        byte tmp;
        for (int i = 0, j = 0; i < OUT_KEY_LENGTH; i++) {
            j = (j + (outKey[i] & 0xff) + (inKey[i % inKey.length] & 0xff)) & 0xff;
            tmp = outKey[i];
            outKey[i] = outKey[j];
            outKey[j] = tmp;
        }
    }


    private static byte[] RC4Base(byte[] input, int offset, int count, byte[] inKey, byte[] outKey)
            throws IllegalArgumentException {
        int x = 0;
        int y = 0;
        initKey(inKey, outKey);
        int xorIndex;
        byte[] result = new byte[count];

        byte tmp;
        for (int i = offset; i < count + offset; i++) {
            x = (x + 1) & 0xff;
            y = ((outKey[x] & 0xff) + y) & 0xff;
            tmp = outKey[x];
            outKey[x] = outKey[y];
            outKey[y] = tmp;
            xorIndex = ((outKey[x] & 0xff) + (outKey[y] & 0xff)) & 0xff;
            result[i - offset] = (byte) ((input[i] & 0xff) ^ outKey[xorIndex]);
        }
        return result;
    }

    /**
     * 解密
     * 
     * @param input
     * @param offset
     * @param count
     * @param inKey
     * @return
     * @throws IllegalArgumentException
     * @throws ArrayIndexOutOfBoundsException
     */
    public static byte[] decryption(byte[] input, int offset, int count, byte[] inKey, byte[] outKey)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (input == null || inKey == null) {
            throw new IllegalArgumentException("input == null || inKey == null");
        }

        if (count + offset > input.length) {
            throw new ArrayIndexOutOfBoundsException("count + offset > input.length");
        }

        return RC4Base(input, offset, count, inKey, outKey);
    }

    public static String decryptionStr(byte[] input, int offset, int count, byte[] inKey, byte[] outKey)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException, UnsupportedEncodingException {

        return new String(decryption(input, offset, count, inKey, outKey), "utf-8");
    }

    /**
     * 加密
     * 
     * @param input
     * @param offset
     * @param count
     * @param inKey
     * @return
     * @throws IllegalArgumentException
     * @throws ArrayIndexOutOfBoundsException
     */
    public static byte[] encryption(byte[] input, int offset, int count, byte[] inKey, byte[] outKey)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (input == null || inKey == null) {
            throw new IllegalArgumentException("input == null || inKey == null");
        }

        if (count + offset > input.length) {
            throw new ArrayIndexOutOfBoundsException("count + offset > input.length");
        }

        return RC4Base(input, offset, count, inKey, outKey);
    }

    public static String encryptionStr(byte[] input, int offset, int count, byte[] inKey, byte[] outKey)
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException, UnsupportedEncodingException {

        return new String(encryption(input, offset, count, inKey, outKey), "utf-8");
    }
}
