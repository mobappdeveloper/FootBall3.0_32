package com.footballio.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.footballio.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Utils {
    public static final String TAG = "Utils";

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static String CurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static void chnageBAckgroundDrawable(Context context, TextView textView, int drawable) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            textView.setBackgroundDrawable(ContextCompat.getDrawable(context, drawable));
        } else {
            textView.setBackground(ContextCompat.getDrawable(context, drawable));
        }
    }

    public static String RemoveCharactersBeforeUnderscore(String s, String splitted) {
        splitted = splitted.split(s)[1];
        return splitted.trim();
    }

    public static boolean currentDateGreater(String date) throws ParseException {
        boolean is_greater = false;
        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMM yyyy");
        String currentdate = sdf.format(c.getTime());
        try {
            String myFormatString = "dd. MMM yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(date);
            Date startingDate = df.parse(currentdate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {
            Log.d(TAG, "onBindViewHolder: " + e.getMessage());
            return false;
        }


    }

    public static void showToast(String s, Context context) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        return year;
    }

    public static int convertStringToInt(String value) {
        int convertedValue = 0;
        try {
            if (value.contains(".")) {
                convertedValue = (int) Float.parseFloat(value);
            } else {
                convertedValue = Integer.parseInt(value);
            }

        } catch (Exception e) {

        }
        return convertedValue;
    }
}
