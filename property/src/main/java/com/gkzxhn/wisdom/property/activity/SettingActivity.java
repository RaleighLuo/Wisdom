package com.gkzxhn.wisdom.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.property.R;

/**
 * Created by Raleigh.Luo on 17/9/11.
 */

public class SettingActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.setting_layout_btn_exit://退出账号
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.setting_layout_tv_clear_cache:
                break;
            case R.id.setting_layout_tv_update_version:
                break;
        }
    }
}
