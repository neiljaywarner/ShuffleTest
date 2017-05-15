package com.meetic.shuffle.sample;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by neil.warner on 4/28/17.
 */

public class RelativeLayoutForceScroll extends LinearLayout {
    private int mTouchSlop;
    private boolean mIsScrolling;

    public RelativeLayoutForceScroll(Context context) {
        super(context);
        initialize(context);
    }

    public RelativeLayoutForceScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);

    }

    public RelativeLayoutForceScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }


    public void initialize(Context context) {
        ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
        Log.e("NJW", "mTouchSlop=" + mTouchSlop);
    }

    float oldDistanceX = 0;
    float oldDistanceY = 0;
/*


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        */
/*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onTouchEvent will be called and we do the actual
         * scrolling there.
         *//*



        final GestureDetector myG;
        // TODO: Math.abs?
        myG = new GestureDetector(this.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1,
                    MotionEvent e2,
                    float distanceX,
                    float distanceY) {

                boolean isYScrolling = false;
                if (e1 == null || e2 == null) {
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }
                Log.e("NJWRLFS", "x2-x1;y2-y1" + e2.getX() + "-" + e1.getX() + ";" + e2.getY() + "-" + e1.getY());
                Log.e("NJWRLFS", "deltaY=" + Math.abs(e2.getY() - e1.getY()));
                Log.e("NJWRLFS", "deltaX=" + Math.abs(e2.getX() - e1.getX()));

                Log.d("NJWRLFS", "onScroll distanceX/distanceY:" + distanceX + "/" + distanceY);

                float deltaY = Math.abs(e2.getY() - e1.getY());
                float deltaX = Math.abs(e2.getX() - e1.getX());
                if (Math.abs(deltaY) > Math.abs(deltaX) && distanceY > 12) {
                    //Once a chain is intercepted, it stops going to the childview.
                    // except the cancel
                    isYScrolling = true;
                }



                if (isYScrolling) {
                    return true;
                } else {
                    return super.onScroll(e1,e2,distanceX, distanceY);
                }





            }
        });



        if (myG.onTouchEvent(ev)) {
            return true;
        } else {
            return false;
        }

    }


*/


    private int calculateDistanceX(MotionEvent ev) {
        //Log.e("NJW", "Start/size"+ ev.getHistoricalX(0) + "/" + ev.getHistorySize());
        float start = ev.getHistoricalX(0);
        float end;
        if (ev.getHistorySize() > 1) {
            end = ev.getHistoricalX(ev.getHistorySize() - 1);
            return Math.round(end - start);
        } else {
            return 0;
        }

    }

    float mDownX;
    float mDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("NJW", "RLFS:onInterceptTouchEvent:" + MotionEvent.actionToString(event.getAction()));
        }

        GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(
                getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                Log.e("TAG4", "onDown******" + event.getY());
                mDownX = event.getX();
                mDownY = event.getY();
                return false;
            }

            @Override
            public void onShowPress(MotionEvent event) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                Log.e("NJW", "onSingleTapUp");
                return false;
            }


            @Override
            public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
                Log.e("TAG4", "onInterceptTouchEvent:it's a scroll");
                boolean hasBoth = true;
                if (event1 == null) {
                    //ev1 oft null
                    hasBoth = false;
                } else {
                    Log.e("TAG4", "event1 NOT NULL");
                    Log.e("TAG4", "egvent1=" + MotionEvent.actionToString(event1.getAction()));
                }
                if (event2 == null) {
                    Log.e("TAG4", "event2 NULL");
                    hasBoth = false;
                } else {
                    Log.e("TAG4", "event2=" + MotionEvent.actionToString(event2.getAction()));
                    Log.e("TAG4", "event2dx=" + event2.getX() + ";dy=" + event2.getY());
                    Log.e("TAG4", "distx=" + Math.abs(mDownX - event2.getX()));
                    Log.e("TAG4", "distY=" + Math.abs(mDownY - event2.getY()));

                }

                if (hasBoth) {
                    float dy = Math.abs(event2.getY() - event1.getY());
                    float dx = Math.abs(event2.getX() - event1.getX());
                    Log.d("TAG4", "dy=" + dy);
                    Log.d("TAG4", "dx=" + dx);
                }

                Log.d("TAG4", "distanceX=" + distanceX);
                Log.d("TAG4", "distanceY=" + distanceY);
                return false;
            }


            @Override
            public void onLongPress(MotionEvent event) {

            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float v1, float v2) {
                Log.e("TAG4", "--->onInterceptTouchEvent:it's a fling");

                if (event1 == null || event2 == null) {
                    Log.e("TAG4", "null events do happen sometimes");
                    return false;
                }
                Log.e("TAG4", "it's a fling");
                float dy = Math.abs(event2.getY() - event1.getY());
                float dx = Math.abs(event2.getX() - event1.getX());
                Log.d("TAG4", "dy=" + dy);
                Log.d("TAG4", "dx=" + dx);
                Log.d("TAG4", "v1=" + v1);
                Log.d("TAG4", "v2=" + v2);
                return false;
            }
        });

        // Should this go in intercept?
        if (gestureDetectorCompat.onTouchEvent(event)) {
            return true;
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent
                    .ACTION_CANCEL) {
                Log.e("TAG4", "up or cancel");
                //TODO: Implements locking?  instance of nestedscroll view or localblescrollview, etc.
                Log.e("NJW", "parent=" + this.getParent().getClass().getSimpleName());
                if (this.getParent() instanceof  LockableScrollView) {
                    LockableScrollView lockableScrollView = (LockableScrollView) this.getParent();
                    lockableScrollView.setEnabled(true);
                    //TODO: Importatn, unlock when up.
                }
            }
            return super.onInterceptTouchEvent(event);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Here we actually handle the touch event (e.g. if the action is ACTION_MOVE,
        // scroll this container).
        // This method will only be called if the touch event was intercepted in
        // onInterceptTouchEvent

        // Here we let it be handled by its outer elements as a Y scroll
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.e("NJW", "RLFS:onTouchEvent:" + MotionEvent.actionToString(ev.getAction()));
        }

        GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(
                getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent event) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent event1, MotionEvent event2, float v1, float v2) {
                Log.e("TAG4", "it's a scroll");
                if (event1 == null || event2 == null) {
                    Log.e("TAG4", "null events do happen sometimes");
                    return false;
                }
                float dy = Math.abs(event2.getY() - event1.getY());
                float dx = Math.abs(event2.getX() - event1.getX());
                Log.d("TAG4", "dy=" + dy);
                Log.d("TAG4", "dx=" + dx);
                Log.d("TAG4", "v1=" + v1);
                Log.d("TAG4", "v2=" + v2);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent event) {

            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float v1, float v2) {
                if (event1 == null || event2 == null) {
                    Log.e("TAG4", "null events do happen sometimes");
                    return false;
                }
                Log.e("TAG4", "it's a fling");
                float dy = Math.abs(event2.getY() - event1.getY());
                float dx = Math.abs(event2.getX() - event1.getX());
                Log.d("TAG4", "dy=" + dy);
                Log.d("TAG4", "dx=" + dx);
                Log.d("TAG4", "v1=" + v1);
                Log.d("TAG4", "v2=" + v2);
                return false;
            }
        });

        // Should this go in intercept?
        if (gestureDetectorCompat.onTouchEvent(ev)) {
            return true;
        } else {
            return super.onTouchEvent(ev);
        }



    }
}
// https://developer.android.com/training/gestures/viewgroup.html
