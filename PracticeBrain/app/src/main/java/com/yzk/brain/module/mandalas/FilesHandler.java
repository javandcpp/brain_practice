// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.brain.module.mandalas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

public class FilesHandler {
    public final String EXTFILE;
    public final String MYDOCFOLDER;
    public final String RAIZ;
    File dir;

    public FilesHandler() {
        super();
        this.MYDOCFOLDER = "mydocfolder";
        this.RAIZ = Environment.getExternalStorageDirectory().getPath();
        this.EXTFILE = ".png";
        this.dir = new File(RAIZ + "/Mandalas2/Temp/");
    }

    public void ArmarDirs() {
        new File(RAIZ + "/Mandalas2/Temp").mkdirs();
        new File(RAIZ + "/Mandalas2/MyGalery").mkdirs();
    }

    public boolean BorrarImagenGallery(String mandalaTag) {
        return new File(new File(RAIZ + "/Mandalas2/MyGalery"), mandalaTag).delete();
    }

    public boolean ComprobarDuplicidadGallery(String mandalaTag) {
        boolean v5 = true;
        File[] v1 = new File(RAIZ + "/Mandalas2/MyGalery").listFiles();
        Boolean v0 = Boolean.valueOf(false);
        int v2;
        for(v2 = 0; v2 < v1.length; ++v2) {
            if(v1[v2].getName().replace(".png", "").trim().equalsIgnoreCase(mandalaTag.trim())) {
                v0 = Boolean.valueOf(true);
            }
        }

        if(!v0.booleanValue()) {
            v5 = false;
        }

        return v5;
    }

    public void DeleteTempFile() {
        if(this.dir.isDirectory()) {
            String[] v0 = this.dir.list();
            int v1;
            for(v1 = 0; v1 < v0.length; ++v1) {
                new File(this.dir, v0[v1]).delete();
            }
        }
    }

    public void SaveDrawing(String tipo, ImageView imagen, String mandalaTag) {
        File destDir = null;
        String dirName = Environment.getExternalStorageDirectory().getPath();
        String fileName = String.valueOf(mandalaTag) + ".png".replace(".png.png",".png");
        if(tipo == "temp") {
            destDir = new File(String.valueOf(dirName) + "/Mandalas2/Temp");
        }
        else if(tipo == "miGaleria") {
            destDir = new File(String.valueOf(dirName) + "/Mandalas2/MyGalery");
        }

        destDir.mkdirs();
        File v2 = new File(destDir, fileName);
        try {
            BitmapFactory.Options v6 = new BitmapFactory.Options();
            v6.inPreferredConfig = Bitmap.Config.ARGB_8888;
            v6.inPurgeable = true;
            v6.inInputShareable = true;
            v6.inMutable = true;
            FileOutputStream v7 = new FileOutputStream(v2);
            imagen.buildDrawingCache();
            Bitmap.createBitmap(((int)(((float)imagen.getDrawingCache().getWidth()))), ((int)(((float)
                    imagen.getDrawingCache().getHeight()))), Bitmap.Config.ARGB_8888);
            Bitmap v1 = imagen.getDrawingCache();
            v1.compress(Bitmap.CompressFormat.PNG, 100,v7);
            v7.flush();
            v7.close();
            if(v1 != null) {
                v1.recycle();
            }

            Log.e("TAG","------------------>"+"save finish");
            imagen.destroyDrawingCache();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean comprobarGallery() {
        boolean v2 = false;
        if((new File(RAIZ + "/Mandalas2/MyGalery").exists()) && new File(RAIZ + "/Mandalas2/MyGalery").listFiles().length > 0) {
            v2 = true;
        }

        return v2;
    }

    public boolean comprobarTemp() {
        boolean v2 = true;
        File v1 = new File(RAIZ + "/Mandalas2/Temp");
        if(!v1.exists()) {
            v2 = false;
        }
        else if(v1.listFiles().length < 1) {
            v2 = false;
        }

        return v2;
    }

    public void comprobarTempyMostrarDibujo(ImageView imageview) {
//        if(this.comprobarTemp()) {
//            new BitmapWorkerTask(imageview).execute(new String[]{this.devolverNombreTemp(), "temp"});
//            imageview.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    v.getContext().startActivity(new Intent(v.getContext(), Mandalas2Activity.class));
//                }
//            });
//        }
    }

    public String devolverNombreTemp() {
        return String.valueOf(new File(RAIZ + "/Mandalas2/Temp").listFiles()[0]);
    }
}

