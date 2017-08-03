package com.gkzxhn.wisdom.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
 * Created by Raleigh.Luo on 17/8/1.
 */

public class FloorDialog extends Dialog {
    private Context context;
    private View.OnClickListener onClickListener;
    private EditText etCurrentFloor,etAllFloor;
    private String currentFloor="",allFloor="";
    private Handler handler=new Handler();
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public FloorDialog(Context context) {
        super(context, R.style.custom_translucent_dialog_style);
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.floor_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){
        findViewById(R.id.floor_dialog_layout_tv_confirm).setOnClickListener(onClickListener);
        etCurrentFloor= (EditText) findViewById(R.id.floor_dialog_layout_et_floor);
        etAllFloor= (EditText)  findViewById(R.id.floor_dialog_layout_et_floor_all);

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
        return String.format("%s%s/%s%s",getCurrentFloor(), context.getString(R.string.floor_unit),
                getAllFloor(), context.getString(R.string.floor_unit));
    }
    public String getCurrentFloor(){
        currentFloor=etCurrentFloor.getText().toString().trim();
        return  etCurrentFloor.getText().toString().trim();
    }
    public String getAllFloor(){
        allFloor=etAllFloor.getText().toString().trim();
        return etAllFloor.getText().toString().trim();
    }

    @Override
    public void show() {
        super.show();
        //设置可获得焦点
        etCurrentFloor.setText(currentFloor);
        etAllFloor.setText(allFloor);
        etCurrentFloor.setFocusable(true);
        etCurrentFloor.setFocusableInTouchMode(true);
        //请求获得焦点
        etCurrentFloor.requestFocus();
        if(currentFloor.length()>0)etCurrentFloor.setSelection(currentFloor.length());//光标在最后
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) etCurrentFloor
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etCurrentFloor, 0);
            }
        },100);
    }
}
