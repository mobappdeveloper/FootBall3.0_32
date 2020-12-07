package com.footballio.repository;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;


public final class AppSession {
    private static final String MyPREFERENCES = "AppPref";
    private static SharedPreferences appSession;
    public static final String PREV_LOGIN = "lastLogin";
    public static final String PREV_USERID = "UserId";
    public static final String PREV_LOGINTYPE = "LoginType";

    private AppSession() {
    }


    private static void getSessionPreference(Context context) {
        if (appSession == null) {
            appSession = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        }
    }

    public static boolean isLastLogin(Context context) {
        getSessionPreference(context);
        return appSession.getBoolean(PREV_LOGIN, false);
    }

    public static int getLastLoginUserId(Context application) {
        getSessionPreference(application);
        return appSession.getInt(PREV_USERID, 0);
    }

    public static String getStringValue(Context application,String value) {
        getSessionPreference(application);
        return appSession.getString(value, "");
    }

    public static int getLastLoginType(Context application) {
        getSessionPreference(application);
        return appSession.getInt(PREV_LOGINTYPE, 0);
    }

    public static boolean addSession(String key, String value) {
        SharedPreferences.Editor editor = appSession.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static boolean addSession(String key, boolean value) {
        SharedPreferences.Editor editor = appSession.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return true;
    }

    public static boolean addSession(String key, int value) {
        SharedPreferences.Editor editor = appSession.edit();
        editor.putInt(key, value);
        editor.commit();
        return true;
    }


}
