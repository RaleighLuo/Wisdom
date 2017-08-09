package com.gkzxhn.wisdom.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/28.
 */

public class CusTextItem extends LinearLayout {
    private TextView tvTitle,tvContent;

    public CusTextItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cus_text_item_layout, this);
        tvTitle= (TextView) findViewById(R.id.cus_text_item_layout_tv_title);
        tvContent= (TextView) findViewById(R.id.cus_text_item_layout_tv_content);
        //获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CusTextItem_Attrs);
        //文字颜色
        int titleColor=a.getResourceId(R.styleable.CusTextItem_Attrs_cti_title_color,R.color.common_text_color);
        int contentColor=a.getResourceId(R.styleable.CusTextItem_Attrs_cti_content_color, R.color.common_dark_gray_text_color);
        int titleSize=a.getDimensionPixelSize(R.styleable.CusTextItem_Attrs_cti_title_size, 0);
        if(titleSize>0) tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        int contentSize=a.getDimensionPixelSize(R.styleable.CusTextItem_Attrs_cti_content_size, 0);
        if(contentSize>0) tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize);

        //获取title属性值,默认为：空字符串
        tvTitle.setText(a.getResourceId(R.styleable.CusTextItem_Attrs_cti_title, R.string.empty));
        tvTitle.setTextColor(getResources().getColor(titleColor));
        //获取title属性值,默认为：空字符串
        tvContent.setText(a.getResourceId(R.styleable.CusTextItem_Attrs_cti_content, R.string.empty));
        tvContent.setTextColor(getResources().getColor(contentColor));
        a.recycle();

    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvContent() {
        return tvContent;
    }
}
