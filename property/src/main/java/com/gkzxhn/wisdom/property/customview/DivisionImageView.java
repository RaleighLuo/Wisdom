package com.gkzxhn.wisdom.property.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.gkzxhn.wisdom.property.R;


/**
 * Created by Raleigh.Luo on 17/8/3.
 */

public class DivisionImageView extends AppCompatImageView {
    private int height=0;
    private int divisor=3;//除数
    private int dividend=4;//被除数

    public DivisionImageView(Context context) {
        super(context);
    }

    public void setDivisor(int divisor,int dividend) {
        this.divisor = divisor;
        this.dividend = dividend;
        requestLayout();
    }

    public DivisionImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DivisionImageView_Attrs);
        dividend=a.getInteger(R.styleable.DivisionImageView_Attrs_div_dividend,dividend);
        divisor=a.getInteger(R.styleable.DivisionImageView_Attrs_div_divisor,divisor);
        a.recycle();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        int mesuredHeight=divisor*getMeasuredWidth()/dividend;
        setMeasuredDimension(measuredWidth, mesuredHeight);
        if(mesuredHeight>0&&height!=mesuredHeight){
            height=mesuredHeight;
        }
    }
    public interface OnChangeHeightListener{
        public void onChange(int measuredHeight);
    }
}
