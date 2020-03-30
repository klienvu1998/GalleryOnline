package com.example.galleryonline.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.example.galleryonline.CustomView.CustomImageView;
import com.example.galleryonline.R;
import com.example.galleryonline.handler.ViewImageHandler;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewImage extends Fragment {

    public static final int COMPLETE_LOAD_LINK = 100;

    private CustomImageView customImageView;
    private View view;
    public static String link;
    private String name;
    private ViewImageHandler handler;

    public ViewImage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_image, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            name = bundle.getString("name");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler = new ViewImageHandler(this);
        mapping();
    }

    private void mapping() {
        customImageView = view.findViewById(R.id.customImageView_fragment_view_image);
    }

    @Override
    public void onResume() {
        super.onResume();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20);
                    link = MediaManager.get().url().transformation(
                            new Transformation().width(customImageView.getWidth()).height(customImageView.getHeight()).crop("fill")).generate(name);
                    Message message = new Message();
                    message.what = COMPLETE_LOAD_LINK;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static class getImage extends AsyncTask<String,Void,Bitmap>{
        private WeakReference<ViewImage> viewImageWeakReference;

        public getImage(ViewImage viewImageWeakReference) {
            this.viewImageWeakReference = new WeakReference<>(viewImageWeakReference);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            viewImageWeakReference.get().customImageView.setBitmap(bitmap);
        }
    }

}
