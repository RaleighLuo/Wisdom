package com.gkzxhn.wisdom.property.customview;

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

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.util.Utils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/9/14.
 * 指派
 */

public class AssignDialog extends Dialog {
    private Context context;
    private WheelView mDeparmentWheel,mNameWheel;
    public AssignDialog(@NonNull Context context) {
        super(context, R.style.assign_dialog_style);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.assign_dialog_layout, null));
        init();
        measureWindow();
    }
    private void  init(){
        mDeparmentWheel= (WheelView) findViewById(R.id.assign_dialog_layout_wv_department);
        mNameWheel= (WheelView) findViewById(R.id.assign_dialog_layout_wv_name);
        initWheelView(mDeparmentWheel);
        initWheelView(mNameWheel);
        mDeparmentWheel.setWheelData(createMainDatas());
        mNameWheel.setWheelData(createSubDatas().get(createMainDatas().get(mDeparmentWheel.getSelection())));
        mDeparmentWheel.join(mNameWheel);
        mDeparmentWheel.joinDatas(createSubDatas());
        mDeparmentWheel.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                mNameWheel.setSelection(mNameWheel.getCurrentPosition());
            }
        });
        findViewById(R.id.assign_dialog_layout_tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initWheelView(WheelView wheelView){
        wheelView.setWheelAdapter(new ArrayWheelAdapter(context)); // 文本数据源
        wheelView.setSkin(WheelView.Skin.None); // common皮肤
        wheelView.setLoop(true);
        WheelView.WheelViewStyle style=new WheelView.WheelViewStyle();
        //选中
        style.selectedTextSize=16;
        style.selectedTextColor=context.getResources().getColor(R.color.common_text_color);
        // 未选中
        style.textColor=context.getResources().getColor(R.color.common_hint_text_color);
        style.textSize=10;
        wheelView.setStyle(style);
    }
    public void measureWindow(){
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        params.width = d.getWidth();
//        params.height=d.getHeight()/2;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(params);
    }

    private List<String> createMainDatas() {
        String[] strings = {"工程部", "清洁部", "保安部"};
        return Arrays.asList(strings);
    }


    private HashMap<String, List<String>> createSubDatas() {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        String[] strings = {"工程部", "清洁部", "保安部"};
        String[] s1 = {"张三", "刘淇", "李庆"};
        String[] s2 = {"林业", "李丽"};
        String[] s3 = {"张琳", "李松", "李山", "罗琦"};
        String[][] ss = {s1, s2, s3};
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }

}
