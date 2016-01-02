package com.tile.janv.userinterface1.swipe;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janv on 22-Oct-15.
 */
public class SwipeListener extends GestureDetector.SimpleOnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private static final String DEBUG_TAG = "Gestures";

    private List<SwipeCallback> callbackList = new ArrayList<>();

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }


    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        boolean result = false;
        try {
            float diffY = event2.getY() - event1.getY();
            float diffX = event2.getX() - event1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void addCallback(SwipeCallback callback) {
        callbackList.add(callback);
    }

    public void removeCallback(SwipeCallback callback) {
        callbackList.remove(callback);
    }

    private void onSwipeRight() {
        for (SwipeCallback swipeCallback : callbackList) {
            swipeCallback.right();
        }
    }

    private void onSwipeLeft() {
        for (SwipeCallback swipeCallback : callbackList) {
            swipeCallback.left();
        }
    }

    private void onSwipeTop() {
        for (SwipeCallback swipeCallback : callbackList) {
            swipeCallback.up();
        }
    }

    private void onSwipeBottom() {
        for (SwipeCallback swipeCallback : callbackList) {
            swipeCallback.down();
        }
    }
}
