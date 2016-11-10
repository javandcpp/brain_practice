package com.yzk.practice_brain.module.mandalas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.yzk.practice_brain.R;

import java.io.File;
import java.util.Random;

public class Mandalas2Activity extends Activity implements Animation.AnimationListener, ColorPicker.OnColorChangedListener {

    public static int[][] v0 = new int[9][8];

    static {
        v0[0][0] = Color.parseColor("#760605");
        v0[0][1] = Color.parseColor("#83111f");
        v0[0][2] = Color.parseColor("#981a1e");
        v0[0][3] = Color.parseColor("#aa1c25");
        v0[0][4] = Color.parseColor("#bb0724");
        v0[0][5] = Color.parseColor("#d44929");
        v0[0][6] = Color.parseColor("#f33004");
        v0[0][7] = Color.parseColor("#f7531d");
        v0[1][0] = Color.parseColor("#ee4a28");
        v0[1][1] = Color.parseColor("#cb5934");
        v0[1][2] = Color.parseColor("#f89920");
        v0[1][3] = Color.parseColor("#f5a014");
        v0[1][4] = Color.parseColor("#ee9e03");
        v0[1][5] = Color.parseColor("#f79120");
        v0[1][6] = Color.parseColor("#f9c32f");
        v0[1][7] = Color.parseColor("#efd905");
        v0[2][0] = Color.parseColor("#ffc41c");
        v0[2][1] = Color.parseColor("#c9a644");
        v0[2][2] = Color.parseColor("#d8c73a");
        v0[2][3] = Color.parseColor("#fee301");
        v0[2][4] = Color.parseColor("#f4e989");
        v0[2][5] = Color.parseColor("#febf0d");
        v0[2][6] = Color.parseColor("#fbd44f");
        v0[2][7] = Color.parseColor("#fae1a4");
        v0[3][0] = Color.parseColor("#18744b");
        v0[3][1] = Color.parseColor("#008235");
        v0[3][2] = Color.parseColor("#67b04c");
        v0[3][3] = Color.parseColor("#b3d734");
        v0[3][4] = Color.parseColor("#87bc82");
        v0[3][5] = Color.parseColor("#5ab751");
        v0[3][6] = Color.parseColor("#9f9d27");
        v0[3][7] = Color.parseColor("#f3f918");
        v0[4][0] = Color.parseColor("#0054aa");
        v0[4][1] = Color.parseColor("#0089de");
        v0[4][2] = Color.parseColor("#a4b9e5");
        v0[4][3] = Color.parseColor("#007ec3");
        v0[4][4] = Color.parseColor("#cbedfb");
        v0[4][5] = Color.parseColor("#0e398b");
        v0[4][6] = Color.parseColor("#0378ce");
        v0[4][7] = Color.parseColor("#437090");
        v0[5][0] = Color.parseColor("#3d106e");
        v0[5][1] = Color.parseColor("#602c91");
        v0[5][2] = Color.parseColor("#8773b2");
        v0[5][3] = Color.parseColor("#997ab8");
        v0[5][4] = Color.parseColor("#b678c6");
        v0[5][5] = Color.parseColor("#dc4fff");
        v0[5][6] = Color.parseColor("#d59ae4");
        v0[5][7] = Color.parseColor("#ece0ef");
        v0[6][0] = Color.parseColor("#760076");
        v0[6][1] = Color.parseColor("#932393");
        v0[6][2] = Color.parseColor("#c330c3");
        v0[6][3] = Color.parseColor("#ff00ff");
        v0[6][4] = Color.parseColor("#e223e2");
        v0[6][5] = Color.parseColor("#ff68ff");
        v0[6][6] = Color.parseColor("#feaefe");
        v0[6][7] = Color.parseColor("#e7cde7");
        v0[7][0] = Color.parseColor("#ff0000");
        v0[7][1] = Color.parseColor("#ffff00");
        v0[7][2] = Color.parseColor("#00ff00");
        v0[7][3] = Color.parseColor("#00ffff");
        v0[7][4] = Color.parseColor("#0000ff");
        v0[7][5] = Color.parseColor("#ff00ff");
        v0[7][6] = Color.parseColor("#626262");
        v0[7][7] = Color.parseColor("#f1690e");
        v0[8][0] = Color.parseColor("#f1690e");
        v0[8][1] = Color.parseColor("#feee00");
        v0[8][2] = Color.parseColor("#009045");
        v0[8][3] = Color.parseColor("#11febe");
        v0[8][4] = Color.parseColor("#009bdb");
        v0[8][5] = Color.parseColor("#5b400a");
        v0[8][6] = Color.parseColor("#a28356");
        v0[8][7] = Color.parseColor("#db9600");
    }


