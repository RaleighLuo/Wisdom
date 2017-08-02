package com.gkzxhn.wisdom.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public class HouseAreaDialog extends Dialog {
    private Context context;
    private View.OnClickListener onClickListener;
    private EditText etArea;
    private String area="";
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public HouseAreaDialog(@NonNull Context context) {
        super(context, R.style.custom_translucent_dialog_style);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.house_area_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){
        findViewById(R.id.house_area_dialog_layout_tv_confirm).setOnClickListener(onClickListener);
        etArea= (EditText) findViewById(R.id.house_area_dialog_layout_et_area);
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
    public String getContent(){
        area=etArea.getText().toString().trim();
        return etArea.getText().toString().trim()+context.getString(R.string.house_area_unit);
    }

    public String getArea() {
        return area ;
    }

    @Override
    public void show() {
        super.show();
        etArea.setText(area);
        etArea.setFocusable(true);
        etArea.setFocusableInTouchMode(true);
        //请求获得焦点
        etArea.requestFocus();
        if(area.length()>0)etArea.setSelection(area.length());//光标在最后
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) etArea
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etArea, 0);
            }
        },100);
    }
}
