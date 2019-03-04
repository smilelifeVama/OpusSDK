package com.txznet.opus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.util.Log;

public class OpusDecoder {
	private long session = 0l;
	
	public OpusDecoder(){
		this(16000, 1);
		Log.i("opus", "create decoder " + session);
	}
	
	public OpusDecoder(int sampleRate, int channels){
		session = OpusSDK.create(1, sampleRate, channels);
		Log.i("opus", "decoder create session = " + session);
	}
	
//	public int decode(byte[] dataIn, int size, short[] dataOut, int frameSize){
//		if(session == 0){
//			return 0;
//		}
//		return OpusSDK.decode(session, dataIn, size, dataOut, frameSize);
//	}
//	
//	public int decode(byte[] dataIn, int size, byte[] dataOut, int frameSize){
//		if(session == 0){
//			return 0;
//		}
//		short[] shorts = new short[dataOut.length/2];
//		int len = OpusSDK.decode(session, dataIn, size, shorts, frameSize);
//		Log.i("opus", "shorts [15]" + shorts[15]);
//		ByteBuffer.wrap(dataOut).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(shorts);
//		return len * 2;
//	}
	
	public int decode(byte[] data, int size, byte[] output){
		if(session == 0){
			return 0;
		}
		return OpusSDK.decode(session, data, size, output);
	}
	
	public void destroy(){
		if(session == 0){
			return;
		}
		OpusSDK.destroy(1, session);
	}
	
	
}
