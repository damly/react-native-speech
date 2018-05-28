package com.damly.haian;

public class TypeUtils {


    public static short[] bytes2Shorts(byte[] v, int iLen) {
        short[] nData = new short[iLen];
        for (int i=0; i < iLen; i++) {
            nData[i]= (short) (((v[i*2 + 1] << 8) | v[i*2 + 0] & 0xff));
        }
        return nData;
    }

    public static byte[] shortsToBytes(short[] n,int writeLen) {
        if (n==null) {
            return null;
        }
        byte[] b = new byte[writeLen * 2];
        int j = 0;
        for (int i = 0; i < writeLen; i++) {
            j = i * 2;
            b[1 + j] = (byte) (n[i] & 0xff);
            b[0 + j] = (byte) ((n[i] >> 8) & 0xff);
        }
        return b;
    }
}