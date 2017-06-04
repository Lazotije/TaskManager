#include "procenti.h"

#include <jni.h>
#include <math.h>


JNIEXPORT jfloat JNICALL Java_aleksandarlazic_ra1042014_example_com_taskmanager_NativeClass_racunajProcente
  (JNIEnv *env, jobject obj, jfloat zavrseni, jfloat ukupno)
{
    return (jfloat) ((zavrseni/ukupno)*100);
}
