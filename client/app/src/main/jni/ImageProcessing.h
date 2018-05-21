//
// Created by Akira on 2/25/2018.
//
#include <jni.h>
#ifndef CLIENT_IMAGEPROCESSING_H
#define CLIENT_IMAGEPROCESSING_H
#ifdef __cplusplus

extern "C" {
#endif //CLIENT_IMAGEPROCESSING_H



JNIEXPORT jboolean JNICALL Java_floatingheads_snapclone_activities_CVImgProcCameraActivity_ImageProcessing(
        JNIEnv* env, jobject thiz,
        jint width, jint height,
        jbyteArray NV21FrameData, jintArray outPixels);



#ifdef __cplusplus
}
#endif
#endif