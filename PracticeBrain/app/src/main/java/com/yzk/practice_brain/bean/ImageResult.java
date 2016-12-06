package com.yzk.practice_brain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 12/6/16.
 */

public class ImageResult implements Serializable {

    public ImageEntity data;

    public class ImageEntity{
      public List<Image> list;
    }
    public class Image{
        public String key;
        public String url;
    }

}
