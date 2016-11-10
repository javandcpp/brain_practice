// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

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

public class SubIndice extends Activity {
    String NombreGrupo;
    GrillaImagenes01 gi;
    private static GridView gv;
    FilesHandler handler;
    ImageView ivTemp;
    MemoryManager mem;

    static {
        SubIndice.gv = null;
    }

    public SubIndice() {
        super();
        this.NombreGrupo = null;
        this.mem = new MemoryManager();
        this.handler = new FilesHandler();
        this.gi = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.subindice);
        this.NombreGrupo = this.getIntent().getStringExtra("grupoPadre");
        this.findViewById(R.id.aIndice3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SubIndice.this.startActivity(new Intent(SubIndice.this.getApplicationContext(), Indice
                        .class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.menu_subindice, menu);
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean v4 = true;
        int v3 = item.getItemId();
        if (v3 == 2131165226) {
            this.startActivity(new Intent(((Context) this), Ayuda.class));
        } else if (v3 == 2131165232) {
            this.startActivity(new Intent(((Context) this), Indice.class));
        } else if (v3 == 2131165225) {
            Intent v1 = new Intent("android.intent.action.MAIN");
            v1.addCategory("android.intent.category.HOME");
            v1.setFlags(268435456);
            this.startActivity(v1);
        } else {
            v4 = super.onOptionsItemSelected(item);
        }

        return v4;
    }

    public void onPause() {
        super.onPause();
        if (this.ivTemp != null) {
            this.mem.limpiarImageview(this.ivTemp);
        }

        if (SubIndice.gv != null) {
            this.mem.cleanmem(SubIndice.gv);
        }

        this.gi = null;
    }

    public void onResume() {
        super.onResume();
        this.ivTemp = (ImageView) this.findViewById(R.id.imagenTemp2);
        this.handler.comprobarTempyMostrarDibujo(this.ivTemp);
        this.poblarGridView(this.NombreGrupo);
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
        System.gc();
    }

    private void poblarGridView(String NombreGrupo) {
        SubIndice.gv = (GridView) this.findViewById(R.id.gridview);
        this.gi = new GrillaImagenes01(SubIndice.gv, NombreGrupo);
        SubIndice.gv.setAdapter(this.gi);
        SubIndice.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg6, View v, int position, long id) {
                String v2 = String.valueOf(v.getContentDescription());

                handler.DeleteTempFile();
                if (SubIndice.this.handler.comprobarTemp()) {
                    new DialogsHandler(SubIndice.this).goToDraw(((View) arg6), v2, "deBiblioteca");
                } else {
                    Intent v1 = new Intent(SubIndice.this.getApplicationContext(), Mandalas2Activity
                            .class);
                    v1.putExtra("mandala-tag", String.valueOf(v2));
                    SubIndice.this.startActivity(v1);
                }
            }
        });
    }
}

