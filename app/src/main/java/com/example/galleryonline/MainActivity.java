package com.example.galleryonline;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ArrayList<String> arrUrlBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCloudinary();
        mapping();
        initUrlImage();
        new DownloadThumbnails().execute("URL");
    }

    private void initUrlImage() {
        arrUrlBitmap = new ArrayList<>();
        arrUrlBitmap.add("https://res.cloudinary.com/deib1e0wj/image/upload/c_thumb,w_150,h_150/1660643_gvxqql.jpg");
        arrUrlBitmap.add("https://res.cloudinary.com/deib1e0wj/image/upload/c_thumb,w_150,h_150/v1585150552/Sea-Seekers-iPhone-Wallpaper-Collection-header2_rxdhsw.jpg");
    }

    private void initCloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "deib1e0wj");
        MediaManager.init(this, config);
    }

    private void mapping() {
        imageView = findViewById(R.id.img_thumbnail);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class DownloadThumbnails extends AsyncTask<String,Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... strings) {
            try {
                java.net.URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(inputStream);
                return img;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
    }
}
