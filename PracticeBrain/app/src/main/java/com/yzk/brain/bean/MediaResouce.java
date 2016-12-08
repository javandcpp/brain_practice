package com.yzk.brain.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 11/18/16.
 */

public class MediaResouce implements Parcelable {

    public String path;
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
    }

    public MediaResouce() {
    }

    protected MediaResouce(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
    }

    public static final Creator<MediaResouce> CREATOR = new Creator<MediaResouce>() {
        @Override
        public MediaResouce createFromParcel(Parcel source) {
            return new MediaResouce(source);
        }

        @Override
        public MediaResouce[] newArray(int size) {
            return new MediaResouce[size];
        }
    };
}
