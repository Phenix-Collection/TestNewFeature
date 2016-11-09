package com.example.wenjunzhong.testnewfeature.utils;

import android.support.annotation.NonNull;

import java.nio.ByteBuffer;
import java.util.Random;

/**
 * 
 * Created by eric on 16/8/7.
 */

public final class DataParseUtil {

    public static final int KEY_LENGTH = 10;

    private DataParseUtil() {}

    public static void generateEncryptionKey(@NonNull byte[] outKey) {
        Random rdm = new Random(System.currentTimeMillis());

        int rand = 0, count = 0, loop = 0;
        while (count < outKey.length) {
            if (loop == 0) {
                rand = rdm.nextInt();
                loop = 2;
            } else {
                loop--;
            }
            outKey[count++] = (byte) rand;
            rand >>= 8;
        }
    }

    /**
     * 解码
     */
    public static byte[] decode(ByteBuffer byteBuffer, int count, byte[] keys, byte[] outKey) {
        if (keys == null) {
            keys = new byte[KEY_LENGTH];
        }

        System.arraycopy(byteBuffer.array(), 0, keys, 0, KEY_LENGTH);
        return RC4.decryption(byteBuffer.array(), KEY_LENGTH, count - KEY_LENGTH, keys, outKey);
    }

    /**
     * 解码
     */
    public static byte[] decode(byte[] byteBuffer, int count, byte[] keys, byte[] outKey) {
        if (keys == null) {
            keys = new byte[KEY_LENGTH];
        }

        System.arraycopy(byteBuffer, 0, keys, 0, KEY_LENGTH);
        return RC4.decryption(byteBuffer, KEY_LENGTH, count - KEY_LENGTH, keys, outKey);
    }

    /**
     * 编码
     */
    public static ByteBuffer encode(byte[] byteBuffer, int count, byte[] keys, byte[] outKey) {
        if (keys == null) {
            keys = new byte[KEY_LENGTH];
        }
        generateEncryptionKey(keys);

        ByteBuffer resultBuffer = ByteBuffer.allocate(count + keys.length);
        resultBuffer.put(keys, 0, keys.length);

        resultBuffer.put(RC4.encryption(byteBuffer, 0, count, keys, outKey), 0, count);
        resultBuffer.flip();
        return resultBuffer;
    }

}
