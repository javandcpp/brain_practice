package com.yzk.brain.module.mandalas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.inter.ResponseStringDataListener;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.yzk.brain.R;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.config.Config;
import com.yzk.brain.log.LogUtil;
import com.yzk.brain.network.HttpRequestUtil;
import com.yzk.brain.preference.PreferenceHelper;
import com.yzk.brain.setting.Setting;
import com.yzk.brain.ui.Controller;
import com.yzk.brain.ui.HintDialog;
import com.yzk.brain.ui.RuleDialog;
import com.yzk.brain.utils.ImageUtils;
import com.yzk.brain.utils.NetworkUtils;
import com.yzk.brain.utils.PhoneUtils;
import com.yzk.brain.utils.SoundEffect;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

import static android.graphics.Color.parseColor;

public class Mandalas2Activity extends BaseFragmentActivity implements Animation.AnimationListener, Controller.ControllerCallBack, ResponseStringDataListener {
    private static final int REQUEST_COMMIT_TASK =0x1 ;
    private final float MAXSCALE;
    private int MAX_SAVING_FILES;
    private final float MINSCALE;
    private final int SUSTITUTONEGRO;
    private Animation anim;
//    private AppRater apprater;
    private Bitmap b;
    public Canvas canvas;
    private CustomDrawableView cdv;
    private boolean comprobarasync;
    private PictureDrawable d;
    private Drawable dibujo;
    //    private Metricas dim;
    private Drawable drawable;
    public boolean efectos;
    private String extFile;
    private boolean eyedropper;
    private FloodFill fd;
    private EfectosSonoros fx;
    private GestureDetector gestureDetector;
    private static Button goterito;
    private Boolean gradient;
    private FilesHandler handler;
    private ImageView imagen;
    private String mDrawableName;
    private int mandalaGroup;
    private int mandalaIdNum;
    private int mandalaMax;
    private String mandalaName;
    private int mandalaNum;
    private String mandalaString;
    private String mandalaTag;
    //    private MemoryManager mem;
    private Menu menu;
    private String mess;
    private int newColor = parseColor("#ffff0000");
    private int numpaleta;
    private int oldColor;
    //    private GrabarPreferencias prefs;
    private int proximoValor;
    private String raiz;
    private boolean runningImagen;
    private boolean runningPaletas;
    //    private Sonidos s;
    private ScaleGestureDetector scaleGestureDetector;
    private View.OnTouchListener scaleGestureListener;
    private SVG svg;
    public Path svgparsdtopath;
    public boolean tocaMusica;
    private int totalScore=10;

    private final String AREA01 = "ff00ff00";//x=108,y=108
    private final String AREA02 = "ff0000ff";//x=236,y=94
    private final String AREA03 = "ffff0000";//x=350,y=112
    private final String AREA04 = "ff0000ff";//x=367,y=225
    private final String AREA05 = "fffff100";//x=360,y=353
    private final String AREA06 = "ffff0000";//x=234,y=388
    private final String AREA07 = "ff0000ff";//x=101,y=349
    private final String AREA08 = "ffff0000";//x=75,y=225
    private final String AREA09 = "fffff100";//x=238,y=238


    @Bind(R.id.redPen)
    Button redPen;

    @Bind(R.id.greenPen)
    Button greenPen;

    @Bind(R.id.yellowPen)
    Button yellowPen;

    @Bind(R.id.bluePen)
    Button bluePen;
    private Controller controller;

    @Bind(R.id.tvScore)
    TextView tvScore;
    private boolean mandala_finish;
//    private int mandalascore=10;


    @OnClick({R.id.redPen, R.id.greenPen, R.id.yellowPen, R.id.bluePen, R.id.finish, R.id.rule})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.redPen:
                redPen.setBackgroundResource(R.drawable.home_red_seleted);
                greenPen.setBackgroundResource(R.drawable.home_green_normal);
                yellowPen.setBackgroundResource(R.drawable.home_yellow_normal);
                bluePen.setBackgroundResource(R.drawable.home_blue_normal);
                newColor = parseColor("#ffff0000");
                break;
            case R.id.greenPen:
                greenPen.setBackgroundResource(R.drawable.home_green_seleted);

