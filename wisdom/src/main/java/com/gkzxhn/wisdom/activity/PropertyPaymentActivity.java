package com.gkzxhn.wisdom.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.customview.PropertyPaymentDialog;

import static com.gkzxhn.wisdom.R.id.property_payment_layout_v_community;

/**
 * Created by Raleigh.Luo on 17/7/18.
 * 支付物业费
 */

public class PropertyPaymentActivity extends SuperActivity{
    private PropertyPaymentDialog mPropertyPaymentDialog;
    private Button btnPay;
    private TextView tvPleasePay;
    private RadioGroup mRadioGroup;
    private int mLastCheckId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.property_payment_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvPleasePay= (TextView) findViewById(R.id.property_payment_layout_tv_please_pay);
        btnPay= (Button) findViewById(R.id.property_payment_layout_btn_bottom);
        mRadioGroup= (RadioGroup) findViewById(R.id.property_payment_layout_rg_month);
    }
    private void init(){
        mRadioGroup.check(R.id.property_payment_layout_rb_first);
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

                tvPleasePay.setText(R.string.has_pay);
                btnPay.setVisibility(View.INVISIBLE);
                findViewById(R.id.property_payment_layout_tv_pay_success).setVisibility(View.VISIBLE);
                if(mLastCheckId!=R.id.property_payment_layout_rb_first)findViewById(R.id.property_payment_layout_rb_first).setEnabled(false);
                if(mLastCheckId!=R.id.property_payment_layout_rb_second)findViewById(R.id.property_payment_layout_rb_second).setEnabled(false);
                if(mLastCheckId!=R.id.property_payment_layout_rb_third)findViewById(R.id.property_payment_layout_rb_third).setEnabled(false);

            }
        });
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:// 返回
                finish();
                break;
            case R.id.property_payment_layout_btn_bottom://支付
                mLastCheckId=mRadioGroup.getCheckedRadioButtonId();
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
