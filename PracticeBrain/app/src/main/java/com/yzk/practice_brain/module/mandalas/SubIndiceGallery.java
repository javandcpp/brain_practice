//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.CheckBox;
//import android.widget.GridView;
//
//import com.yzk.practice_brain.R;
//
//
//public class SubIndiceGallery extends Activity {
//    GrillaGallery gi;
//    GridView gv;
//    FilesHandler handler;
//    MemoryManager mem;
//    Menu menu;
//
//    public SubIndiceGallery() {
//        super();
//        this.gv = null;
//        this.mem = new MemoryManager();
//        this.handler = new FilesHandler();
//        this.gi = null;
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setContentView(R.layout.subindice);
//        this.poblarGridView();
//        this.findViewById(R.id.aIndice3).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                SubIndiceGallery.this.startActivity(new Intent(SubIndiceGallery.this, Indice.class));
//            }
//        });
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        this.getMenuInflater().inflate(R.menu.menu_saved_gallery, menu);
//        return true;
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        boolean v9 = true;
//        int v7 = item.getItemId();
//        if(v7 == R.id.action_ayuda) {
//            this.startActivity(new Intent(((Context)this), Ayuda.class));
//        }
//        else if(v7 == R.id.action_salir) {
//            Intent v4 = new Intent("android.intent.action.MAIN");
//            v4.addCategory("android.intent.category.HOME");
//            v4.setFlags(0x10000000);
//            this.startActivity(v4);
//        }
//        else if(v7 == R.id.action_aindice) {
//            this.startActivity(new Intent(((Context)this), Indice.class));
//        }
//        else if(v7 == R.id.action_borrar) {
//            int v8 = this.gv.getCount();
//            int v1;
//            for(v1 = 0; v1 < v8; ++v1) {
//                View v0 = ((ViewGroup)this.gv.getChildAt(v1)).getChildAt(2);
//                if((((CheckBox)v0).isChecked()) && (this.handler.BorrarImagenGallery(String.valueOf(((
//                        CheckBox)v0).getContentDescription())))) {
//                    this.startActivity(new Intent(((Context)this), SubIndiceGallery.class));
//                }
//            }
//        }
//        else {
//            v9 = super.onOptionsItemSelected(item);
//        }
//
//        return v9;
//    }
//
//    public void onPause() {
//        super.onPause();
//        if(this.gv != null) {
//            this.mem.cleangal(this.gv);
//        }
//    }
//
//    public void onResume() {
//        super.onResume();
//        this.poblarGridView();
//    }
//
//    public void onStart() {
//        super.onStart();
//    }
//
//    public void onStop() {
//        super.onStop();
//        if(this.gi != null) {
//            this.gi = null;
//        }
//    }
//
//    private void poblarGridView() {
//        this.gv = (GridView) this.findViewById(R.id.gridview);
//        this.gi = new GrillaGallery(this.gv);
//        this.gv.setAdapter(this.gi);
//        this.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView arg6, View v, int position, long id) {
//                String v2 = String.valueOf(v.getContentDescription());
//                if(SubIndiceGallery.this.handler.comprobarTemp()) {
//                    new DialogsHandler(SubIndiceGallery.this).goToDraw(((View)arg6), v2, "deGallery");
//                }
//                else {
//                    Intent v1 = new Intent(SubIndiceGallery.this.getApplicationContext(), Mandalas2Activity
//                            .class);
//                    v1.putExtra("numero-archivoGuardado", String.valueOf(v2));
//                    SubIndiceGallery.this.startActivity(v1);
//                }
//            }
//        });
//    }
//}
//
