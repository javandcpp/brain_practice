// IDownloadInterface.aidl
package com.yzk.practice_brain;

// Declare any non-default types here with import statements
import com.yzk.practice_brain.busevent.DownloadMusicEvent;

interface IDownloadInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);



    void downLoadMusic(in DownloadMusicEvent downloadMusicEvent);


}
