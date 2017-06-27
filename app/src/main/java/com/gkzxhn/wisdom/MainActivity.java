package com.gkzxhn.wisdom;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gkzxhn.wisdom.adapter.NoticeAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleContainerVisible = true;


    @Bind(R.id.main_ll_title_container)
    View mLlTitleContainer; // Title的LinearLayout

    @Bind(R.id.main_fl_title)
    FrameLayout mFlTitleContainer; // Title的FrameLayout

    @Bind(R.id.main_abl_app_bar)
    AppBarLayout mAblAppBar; // 整个可以滑动的AppBar

    @Bind(R.id.main_layout_nestedscrollview)
    NestedScrollView mNestedScrollView; // 标题栏Title
    @Bind(R.id.main_layout_recyclerView)
    RecyclerView mRecyclerView; // 工具栏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        // AppBar的监听
        mAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                handleAlphaOnTitle(percentage);
            }
        });

        initParallaxValues(); // 自动滑动效果
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new NoticeAdapter(this));
        mNestedScrollView.setFillViewport(true);//NestedScrollView子项全屏
//        mRecyclerView.setNestedScrollingEnabled(false);//解决滑动不流畅的问题
    }
    private boolean isExpanded=true;
    public void onClickListener(View view){
        isExpanded=!isExpanded;
        mAblAppBar.setExpanded(isExpanded);
    }

    // 设置自动滑动的动画效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();
        petBackgroundLp.setParallaxMultiplier(PERCENTAGE_TO_HIDE_TITLE_DETAILS);
        mFlTitleContainer.setLayoutParams(petBackgroundLp);
    }


    // 控制Title的显示
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);

                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 设置渐变的动画
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
