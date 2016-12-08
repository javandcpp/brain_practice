package com.yzk.brain.bean;

import java.io.Serializable;

/**
 * Created by android on 12/5/16.
 */

public class RuleResult implements Serializable {
    public String message;
    public String code;
    public RuleEntity data;

    @Override
    public String toString() {
        return "RuleResult{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public class RuleEntity{
        public String typeRulesContent;

        @Override
        public String toString() {
            return "RuleEntity{" +
                    "typeRulesContent='" + typeRulesContent + '\'' +
                    '}';
        }
    }
}
