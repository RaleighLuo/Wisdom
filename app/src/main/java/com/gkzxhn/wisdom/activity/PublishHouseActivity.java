package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.customview.CusTextItem;
import com.gkzxhn.wisdom.customview.HouseTypeDialog;

/**
 * Created by Raleigh.Luo on 17/7/28.
 */

public class PublishHouseActivity extends SuperActivity {
    private HouseTypeDialog mHouseTypeDialog;
    private CusTextItem ctiHouseType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_house_layout);
        initControls();
        init();
    }
    private void initControls(){
        ctiHouseType= (CusTextItem) findViewById(R.id.publish_house_layout_cti_house_type);
    }
    private void init(){
        mHouseTypeDialog=new HouseTypeDialog(this);
        mHouseTypeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHouseTypeDialog!=null&&mHouseTypeDialog.isShowing())mHouseTypeDialog.dismiss();
                ctiHouseType.getTvContent().setText(mHouseTypeDialog.getSelectedAll());
            }
        });
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
            case R.id.publish_house_layout_cti_area://区域
                break;
            case R.id.publish_house_layout_cti_rent_money://租金
                break;
            case R.id.publish_house_layout_cti_decorate://装修
                break;
        }
    }
}
