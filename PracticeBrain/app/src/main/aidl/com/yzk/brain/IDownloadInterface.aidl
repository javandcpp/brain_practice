// IDownloadInterface.aidl
package com.yzk.brain;

// Declare any non-default types here with import statements

interface IDownloadInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);



    void downLoadMusic(int method, int media_type, String url, String name, long fileLenght, int version, String cachedPath);


}
