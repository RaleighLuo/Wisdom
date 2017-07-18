package com.gkzxhn.wisdom.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.customview.PropertyPaymentDialog;

/**
 * Created by Raleigh.Luo on 17/7/18.
 * 支付物业费
 */

public class PropertyPaymentActivity extends SuperActivity{
    private PropertyPaymentDialog mPropertyPaymentDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_payment_layout);
        initControls();
        init();
    }
    private void initControls(){

    }
    private void init(){
        mPropertyPaymentDialog=new PropertyPaymentDialog(this);
        mPropertyPaymentDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.property_payment_dialog_layout_tv_alipay://支付宝支付
                        break;
                    case R.id.property_payment_dialog_layout_tv_weixin://微信支付
                        break;
                }
            }
        });
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:// 返回
                finish();
                break;
            case R.id.property_payment_layout_btn_bottom://支付
                if(mPropertyPaymentDialog!=null&&!mPropertyPaymentDialog.isShowing())mPropertyPaymentDialog.show();
                break;
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mPropertyPaymentDialog!=null&&mPropertyPaymentDialog.isShowing())mPropertyPaymentDialog.measureWindow();

    }

    @Override
    protected void onDestroy() {
        if(mPropertyPaymentDialog!=null&&mPropertyPaymentDialog.isShowing())mPropertyPaymentDialog.dismiss();
        super.onDestroy();
    }
}
