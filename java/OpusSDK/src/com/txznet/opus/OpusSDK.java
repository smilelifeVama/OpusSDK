package com.txznet.opus;

public class OpusSDK {
    static {
        System.loadLibrary("opus_sdk");
    }

    static native long create(int type, int sampleRate, int channels);

    static native int encode(long session, byte[] data, int size, byte[] output, int frameSize);

    static native int decode(long session, byte[] data, int size, byte[] output, int frameSize);

    static native void destroy(int type, long session);

}
