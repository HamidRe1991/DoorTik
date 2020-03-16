package com.smr.estate.Internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet
{
    public static boolean Checked(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;

        //Check wifi
        NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        //Check mobile data
        NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        //Check network
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return wifiNetwork != null && wifiNetwork.isConnected() || mobileNetwork != null && mobileNetwork.isConnected() || activeNetwork != null && activeNetwork.isConnected();
    }
}
