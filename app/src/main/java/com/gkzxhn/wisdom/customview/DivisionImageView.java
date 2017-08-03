package com.gkzxhn.wisdom.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.starlight.mobile.android.lib.view.EqualImageView;

/**
 * Created by Raleigh.Luo on 17/8/3.
 */

public class DivisionImageView extends AppCompatImageView {
    private int height=0;

    public DivisionImageView(Context context) {
        super(context);
    }

    public DivisionImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DivisionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        int mesuredHeight=3*getMeasuredWidth()/4;
        setMeasuredDimension(measuredWidth, mesuredHeight);
        if(mesuredHeight>0&&height!=mesuredHeight){
            height=mesuredHeight;
        }
    }
    public interface OnChangeHeightListener{
        public void onChange(int measuredHeight);
    }
}
