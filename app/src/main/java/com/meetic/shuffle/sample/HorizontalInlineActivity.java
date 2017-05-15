package com.meetic.shuffle.sample;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.meetic.dragueur.Direction;
import com.meetic.dragueur.DraggableView;
import com.meetic.shuffle.Shuffle;
import com.meetic.shuffle.ShuffleViewAnimator;
import com.meetic.shuffle.sample.adapter.TestAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HorizontalInlineActivity extends AppCompatActivity {

    @Bind(R.id.shuffle) Shuffle shuffle;
    @Bind(R.id.dashboard_scrollview)
    LockableScrollView scrollView;

    @Bind(R.id.outer)
    ViewGroup viewGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_inline);
        ButterKnife.bind(this);
        shuffle.setViewAnimator(new ShuffleViewAnimator()
            .setPushLeftAnimateViewStackScaleUp(false)
            .setPushRightAnimateViewStackScaleUp(false)
        );
        shuffle.getFirstDraggableView().setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent event) {
                Log.e("NJW", "getFirstDraggableViewondrag");
                return false;
            }
        });

        shuffle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("TAG4", "--OTL---event=" + MotionEvent.actionToString(event.getAction()));
                return false;
            }
        });
        shuffle.addListener(new Shuffle.Listener() {
            @Override
            public void onViewChanged(int position) {
                Log.d("TAG", "onViewChanged" + position);
            }

            @Override
            public void onScrollStarted() {
                //shuffle.getParent().requestDisallowInterceptTouchEvent(true);
                Log.e("TAG2", "---->onScrollStarted; disallow intercept");
            }

            @Override
            public void onScrollFinished() {
                //shuffle.getParent().requestDisallowInterceptTouchEvent(false);
                Log.d("TAG2", "-------->onScrollFinished; disallow intercept");
            }

            @Override
            public void onViewExited(DraggableView draggableView, Direction direction) {
                Log.e("TAG", "onViewExited:" + direction.name());

            }

            @Override
            public void onViewScrolled(DraggableView draggableView, float percentX, float percentY) {
                float deltaX = Math.abs(percentX);
                float deltaY = Math.abs(percentY);
                if (deltaY > 0.0) {
                   // Log.e("TAG3", "Will this ever happen with inline mode?");
                }
                if (deltaX ==0) {
                    Log.e("TAG3JW", "*******dx=0" + deltaX);
                }
                if (deltaX < 0.00001) {
                    Log.e("TAG3", "???:deltaX=" + deltaX + "dy=" + deltaY);
                    scrollView.setScrollingEnabled(true);
                    return;
                } else {
                    // TODO: And not fling; if Flinging Y axis it shoudl work.
                    scrollView.setScrollingEnabled(false);
                    return;
                }
                /*
                //experiment with numbers, then realied elta=better
                if (deltaX >0.0 && deltaX < 0.05) {
                    Log.e("TAG", "allow scrolling b/c deltaX<0.1;deltax=" + deltaX);
                    Log.e("TAG", "deltay=" + deltaY);
                    //scrollView.setScrollingEnabled(true);
                }
                if (deltaX > 0.1 && deltaX < 0.2) {
                    //Log.d("TAG", "really starting:" + percentX);
                    scrollView.setScrollingEnabled(false);
                } else if (deltaX > 0.95) {
                    //Log.d("TAG", "about done:" + percentX);
                }
                */

                // Log.e("NJW", "********View scrolledY:" + percentY);
               // Log.e("NJW", "********View scrolledX:" + percentX);

            }
        });
        shuffle.setShuffleAdapter(new TestAdapter());


        /*
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v,
                    int scrollX,
                    int scrollY,
                    int oldScrollX,
                    int oldScrollY) {

                int diffY = Math.abs(scrollY - oldScrollY);
                int diffX = Math.abs(scrollX - oldScrollX);
                Log.e("NJW", "diffy=" + diffY);
                Log.e("NJW", "diffx=" + diffX);
                Log.e("TAG", "---in onScroll of ScrollView (enabled?): diffX = " + diffX);

                if (diffY > diffX) {
                    //shuffle.enable(false);
                    Log.e("NJW", "should disable");
                } else {
                    Log.e("NJW", "should enable");

                    //shuffle.enable(true);
                }
            }
        });

        */




    }

    float mDown;


    class Gesture extends GestureDetector.SimpleOnGestureListener{

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                float distanceY) {
            Log.d("NJW", "Scroll");
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            Log.d("NJW", "fling");

            return true;
        }
    }

}
