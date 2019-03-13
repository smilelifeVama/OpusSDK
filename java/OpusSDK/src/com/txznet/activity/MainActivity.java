package com.txznet.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import com.txznet.opus.OpusDecoder;
import com.txznet.opus.OpusEncoder;
import com.txznet.opus.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btnInit,btnEncode,btnDecode;
	private OpusDecoder mDecoder;
	private OpusEncoder mEncoder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		registerListener();
	}

	private void registerListener() {
		btnInit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDecoder = new OpusDecoder();
				mEncoder = new OpusEncoder();
			}
		});
		btnEncode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				encode();
			}
		});
		btnDecode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				decode();
				decodeFromOpusFrame();
			}
		});
	}

	private void encode() {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try{
			File file = new File("/mnt/sdcard/txz/opus.pcm");
			File fileAfter = new File("/mnt/sdcard/txz/opused.data");
			fos = new FileOutputStream(fileAfter, true);
			fis = new FileInputStream(file);
			byte[] buf = new byte[640];
			byte[] bufDecode = new byte[640];
			byte[] bufOut = new byte[640];
			int len = 0;
			while((len = fis.read(buf)) > 0){
				int l = mEncoder.encode(buf, len, bufOut, 320);
				Log.i("test", "opus encode["+len+"to"+l+"]");
//				fos.write(bufOut, 0, l);
				int ll = mDecoder.decode(bufOut, l, bufDecode, 320);
				Log.i("test", "opus decode["+l+"to"+ll+"]");
				fos.write(bufDecode, 0, ll);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void decodeFromOpusFrame() {
		try {
			FileReader fr = new FileReader(new File("/mnt/sdcard/txz/opusFrame.txt"));
			BufferedReader br = new BufferedReader(fr);
			FileOutputStream fos = new FileOutputStream(new File("/mnt/sdcard/txz/opusFrame.pcm"), true);
			String line = null;
			while((line = br.readLine()) != null){
				Log.i("tag", "line = " + line);
				int index = line.indexOf("voiceFrame:");
				String strData = line.substring(index + 11).trim();
				Log.i("tag", "strData = " + strData);
				for(int i = 0; i<3; i++){
					byte[] bytes = hexStringToByte(strData.substring(i * 80, 80 * (i+1)));
					Log.i("tag", "bytes length = " + bytes.length);
					byte[] bufOut = new byte[640];
					int l = mDecoder.decode(bytes, bytes.length, bufOut, 320);
					Log.i("tag", "bufOut length = " + l);
					fos.write(bufOut, 0, l);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
		}
	}
	
	 public static byte[] hexStringToByte(String hex) {
		   int len = (hex.length() / 2);
		   byte[] result = new byte[len];
		   char[] achar = hex.toCharArray();
		   for (int i = 0; i < len; i++) {
		    int pos = i * 2;
		    result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		   }
		   return result;
		  }
	 
	 private static int toByte(char c) {
		    byte b = (byte) "0123456789ABCDEF".indexOf(c);
		    return b;
		 }
	
	private void decode() {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try{
			File file = new File("/mnt/sdcard/txz/opus1.pcm");
			File fileOpused = new File("/mnt/sdcard/txz/opused.data");
			fos = new FileOutputStream(file, true);
			fis = new FileInputStream(fileOpused);
			byte[] buf = new byte[640];
			byte[] bufOut = new byte[640];
			int len = 0; 
			while((len = fis.read(buf, 0, buf.length)) > 0){
				Log.i("opus", String.format("1decode buf[0][%02X] buf[1][%02X]", buf[0], buf[1]));
				int l = mDecoder.decode(buf, len, bufOut, 320);
				Log.i("opus", String.format("2decode buf[0][%02X] buf[1][%02X]", buf[0], buf[1]));
//				Log.i("opus", "decode buf[0] = " +  String.format("%02X", buf[0]) + ", buf[1] = ");
				fos.write(bufOut, 0, l);
				Log.i("test", "opus decode["+len+"to"+l+"]");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void initView() {
		btnInit = (Button)findViewById(R.id.btn_main_init);
		btnEncode = (Button)findViewById(R.id.btn_main_encode);
		btnDecode = (Button)findViewById(R.id.btn_main_decode);
	}
}
