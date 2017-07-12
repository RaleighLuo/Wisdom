package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/12.
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
        }
    }
}
