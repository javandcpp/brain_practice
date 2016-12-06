package com.yzk.practice_brain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 12/6/16.
 */

public class ExplainResult implements Serializable {

    public String message;
    public String code;
    public List<Explain> data;

//    {"name":"2.mp3",
// "url":"http://123.56.190.160:8999/kujisoftware/learnExplain/downloadLearnExplainVoice?id=1016","
// imgUrl":"http://123.56.190.160:8999/kujisoftware/upload/explain/2.png",
// "id":1016,"
// fileLength":4194304}]

    public class Explain {
        public String name;
        public int version;
        public String url;
        public String imgUrl;
        public int id;
        public long fileLength;
    }

}
