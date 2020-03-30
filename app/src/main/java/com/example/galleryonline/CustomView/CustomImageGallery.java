package com.example.galleryonline.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomImageGallery extends View {
    private Bitmap bitmap;
    public int widthSize;
    public CustomImageGallery(Context context) {
        super(context);
    }

    public CustomImageGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        invalidate();
    }

    private int measureHeight(int heightMeasureSpec, int widthMeasureSpec){
        int result;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode == MeasureSpec.EXACTLY){
            result = heightSize;
        }
        else{
            result = (MeasureSpec.getSize(widthMeasureSpec) + getPaddingLeft() + getPaddingRight());
            if(heightMode == MeasureSpec.AT_MOST){
                result = Math.min(result,heightSize);
            }
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec){
        int result;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            result = widthSize;
        }
        else{
            result =  (widthSize + getPaddingLeft() + getPaddingRight());
            if(widthMode == MeasureSpec.AT_MOST){
                result = Math.min(result,widthSize);
            }
        }
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("abc","abc");
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec,widthMeasureSpec));
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.getClipBounds();
        if(bitmap!=null){
            Log.d("abc","bcd");
            canvas.drawBitmap(bitmap,0,0,null);
        }
    }
    public int getWidthSize(){
        return widthSize;
    }
}
