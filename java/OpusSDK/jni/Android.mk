LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := opus_sdk
LOCAL_SRC_FILES := opus_sdk.cpp
LOCAL_LDLIBS := -L$(LOCAL_PATH) -lopus_static -llog

include $(BUILD_SHARED_LIBRARY)