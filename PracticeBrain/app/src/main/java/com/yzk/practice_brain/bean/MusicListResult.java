package com.yzk.practice_brain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 12/1/16.
 */

public class MusicListResult implements Serializable {

    public String message;
    public String code;
    public List<MusicEntity> music;

    @Override
    public String toString() {
        return "MusicListResult{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", music=" + music +
                '}';
    }

    public class MusicEntity implements Serializable{

        public String url;
        public String name;
        public String id;
        public long fileLength;
        public int version;

        @Override
        public String toString() {
            return "MusicEntity{" +
                    "url='" + url + '\'' +
                    ", name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }
}
