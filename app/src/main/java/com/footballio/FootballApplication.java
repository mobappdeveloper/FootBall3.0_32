package com.footballio;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.provider.Settings;


import com.footballio.Utils.Prefs;
import com.footballio.network.InternetUtil;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import dagger.hilt.android.HiltAndroidApp;


@HiltAndroidApp
public class FootballApplication extends Application {
    private static Context mContext;
    private String deviceId = "";
    private String deviceType = "";
    private String authKey = "";
    private Class<?> loginActivity;
    private Class<?> launcherActivity;
    public static String dsnSentry = "";
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());

        mContext = this;
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceType = "Android";

      /*  FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/

        //FirebaseApp.initializeApp(this);
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        // Initialize the Shared Preferences class
//        new Prefs.Builder().setContext(this)
//                .setMode(ContextWrapper.MODE_PRIVATE)
//                .setPrefsName(getPackageName())
//                .setUseDefaultSharedPreference(true).build();

        InternetUtil.init(this);


    }


    public static FootballApplication instance() {

        return (FootballApplication) mContext.getApplicationContext();
    }

    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) instance().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
        else
            return false;
    }


    public String getDeviceId() {

        return deviceId;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = FootballApplication.instance().getPackageManager().getPackageInfo(FootballApplication.instance().getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    public int getDp(int pixel) {
        float density = FootballApplication.instance().getResources().getDisplayMetrics().density;
        float dp = pixel / density;
        return (int) dp;
    }

    public int getPixel(int dp) {
        float density = FootballApplication.instance().getResources().getDisplayMetrics().density;
        float px = dp * density;
        return (int) px;
    }

    public Class<?> getLoginActivity() {
        return loginActivity;
    }

    public void setLoginActivity(Class<?> loginActivity) {
        this.loginActivity = loginActivity;
    }

    public Class<?> getLauncherActivity() {
        return launcherActivity;
    }

    public void setLauncherActivity(Class<?> launcherActivity) {
        this.launcherActivity = launcherActivity;
    }

}
