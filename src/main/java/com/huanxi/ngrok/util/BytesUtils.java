package com.huanxi.ngrok.util;


import java.util.Arrays;

/**
 * @author huanxi
 * @version 1.0
 * @date 2021/1/7 2:19 下午
 */
public class BytesUtils {
    /**
     * int64 -> byte[8]
     *
     * @param values
     * @return
     */
    public static byte[] longToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[7 - i] = (byte) ((values >> offset) & 0xff);
        }

        return buffer;
    }

    public static long bytesToLong(byte[] buffer) {
        long values = 0;
        for (int i = 0; i < 8; i++) {
            values <<= 8;
            values |= (buffer[7-i] & 0xff);
        }
        return values;
    }

    public static void main(String[] args) {
        System.out.println(bytesToLong(longToBytes(256)));

    }

    public static byte[] concat(byte[] array1, byte[] array2) {

        byte[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);

        return result;
    }
}
