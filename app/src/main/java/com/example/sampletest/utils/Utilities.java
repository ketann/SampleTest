package com.example.sampletest.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;

import com.example.sampletest.R;

/**
 * Created by Ketan on 06,September,2019
 */

public class Utilities {

    public static String pageSize = "10";

    private static String DTSreturn;

    // display alert dialog in app any where when internet is not available
    public static void showAlertDialog(final Context context, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();
    }

    // display alert Toast in app any where
    public static void showToast(Context context, String message) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // for Check string is empty or not
    public static boolean isStringEmpty(String s) {

        if (s.length() == 0 || s == null || s.equals("") || s.equals(" ")) {
            return true;

        } else {
            return false;

        }

    }
}
