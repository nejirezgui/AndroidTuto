#include <jni.h>
#include <string>
#include <stdlib.h>

#include <android/log.h>


#define  MAX_BUFFER_SIZE  1024


extern "C"

JNICALL void

Java_com_example_win_myapplication_MainActivity_saveFile(
        JNIEnv *env,
        jstring obj,
        jstring filename,
        jstring content) {


    const char *fstr = env->GetStringUTFChars(filename, 0);
    const char *str = env->GetStringUTFChars(content, 0);

    FILE *f = fopen(fstr, "w");
    char *err;
    if (f == NULL) {
        err = strerror(errno);
    } else {


        fwrite(str, 1, strlen(str), f);
        fclose(f);
    }


}
extern "C"
jstring

Java_com_example_win_myapplication_MainActivity_openFile(
        JNIEnv *env,
        jstring obj,
        jstring filename
) {


    const char *fstr = env->GetStringUTFChars(filename, 0);
    char buffer[MAX_BUFFER_SIZE];

    FILE *f = fopen(fstr, "r");
    char *err;
    if (f == NULL) {

        err = strerror(errno);

        __android_log_print(ANDROID_LOG_DEBUG, " fopen error = ", "err");

    } else {

        __android_log_print(ANDROID_LOG_DEBUG, "fopen OK   ", "OK"); //just for debugging


        int l = 0;
        while (!feof(f)) {


            buffer[l++] = fgetc(f);

        }

        buffer[l - 1] = 0;
        fclose(f);


    }

    return env->NewStringUTF(buffer);
}
