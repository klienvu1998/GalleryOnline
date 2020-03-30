package com.example.galleryonline.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.galleryonline.CustomView.CustomImageGallery;
import com.example.galleryonline.R;
import com.example.galleryonline.fragment.MyGallery;
import com.example.galleryonline.fragment.ViewImage;
import com.example.galleryonline.model.ImageModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private int layout;
    private Context context;
    private ArrayList<ImageModel> list;

    public ImageAdapter(int layout, Context context, ArrayList<ImageModel> list) {
        this.layout = layout;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(layout,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        new DownloadThumbnails(holder,position).execute(list.get(position).getLink());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ViewImage viewImage = new ViewImage();
                Bundle bundle = new Bundle();
                bundle.putString("name",MyGallery.arrImage.get(position).getName());
                viewImage.setArguments(bundle);
                fragmentTransaction.add(R.id.frameLayout_fragment,viewImage).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CustomImageGallery imageView;
        private LinearLayout linearLayout;
        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.cutomImageGallery_line_image);
            linearLayout = itemView.findViewById(R.id.linearLayout_line_image);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadThumbnails extends AsyncTask<String,Void, Bitmap> {
        private WeakReference<MyViewHolder> viewHolder;
        private int position;

        private DownloadThumbnails(MyViewHolder viewHolder,int position) {
            this.viewHolder = new WeakReference<>(viewHolder);
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... strings) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                File directory = context.getDir("imageDir",Context.MODE_PRIVATE);
                File myPath = new File(directory,list.get(position).getName()+".jpg");
                if(!myPath.exists()){
                    java.net.URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    Bitmap img = BitmapFactory.decodeStream(inputStream);
                    FileOutputStream fos = null;
                    try {
                        Log.d("ImageAdapter","File not exists");
                        fos = new FileOutputStream(myPath);
                        img.compress(Bitmap.CompressFormat.PNG,100,fos);
                        return loadImageFromStorage(myPath.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            assert fos != null;
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    return loadImageFromStorage(myPath.toString());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Bitmap result) {
            super.onPostExecute(result);
            Bitmap bitmap;
            bitmap = Bitmap.createScaledBitmap(result, viewHolder.get().imageView.getWidthSize(), viewHolder.get().imageView.getWidthSize(), false);
            viewHolder.get().imageView.setBitmap(bitmap);
        }
    }

    private Bitmap loadImageFromStorage(String path){
        File file = new File(path,"");
        Bitmap b;
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(file));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
