package com.yzk.brain.bean;

import java.io.Serializable;

/**
 * Created by android on 12/5/16.
 */

public class HelpResult implements Serializable {
    public String message;
    public String code;
    public HelpEntity data;

    @Override
    public String toString() {
        return "HelpResult{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public class HelpEntity{
        public String helpContent;

        @Override
        public String toString() {
            return "HelpEntity{" +
                    "helpContent='" + helpContent + '\'' +
                    '}';
        }
    }
}
