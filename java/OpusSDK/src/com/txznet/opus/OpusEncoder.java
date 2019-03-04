package com.txznet.opus;

import android.util.Log;

public class OpusEncoder {
	private long session = 0l;
	private int frameSize = 0;
	
	public OpusEncoder(){
		this(16000, 1);
	}
	
	public OpusEncoder(int sampleRate, int channels){
		frameSize = sampleRate / 100 * 2; 
		session = OpusSDK.create(0, sampleRate, channels);
		Log.i("opus", "encoder create session = " + session + "frameSize = " + frameSize);
	}
	
	public int encode(byte[] data, int size, byte[] output){
		if(session == 0){
			return 0;
		}
		return OpusSDK.encode(session, data, size, output);
	}
	
	public void destroy(){
		if(session == 0){
			return;
		}
		OpusSDK.destroy(0, session);
	}
}
