package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.customview.ScrollImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/21.
 */

public class HouseLeaseDetailActivity extends SuperActivity {
    private ScrollImage mScrollImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_lease_detail_layout);
        initControls();
        init();
    }
    private void initControls(){
        mScrollImage = (ScrollImage) findViewById(R.id.house_lease_detail_layout_scrollImage);
    }
    private void init(){
        mScrollImage.hidePageControlView();
        List<String> defualt = new ArrayList<String>();
        defualt.add("");
        mScrollImage.setImageId(R.mipmap.lease_image);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
        }
    }
}