                redPen.setBackgroundResource(R.drawable.home_red_normal);
                yellowPen.setBackgroundResource(R.drawable.home_yellow_normal);
                bluePen.setBackgroundResource(R.drawable.home_blue_normal);
                newColor = parseColor("#ff00ff00");
                break;
            case R.id.yellowPen:
                yellowPen.setBackgroundResource(R.drawable.home_yellow_seleted);
                redPen.setBackgroundResource(R.drawable.home_red_normal);
                greenPen.setBackgroundResource(R.drawable.home_green_normal);
                bluePen.setBackgroundResource(R.drawable.home_blue_normal);
                newColor = parseColor("#fffff100");
                break;
            case R.id.bluePen:
                bluePen.setBackgroundResource(R.drawable.home_blue_seleted);
                redPen.setBackgroundResource(R.drawable.home_red_normal);
                yellowPen.setBackgroundResource(R.drawable.home_yellow_normal);
                greenPen.setBackgroundResource(R.drawable.home_green_normal);
                newColor = parseColor("#ff0000ff");
                break;
            case R.id.finish:
                compareColor();
                break;
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "5");
                builder.create().show();
                break;
        }
    }

    @Override
    public void controll(View view) {
        switch (view.getId()) {
            case R.id.retry:
                retry();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.play:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isPlaying()) {
                        ((ImageButton) view).setSelected(false);
                        GlobalApplication.instance.getiMediaInterface().pause();
                    } else {
                        ((ImageButton) view).setSelected(true);
                        GlobalApplication.instance.getiMediaInterface().play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onDataDelivered(int taskId, String data) {
        LogUtil.e(data);
    }

    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {

    }


    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        int distanciaX;
        int distanciaY;
        private Point point;

        MyGestureDetector() {
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
                Mandalas2Activity.this.fd.floodFill(Mandalas2Activity.this.b, this.point, oldColor, Mandalas2Activity.this.newColor);
                Mandalas2Activity.this.imagen.invalidate();
                //播放填色音效
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.FILL_CORRECT);
                }
            }

            return true;
        }
    }


    /**
     * AREA01 = "ff00ff00";//x=108,y=108
     * AREA02 = "ff0000ff";//x=236,y=94
     * AREA03 = "ffff0000";//x=350,y=112
     * AREA04 = "ff0000ff";//x=367,y=225
     * AREA05 = "fffff100";//x=360,y=353
     * AREA06 = "ffff0000";//x=234,y=388
     * AREA07 = "ff0000ff";//x=101,y=349
     * AREA08 = "ffff0000";//x=75,y=225
     * AREA09 = "fffff100";//x=238,y=238
     */
    private void compareColor() {
        String area01_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 105, 116);
        String area02_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 220, 92);
        String area03_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 354, 125);

        String area04_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 370, 252);
        String area05_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 352, 379);
        String area06_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 224, 402);

        String area07_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 104, 373);
        String area08_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 70, 252);
        String area09_value = ImageUtils.getImageColorValue(Mandalas2Activity.this.b, 220, 260);


        if (mandala_finish) {//已练习
            if (area01_value.equals(AREA01) && area02_value.equals(AREA02) && area03_value.equals(AREA03) && area04_value.equals(AREA04) && area05_value.equals(AREA05) && area06_value.equals(AREA06)
                    && area07_value.equals(AREA07) && area08_value.equals(AREA08) && area09_value.equals(AREA09)) {
                HintDialog.Builder builder = new HintDialog. Builder(Mandalas2Activity.this);
                HintDialog hintDialog = builder.setStatus(0).setScoreVisiblle(0).setTvScore(totalScore).create();
                hintDialog.show();
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                }
            } else {
                Toast toast = Toast.makeText(this, "还是不对，再检查下吧", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.FILL_ERROR);
                }
            }
        } else {//未练习


            if (totalScore < 0) {
                HintDialog.Builder builder = new HintDialog. Builder(Mandalas2Activity.this);
                HintDialog hintDialog = builder.setStatus(0).create();
                hintDialog.show();

                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.FAILURE);
                }


