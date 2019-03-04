package com.txznet.opus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CommonUtil {

	public static short[] bytesToShort(byte[] bytes) {
		if(bytes==null){
			return null;
		}
		short[] shorts = new short[bytes.length/2];
		ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
	    return shorts;
	}
	public static byte[] shortToBytes(short[] shorts) {
		if(shorts==null){
			return null;
		}
		byte[] bytes = new byte[shorts.length * 2];
		ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(shorts);
		
	    return bytes;
	}

}
