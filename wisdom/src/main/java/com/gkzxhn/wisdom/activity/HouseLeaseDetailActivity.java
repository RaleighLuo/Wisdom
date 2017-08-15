package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.ScrollImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/21.
 */

public class HouseLeaseDetailActivity extends SuperActivity {
    private ScrollImage mScrollImage;
    private TextView tvMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_lease_detail_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvMoney= (TextView) findViewById(R.id.house_lease_detail_layout_tv_money);

        mScrollImage = (ScrollImage) findViewById(R.id.house_lease_detail_layout_scrollImage);
    }
    private void init(){
        mScrollImage.hidePageControlView();
        List<String> defualt = new ArrayList<String>();
        defualt.add("");
        mScrollImage.setImageId(R.mipmap.lease_image);
        int tab=getIntent().getIntExtra(Constants.EXTRA_TAB,Constants.HOUSE_LEASE_TAB);
        if(tab==Constants.HOUSE_SALE_TAB){
            tvMoney.setText("80万");
            ((TextView)findViewById(R.id.house_lease_detail_layout_tv_method)).setText(R.string.all_sale);
        }

    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.house_lease_detail_layout_tv_view_phone://查看电话
                break;
            case R.id.house_lease_detail_layout_tv_chat://聊天
                break;
        }
    }
}