//                PreferenceHelper.writeBool("mandala_finish", true);//记录第一次
            } else if (area01_value.equals(AREA01) && area02_value.equals(AREA02) && area03_value.equals(AREA03) && area04_value.equals(AREA04) && area05_value.equals(AREA05) && area06_value.equals(AREA06)
                    && area07_value.equals(AREA07) && area08_value.equals(AREA08) && area09_value.equals(AREA09)) {

                HintDialog.Builder builder = new HintDialog. Builder(Mandalas2Activity.this);
                HintDialog hintDialog = builder.setStatus(1).setTvScore(totalScore).create();
                hintDialog.show();
                //上传积分
                if (NetworkUtils.isConnected(this)){
//                    score=90&exerciseId=1000&whichDay=1&device=asd123&type=2
                    String params="&score="+totalScore+"&whichDay=1"+"&type=2"+"&device="+ PhoneUtils.getPhoneIMEI(this);
                    HttpRequestUtil.HttpRequestByGet(Config.COMMIT_SCORE+params,this,REQUEST_COMMIT_TASK);
                }

                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.SUCCESS);
                }
                PreferenceHelper.writeBool("mandala_finish", true);//记录第一次
            } else if (totalScore >= 0) {
                Toast toast = Toast.makeText(this, "还是不对，再检查下吧", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                --totalScore;
                if (totalScore < 0) {
                    totalScore = -1;
                }
                int score = totalScore >= 0 ? totalScore : 0;
                tvScore.setText("错误次数:" + score);
                if (1 == Setting.getVoice()) {
                    SoundEffect.getInstance().play(SoundEffect.FILL_ERROR);
                }
            }
            PreferenceHelper.writeInt("mandalascore", totalScore);//每次错误记录分数

        }
//        }else{
//            if (area01_value.equals(AREA01) && area02_value.equals(AREA02) && area03_value.equals(AREA03) && area04_value.equals(AREA04) && area05_value.equals(AREA05) && area06_value.equals(AREA06)
//                    && area07_value.equals(AREA07) && area08_value.equals(AREA08) && area09_value.equals(AREA09)) {
//                Toast toast = Toast.makeText(this,"还是不对，再检查下吧", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER , 0, 0);
//
//                toast.show();
//            }
//
//
//        }
    }


    public Mandalas2Activity() {
        super();
//        this.mem = new MemoryManager();
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
        this.SUSTITUTONEGRO = parseColor("#121211");
        this.oldColor = -1;
        this.eyedropper = false;
        this.gradient = Boolean.valueOf(false);
//        this.dim = new Metricas();

        this.runningPaletas = false;
        this.runningImagen = false;
        this.fd = new FloodFill();
        this.comprobarasync = false;
    }


