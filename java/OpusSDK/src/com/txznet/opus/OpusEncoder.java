package com.txznet.opus;

import android.util.Log;

/**
 * opus编码
 *
 */
public class OpusEncoder {
	private long session = 0l;
	private int frameSize = 0;
	
	/**
	 * Constructor
	 */
	public OpusEncoder(){
		this(16000, 1, 0);
	}
	
	/**
	 * Constructor
	 * @param sampleRate 采样率
	 * @param channels 信道（1or2）
	 */
	public OpusEncoder(int sampleRate, int channels){
		this(sampleRate, channels, 0);
	}
	
	/**
	 * Constructor
	 * @param sampleRate 采样率
	 * @param channels 信道（1or2）
	 * @param VBR 静态码率/动态码率（0or1）
	 */
	public OpusEncoder(int sampleRate, int channels, int VBR){
		frameSize = sampleRate / 100 * 2; 
		session = OpusSDK.create(0, sampleRate, channels, VBR);
		Log.i("opus", "encoder create session = " + session + ", frameSize = " + frameSize 
				+ ", sampleRate = " + sampleRate + ", channels = " + channels + ", VBR = " + VBR);
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
	 * destroy
	 * 释放资源
	 */
	public void destroy(){
		if(session == 0){
			return;
		}
		OpusSDK.destroy(0, session);
	}
}
