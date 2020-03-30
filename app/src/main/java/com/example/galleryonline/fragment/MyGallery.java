package com.example.galleryonline.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.example.galleryonline.adapter.ImageAdapter;
import com.example.galleryonline.R;
import com.example.galleryonline.model.ImageModel;
import java.util.ArrayList;


public class MyGallery extends Fragment {

    public static ArrayList<ImageModel> arrImage;
    private ImageAdapter imageAdapter;
    private RecyclerView recyclerView;

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

    private void initUrlImage() {
        arrImage.add(new ImageModel("3_h73rml", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("3_h73rml")));
        arrImage.add(new ImageModel("5_edb2bf", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("5_edb2bf")));
        arrImage.add(new ImageModel("4_d84rop", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("4_d84rop")));
        arrImage.add(new ImageModel("3_h73rml", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("3_h73rml")));
        arrImage.add(new ImageModel("5_edb2bf", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("5_edb2bf")));
        arrImage.add(new ImageModel("4_d84rop", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("4_d84rop")));
        arrImage.add(new ImageModel("3_h73rml", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("3_h73rml")));
        arrImage.add(new ImageModel("5_edb2bf", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("5_edb2bf")));
        arrImage.add(new ImageModel("4_d84rop", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("4_d84rop")));
        arrImage.add(new ImageModel("3_h73rml", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("3_h73rml")));
        arrImage.add(new ImageModel("5_edb2bf", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("5_edb2bf")));
        arrImage.add(new ImageModel("4_d84rop", MediaManager.get().url().transformation(new Transformation().width(150).height(150).crop("thumb")).generate("4_d84rop")));
        imageAdapter.notifyDataSetChanged();
    }

}
