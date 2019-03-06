#ifndef _Included_com_txznet_opus_OpusSDK
#define _Included_com_txznet_opus_OpusSDK

#ifdef ANDROID
#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    create
 */
JNIEXPORT jlong JNICALL Java_com_txznet_opus_OpusSDK_create
  (JNIEnv * env, jclass clazz, jint type, jint sampleRate, jint channels);

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    encode
 */
JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_encode
  (JNIEnv * env, jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output, jint frame_size);

/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    decode
 */
JNIEXPORT jint JNICALL Java_com_txznet_opus_OpusSDK_decode
  (JNIEnv * env, jclass clazz, jlong session, jbyteArray data, jint size, jbyteArray output,
		  jint frame_size);


/*
 * Class:     com_txznet_opus_OpusSDK
 * Method:    destroy
 */
JNIEXPORT void JNICALL Java_com_txznet_opus_OpusSDK_destroy
	(JNIEnv * env, jclass clazz, jint type, jlong session);

#ifdef __cplusplus
}
#endif
#endif
#endif
