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
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public class MoneyDialog extends Dialog {
    private Context context;
    private View.OnClickListener onClickListener;
    private EditText etMoney;
    private String money="";
    private int TAB;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public MoneyDialog(@NonNull Context context,int TAB) {
        super(context, R.style.custom_translucent_dialog_style);
        this.context = context;
        this.TAB=TAB;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.money_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){
        findViewById(R.id.money_dialog_layout_tv_confirm).setOnClickListener(onClickListener);
        etMoney = (EditText) findViewById(R.id.money_dialog_layout_et_money);
        if(TAB== Constants.HOUSE_SALE_TAB){
            ((TextView)findViewById(R.id.money_dialog_layout_tv_title)).setText(R.string.please_input_house_sale_money_infor);
            ((TextView)findViewById(R.id.money_dialog_layout_tv_money)).setText(R.string.sale_money);
            ((TextView)findViewById(R.id.money_dialog_layout_tv_money_unit)).setText(R.string.sale_money_unit);

        }
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
        money = etMoney.getText().toString().trim();
        return etMoney.getText().toString().trim()+context.getString(TAB==Constants.HOUSE_LEASE_TAB?R.string.lease_money_unit:R.string.sale_money_unit);
    }

    public String getMoney() {
        return money;
    }

    @Override
    public void show() {
        super.show();
        etMoney.setText(money);
        etMoney.setFocusable(true);
        etMoney.setFocusableInTouchMode(true);
        //请求获得焦点
        etMoney.requestFocus();
        if(money.length()>0)etMoney.setSelection(money.length());//光标在最后
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用系统输入法
                InputMethodManager inputManager = (InputMethodManager) etMoney
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etMoney, 0);
            }
        },100);
    }
}
