package com.gkzxhn.wisdom.property.activity;

import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.property.R;

/**
 * Created by Raleigh.Luo on 17/9/13.
 */

public class OrderDetailActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.order_detail_layout_tv_assign://指派
                break;
            case R.id.order_detail_layout_tv_refuse://拒绝工单
                break;
            case R.id.order_detail_layout_tv_accept://接受工单
                break;
            case R.id.order_detail_layout_tv_call://拨打电话
                break;
        }
    }
}
