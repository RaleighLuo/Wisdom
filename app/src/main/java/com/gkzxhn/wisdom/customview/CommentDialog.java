package com.gkzxhn.wisdom.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;

/**
 * Created by Raleigh.Luo on 17/8/3.
 * 话题回复
 */

public class CommentDialog  extends Dialog {
    private Context context;
    public CommentDialog(@NonNull Context context) {
        super(context, R.style.custom_translucent_dialog_style);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.money_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){

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

    @Override
    public void show() {
        super.show();
//        etMoney.setText(money);
//        etMoney.setFocusable(true);
//        etMoney.setFocusableInTouchMode(true);
//        //请求获得焦点
//        etMoney.requestFocus();
//        if(money.length()>0)etMoney.setSelection(money.length());//光标在最后
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //调用系统输入法
//                InputMethodManager inputManager = (InputMethodManager) etMoney
//                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(etMoney, 0);
//            }
//        },100);
    }
}
