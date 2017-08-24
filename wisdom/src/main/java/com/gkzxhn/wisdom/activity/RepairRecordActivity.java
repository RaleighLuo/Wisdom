package com.gkzxhn.wisdom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.fragment.RepairRecordFragment;
import com.starlight.mobile.android.lib.adapter.PagerTabAdapter;
import com.starlight.mobile.android.lib.view.CusHeadView;
import com.starlight.mobile.android.lib.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/11.
 * 我的报修记录
 */

public class RepairRecordActivity extends SuperFragmentActivity {
    private ViewPager viewPager;
    private int currentTab=0;
    private PagerTabAdapter adapter;
    private PagerSlidingTabStrip mTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_record_layout);
        initControl();
        init();
    }
    private void initControl(){
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.repair_record_layout_tabs);
        viewPager= (ViewPager) findViewById(R.id.repair_record_layout_viewPager);
    }
    private void init(){
        CusHeadView chHead= (CusHeadView) findViewById(R.id.repair_record_layout_ch_head);
        int tab=getIntent().getIntExtra(Constants.EXTRA_TAB,Constants.REPAIR_TAB);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        if(tab==Constants.REPAIR_TAB) {
            chHead.getRightBtn().setVisibility(View.GONE);//隐藏发布按钮
            chHead.getTvTitle().setText(R.string.repair_record);
            fragmentList.add(getFragment(Constants.REPAIR_PROGRESSING_TAB));
            fragmentList.add(getFragment(Constants.REPAIR_FINISHED_TAB));
        }else {
            chHead.getTvTitle().setText(R.string.community_repair);
            fragmentList.add(getFragment(Constants.COMMUNITY_REPAIR_PROGRESSING_TAB));
            fragmentList.add(getFragment(Constants.COMMUNITY_REPAIR_FINISHED_TAB));
        }
        List<String> titleList = new ArrayList<String>();
        titleList.add(getString(R.string.progressing));
        titleList.add(getString(R.string.finished));
        adapter=new PagerTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(currentTab, true);
        mTabs.setViewPager(viewPager);
        mTabs.setOnPageChangeListener(onPageChangeListener);
    }
    public RepairRecordFragment getFragment(int TAB){
        RepairRecordFragment fragment=new RepairRecordFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.EXTRA_TAB,TAB);
        fragment.setArguments(bundle);
        return fragment;
    }


    private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int selectedTab) {
            currentTab=selectedTab;
        }

        @Override
        public void onPageScrolled(int selectedTab, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.common_head_layout_iv_right://发布
                startActivityForResult(new Intent(this,PublishRepairActivity.class), Constants.EXTRA_CODE);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.EXTRA_CODE&&resultCode==RESULT_OK){
            showToast(R.string.has_publish_repair);
            if(adapter.getItem(0)!=null) {
                ((RepairRecordFragment) adapter.getItem(0)).onRefresh();//只更新第一个页面 正在进行
            }
        }
    }
}