//    public void animarConEscala(View v) {
//        this.anim = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.animcolores);
//        v.startAnimation(this.anim);
//    }

    private void cargarImagen() {
        this.runningImagen = true;
        Intent v10 = this.getIntent();
        this.mandalaTag = v10.hasExtra("numero-archivoGuardado") ? v10.getStringExtra("numero-archivoGuardado")
                : v10.getStringExtra("mandala-tag");

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

//        View v8 = this.findViewById(R.id.TextoNombreMandala);
//        ((TextView) v8).setText(this.mandalaTag.replace("_", " ").replace(".png", ""));
//        ((TextView) v8).startAnimation(AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.animmandalatag));
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
            this.mandalaTag = new File(v11).getName() + ".png";
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

//    public void getColor(View v) {
//        new ColorPicker(this, this, "", -16777216, -1).show();
//    }

    private static Point getDisplaySize(Display display) {
        Point v1 = new Point();
        try {
            display.getSize(v1);
        } catch (NoSuchMethodError v0) {
            v1.x = display.getWidth() - 100;
            v1.y = display.getHeight() - 100;
        }

        return v1;
    }


    public void onAnimationEnd(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }


    /**
     * 重试
     */
    private void retry() {
        this.numpaleta = 0;
        cargarImagen();
        gesture();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "------------------------------>>" + "onCreate");
        this.setContentView(R.layout.activity_mandalas2);
//        this.prefs = new GrabarPreferencias(((Context) this));
//        this.efectos = this.prefs.getEfectos();
        prenderEfectos();
        mandala_finish = PreferenceHelper.getBool("mandala_finish");
//        mandalascore = PreferenceHelper.getScore("mandalascore");

//        totalScore=mandalascore;
        initView();


        if (mandala_finish) {//已练习过,不再记录积分和错误次数
            tvScore.setVisibility(View.GONE);
        }else {//未练习过或积分大于等于0
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText("错误次数:" + totalScore);
        }

        this.imagen = (ImageView) this.findViewById(R.id.imagen2);
        final View v1 = this.findViewById(R.id.gomaborrar);
        redPen.setBackgroundResource(R.drawable.home_red_seleted);
        yellowPen.setBackgroundResource(R.drawable.home_yellow_normal);
        greenPen.setBackgroundResource(R.drawable.home_green_normal);
        bluePen.setBackgroundResource(R.drawable.home_blue_normal);

        newColor = parseColor("#ffff0000");


        this.numpaleta = 0;
        if (!this.runningImagen) {
            this.cargarImagen();
        }
        gesture();
//
//        this.findViewById(R.id.aIndice2).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Mandalas2Activity.this.startActivity(new Intent(Mandalas2Activity.this.getApplicationContext(),
//                        Indice.class));
//            }
//        });
//        this.findViewById(R.id.rotation).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Mandalas2Activity.this.anim = AnimationUtils.loadAnimation(Mandalas2Activity.this.getApplicationContext(),
//                        R.anim.rotate);
//                Mandalas2Activity.this.imagen.startAnimation(Mandalas2Activity.this.anim);
//            }
//        });
//        this.findViewById(R.id.rotation2).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Mandalas2Activity.this.anim = AnimationUtils.loadAnimation(Mandalas2Activity.this.getApplicationContext(),
//                        R.anim.rotate2);
//                Mandalas2Activity.this.imagen.startAnimation(Mandalas2Activity.this.anim);
//            }
//        });
//        this.findViewById(R.id.centrado).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Mandalas2Activity.this.centrarImagen();
//            }
//        });


//        this.findViewById(R.id.grabar).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Mandalas2Activity.this.animarConEscala(v);
//                if (new File(String.valueOf(Mandalas2Activity.this.raiz) + "/Mandalas2/MyGalery").listFiles()
//                        .length < Mandalas2Activity.this.MAX_SAVING_FILES) {
//                    Mandalas2Activity.this.centrarImagen();
//                    Mandalas2Activity.this.handler.SaveDrawing("miGaleria", Mandalas2Activity.this.imagen,
//                            Mandalas2Activity.this.mandalaTag);
//                } else if (Mandalas2Activity.this.handler.ComprobarDuplicidadGallery(Mandalas2Activity.
//                        this.mandalaTag)) {
//                    Mandalas2Activity.this.centrarImagen();
//                    Mandalas2Activity.this.handler.SaveDrawing("miGaleria", Mandalas2Activity.this.imagen,
//                            Mandalas2Activity.this.mandalaTag);
//                } else {
//                    new DialogsHandler(Mandalas2Activity.this).infoLimiteGrabado(Mandalas2Activity.this);
//                }
//            }
//        });
//        this.apprater = new AppRater(((Context) this));
//        this.apprater.app_launched(((Context) this));
    }

    /**
     * View 初始化
     */
    private void initView() {
        controller = (Controller) findViewById(R.id.controlPanel);
        controller.setClickCallBack(this);


    }

    public void onResume() {
        super.onResume();
        Log.e("TAG", "------------------------>" + "onResume");

    }

    public void onDestroy() {
        Log.e("TAG", "------------------------------>>" + "onDestroy");
        super.onDestroy();

        this.runningImagen = false;
        this.runningPaletas = false;
        this.gestureDetector = null;
        this.scaleGestureListener = null;
//        this.apprater = null;

//        if ((this.tocaMusica) && this.s != null) {
//            this.s = null;
//        }
        if (this.fx != null) {
            this.fx = null;
        }
        if (this.imagen != null) {
            this.imagen = null;
        }
        if (this.b != null) {
            this.b.recycle();
        }

    }


    private void gesture() {
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


//        if (this.prefs != null) {
//            this.prefs.setEfectos(Boolean.valueOf(this.efectos));
//        }

    }

    @Override
    protected void uIViewInit() {

    }

    @Override
    protected void uIViewDataApply() {

    }

    public void onStop() {
        Log.e("TAG", "------------------------>" + "onStop");
        ImageView v1 = null;
        super.onStop();


//        if (this.prefs != null) {
//            this.prefs = null;
//        }
    }


    private void prenderEfectos() {
        this.fx = new EfectosSonoros(((Context) this));
    }

//    private void prenderLluvia() {
//        this.s = new Sonidos(((Context) this));
//    }


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
         *
         * @return
         */
        private RectF drawRectF() {
            return new RectF(0f, 0f, ((float) this.anchocanvas), ((float) this.anchocanvas));
        }

        /**
         * 绘制
         *
         * @return
         */
        protected void onDraw(Canvas canvas) {
            this.rectang = this.drawRectF();
            this.parseSVG();
        }
    }
}

