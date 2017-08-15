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
import com.starlight.mobile.android.lib.view.CusHeadView;

/**
 * Created by Raleigh.Luo on 17/7/18.
 */

public class PropertyPaymentDialog extends Dialog {
    private Context context;
    private TextView tvMoney,tvAlipay,tvWeixin;
    private float money=800;
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public PropertyPaymentDialog(Context context) {
        super(context, R.style.custom_dialog_style);
        this.context=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.property_payment_dialog_layout, null));
        init();
        measureWindow();
    }
    private void init(){
        tvMoney= (TextView) findViewById(R.id.property_payment_dialog_layout_tv_money);
        tvAlipay= (TextView) findViewById(R.id.property_payment_dialog_layout_tv_alipay);
        tvWeixin= (TextView) findViewById(R.id.property_payment_dialog_layout_tv_weixin);
        tvMoney.setText(String.format("%s%.2f",context.getString(R.string.money_unit),money));
         findViewById(R.id.property_payment_dialog_layout_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onClickListener!=null)onClickListener.onClick(v);
            }
        });
        tvAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(onClickListener!=null)onClickListener.onClick(v);
            }
        });
    }

    public void setMoney(float money){
        this.money=money;
        if(tvMoney!=null)tvMoney.setText(String.format("%s%.2f",context.getString(R.string.money_unit),money));
    }
    public void measureWindow(){
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();

        Display d = m.getDefaultDisplay();
        params.width = d.getWidth();
        //	        params.height=d.getHeight();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(params);
    }
}
