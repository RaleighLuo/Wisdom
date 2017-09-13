package com.gkzxhn.wisdom.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.property.R;

/**
 * Created by Raleigh.Luo on 17/9/13.
 */

public class PersonInforActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_infor_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.person_infor_layout_tv_portrait:
                break;
            case R.id.person_infor_layout_tv_nickname_title:
                startActivity(new Intent(this,AlterNicknameActivity.class));
                break;
        }
    }
}
