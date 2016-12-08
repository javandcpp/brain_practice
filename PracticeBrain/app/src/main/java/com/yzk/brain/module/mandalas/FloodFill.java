// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.brain.module.mandalas;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

import java.util.LinkedList;
import java.util.Queue;

public class FloodFill {
    public FloodFill() {
        super();
    }
    public void floodFill(final Bitmap image, Point node, final int targetColor, final int replacementColor) {


//
//
        int v30 = image.getWidth();
        int v12 = image.getHeight();
        int v29 = targetColor;
        int v23 = replacementColor;
        int v28 = v29 >>> 16 & 255;
        int v27 = v29 >>> 8 & 255;
        int v26 = v29 >>> 0 & 255;
        int v22 = v23 >>> 16 & 255;
        int v21 = v23 >>> 8 & 255;
        int v20 = v23 >>> 0 & 255;
        if (v29 != v23) {
            LinkedList v19 = new LinkedList();
            do {
                int v31 = node.x;
                int v32 = node.y;
                while (v31 > 0 && image.getPixel(v31 - 1, v32) == targetColor && image.getPixel(v31 - 1, v32) != 0 && (image.getPixel(v31 - 1, v32) != v23)) {
                    --v31;
                }

                int v25 = 0;
                int v24 = 0;
                while (v31 < v30 && image.getPixel(v31, v32) == targetColor && image.getPixel(v31, v32) != 0
                        && (image.getPixel(v31, v32) != v23)) {
                    if (image.getPixel(v31, v32) == v29) {
                        image.setPixel(v31, v32, v23);
                    } else {
                        int v11 = image.getPixel(v31, v32);
                        image.setPixel(v31, v32, Color.argb(255, ((int) Math.ceil((((double) v22)) * ((((
                                double) (v11 >>> 16 & 255))) / (((double) v28))))), ((int) Math.ceil((((
                                double) v21)) * ((((double) (v11 >>> 8 & 255))) / (((double) v27))))), ((
                                int) Math.ceil((((double) v20)) * ((((double) (v11 >>> 0 & 255))) / (((
                                double) v26)))))));
                    }

                    if (v25 == 0 && v32 > 0 && image.getPixel(v31, v32 - 1) == v29) {
                        v19.add(new Point(v31, v32 - 1));
                        v25 = 1;
                    } else if (v25 != 0 && v32 > 0 && image.getPixel(v31, v32 - 1) != v29) {
                        v25 = 0;
                    }

                    if (v24 == 0 && v32 < v12 - 1 && image.getPixel(v31, v32 + 1) == v29) {
                        v19.add(new Point(v31, v32 + 1));
                        v24 = 1;
                    } else if (v24 != 0 && v32 < v12 - 1 && image.getPixel(v31, v32 + 1) != v29) {
                        v24 = 0;
                    }

                    ++v31;
                }

//                Object v39 = ((Queue)v19).poll();
//                if(v39 != null) {
//                    continue;
//                }
//
//                return;
            }
            while ((node = (Point) ((Queue) v19).poll()) != null);
        }
    }
}

