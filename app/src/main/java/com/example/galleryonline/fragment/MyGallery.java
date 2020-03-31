package com.example.galleryonline.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.galleryonline.adapter.ImageAdapter;
import com.example.galleryonline.R;
import com.example.galleryonline.model.ImageModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MyGallery extends Fragment {

    private final static String URL_GALLERY = "https://firebasestorage.googleapis.com/v0/b/galleryonline-e0b49.appspot.com/o/dataGallery.json?alt=media&token=48f96193-e2ed-44e7-9c5c-53dd42c3fdae";
    public static ArrayList<ImageModel> arrImage;
    private ImageAdapter imageAdapter;
    private RecyclerView recyclerView;
    private String result;

    public MyGallery() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_gallery, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapping();
        initUrlImage();
    }
    private void mapping() {
        if(getActivity()!=null)
            recyclerView = getActivity().findViewById(R.id.recyclerView_mainActivity);
        arrImage = new ArrayList<>();
        imageAdapter = new ImageAdapter(R.layout.line_image_recyclerview,getContext(),arrImage);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    public static class GetDataJSON extends AsyncTask<String,Void,String>{

        WeakReference<MyGallery> weakReference;

        private GetDataJSON(MyGallery myGallery) {
            this.weakReference = new WeakReference<>(myGallery);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                weakReference.get().result = bufferedReader.readLine();
                urlConnection.disconnect();
                return weakReference.get().result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String resultT) {
            super.onPostExecute(weakReference.get().result);
            Gson gson = new Gson();
            MyGallery.arrImage = gson.fromJson(resultT,new TypeToken<ArrayList<ImageModel>>(){}.getType());
            weakReference.get().imageAdapter.refreshMyList(MyGallery.arrImage);
        }
    }

    private void initUrlImage() {
        new GetDataJSON(this).execute(URL_GALLERY);
    }
}


