package com.gkzxhn.wisdom.property.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.common.Constants;

/**
 * Created by Raleigh.Luo on 17/9/13.
 */

public class OrderDetailActivity extends SuperActivity {
    String mStatus;
    private ProgressBar mProgressBar;
    private TextView tvStatus,tvRepairmanTitle,tvRepairman;
    private String[] mStatusArray;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_layout);
        initControls();
        init();
        initStatus();
    }
    private void initControls(){
        tvRepairmanTitle= (TextView) findViewById(R.id.order_detail_layout_layout_tv_repairman_title);
        tvRepairman= (TextView) findViewById(R.id.order_detail_layout_layout_tv_repairman);
        tvStatus= (TextView) findViewById(R.id.order_detail_layout_layout_tv_status);
        mProgressBar= (ProgressBar) findViewById(R.id.order_detail_layout_layout_progress);
    }
    private void init(){
        preferences=getSharedPreferences(Constants.USER_TABLE, Context.MODE_PRIVATE);
        mStatus=getIntent().getStringExtra(Constants.EXTRA);
        mStatusArray=getResources().getStringArray(R.array.order_status);
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
    private void initStatus(){
        switch (mStatus){
            case Constants.ORDER_STATUS_UNASSIGN:
                tvStatus.setText(mStatusArray[0]);
                mProgressBar.setProgress(0);
                tvRepairmanTitle.setVisibility(View.GONE);
                tvRepairman.setVisibility(View.GONE);
                if(preferences.getInt(Constants.USER_ROLE,Constants.REPAIRMANE_ROLE)==Constants.MANAGER_ROLE) {
                    //物业经理角色
                    findViewById(R.id.order_detail_layout_tv_assign).setVisibility(View.VISIBLE);
                }
                break;
            case Constants.ORDER_STATUS_UNACCEPT:
                tvStatus.setText(mStatusArray[1]);
                mProgressBar.setProgress(1);
                if(preferences.getInt(Constants.USER_ROLE,Constants.REPAIRMANE_ROLE)==Constants.REPAIRMANE_ROLE) {
                    //维修工角色
                    findViewById(R.id.order_detail_layout_tv_refuse).setVisibility(View.VISIBLE);
                    findViewById(R.id.order_detail_layout_tv_accept).setVisibility(View.VISIBLE);
                }
                break;
            case Constants.ORDER_STATUS_ACCEPTED:
                tvStatus.setText(mStatusArray[2]);
                mProgressBar.setProgress(2);
                if(preferences.getInt(Constants.USER_ROLE,Constants.REPAIRMANE_ROLE)==Constants.REPAIRMANE_ROLE) {
                    //维修工角色
                    findViewById(R.id.order_detail_layout_tv_inprogress).setVisibility(View.VISIBLE);
                }
                break;
            case Constants.ORDER_STATUS_REPAIRING:
                tvStatus.setText(mStatusArray[3]);
                mProgressBar.setProgress(3);
                if(preferences.getInt(Constants.USER_ROLE,Constants.REPAIRMANE_ROLE)==Constants.REPAIRMANE_ROLE) {
                    //维修工角色
                    TextView tvOperate= (TextView) findViewById(R.id.order_detail_layout_tv_inprogress);
                    tvOperate.setVisibility(View.VISIBLE);
                    tvOperate.setText(R.string.finish_repair);
                }
                break;
            case Constants.ORDER_STATUS_REPAIRED:
                tvStatus.setText(mStatusArray[4]);
                mProgressBar.setProgress(4);
                break;
            case Constants.ORDER_STATUS_REFUSED:
                tvStatus.setText(mStatusArray[5]);
                mProgressBar.setMax(2);
                mProgressBar.setProgress(2);
                break;

        }
    }
}
