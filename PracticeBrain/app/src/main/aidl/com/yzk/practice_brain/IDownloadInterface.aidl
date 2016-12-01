// IDownloadInterface.aidl
package com.yzk.practice_brain;

// Declare any non-default types here with import statements

interface IDownloadInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);



    void downLoadMusic(String name);


}
