// IMediaInterface.aidl
package com.yzk.brain;

// Declare any non-default types here with import statements

interface IMediaInterface {

    boolean play();

    boolean stop();

    boolean pause();

    boolean playOther(String mediaName);

    void closeVolume();

    void openVolume();

    void adjustVolume(int volume);

    boolean isPlaying();

    boolean isPause();

    boolean isSilent();

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
