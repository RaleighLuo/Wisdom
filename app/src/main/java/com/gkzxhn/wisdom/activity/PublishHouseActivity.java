package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/28.
 */

public class PublishHouseActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_house_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.publish_house_layout_cti_floor://楼层
                break;
            case R.id.publish_house_layout_cti_house_area://面积
                break;
            case R.id.publish_house_layout_cti_house_type://户型
                break;
            case R.id.publish_house_layout_cti_area://区域
                break;
            case R.id.publish_house_layout_cti_rent_money://租金
                break;
            case R.id.publish_house_layout_cti_decorate://装修
                break;
        }
    }
}
