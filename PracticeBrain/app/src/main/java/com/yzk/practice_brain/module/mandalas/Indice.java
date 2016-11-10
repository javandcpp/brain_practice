
package com.yzk.practice_brain.module.mandalas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.yzk.practice_brain.R;


public class Indice extends Activity {
    String NombreGrupo;
    GrillaImagenes gi;
    private static GridView gv;
    FilesHandler handler;
    ImageView ivGal;
    ImageView ivTemp;
    MemoryManager mem;

    static {
        Indice.gv = null;
    }

    public Indice() {
        super();
        this.handler = new FilesHandler();
        this.mem = new MemoryManager();
        this.NombreGrupo = "indicePadre";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.indice);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu_indice, menu);
        "android.intent.action.SEARCH".equals(this.getIntent().getAction());
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean v3 = true;
        int v2 = item.getItemId();
        if(v2 == 2131165226) {
            this.startActivity(new Intent(((Context)this), Ayuda.class));
        }
        else if(v2 == 2131165225) {
            Intent v1 = new Intent("android.intent.action.MAIN");
            v1.addCategory("android.intent.category.HOME");
            v1.setFlags(268435456);
            this.startActivity(v1);
        }
        else {
            v3 = super.onOptionsItemSelected(item);
        }

        return v3;
    }

    public void onPause() {
        super.onPause();
        if(this.ivTemp != null) {
            this.mem.limpiarImageview(this.ivTemp);
        }

        if(Indice.gv != null) {
            this.mem.cleanmem(Indice.gv);
        }

        this.gi = null;
    }

    public void onResume() {
        super.onResume();
        this.ivTemp = (ImageView) this.findViewById(R.id.imagenTemp);
        this.poblarGridView(this.NombreGrupo);
        this.handler.comprobarTempyMostrarDibujo(this.ivTemp);
        System.gc();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
        System.gc();
    }

    private void poblarGridView(String Nombre) {
        Indice.gv = (GridView) this.findViewById(R.id.gridview);
        this.gi = new GrillaImagenes(Indice.gv, Nombre);
        Indice.gv.setAdapter(this.gi);
        this.gi = null;
        Indice.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg5, View v, int position, long id) {



                Intent v0;
                String v1 = String.valueOf(v.getContentDescription());
//
                if(v1 == "mydocfolder") {
                    v0 = new Intent(Indice.this.getApplicationContext(), SubIndiceGallery.class);
                }
                else {
                    v0 = new Intent(Indice.this.getApplicationContext(), SubIndice.class);
                    v0.putExtra("grupoPadre", v1);
                }

                Indice.this.startActivity(v0);
            }
        });
    }
}

