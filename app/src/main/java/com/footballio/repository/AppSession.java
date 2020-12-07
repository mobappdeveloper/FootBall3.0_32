package com.footballio.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private static final String MyPREFERENCES = "AppPref";
    private static SharedPreferences appSession;
    public static boolean getLastLogin(Context context) {
        appSession = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return appSession.getBoolean("lastLogin", false);
    }

}