    public class EscalaImagen extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public EscalaImagen() {
//            Mandalas2Activity.this = arg1;
            super();
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float v7 = 3.75f;
            float v6 = 0.75f;
            float v4 = detector.getScaleFactor();
            float v0 = Mandalas2Activity.this.imagen.getScaleX();
            float v1 = Mandalas2Activity.this.imagen.getScaleY();
            float v2 = v0 * v4;
            float v3 = v1 * v4;
            if (v2 >= v7 || v3 >= v7) {
                v4 = 0.99f;
            }

            if (v2 <= v6 || v3 <= v6) {
                v4 = 1.01f;
            }

            Mandalas2Activity.this.imagen.setScaleX(v0 * v4);
            Mandalas2Activity.this.imagen.setScaleY(v1 * v4);
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        int distanciaX;
        int distanciaY;
        private Point point;

        MyGestureDetector() {
//            Mandalas2Activity.this = arg2;
            super();
            this.distanciaX = 0;
            this.distanciaY = 0;
        }

        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return true;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            this.distanciaX = ((int) distanceX);
            this.distanciaY = ((int) distanceY);
            Mandalas2Activity.this.imagen.scrollBy(((int) distanceX), ((int) distanceY));
            return true;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onSingleTapUp(MotionEvent event) {
            Mandalas2Activity.this.imagen.getLocationOnScreen(new int[2]);
            Matrix v1 = new Matrix();
            float[] v2 = new float[]{event.getX(), event.getY()};
            Mandalas2Activity.this.imagen.setImageMatrix(v1);
            Mandalas2Activity.this.imagen.getImageMatrix().invert(v1);
            v1.preTranslate(((float) Mandalas2Activity.this.imagen.getScrollX()), ((float) Mandalas2Activity
                    .this.imagen.getScrollY()));
            v1.mapPoints(v2);
            int v5 = ((int) v2[0]);
            int v6 = ((int) v2[1]);
            int v4 = Mandalas2Activity.this.imagen.getLeft() + Mandalas2Activity.this.b.getWidth();
            int v0 = Mandalas2Activity.this.imagen.getTop() + Mandalas2Activity.this.b.getHeight();
            this.point = new Point(v5, v6);
            if (v6 < v0 && v5 < v4) {
                Mandalas2Activity.this.oldColor = Mandalas2Activity.this.b.getPixel(v5, v6);
                if (Mandalas2Activity.this.efectos) {
                    Mandalas2Activity.this.fx.EfectoAlTocar();
                }

                if (Mandalas2Activity.this.eyedropper) {
                    Mandalas2Activity.this.newColor = Mandalas2Activity.this.b.getPixel(v5, v6);
                    Mandalas2Activity.goterito.setBackgroundResource(R.drawable.pick);
                    Mandalas2Activity.this.eyedropper = false;
                    return true;
                }

                if (Mandalas2Activity.this.newColor == Color.parseColor("#FF000000")) {
                    Mandalas2Activity.this.newColor = Mandalas2Activity.this.SUSTITUTONEGRO;
                }
                Mandalas2Activity.this.fd.floodFill(Mandalas2Activity.this.b, this.point, oldColor, Mandalas2Activity.this.newColor);
                Mandalas2Activity.this.imagen.invalidate();
            }

            return true;
        }
    }

