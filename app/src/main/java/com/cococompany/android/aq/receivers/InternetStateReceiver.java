package com.cococompany.android.aq.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.cococompany.android.aq.MainActivity;
import com.cococompany.android.aq.fragments.NotificationsFragment;

/**
 * Created by alexandrmyagkiy on 10.12.16.
 */

public class InternetStateReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if (wifi != null && wifi.isAvailable() || mobile != null && mobile.isAvailable()) {
//            application.getInstance().lp.connect();
//        } else {
//            application.getInstance().lp.disconnect();
//        }
    }
}
