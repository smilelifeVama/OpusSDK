package com.txznet.opus;

import android.util.Log;

/**
 * opus解码
 *
 */
public class OpusDecoder {
	private long session = 0l;
	
	/**
	 * Constructor
	 */
	public OpusDecoder(){
		this(16000, 1);
	}
	
	/**
	 * Constructor
	 * @param sampleRate 采样率
	 * @param channels 信道（1or2）
	 */
	public OpusDecoder(int sampleRate, int channels){
		this(sampleRate, channels, 0);
	}
	
	/**
	 * Constructor
	 * @param sampleRate 采样率
	 * @param channels 信道（1or2）
	 * @param VBR 静态码率/动态码率（0or1）
	 */
	public OpusDecoder(int sampleRate, int channels, int VBR){
		session = OpusSDK.create(1, sampleRate, channels, VBR);
		Log.i("opus", "decoder create session = " + session + ", sampleRate = " 
				+ sampleRate + ", channels = " + channels + ", VBR = " + VBR);
	}
	
	/**
	 * 解码数据段
	 * @param data
	 * @param size
	 * @param output
	 * @param frameSize
	 * @return length of output
	 */
	public int decode(byte[] data, int size, byte[] output, int frameSize){
		if(session == 0){
			return 0;
		}
		return OpusSDK.decode(session, data, size, output, frameSize);
	}
	
	/**
	 * destroy
	 * 释放资源
	 */
	public void destroy(){
		if(session == 0){
			return;
		}
		OpusSDK.destroy(1, session);
	}
	
	
}
