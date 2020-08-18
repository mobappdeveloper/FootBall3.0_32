package com.footballio.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import android.util.Log;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.footballio.R;

import java.util.Objects;

public class Loader {

    private static CustomProgress customProgress;

    public static void showLoad(Context activity, boolean load){
        try {
            if(customProgress == null) {
                customProgress = new CustomProgress(activity);
                customProgress.setCanceledOnTouchOutside(false);
                customProgress.setCancelable(false);
                Objects.requireNonNull(customProgress.getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                Objects.requireNonNull(customProgress.getWindow()).setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(activity, R.color.transparent)));
            }
            if(load){
                customProgress.show();
            }else {
                customProgress.dismiss();
                customProgress = null;
            }
        }catch (Exception e){
            Log.d("LoaderError",e.toString());
        }
    }

    public static void showDialogLoad(Context activity, boolean load){
        try {
            if(customProgress == null) {
                customProgress = new CustomProgress(activity);
                customProgress.setCanceledOnTouchOutside(true);
                customProgress.setCancelable(true);
                Objects.requireNonNull(customProgress.getWindow()).clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                Objects.requireNonNull(customProgress.getWindow()).setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(activity, R.color.transparent)));
            }
            if(load){
                customProgress.show();
            }else {
                customProgress.dismiss();
                customProgress = null;
            }
        }catch (Exception e){
            Log.d("LoaderError",e.toString());
        }
    }

}