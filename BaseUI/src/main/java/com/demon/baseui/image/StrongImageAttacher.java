package com.demon.baseui.image;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;


public class StrongImageAttacher implements View.OnTouchListener {

    private StrongImageView mStrongView;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;

    public StrongImageAttacher(StrongImageView intensifyView) {
        mStrongView = intensifyView;
        Context context = intensifyView.getContext();
        mScaleGestureDetector = new ScaleGestureDetector(context, new OnScaleGestureAdapter());
        mGestureDetector = new GestureDetector(context, new OnGestureAdapter());
        mStrongView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) | mScaleGestureDetector.onTouchEvent(event);
    }

    private class OnScaleGestureAdapter extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mStrongView.addScale(detector.getScaleFactor(),
                    detector.getFocusX(), detector.getFocusY());
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mStrongView.home();
        }
    }

    private class OnGestureAdapter extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            mStrongView.onTouch(e.getX(), e.getY());
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mStrongView.doubleTap(e.getX(), e.getY());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mStrongView.scroll(distanceX, distanceY);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mStrongView.fling(-velocityX, -velocityY);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mStrongView.singleTap(e.getX(), e.getY());
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mStrongView.longPress(e.getX(), e.getY());
        }
    }
}
