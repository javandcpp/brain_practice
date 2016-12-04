package com.yzk.practice_brain.bean;

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

        @Override
        public String toString() {
            return "TableEntity{" +
                    "fiveContentView=" + fiveContentView +
                    '}';
        }
    }

    public class Table implements Serializable{
        public String key;
        public String value;
        public boolean flag;

        @Override
        public String toString() {
            return "Table{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }




}
