//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.view.View;
//
//import com.yzk.practice_brain.R;
//
//
//public class DialogsHandler {
//    private final Context context;
//    boolean comprobar;
//    public String vieneDe;
//    private FilesHandler handler;
//
//    public boolean tag;
//    public DialogsHandler(Context context) {
//        super();
//        this.context=context;
//        this.handler = new FilesHandler();
//    }
//
//
//    public void goToDraw(View v, final String tag, final String vieneDe) {
//        Context v2 = v.getContext();
//        AlertDialog.Builder v1 = new AlertDialog.Builder(this.context);
//        v1.setTitle(v2.getResources().getString(R.string.atencion));
//        v1.setMessage(v2.getResources().getString(R.string.seperdera)).setCancelable(false).setPositiveButton(
//                v2.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//
//
//
//
//                    public void onClick(DialogInterface dialog, int id) {
//                DialogsHandler.this.handler.DeleteTempFile();
//                Intent v0 = new Intent(context, Mandalas2Activity.class);
//                if(vieneDe == "deGallery") {
//                    v0.putExtra("numero-archivoGuardado", tag);
//                }
//                else {
//                    v0.putExtra("mandala-tag", tag);
//                }
//
//                context.startActivity(v0);
//            }
//        }).setNegativeButton(v2.getResources().getString(R.string.no), new DialogInterface.OnClickListener
//                () {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//        v1.create().show();
//    }
//
//    public void infoLimiteGrabado(final Context context) {
//        AlertDialog.Builder v1 = new AlertDialog.Builder(context);
//        v1.setTitle(context.getResources().getString(R.string.atencion));
//        v1.setMessage(context.getResources().getString(R.string.limite)).setCancelable(false).setPositiveButton(
//                context.getResources().getString(R.string.entiendo), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        }).setNegativeButton(context.getResources().getString(R.string.ayuda), new DialogInterface.OnClickListener
//                () {
//            public void onClick(DialogInterface dialog, int id) {
//                context.startActivity(new Intent(context.getApplicationContext(),
//                        Ayuda.class));
//            }
//        });
//        v1.create().show();
//    }
//}
//
