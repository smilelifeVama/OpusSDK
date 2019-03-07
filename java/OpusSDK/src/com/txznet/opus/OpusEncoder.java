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
	
	/**
	 * 编码
	 * @param data
	 * @param size 
	 * @param output
	 * @param frameSize Number of samples per channel in theinput signal. This must be an Opus frame size for
     *        the encoder's sampling rate.  For example, at 48 kHz the permitted values are 120, 240, 480, 960, 1920,and 2880.                     
	 * @return
	 */
	public int encode(byte[] data, int size, byte[] output, int frameSize){
		if(session == 0){
			return 0;
		}
		return OpusSDK.encode(session, data, size, output, frameSize);
	}
	
	/**
	 * 编码后混存
	 * @param data
	 * @param size
	 * @param output
	 * @param frameSize
	 * @return
	 */
	public int encode2File(byte[] data, int frameSize, byte[] output, int maxLen){
		if(session == 0){
			return 0;
		}
		int ret = OpusSDK.encode(session, data, frameSize, output, maxLen - 2);
		if(ret < 0){
			return ret;
		}
		
		
		
		return ret + 2;
		
	}
	
	
	public void destroy(){
		if(session == 0){
			return;
		}
		OpusSDK.destroy(0, session);
	}
}
