package com.gkzxhn.wisdom.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.wx.wheelview.widget.WheelViewDialog;

/**
 * Created by Raleigh.Luo on 17/8/1.
 */

public class HouseTypeDialog extends Dialog {
    private Context context;
    private WheelView<String> wvRoom,wvHall,wvGuards;
    private TextView tvTitle,tvConfirm;
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HouseTypeDialog(Context context) {
        super(context, R.style.custom_dialog_style);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.house_type_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){
        tvTitle= (TextView) findViewById(R.id.house_type_dialog_layout_tv_title);
        tvConfirm= (TextView) findViewById(R.id.house_type_dialog_layout_tv_confirm);
        tvConfirm.setOnClickListener(onClickListener);
        wvRoom= (WheelView) findViewById(R.id.house_type_dialog_layout_wv_room);
        initWheelView(wvRoom,R.array.house_type_room);
        wvHall= (WheelView) findViewById(R.id.house_type_dialog_layout_wv_hall);
        initWheelView(wvHall,R.array.house_type_hall);
        wvGuards= (WheelView) findViewById(R.id.house_type_dialog_layout_wv_guards);
        initWheelView(wvGuards,R.array.house_type_guards);
    }
    private void initWheelView(WheelView wheelView,int arrayResId){
        wheelView.setWheelAdapter(new ArrayWheelAdapter(context)); // 文本数据源
        wheelView.setSkin(WheelView.Skin.None); // common皮肤
        wheelView.setWheelData(Utils.arrayToList(context.getResources().getStringArray(arrayResId)));  // 数据集合
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
    public String  getRoom(){
        return wvRoom.getSelectionItem();
    }
    public String  getHall(){
        return wvHall.getSelectionItem();
    }
    public String  getGuards(){
        return wvGuards.getSelectionItem();
    }
    public String getSelectedAll(){
        return wvRoom==null?null:wvRoom.getSelectionItem()+wvHall.getSelectionItem()+wvGuards.getSelectionItem();
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
