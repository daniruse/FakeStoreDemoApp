package com.example.FakeStoreDemoApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class DownloadImageRequest extends AsyncTask<String,String, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {

        String imageUrl = strings[0];
        try {
            java.net.URL url = new java.net.URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmapImage = BitmapFactory.decodeStream(input);
            return bitmapImage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
