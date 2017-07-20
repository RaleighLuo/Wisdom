package com.gkzxhn.wisdom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class RepairDetailActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_detail_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.repair_detail_layout_tv_view_progress:
                startActivity(new Intent(this,RepairProgressActivity.class));
                break;
            case R.id.common_head_layout_iv_left:
                finish();
                break;
        }
    }
}
