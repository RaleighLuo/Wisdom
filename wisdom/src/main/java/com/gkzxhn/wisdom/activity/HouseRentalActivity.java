package com.gkzxhn.wisdom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioGroup;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.fragment.HouseRentalFragment;
import com.starlight.mobile.android.lib.adapter.ViewPagerAdapter;
import com.starlight.mobile.android.lib.view.CusViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/21.
 * 房屋租售
 */

public class HouseRentalActivity  extends SuperFragmentActivity{
    private CusViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int currentTab=0;
    private RadioGroup mRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_rental_layout);
        initControl();
        init();
    }
    private void initControl(){
        viewPager= (CusViewPager) findViewById(R.id.house_rental_layout_viewPager);
        mRadioGroup= (RadioGroup) findViewById(R.id.house_rental_layout_radiogroup);
    }
    private void init(){
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(getFragment(Constants.HOUSE_LEASE_TAB));
        fragmentList.add(getFragment(Constants.HOUSE_SALE_TAB));
        adapter=new ViewPagerAdapter(this,getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        setCurrentItem(currentTab);
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }
    public HouseRentalFragment getFragment(int TAB){
        HouseRentalFragment fragment=new HouseRentalFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.EXTRA_TAB,TAB);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.house_rental_layout_rb_first:
                        setCurrentItem(0);
                        break;
                    case R.id.house_rental_layout_rb_second:
                        setCurrentItem(1);
                        break;
                }
            }
    };

    /*＊滑动界面
     * @param tab
     */
    private void setCurrentItem(int tab){
        currentTab=tab;
        viewPager.setCanScroll(true);
        viewPager.setCurrentItem(tab,false);//滑动
        viewPager.setCanScroll(false);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.common_head_layout_tv_right:
                Intent intent=new Intent(this,PublishHouseActivity.class);
                intent.putExtra(Constants.EXTRA_TAB,currentTab==0?Constants.HOUSE_LEASE_TAB:Constants.HOUSE_SALE_TAB);
                startActivity(intent);
                break;
        }
    }
}
