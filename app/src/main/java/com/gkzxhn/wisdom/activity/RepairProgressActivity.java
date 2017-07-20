package com.gkzxhn.wisdom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/18.
 * 报修进度
 */

public class RepairProgressActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_progress_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.repair_progress_layout_iv_back:
                finish();
                break;
            case R.id.repair_progress_layout_iv_award:
                startActivity(new Intent(this,RepairRewardActivity.class));
                break;
        }
    }
}
