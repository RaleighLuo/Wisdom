package com.gkzxhn.wisdom.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.util.Utils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

/**
 * Created by Raleigh.Luo on 17/8/1.
 */

public class DecorateDialog extends Dialog {
    private Context context;
    private View.OnClickListener onClickListener;
    private TextView tvConfirm;
    private WheelView<String> mWheelView;
    private int mArrayResId;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public DecorateDialog(@NonNull Context context,int arrayResId) {
        super(context, R.style.custom_dialog_style);
        this.context=context;
        this.mArrayResId=arrayResId;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.decorate_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){
        tvConfirm= (TextView) findViewById(R.id.decorate_dialog_layout_tv_confirm);
        mWheelView= (WheelView) findViewById(R.id.decorate_dialog_layout_wheel);
        tvConfirm.setOnClickListener(onClickListener);
        mWheelView.setWheelAdapter(new ArrayWheelAdapter(context)); // 文本数据源
        mWheelView.setSkin(WheelView.Skin.None); // common皮肤
        mWheelView.setWheelData(Utils.arrayToList(context.getResources().getStringArray(mArrayResId)));  // 数据集合
        mWheelView.setLoop(true);
        WheelView.WheelViewStyle style=new WheelView.WheelViewStyle();
        //选中
        style.selectedTextSize=16;
        style.selectedTextColor=context.getResources().getColor(R.color.common_text_color);
        // 未选中
        style.textColor=context.getResources().getColor(R.color.common_hint_text_color);
        style.textSize=10;
        mWheelView.setStyle(style);
    }
    public String getSelectedAll(){
        return mWheelView==null?null:mWheelView.getSelectionItem();
    }
    public void measureWindow(){
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();

        Display d = m.getDefaultDisplay();
        params.width = d.getWidth();
        params.height=d.getHeight()/2;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(params);
    }
}
