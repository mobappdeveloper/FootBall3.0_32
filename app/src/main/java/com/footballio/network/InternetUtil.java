package com.footballio.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.footballio.FootballApplication;

import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;


public final class InternetUtil {
    private static FootballApplication footballApplication;

    // private BroadcastReceiver broadcastReceiver;
    public static void init(@ApplicationContext FootballApplication context) {
        footballApplication = context;
    }

    public static boolean isInternetOn() {
        ConnectivityManager cm = (ConnectivityManager) footballApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        return connected;
    }

    public boolean isWifiOn() {
        ConnectivityManager cm = (ConnectivityManager) footballApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean isWiFi = nInfo.getType() == ConnectivityManager.TYPE_WIFI;
        return isWiFi;
    }
}
