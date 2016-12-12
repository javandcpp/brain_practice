package com.yzk.brain.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by android on 12/2/16.
 */

public class SuTableResult implements Serializable {

    public String code;
    public String message;
    public TableEntity data;

    @Override
    public String toString() {
        return "SuTableResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public class TableEntity implements Serializable{

       public ArrayList<Table> fiveContentView;
        public Five five;

        @Override
        public String toString() {
            return "TableEntity{" +
                    "fiveContentView=" + fiveContentView +
                    '}';
        }
    }

    public class Five implements Serializable{
        public int errorNumber;
        public int fiveScore;
    }

    public class Table implements Serializable{
        public String key;
        public String value;
        public boolean flag;

        public boolean clicked;
        @Override
        public String toString() {
            return "Table{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }




}
