package com.gkzxhn.wisdom.customview;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/8/1.
 */

public class FloorPopupWindow extends PopupWindow {
    private Context mContext;

    public FloorPopupWindow(Context context) {
        super(context);
        this.mContext = context;
        View view= LayoutInflater.from(context).inflate(R.layout.floor_popup_window_layout,null);
        setContentView(view);
        WindowManager m = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);;
        Display d = m.getDefaultDisplay();
        setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        setWidth(FrameLayout.LayoutParams.WRAP_CONTENT);
//        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.home_park_bg_shape));
        setOutsideTouchable(true);
    }
}
