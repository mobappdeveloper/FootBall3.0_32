package com.footballio.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*

Created by Gowtham.P on 29-04-2019.
Copyright (c) 2018 Razrators. All rights reserved.

*/
public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILENETWORK = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private static boolean aResult;

    public static int getConnectivityStatus( Context context ) {
        ConnectivityManager cm = ( ConnectivityManager )context
                .getSystemService( Context.CONNECTIVITY_SERVICE );

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if( null != activeNetwork ) {
            if( activeNetwork.getType() == ConnectivityManager.TYPE_WIFI )
                return TYPE_WIFI;

            if( activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE )
                return TYPE_MOBILENETWORK;
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * Check the internet connection and return true or false
     *
     * @param aContext
     * @return
     */
    public static boolean isInternetOn( Context aContext ) {
        try {

            if( aContext == null )
                return false;

            ConnectivityManager connectivityManager = ( ConnectivityManager )aContext
                    .getSystemService( Context.CONNECTIVITY_SERVICE );
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null;
        } catch( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }
}
