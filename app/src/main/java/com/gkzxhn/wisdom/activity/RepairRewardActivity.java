package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class RepairRewardActivity extends SuperActivity {
    private EditText etMoney,etTitle;
    private TextView tvMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_record_layout);
        initControls();
        init();
    }
    private void initControls(){
        etMoney= (EditText) findViewById(R.id.repair_reward_layout_et_money);
        etTitle= (EditText) findViewById(R.id.repair_reward_layout_et_title);
        tvMoney= (TextView) findViewById(R.id.repair_reward_layout_tv_money);
    }
    private void init(){

    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.repair_reward_layout_tv_submit:// 塞钱
                break;
            case R.id.common_head_layout_iv_left:
                finish();
                break;
        }
    }
}
