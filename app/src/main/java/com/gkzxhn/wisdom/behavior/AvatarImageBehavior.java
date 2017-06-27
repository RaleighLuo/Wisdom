package com.gkzxhn.wisdom.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 图片控件位置动画
 *
 * @author wangchenlong
 */
@SuppressWarnings("unused")
public class AvatarImageBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {
    private final static float MIN_AVATAR_PERCENTAGE_SIZE = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING = 80;

    private int mStartYPosition; // 起始的Y轴位置
    private int mFinalYPosition; // 结束的Y轴位置
    private int mStartHeight; // 开始的图片高度
    private int mFinalHeight; // 结束的图片高度
    private int mStartXPosition; // 起始的X轴高度
    private int mFinalXPosition; // 结束的X轴高度
    private float mStartToolbarPosition; // Toolbar的起始位置

    private final Context mContext;
    private float mAvatarMaxSize;
    private int mImageStartMargin,mImageFinalMargin;

    public AvatarImageBehavior(Context context, AttributeSet attrs) {
        mContext = context;
        init();
    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
        mImageStartMargin =mContext.getResources().getDimensionPixelSize(R.dimen.image_start_margin);
        mImageFinalMargin =mContext.getResources().getDimensionPixelSize(R.dimen.image_final_margin);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        // 依赖Toolbar控件
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RelativeLayout childRoot, View dependency) {
        CircleImageView child= (CircleImageView) childRoot.getChildAt(0);

        // 初始化属性
        shouldInitProperties(childRoot,child, dependency);

        // 最大滑动距离: 起始位置-状态栏高度
//        final int maxScrollDistance = (int) (mStartToolbarPosition - getStatusBarHeight());
        final int  maxScrollDistance=getStatusBarHeight();
        // 滑动的百分比
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        for(int i=1;i<childRoot.getChildCount();i++){
            TextView tvTitle= (TextView) childRoot.getChildAt(i);
            int dimen=0;
            switch (tvTitle.getId()){
                case R.id.home_full_screen_layout_tv_week:
                case R.id.home_full_screen_layout_tv_date:
                    dimen=R.dimen.main_layout_date_text_size;
                    break;
                case R.id.home_full_screen_layout_tv_home_number:
                    dimen=R.dimen.main_layout_home_number_text_size;
                    break;
                case R.id.home_full_screen_layout_tv_community_name:
                    dimen=R.dimen.main_layout_community_name_text_size;
                    break;

            }
            int titleSize=mContext.getResources().getDimensionPixelSize(dimen);
            float disSize=4*(1-expandedPercentageFactor);//[0-4的区间]
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (titleSize-disSize));
        }
//        Log.e("mStartToolbarPosition="+mStartToolbarPosition,"raleigh_test");
//        Log.e("getStatusBarHeight()="+getStatusBarHeight(),"raleigh_test");
//        Log.e("dependency.getY() ="+dependency.getY() ,"raleigh_test");
//        Log.e("expandedPercentageFactor="+expandedPercentageFactor,"raleigh_test");
        int dis=expandedPercentageFactor==0?(mFinalHeight/2):(child.getHeight() / 2);
        // Y轴距离
        float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                * (1f - expandedPercentageFactor)) +dis;

        // X轴距离
        float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                * (1f - expandedPercentageFactor)) + dis;

        // Y轴距离
//        float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
//                * (1f - expandedPercentageFactor)) + (child.getHeight() / 2);
//
//        // X轴距离
//        float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
//                * (1f - expandedPercentageFactor)) + (child.getWidth() / 2);

//        Log.e("distanceXToSubtract="+distanceXToSubtract,"raleigh_test");
//        Log.e("distanceYToSubtract="+distanceYToSubtract,"raleigh_test");
//        Log.e("child.getWidth()="+child.getWidth(),"raleigh_test");
//        Log.e("mStartXPosition="+mStartXPosition,"raleigh_test");
//        Log.e("mFinalXPosition="+mFinalXPosition,"raleigh_test");
//        Log.e("expandedPercentageFactor="+expandedPercentageFactor,"raleigh_test");
//        Log.e("mStartYPosition - distanceYToSubtract="+(mStartYPosition - distanceYToSubtract),"raleigh_test");
//        Log.e("mStartXPosition - distanceXToSubtract="+(mStartXPosition - distanceXToSubtract),"raleigh_test");

        // 高度减小
        float heightToSubtract = ((mStartHeight - mFinalHeight) * (1f - expandedPercentageFactor));

        // 图片位置
        child.setY(mStartYPosition - distanceYToSubtract);
        child.setX(mStartXPosition - distanceXToSubtract);

        // 图片大小
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) child.getLayoutParams();
        lp.width = (int) (mStartHeight - heightToSubtract);
        lp.height = (int) (mStartHeight - heightToSubtract);
        child.setLayoutParams(lp);

        return true;
    }

    /**
     * 初始化动画值
     *
     * @param child      图片控件
     * @param dependency ToolBar
     */
    private void shouldInitProperties( RelativeLayout childRoot,CircleImageView child, View dependency) {
//        Log.e("childX="+child.getX()+",childY"+ child.getY()+",childHeigth"+child.getHeight(),"raleigh_test");
//        Log.e("dependencyX="+dependency.getX()+",dependencyY"+ dependency.getY()+",dependencyHeigth"+dependency.getHeight(),"raleigh_test");

        // 图片控件中心
        if (mStartYPosition == 0)
//            mStartYPosition = (int) (child.getY() + (child.getHeight() / 2));
            mStartYPosition = (int) (child.getY()> mImageStartMargin ?child.getY(): mImageStartMargin + (child.getHeight() / 2));
        // Toolbar中心

        // 图片高度
        if (mStartHeight == 0)
            mStartHeight = child.getHeight();

        // Toolbar缩略图高度
        if (mFinalHeight == 0)
            mFinalHeight = mContext.getResources().getDimensionPixelOffset(R.dimen.image_final_width);

        // 图片控件水平中心
        if (mStartXPosition == 0)
//            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));
            mStartXPosition = (int) (child.getX()> mImageStartMargin ?child.getX(): mImageStartMargin + (child.getWidth() / 2));

        // 边缘+缩略图宽度的一半
        if (mFinalXPosition == 0)
            mFinalXPosition=childRoot.getPaddingLeft()+(mFinalHeight / 2);
//            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.image_final_margin) + (mFinalHeight / 2);
        if (mFinalYPosition == 0)
            mFinalYPosition = childRoot.getPaddingTop()+(mFinalHeight / 2);

        // Toolbar的起始位置
        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY() + (dependency.getHeight() / 2);
    }

    // 获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        result=mContext.getResources().getDimensionPixelSize(R.dimen.toolbar_height);
//        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
//
//        if (resourceId > 0) {
//            result = mContext.getResources().getDimensionPixelSize(resourceId);
//        }
        return result;
    }
}