package com.gkzxhn.wisdom.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.gkzxhn.wisdom.R;
import com.starlight.mobile.android.lib.view.EqualImageView;

/**
 * Created by Raleigh.Luo on 17/7/21.
 */

public class RoundCornersImageView extends AppCompatImageView {
    private float radiusX=10;
    private float radiusY=10;

    public RoundCornersImageView(Context context) {
        super(context);
    }

    public RoundCornersImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCorners);
        radiusX =a.getDimension(R.styleable.RoundCorners_radiusX, 10);
        radiusY =a.getDimension(R.styleable.RoundCorners_radiusY, 10);

    }

    public RoundCornersImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundCorners);
        radiusX = (int) a.getDimension(R.styleable.RoundCorners_radiusX, 10);
        radiusY = (int) a.getDimension(R.styleable.RoundCorners_radiusY, 10);
    }

    /**
     *
     * @param rx x方向弧度
     * @param ry y方向弧度
     */
    public void setRadius(float rx, float ry) {
        this.radiusX = rx;
        this.radiusY = ry;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        RectF rectF = new RectF(rect);
        path.addRoundRect(rectF, radiusX, radiusY, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);//Op.REPLACE这个范围内的都将显示，超出的部分覆盖
        super.onDraw(canvas);
    }
    private int height=0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth=getMeasuredWidth();
        setMeasuredDimension(measuredWidth, measuredWidth);

        if(measuredWidth>0&&height!=measuredWidth){
            height=measuredWidth;
        }
    }

}
