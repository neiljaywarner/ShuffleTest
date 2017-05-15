package com.meetic.shuffle.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.meetic.dragueur.Direction;
import com.meetic.dragueur.DraggableView;
import com.meetic.shuffle.Shuffle;
import com.meetic.shuffle.ShuffleViewAnimator;
import com.meetic.shuffle.sample.adapter.TestAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HorizontalInlineActivity extends AppCompatActivity {

    public static final double TINY_PERCENTAGE = 0.00001;
    @Bind(R.id.shuffle) Shuffle shuffle;
    @Bind(R.id.dashboard_scrollview)
    LockableScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_inline);
        ButterKnife.bind(this);
        shuffle.setViewAnimator(new ShuffleViewAnimator()
            .setPushLeftAnimateViewStackScaleUp(false)
            .setPushRightAnimateViewStackScaleUp(false)
        );

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

                if (deltaX < TINY_PERCENTAGE) {
                    scrollView.setScrollingEnabled(true);
                } else {
                    // TODO: And not fling; if Flinging Y axis it shoudl work.
                    scrollView.setScrollingEnabled(false);
                }
            }
        });
        shuffle.setShuffleAdapter(new TestAdapter());
    }
}
