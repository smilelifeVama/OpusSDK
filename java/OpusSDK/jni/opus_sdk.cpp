#include "opus_sdk.h"
#include "opus.h"
#include<android/log.h>
#include <stdlib.h>
#include <cstring>

#define TAG "jni-tag"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

#ifdef ANDROID

unsigned char *remain_data = NULL;
int remain_len = 0;

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    create
 */
JNIEXPORT jlong JNICALL Java_com_txznet_opus_OpusSDK_create(JNIEnv * env,
		jclass clazz, jint type, jint sampleRate, jint channels) {
	LOGD("create type[%d] sampleRate[%d] channels[%d]", type, sampleRate,
			channels);
	remain_data = (unsigned char *)malloc(sampleRate * channels * sizeof(char));
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

///*
// * Class:     com_txznet_opus_OpusSDK
// * Method:    encode
// */
//JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_encode(JNIEnv * env,
//		jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output, jint frame_size) {
//	LOGD("1encode session[%lld] size[%d]", session, size);
//	OpusEncoder* encoder = (OpusEncoder*) ((void*) session);
//	if (NULL == encoder)
//		return -1;
//	jbyte* jdata = env->GetByteArrayElements(data, NULL);
//	jbyte* joutput = env->GetByteArrayElements(output, NULL);
//	opus_int16 * frame_buf = (opus_int16 *)jdata;
//	if (NULL == jdata || NULL == joutput)
//		return 0;
//	opus_int32 len = opus_encode(encoder, frame_buf, size / 2, (unsigned char*)(joutput + 2), 320);
//	*joutput = (opus_int16)len;
//	LOGD("encode after length[%d][%04X] char[0] head[%02X] ", len, len, joutput[0]);
//	if(len < 0)
//	{
//		len = 0;
//	}
//	env->SetByteArrayRegion(output, 0, len + 2, joutput);
//	env->ReleaseByteArrayElements(data, jdata, 0);
//	env->ReleaseByteArrayElements(output, joutput, 0);
//	return len + 2;
//}



///*
// * Class:     com_txznet_opus_OpusSDK
// * Method:    decode
// */
//JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_decode(JNIEnv * env,
//		jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output, jint frame_size) {
//	LOGD("decode session[%lld] size[%d]", session, size);
//	OpusDecoder* decoder = (OpusDecoder*) (void*) session;
//	if (NULL == decoder)
//		return 0;
//	jbyte* jdata = env->GetByteArrayElements(data, NULL);
//	jbyte* joutput = env->GetByteArrayElements(output, NULL);
//	int max_out_len = env->GetArrayLength(output);
//	int bodyLen = 0;
//	int out_len = 0;//结果长度
//	int decode_len = 0;//解码长度
//	int err_code = 0;
//	if (NULL == jdata || NULL == joutput)
//		return 0;
//	std::strncat((char *)remain_data, (const char *)jdata, size);
//	remain_len += size;
//	LOGD("decode add remain_data after len[%d]", remain_len);
//	while(remain_len > 0)
//	{
//		bodyLen = ((opus_int16 *)remain_data)[0];
//		LOGD("decode bodylen[%d][%04X] short[%hd] remain_data[0][%02X] remain_data[1][%02X]", bodyLen, bodyLen, ((opus_int16 *)remain_data)[0], remain_data[0], remain_data[1]);
//		if(bodyLen < 0)
//		{
//			err_code = -1;
//			break;
//		}
//		int channels = opus_packet_get_nb_channels(remain_data + 2);
//		LOGD("decode channels[%d]", channels);
//		if(frame_size * channels * sizeof(opus_int16) > max_out_len - out_len)
//		{
//			LOGD("decode out buff not enough, target[%d] remain[%d]", frame_size * channels * sizeof(opus_int16), max_out_len - out_len);
//			break;
//		}
//		decode_len = opus_decode(decoder, remain_data + 2, bodyLen, (opus_int16 *)joutput, frame_size, 0);
//		LOGD("decode decode_len[%d]", decode_len);
//		if(decode_len != frame_size)
//		{
//			LOGD("decode fail ret[%d]", decode_len);
////			err_code = -1;
//			break;
//		}
//		out_len += frame_size;
//		remain_data += (bodyLen + 2);
//		joutput += frame_size;
//		remain_len -= (bodyLen + 2);
//	}
////	remain_data = std::string(input_data, data_len);
//	env->SetByteArrayRegion(output, 0, out_len * 2, joutput);
//	env->ReleaseByteArrayElements(data, jdata, 0);
//	env->ReleaseByteArrayElements(output, joutput, 0);
//	if(err_code < 0) return err_code;
//	return out_len * 2;
//}

///*
// * Class:     com_txznet_opus_OpusSDK
// * Method:    decode
// */
//JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_decode(JNIEnv * env,
//		jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output, jint frame_size, jbyteArray remain) {
//	LOGD("decode session[%lld] size[%d]", session, size);
//	OpusDecoder* decoder = (OpusDecoder*) (void*) session;
//	if (NULL == decoder)
//		return 0;
//	jbyte* jdata = env->GetByteArrayElements(data, NULL);
//	jbyte* joutput = env->GetByteArrayElements(output, NULL);
//	jbyte* jremain = env->GetByteArrayElements(remain, NULL);
//	int max_out_len = env->GetArrayLength(output);
//	int bodyLen = 0;
//	int out_len = 0;//结果长度
//	int decode_len = 0;//解码长度
//	int err_code = 0;
//	if (NULL == jdata || NULL == joutput)
//		return 0;
//	while(size > 0)
//	{
//		bodyLen = ((opus_int16 *)jdata)[0];
//		LOGD("decode bodylen[%d][%04X] short[%hd] jdata[0][%02X] jdata[1][%02X]", bodyLen, bodyLen, ((opus_int16 *)jdata)[0], jdata[0], jdata[1]);
//		if(bodyLen < 0)
//		{
//			err_code = -1;
//			break;
//		}
//		int channels = opus_packet_get_nb_channels((unsigned char *)jdata + 2);
//		LOGD("decode channels[%d]", channels);
//		if(frame_size * channels * sizeof(opus_int16) > max_out_len - out_len)
//		{
//			LOGD("decode out buff not enough, target[%d] remain[%d]", frame_size * channels * sizeof(opus_int16), max_out_len - out_len);
//			break;
//		}
//		decode_len = opus_decode(decoder, (unsigned char *)jdata + 2, bodyLen, (opus_int16 *)joutput, frame_size, 0);
//		LOGD("decode decode_len[%d]", decode_len);
//		if(decode_len != frame_size)
//		{
//			LOGD("decode fail ret[%d]", decode_len);
////			err_code = -1;
//			break;
//		}
//		out_len += frame_size;
//		jdata += (bodyLen + 2);
//		joutput += frame_size;
//		size -= (bodyLen + 2);
//	}
//	env->SetByteArrayRegion(output, 0, out_len * 2, joutput);
//	env->SetByteArrayRegion(remain, 0, size, jdata);
//	env->ReleaseByteArrayElements(data, jdata, 0);
//	env->ReleaseByteArrayElements(output, joutput, 0);
//	if(err_code < 0) return err_code;
//	return out_len * 2;
//}

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    decode
 */
JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_decode(JNIEnv * env,
		jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output, jint frame_size) {
//	LOGD("decode session[%lld] size[%d]", session, size);
	OpusDecoder* decoder = (OpusDecoder*) (void*) session;
	if (NULL == decoder)
		return 0;
	jbyte* jdata = env->GetByteArrayElements(data, NULL);
	jbyte* joutput = env->GetByteArrayElements(output, NULL);
	if (NULL == jdata || NULL == joutput)
		return 0;
	int len = opus_decode(decoder, (unsigned char *)jdata, size, (opus_int16 *)joutput, frame_size, 0);
	if(len < 0) {
		env->ReleaseByteArrayElements(data, jdata, 0);
		env->ReleaseByteArrayElements(output, joutput, 0);
		return len;
	}
	env->SetByteArrayRegion(output, 0, len * 2, joutput);
	env->ReleaseByteArrayElements(data, jdata, 0);
	env->ReleaseByteArrayElements(output, joutput, 0);
	return len * 2;
}

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    encode
 */
JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_encode(JNIEnv * env,
		jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output, jint frame_size) {
//	LOGD("1encode session[%lld] size[%d]", session, size);
	OpusEncoder* encoder = (OpusEncoder*) ((void*) session);
	if (NULL == encoder)
		return -1;
	jbyte* jdata = env->GetByteArrayElements(data, NULL);
	jbyte* joutput = env->GetByteArrayElements(output, NULL);
	int max_data_bytes = env->GetArrayLength(output);
	opus_int16 * frame_buf = (opus_int16 *)jdata;
	if (NULL == jdata || NULL == joutput)
		return 0;
	opus_int32 len = opus_encode(encoder, frame_buf, frame_size, (unsigned char*)joutput, max_data_bytes);
	if(len > 0){
		env->SetByteArrayRegion(output, 0, len, joutput);
	}
	env->ReleaseByteArrayElements(data, jdata, 0);
	env->ReleaseByteArrayElements(output, joutput, 0);
	return len;
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

#endif

