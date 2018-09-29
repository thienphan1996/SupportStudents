package com.project.thienphan.timesheet.Support;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Window;

import com.project.thienphan.supportstudent.R;

public class InfoDialog {

    public static void ShowInfoDiaLog(Activity activity, String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        Dialog dialogDisplay = dialog.create();
        dialogDisplay.show();
    }

    public static void ShowProgressDialog(Activity activity, boolean isShow){
        Dialog dialog = new Dialog(activity);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);
        if (isShow){
            dialog.show();
        }
        else dialog.dismiss();
    }
}
