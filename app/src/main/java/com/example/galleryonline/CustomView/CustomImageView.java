package com.example.galleryonline.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class CustomImageView extends View {

    private ScaleGestureDetector mScaleDetector;
    private Bitmap bitmap;

    private float mSacleFactor = 1f;
    private float scalePointX;
    private float scalePointY;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScaleDetector = new ScaleGestureDetector(context,new ScaleListener());
    }

    public CustomImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    private int measureHeight(int heightMeasureSpec){
        int result;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode == MeasureSpec.EXACTLY){
            result = heightSize;
        }
        else{
            result = (heightSize + getPaddingLeft() + getPaddingRight());
            if(heightMode == MeasureSpec.AT_MOST){
                result = Math.min(result,heightSize);
            }
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec){
        int result ;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            result = widthSize;
        }
        else{
            result = (widthSize + getPaddingLeft() + getPaddingRight());
            if(widthMode == MeasureSpec.AT_MOST){
                result = Math.min(result,widthSize);
            }
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmap!=null){
            canvas.save();
            if(mSacleFactor>=1) {
                canvas.scale(mSacleFactor, mSacleFactor, scalePointX, scalePointY);
            }
//            canvas.translate(mPosX,mPosY);
            canvas.drawBitmap(bitmap,0,0,null);
            canvas.restore();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch(action & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN: {
//                final float x = (ev.getX() - scalePointX)/mSacleFactor;
//                final float y = (ev.getY() - scalePointY)/mSacleFactor;
//                mLastTouchX = x;
//                mLastTouchY = y;
//                break;
//            }
//            case MotionEvent.ACTION_MOVE: {
//
//                final float x = (ev.getX() - scalePointX)/mSacleFactor;
//                final float y = (ev.getY() - scalePointY)/mSacleFactor;
//                // Only move if the ScaleGestureDetector isn't processing a gesture.
//                if (!mScaleDetector.isInProgress()) {
//                    final float dx = x - mLastTouchX; // change in X
//                    final float dy = y - mLastTouchY; // change in Y
//                    mPosX += dx;
//                    mPosY += dy;
//                    Log.d("CustomView","PosX = "+mPosX+" ; PosY = "+mPosY);
//                    invalidate();
//                }
//
//                mLastTouchX = x;
//                mLastTouchY = y;
//                break;
//
//            }
            case MotionEvent.ACTION_UP: {
                invalidate();
            }
        }
        return true;
    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mSacleFactor *= detector.getScaleFactor();
            mSacleFactor = Math.max(0.1f, Math.min(mSacleFactor, 4.0f));
            scalePointX =  detector.getFocusX();
            scalePointY = detector.getFocusY();
            invalidate();
            return true;
        }
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        invalidate();
    }

}
