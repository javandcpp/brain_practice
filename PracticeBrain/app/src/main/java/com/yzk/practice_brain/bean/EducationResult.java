package com.yzk.practice_brain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 11/30/16.
 */

public class EducationResult implements Serializable {
//    {
//        "message": "查詢成功",
//            "data": [
//        {
//            "createTime": 1480401866000,
//                "exerciseId": 0,
//                "eduCounseId": 1,
//                "eduCounseTitle": "1",
//                "eduCounseContent": "1",
//                "eduCounseImg": "1"
//        }
//        ],
//        "code": "0"
//    }


    @Override
    public String toString() {
        return "EducationResult{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public String message;
    public String code;
    public List<EducationNews> data;

    public class EducationNews implements Serializable{
        public String exerciseId;
        public String eduCounseId;
        public String eduCounseTitle;
        public String eduCounseContent;
        public String eduCounseImg;
        public long createTime;
    }
}
