package com.yzk.brain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 12/6/16.
 */

public class ImageResult implements Serializable {

    public ImageEntity data;

    public class ImageEntity implements Serializable{
      public PictureMemory pictureMemory;
      public List<Image> list;
    }
    public class Image implements Serializable{
        public String key;
        public String url;
    }

    public class PictureMemory implements Serializable{
        public int pictureMemoryErrorNumber;
        public int pictureMemoryScore;

    }

}
