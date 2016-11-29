package com.cococompany.android.aq.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Valentin on 29.11.2016.
 */

public class DownloadAvatarTask extends AsyncTask<String,Void,Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(strings[0]);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("download", "Error getting bitmap", e);
            }
            return bm;
    }
}