    final float MAXSCALE;
    int MAX_SAVING_FILES;
    final float MINSCALE;
    private static final String MY_AD_UNIT_ID = "ca-app-pub-0815276588564171/1181378103";
    final int SUSTITUTONEGRO;
    //    private AdView adView;
    Animation anim;
    AppRater apprater;
    private Bitmap b;
    public Canvas canvas;
    CustomDrawableView cdv;
    boolean comprobarasync;
    PictureDrawable d;
    Drawable dibujo;
    private Metricas dim;
    Drawable drawable;
    public boolean efectos;
    String extFile;
    boolean eyedropper;
    FloodFill fd;
    private FrameLayout flayout;
    EfectosSonoros fx;
    private GestureDetector gestureDetector;
    private static Button goterito;
    Boolean gradient;
    private FilesHandler handler;
    private ImageView imagen;
    private String mDrawableName;
    int mandalaGroup;
    int mandalaIdNum;
    int mandalaMax;
    String mandalaName;
    int mandalaNum;
    String mandalaString;
    String mandalaTag;
    MemoryManager mem;
    Menu menu;
    String mess;
    private int newColor;
    private int numpaleta;
    private int oldColor;
    GrabarPreferencias prefs;
    int proximoValor;
    String raiz;
    boolean runningImagen;
    boolean runningPaletas;
    Sonidos s;
    private ScaleGestureDetector scaleGestureDetector;
    private View.OnTouchListener scaleGestureListener;
    SVG svg;
    public Path svgparsdtopath;
    public boolean tocaMusica;
    ViewFlipper vflip;

    public Mandalas2Activity() {
        super();
        this.mem = new MemoryManager();
        this.mandalaGroup = 0;
        this.mandalaNum = 1;
        this.mandalaTag = "";
        this.mandalaMax = 8;
        this.proximoValor = 1;
        this.MAX_SAVING_FILES = 5;
        this.efectos = false;
        this.tocaMusica = false;
        this.MAXSCALE = 3.75f;
        this.MINSCALE = 0.75f;
        this.extFile = ".png";
        this.raiz = Environment.getExternalStorageDirectory().getPath();
        this.handler = new FilesHandler();
        this.mDrawableName = "mandala";
        this.numpaleta = 0;
        this.SUSTITUTONEGRO = Color.parseColor("#121211");
        this.newColor = -65536;
        this.oldColor = -1;
        this.eyedropper = false;
        this.gradient = Boolean.valueOf(false);
        this.dim = new Metricas();
        this.runningPaletas = false;
        this.runningImagen = false;
        this.fd = new FloodFill();
        this.comprobarasync = false;
    }


    private void ads() {
//        this.adView = new AdView(((Context)this));
//        this.adView.setAdUnitId("ca-app-pub-0815276588564171/1181378103");
//        this.adView.setAdSize(AdSize.SMART_BANNER);
//        this.findViewById(2131165199).addView(this.adView);
//        this.adView.loadAd(new Builder().addTestDevice("D3E1141FE77945F61166D3E4B21B66D4").addTestDevice(
//                "64C65EF40443349A729FF4121EA8759F").build());
    }

    public void animarConEscala(View v) {
        this.anim = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.animcolores);
        v.startAnimation(this.anim);
    }

    private void cargarImagen() {
        this.runningImagen = true;
        Intent v10 = this.getIntent();
        this.mandalaTag = v10.hasExtra("numero-archivoGuardado") ? v10.getStringExtra("numero-archivoGuardado")
                : v10.getStringExtra("mandala-tag");


//        this.handler.comprobarTempyMostrarDibujo(this.imagen);

        this.flayout = (FrameLayout) this.findViewById(R.id.framelayout);
        BitmapFactory.Options v13 = new BitmapFactory.Options();
        v13.inPreferredConfig = Bitmap.Config.ARGB_8888;
        v13.inPurgeable = true;
        v13.inInputShareable = true;
        v13.inDither = true;
        v13.inMutable = true;
        if (this.handler.comprobarTemp()) {
            String v11 = this.handler.devolverNombreTemp();
            this.mandalaTag = new File(v11).getName();
            this.b = BitmapFactory.decodeFile(v11, v13);
            this.imagen.setImageBitmap(this.b);
        } else if (v10.hasExtra("numero-archivoGuardado")) {
            String.valueOf(this.mandalaTag);
            this.b = BitmapFactory.decodeFile(new File(String.valueOf(Environment.getExternalStorageDirectory()
                    .getPath()) + "/Mandalas2/MyGalery/" + this.mandalaTag).getAbsolutePath(), v13);

        } else {
            final Display v7 = this.getWindowManager().getDefaultDisplay();
            new Point();
            int v17 = Mandalas2Activity.getDisplaySize(v7).x;
            Mandalas2Activity.getDisplaySize(v7);
            this.mandalaTag = this.mandalaTag.replace(".png", "");
            this.mDrawableName = this.mandalaTag;
            this.b = Bitmap.createBitmap(v17, v17, Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas() {
                public void drawPath(Path path, Paint paint) {
                    if (v7.getWidth() < 768) {
                        paint.setAntiAlias(true);
                    }
                    paint.setStyle(Paint.Style.FILL);
//                    paint.setColor(-1);
//                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(0xFF000000);
                    paint.setStrokeJoin(Paint.Join.ROUND);
                    paint.setStrokeMiter(10f);
                    paint.setStrokeWidth(1f);

                    Log.e("TAG", "------------------------------>>" + path.toString());
                    super.drawPath(path, paint);
                }
            };
            this.cdv = new CustomDrawableView(this, this.mDrawableName, ((float) v17));
            this.cdv.draw(this.canvas);

        }


        this.imagen.setDrawingCacheEnabled(true);
        Log.e("TAG", "------------------------------>>" + "set drawingCache");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.SaveDrawing("temp", imagen, mandalaTag);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setImage();
                    }
                }, 300);

            }
        }, 300);

