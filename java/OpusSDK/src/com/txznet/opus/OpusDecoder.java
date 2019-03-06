package com.txznet.opus;

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
	
	/**
	 * 解码数据段
	 * @param data
	 * @param size
	 * @param output
	 * @param frameSize
	 * @return
	 */
	public int decode(byte[] data, int size, byte[] output, int frameSize){
		if(session == 0){
			return 0;
		}
		return OpusSDK.decode(session, data, size, output, frameSize);
	}
	
	public void destroy(){
		if(session == 0){
			return;
		}
		OpusSDK.destroy(1, session);
	}
	
	
}
