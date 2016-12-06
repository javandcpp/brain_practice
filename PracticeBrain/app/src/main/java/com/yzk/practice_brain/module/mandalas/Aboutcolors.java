//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.text.Html;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TextView;
//
//import com.yzk.practice_brain.R;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class Aboutcolors extends Activity {
//    public Aboutcolors() {
//        super();
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setContentView(R.layout.ayuda);
//        ((TextView)findViewById(R.id.txtvhelp)).setText(Html.fromHtml(this.readTxt(), new Html.ImageGetter() {
//            public Drawable getDrawable(String source) {
//                Drawable v1;
//                try {
//                    v1 = Aboutcolors.this.getResources().getDrawable(Aboutcolors.this.getResources()
//                            .getIdentifier(source, "drawable", "com.mandalas.manadalas.coloring"));
//                    v1.setBounds(0, 0, v1.getIntrinsicWidth(), v1.getIntrinsicHeight());
//                }
//                catch(Exception v2) {
//                    Drawable v0 = Aboutcolors.this.getResources().getDrawable(R.drawable.ic_launcher);
//                    v0.setBounds(0, 0, v0.getIntrinsicWidth(), v0.getIntrinsicHeight());
//                    v1 = v0;
//                }
//
//                return v1;
//            }
//        }, null));
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        this.getMenuInflater().inflate(R.menu.menu_ayuda, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        boolean v3 = true;
//        int v2 = item.getItemId();
//        if(v2 == 2131165225) {
//            Intent v0 = new Intent("android.intent.action.MAIN");
//            v0.addCategory("android.intent.category.HOME");
//            v0.setFlags(268435456);
//            this.startActivity(v0);
//        }
//        else if(v2 == 2131165224) {
//            this.startActivity(new Intent(((Context)this), Indice.class));
//        }
//        else {
//            v3 = super.onOptionsItemSelected(item);
//        }
//
//        return v3;
//    }
//
//    public void onPause() {
//        super.onPause();
//    }
//
//    public void onResume() {
//        super.onResume();
//    }
//
//    public void onStart() {
//        super.onStart();
//    }
//
//    public void onStop() {
//        super.onStop();
//    }
//
//    private String readTxt() {
//        InputStream v3 = this.getResources().openRawResource(this.getResources().getIdentifier("com.mandalas.manadalas.coloring:raw/aboutcolors",
//                null, null));
//        ByteArrayOutputStream v0 = new ByteArrayOutputStream();
//        try {
//            int v1;
//            for(v1 = v3.read(); v1 != -1; v1 = v3.read()) {
//                v0.write(v1);
//            }
//
//            v3.close();
//        }
//        catch(IOException v4) {
//        }
//
//        return v0.toString();
//    }
//}
//