//        setImage();
        this.mandalaTag = this.mandalaTag.replace(".png", "");

        View v8 = this.findViewById(R.id.TextoNombreMandala);
        ((TextView) v8).setText(this.mandalaTag.replace("_", " ").replace(".png", ""));
        ((TextView) v8).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.animmandalatag));
    }


    public void setImage() {
        BitmapFactory.Options v13 = new BitmapFactory.Options();
        v13.inPreferredConfig = Bitmap.Config.ARGB_8888;
        v13.inPurgeable = true;
        v13.inInputShareable = true;
        v13.inDither = true;
        v13.inMutable = true;
        Log.e("TAG", "------------------------------>>" + "set image");
        if (this.handler.comprobarTemp()) {
            String v11 = this.handler.devolverNombreTemp();
            this.mandalaTag = new File(v11).getName();
            Log.e("TAG", "------------------------------>>" + "set image:" + mandalaTag);
            this.b = BitmapFactory.decodeFile(v11, v13);
            this.imagen.setImageBitmap(this.b);
        }
    }

    private void centrarImagen() {
        this.imagen.scrollTo(0, 0);
    }

    public void colorChanged(String key, int color) {
        this.newColor = color;
    }

    public void getColor(View v) {
        new ColorPicker(this, this, "", -16777216, -1).show();
    }

    private static Point getDisplaySize(Display display) {
        Point v1 = new Point();
        try {
            display.getSize(v1);
        } catch (NoSuchMethodError v0) {
            v1.x = display.getWidth()-100;
            v1.y = display.getHeight()-100;
        }

        return v1;
    }


    public void onAnimationEnd(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "------------------------------>>" + "onCreate");
        this.setContentView(R.layout.activity_mandalas2);
        this.prefs = new GrabarPreferencias(((Context) this));
        this.efectos = this.prefs.getEfectos();
        this.findViewById(R.id.nextPaleta).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity.this.anim = AnimationUtils.loadAnimation(Mandalas2Activity.this.getApplicationContext(),
                        R.anim.swipe_right_to_left);
                Mandalas2Activity.this.vflip.startAnimation(Mandalas2Activity.this.anim);
                if (Mandalas2Activity.this.efectos) {
                    Mandalas2Activity.this.fx.EfectoAlTocar();
                }

                Mandalas2Activity.this.vflip.showNext();
            }
        });
        final Button color = (Button) findViewById(R.id.color);
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random random = new Random();
                Random random1 = new Random();
                int i = random.nextInt(9);
                int j = random.nextInt(8);

                if (Mandalas2Activity.this.efectos) {
                    Mandalas2Activity.this.fx.EfectoAlTocar();
                }

                Mandalas2Activity.this.newColor = v0[i][j];
                color.setBackgroundColor(newColor);
            }
        });

        this.imagen = (ImageView) this.findViewById(R.id.imagen2);
        final View v1 = this.findViewById(R.id.gomaborrar);
        ((Button) v1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity.this.anim = AnimationUtils.loadAnimation(Mandalas2Activity.this.getApplicationContext(),
                        R.anim.animcolores);
                v1.startAnimation(Mandalas2Activity.this.anim);
                Mandalas2Activity.this.newColor = -1;
            }
        });
        Button btn = (Button) findViewById(R.id.buttoncolorpick);
        btn.setBackgroundColor(newColor);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Mandalas2Activity.this.efectos) {
                    Mandalas2Activity.this.fx.EfectoAlTocar();
                }

                Mandalas2Activity.this.getColor(v);
            }
        });
        this.findViewById(R.id.aIndice2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity.this.startActivity(new Intent(Mandalas2Activity.this.getApplicationContext(),
                        Indice.class));
            }
        });
        this.findViewById(R.id.rotation).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity.this.anim = AnimationUtils.loadAnimation(Mandalas2Activity.this.getApplicationContext(),
                        R.anim.rotate);
                Mandalas2Activity.this.imagen.startAnimation(Mandalas2Activity.this.anim);
            }
        });
        this.findViewById(R.id.rotation2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity.this.anim = AnimationUtils.loadAnimation(Mandalas2Activity.this.getApplicationContext(),
                        R.anim.rotate2);
                Mandalas2Activity.this.imagen.startAnimation(Mandalas2Activity.this.anim);
            }
        });
        this.findViewById(R.id.centrado).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity.this.centrarImagen();
            }
        });
        Mandalas2Activity.goterito = (Button) this.findViewById(R.id.gotero);
        Mandalas2Activity.goterito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Mandalas2Activity.this.eyedropper) {
                    Mandalas2Activity.goterito.setBackgroundResource(R.drawable.pick);
                    Mandalas2Activity.this.eyedropper = false;
                } else {
                    Mandalas2Activity.goterito.setBackgroundResource(R.drawable.pick1);
                    Mandalas2Activity.this.eyedropper = true;
                }
            }
        });
        this.prenderEfectos();
        View v5 = this.findViewById(R.id.efectos);
        if (this.efectos) {
            ((Button) v5).setBackgroundResource(R.drawable.soundon);
        } else {
            ((Button) v5).setBackgroundResource(R.drawable.soundoff);
        }

        ((Button) v5).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity v1 = Mandalas2Activity.this;
                boolean v0 = Mandalas2Activity.this.efectos ? false : true;
                v1.efectos = v0;
                if (Mandalas2Activity.this.efectos) {
                    Mandalas2Activity.this.prenderEfectos();
                    v.setBackgroundResource(R.drawable.soundon);
                } else {
                    Mandalas2Activity.this.fx.clickStop();
                    Mandalas2Activity.this.fx = null;
                    v.setBackgroundResource(R.drawable.soundoff);
                }
            }
        });
        this.findViewById(R.id.musica).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity v1 = Mandalas2Activity.this;
                boolean v0 = Mandalas2Activity.this.tocaMusica ? false : true;
                v1.tocaMusica = v0;
                if (Mandalas2Activity.this.tocaMusica) {
                    Mandalas2Activity.this.prenderLluvia();
                    Mandalas2Activity.this.s.PlayMusic();
                    v.setBackgroundResource(R.drawable.musicon);
                } else {
                    Mandalas2Activity.this.s.StopMusic();
                    v.setBackgroundResource(R.drawable.musicoff);
                    Mandalas2Activity.this.s = null;
                }
            }
        });
        this.findViewById(R.id.grabar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mandalas2Activity.this.animarConEscala(v);
                if (new File(String.valueOf(Mandalas2Activity.this.raiz) + "/Mandalas2/MyGalery").listFiles()
                        .length < Mandalas2Activity.this.MAX_SAVING_FILES) {
                    Mandalas2Activity.this.centrarImagen();
                    Mandalas2Activity.this.handler.SaveDrawing("miGaleria", Mandalas2Activity.this.imagen,
                            Mandalas2Activity.this.mandalaTag);
                } else if (Mandalas2Activity.this.handler.ComprobarDuplicidadGallery(Mandalas2Activity.
                        this.mandalaTag)) {
                    Mandalas2Activity.this.centrarImagen();
                    Mandalas2Activity.this.handler.SaveDrawing("miGaleria", Mandalas2Activity.this.imagen,
                            Mandalas2Activity.this.mandalaTag);
                } else {
                    new DialogsHandler(Mandalas2Activity.this).infoLimiteGrabado(Mandalas2Activity.this);
                }
            }
        });
        this.apprater = new AppRater(((Context) this));
        this.apprater.app_launched(((Context) this));
    }


    public void onDestroy() {
        Log.e("TAG", "------------------------------>>" + "onDestroy");
        super.onDestroy();
        if ((this.tocaMusica) && this.s != null) {
            this.s = null;
        }
        if (this.fx != null) {
            this.fx = null;
        }
    }


    public void onResume() {
        super.onResume();
        Log.e("TAG", "------------------------>" + "onResume");

        this.numpaleta = 0;
        if (!this.runningImagen) {
            this.cargarImagen();
        }


        this.gestureDetector = new GestureDetector(((Context) this), new MyGestureDetector());
//        this.scaleGestureDetector = new ScaleGestureDetector(((Context)this), new EscalaImagen());
        this.scaleGestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    if (event.getPointerCount() < 2) {
                        Mandalas2Activity.this.gestureDetector.onTouchEvent(event);
                        return true;
                    }

//                    Mandalas2Activity.this.scaleGestureDetector.onTouchEvent(event);
                } catch (Exception v0) {
                }

                return true;
            }
        };
        this.imagen.setOnTouchListener(this.scaleGestureListener);
    }

    public void onStart() {
        super.onStart();
        Log.e("TAG", "------------------------>" + "onStart");
    }

    public void onPause() {
        Log.e("TAG", "------------------------------>>" + "onPause");
        ViewFlipper v4 = null;
        super.onPause();

        handler.SaveDrawing("temp", imagen, mandalaTag);
        if (this.tocaMusica) {
            this.tocaMusica = false;
            if (this.s != null) {
                this.s.StopMusic();
            }
        }

        if (this.prefs != null) {
            this.prefs.setEfectos(Boolean.valueOf(this.efectos));
        }

        if (this.vflip != null) {
            this.vflip = null;
        }

        this.runningImagen = false;
        this.runningPaletas = false;
        this.gestureDetector = null;
        this.scaleGestureListener = null;
        this.apprater = null;
    }

    public void onStop() {
        Log.e("TAG", "------------------------>" + "onStop");
        ImageView v1 = null;
        super.onStop();
        if (this.b != null) {
            this.b.recycle();
        }

        if (this.imagen != null) {
            this.imagen = null;
        }

        if (this.prefs != null) {
            this.prefs = null;
        }
    }

    private void prenderEfectos() {
        this.fx = new EfectosSonoros(((Context) this));
    }

    private void prenderLluvia() {
        this.s = new Sonidos(((Context) this));
    }


    /**
     * 自定义绘制Drawable
     */
    public class CustomDrawableView extends View {
        int anchocanvas;
        Paint mpaint;
        private String name;
        Rect rect;
        RectF rectang;
        SVG svg;


        public CustomDrawableView(Context context, String passedName, float
                ancho) {
            super(context);
            this.anchocanvas = 100;
            this.mpaint = new Paint();
            this.rect = new Rect(0, 0, 500, 500);
            this.anchocanvas = (((int) ancho)) - 30;
            this.name = passedName;
        }

        /**
         * 解析SVG
         */
        private void parseSVG() {
            try {
                Log.e("TAG", this.name);
                this.svg = SVG.getFromResource(this.getContext(), this.getResources().getIdentifier(
                        this.name, "raw", Mandalas2Activity.this.getPackageName()));
                if (this.svg.getDocumentWidth() == -1f) {
                    return;
                }

                this.svg.setDocumentWidth(((float) this.anchocanvas));
                this.svg.setDocumentHeight(((float) this.anchocanvas));
                this.svg.setDocumentPreserveAspectRatio(PreserveAspectRatio.STRETCH);
                this.svg.renderToCanvas(Mandalas2Activity.this.canvas, this.rectang);
                Mandalas2Activity.this.drawable = new PictureDrawable(this.svg.renderToPicture(this.
                        anchocanvas, this.anchocanvas));
                imagen.setImageDrawable(drawable);
            } catch (SVGParseException v0) {
                v0.printStackTrace();
            }
        }

        /**
         * 绘制矩形
         * @return
         */
        private RectF drawRectF() {
            return new RectF(0f, 0f, ((float) this.anchocanvas), ((float) this.anchocanvas));
        }

        /**
         * 绘制
         * @return
         */
        protected void onDraw(Canvas canvas) {
            this.rectang = this.drawRectF();
            this.parseSVG();
        }
    }
}

