package com.gkzxhn.wisdom.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CusTextItem;
import com.gkzxhn.wisdom.customview.DecorateDialog;
import com.gkzxhn.wisdom.customview.HouseTypeDialog;

/**
 * Created by Raleigh.Luo on 17/7/28.
 */

public class PublishHouseActivity extends SuperActivity {
    private HouseTypeDialog mHouseTypeDialog;
    private DecorateDialog mDecorateDialog,mRentWayDialog;
    private CusTextItem ctiHouseType,ctiDecorate,ctiRentWay;
    private int TAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_house_layout);
        initControls();
        init();
    }
    private void initControls(){
        ctiRentWay= (CusTextItem) findViewById(R.id.publish_house_layout_cti_method);
        ctiDecorate= (CusTextItem) findViewById(R.id.publish_house_layout_cti_decorate);
        ctiHouseType= (CusTextItem) findViewById(R.id.publish_house_layout_cti_house_type);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mHouseTypeDialog.measureWindow();
        mDecorateDialog.measureWindow();
    }

    private void init(){
        TAB=getIntent().getIntExtra(Constants.EXTRA_TAB,Constants.HOUSE_LEASE_TAB);
        mHouseTypeDialog=new HouseTypeDialog(this);
        mHouseTypeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHouseTypeDialog!=null&&mHouseTypeDialog.isShowing())mHouseTypeDialog.dismiss();
                ctiHouseType.getTvContent().setText(mHouseTypeDialog.getSelectedAll());
            }
        });
        mDecorateDialog=new DecorateDialog(this,R.array.decorate_type);
        mDecorateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDecorateDialog!=null&&mDecorateDialog.isShowing())mDecorateDialog.dismiss();
                ctiDecorate.getTvContent().setText(mDecorateDialog.getSelectedAll());
            }
        });

        if(TAB==Constants.HOUSE_LEASE_TAB) {//房屋出租发布
            mRentWayDialog = new DecorateDialog(this, R.array.rent_ways);
            mRentWayDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mRentWayDialog != null && mRentWayDialog.isShowing())
                        mRentWayDialog.dismiss();
                    ctiRentWay.getTvContent().setText(mRentWayDialog.getSelectedAll());
                }
            });
        }else{
            ctiRentWay.getTvContent().setText(R.string.all_sale);
            ctiRentWay.setOnClickListener(null);
        }
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
                if(!mHouseTypeDialog.isShowing())mHouseTypeDialog.show();
                break;
            case R.id.publish_house_layout_cti_method://方式出租
                if(TAB==Constants.HOUSE_LEASE_TAB&&mRentWayDialog!=null&&!mRentWayDialog.isShowing())mRentWayDialog.show();
                break;
            case R.id.publish_house_layout_cti_rent_money://租金
                break;
            case R.id.publish_house_layout_cti_decorate://装修
                if(!mDecorateDialog.isShowing())mDecorateDialog.show();
                break;
        }
    }
}
