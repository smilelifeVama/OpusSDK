package com.txznet.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.txznet.opus.OpusDecoder;
import com.txznet.opus.OpusEncoder;
import com.txznet.opus.R;

import android.app.Activity;
import android.os.Bundle;
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
				decode();
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
				int l = mEncoder.encode(buf, len, bufOut);
				Log.i("test", "opus encode["+len+"to"+l+"]");
				fos.write(bufOut, 0, l);
//				int ll = mDecoder.decode(bufOut, l, bufDecode);
//				Log.i("test", "opus decode["+l+"to"+ll+"]");
//				fos.write(bufDecode, 0, ll);
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
	
	private void decode() {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try{
			File file = new File("/mnt/sdcard/txz/opus1.pcm");
			File fileOpused = new File("/mnt/sdcard/txz/opus.encode");
			fos = new FileOutputStream(file, true);
			fis = new FileInputStream(fileOpused);
			byte[] buf = new byte[640];
			byte[] bufOut = new byte[640];
			int len = 0; 
			while((len = fis.read(buf)) > 0){
				int l = mDecoder.decode(buf, len, bufOut);
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
