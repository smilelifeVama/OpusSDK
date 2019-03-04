#include "opus_sdk.h"
#include "opus.h"
#include<android/log.h>
#include <stdlib.h>

#define TAG "jni-tag"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型
/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    create
 */
JNIEXPORT jlong JNICALL Java_com_txznet_opus_OpusSDK_create(JNIEnv * env,
		jclass clazz, jint type, jint sampleRate, jint channels) {
	LOGD("create type[%d] sampleRate[%d] channels[%d]", type, sampleRate,
			channels);
	int err = 0;
	if (type == 0) {
		return (long)opus_encoder_create(sampleRate, channels, OPUS_APPLICATION_AUDIO,
				&err);
	} else if (type == 1) {
		return (long)opus_decoder_create(sampleRate, channels,
				&err);
	}
	return 0;
}

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    encode
 */
JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_encode(JNIEnv * env,
		jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output) {
	LOGD("1encode session[%lld] size[%d]", session, size);
	OpusEncoder* encoder = (OpusEncoder*) ((void*) session);
	if (NULL == encoder)
		return -1;
	jbyte* jdata = env->GetByteArrayElements(data, NULL);
	jbyte* joutput = env->GetByteArrayElements(output, NULL);
	opus_int16 * frame_buf = (opus_int16 *)jdata;
	if (NULL == jdata || NULL == joutput)
		return 0;
	opus_int32 len = opus_encode(encoder, frame_buf, size / 2, (unsigned char*)(joutput + 2), 320 - 1);
	frame_buf[0] = (opus_int16)(len & 0xFFFF);
	LOGD("encode after length[%d] char[0][%02X] ", len, joutput[0]);
	if(len < 0){
		len = 0;
	}
	env->SetByteArrayRegion(output, 0, len, joutput);
	env->ReleaseByteArrayElements(data, jdata, 0);
	env->ReleaseByteArrayElements(output, joutput, 0);
	return len;
}

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    decode
 */
JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_decode(JNIEnv * env,
		jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output) {
	LOGD("decode session[%lld] size[%d]", session, size);
	OpusDecoder* decoder = (OpusDecoder*) (void*) session;
	if (NULL == decoder)
		return 0;
	jbyte* jdata = env->GetByteArrayElements(data, NULL);
	jbyte* joutput = env->GetByteArrayElements(output, NULL);
	if (NULL == jdata || NULL == joutput)
		return 0;
	int len = opus_decode(decoder, (unsigned char *)jdata, size, (opus_int16 *)joutput, 320, 0);
	if(len < 0) {
		len = 0;
	}
	env->SetByteArrayRegion(output, 0, len * 2, joutput);
	env->ReleaseByteArrayElements(data, jdata, 0);
	env->ReleaseByteArrayElements(output, joutput, 0);
	return len * 2;
}

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    destroy
 */
JNIEXPORT void JNICALL Java_com_txznet_opus_OpusSDK_destroy
(JNIEnv * env, jclass clazz, jint type, jlong session)
{
	LOGD("destroy session[%lld]", session);
	if(type == 0) {
		OpusEncoder* encoder = (OpusEncoder*) (void*) session;
		if (NULL == encoder) return;
		opus_encoder_destroy(encoder);
	}
	else if(type == 1)
	{
		OpusDecoder* decoder = (OpusDecoder*) (void*) session;
		if (NULL == decoder) return;
		opus_decoder_destroy(decoder);
	}
}

public static short byte2short(byte[] b)
{
	short l = 0;
    for (int i = 0; i < 2; i++) {
    	l<<=8; //<<=和我们的 +=是一样的，意思就是 l = l << 8
        l |= (b[i] & 0xff); //和上面也是一样的  l = l | (b[i]&0xff)
    }
    return l;
}

public static byte[] short2byte(short s)
{
     byte[] b = new byte[2];
     for(int i = 0; i < 2; i++){
         int offset = 16 - (i+1)*8; //因为byte占4个字节，所以要计算偏移量
         b[i] = (byte)((s >> offset)&0xff); //把16位分为2个8位进行分别存储
     }
     return b;
}
