package com.gkzxhn.wisdom.behavior;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.starlight.mobile.android.lib.util.ViewUtil;

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

    private final Context mContext;
    private float mAvatarMaxSize;
    private float margin;

    public AvatarImageBehavior(Context context, AttributeSet attrs) {
        mContext = context;
        init();
    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
        margin=mContext.getResources().getDimension(R.dimen.main_image_margin);

    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        // 依赖Toolbar控件
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RelativeLayout childRoot, View dependency) {
        CircleImageView child= (CircleImageView) childRoot.getChildAt(0);

        // 初始化属性
        shouldInitProperties(childRoot,child, dependency);

        // 最大滑动距离: 起始位置-状态栏高度
//        final int maxScrollDistance = (int) (mStartToolbarPosition - getStatusBarHeight());
        Point point=new Point();
        ViewUtil.getScreenDisplay(mContext).getSize(point);
        final int  maxScrollDistance=point.x;//获取屏幕的高度  NestedScrollView滑动的区域是整个屏幕
        // 滑动的百分比
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;
        if(expandedPercentageFactor>1)expandedPercentageFactor=1;

        for(int i=1;i<childRoot.getChildCount();i++){
            childRoot.getChildAt(i);
            View mChild=childRoot.getChildAt(i);
                int dimen=0;
                float disSize=0;
                int padingTop=0;
                TextView tvTitle;
                switch (mChild.getId()){
                    case R.id.home_full_screen_layout_tv_week:
                    case R.id.home_full_screen_layout_tv_date:
                        tvTitle= (TextView) mChild;
                        dimen=R.dimen.main_layout_date_text_size;
                        padingTop=mContext.getResources().getDimensionPixelSize(
                                R.dimen.main_layout_date_padding_top);
                        disSize=5*(1-expandedPercentageFactor);//[0-5的区间]
                        int titleSize = mContext.getResources().getDimensionPixelSize(dimen);
                        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize - disSize);
                        //区间 expandedPercentageFactor［0，1］换区间［0.3-1］
                        tvTitle.setPadding(0, (int) (padingTop * (0.7 * expandedPercentageFactor + 0.3)), 0, 0);
                        break;
                    case R.id.home_full_screen_layout_tv_home_number:
                        tvTitle= (TextView) mChild;
                        dimen=R.dimen.main_layout_home_number_text_size;
                        padingTop=mContext.getResources().getDimensionPixelSize(
                                R.dimen.main_layout_home_number_padding_top);
                        disSize=25*(1-expandedPercentageFactor);//[0-25的区间]
                        titleSize = mContext.getResources().getDimensionPixelSize(dimen);
                        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize - disSize);
                        //区间 expandedPercentageFactor［0，1］换区间［0.3-1］
                        tvTitle.setPadding(0, (int) (padingTop * (0.7 * expandedPercentageFactor + 0.3)), 0, 0);

                        break;
                    case R.id.home_full_screen_layout_tv_community_name:
                        tvTitle= (TextView) mChild;
                        padingTop=mContext.getResources().getDimensionPixelSize(
                                R.dimen.main_layout_community_name_padding_top);
                        dimen=R.dimen.main_layout_community_name_text_size;
                        disSize=30*(1-expandedPercentageFactor);//区间 expandedPercentageFactor［0，1］换[0-30的区间]
                        titleSize = mContext.getResources().getDimensionPixelSize(dimen);
                        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize - disSize);
                        //区间 expandedPercentageFactor［0，1］换区间［0.3-1］
                        tvTitle.setPadding(0, (int) (padingTop * (0.7 * expandedPercentageFactor + 0.3)), 0, 0);

                        break;
                    default: //View
                        mChild.setEnabled(expandedPercentageFactor>=0.9);
//                        mChild.setAlpha(expandedPercentageFactor);
                        //为显示透明度效果好点
                        float alpha= expandedPercentageFactor==1?expandedPercentageFactor:(expandedPercentageFactor/2);
                        mChild.setAlpha(alpha);
                        break;

                }
        }
        int dis=expandedPercentageFactor==0?(mFinalHeight/2):(child.getHeight() / 2);
        // Y轴距离
        float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                * (1f - expandedPercentageFactor)) +dis;

        // X轴距离
        float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                * (1f - expandedPercentageFactor)) + dis;
        // 高度减小
        float heightToSubtract = ((mStartHeight - mFinalHeight) * (1f - expandedPercentageFactor));

        child.setY(margin);
        child.setX(margin);

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
        int imageMargrinTop=childRoot.getPaddingTop()+mContext.getResources().getDimensionPixelSize(R.dimen.image_start_margin);
        int imageMargrinLeft=childRoot.getPaddingLeft()+mContext.getResources().getDimensionPixelSize(R.dimen.image_start_margin);
        // 图片控件中心
        if (mStartYPosition == 0)
//            mStartYPosition = (int) (child.getY() + (child.getHeight() / 2));
            mStartYPosition = (int) (child.getY()> imageMargrinTop ?child.getY(): imageMargrinTop + (child.getHeight() / 2));
        // Toolbar中心

        // 图片高度
        if (mStartHeight == 0)
            mStartHeight = child.getHeight();

        // Toolbar缩略图高度
        if (mFinalHeight == 0)
            mFinalHeight = mContext.getResources().getDimensionPixelOffset(R.dimen.image_final_width);

        // 图片控件水平中心
        if (mStartXPosition == 0)
            mStartXPosition = (int) (child.getX()> imageMargrinLeft ?child.getX(): imageMargrinLeft + (child.getWidth() / 2));

        // 边缘+缩略图宽度的一半
        if (mFinalXPosition == 0)
            mFinalXPosition=childRoot.getPaddingLeft()+(mFinalHeight / 2);
        if (mFinalYPosition == 0)
            mFinalYPosition = childRoot.getPaddingTop()+(mFinalHeight / 2);
    }
}