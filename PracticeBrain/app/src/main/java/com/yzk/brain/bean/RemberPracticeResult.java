package com.yzk.brain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 12/5/16.
 */

public class RemberPracticeResult implements Serializable {

    public String message;
    public String code;
    public PracticeEntity data;

    @Override
    public String toString() {
        return "RemberPracticeResult{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public class MemoryTrain implements Serializable{

        public int memoryTrainErrorNumber;
        public int memoryTrainScore;
    }


    public class PracticeEntity implements Serializable {

        public MemoryTrain memoryTrain;
        public List<Practice> memoryTrainWordsView;

        @Override
        public String toString() {
            return "PracticeEntity{" +
                    "memoryTrainWordsView=" + memoryTrainWordsView +
                    '}';
        }
    }

    public class Practice implements Serializable {
        public String key;

        @Override
        public String toString() {
            return "Practice{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        public String value;

    }
}
