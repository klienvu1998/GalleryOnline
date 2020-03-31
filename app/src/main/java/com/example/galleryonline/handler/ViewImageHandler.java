package com.example.galleryonline.handler;
import android.os.Handler;
import android.os.Message;
import com.example.galleryonline.fragment.ViewImage;
import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;

public class ViewImageHandler extends Handler {
    private WeakReference<ViewImage> weakReference;
    public ViewImageHandler(ViewImage weakReference) {
        this.weakReference = new WeakReference<>(weakReference);
    }


    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == ViewImage.COMPLETE_LOAD_LINK){
            new ViewImage.getImage(weakReference.get()).execute(ViewImage.link);
        }
    }

}
