package com.mypc.slotmachine.servisec;

import android.content.Context;
import android.widget.Toast;

public class SingleToast {
    private static Toast toast;

    public static void show(Context context, String message, int duration){
        if (toast != null){
            //Remove existing toast
            toast.cancel();
        }
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }}
