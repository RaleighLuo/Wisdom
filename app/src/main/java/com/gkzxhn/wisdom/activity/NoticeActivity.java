package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.fragment.MessageFragment;
import com.gkzxhn.wisdom.fragment.NoticeFragment;
import com.starlight.mobile.android.lib.adapter.PagerTabAdapter;
import com.starlight.mobile.android.lib.adapter.ViewPagerAdapter;
import com.starlight.mobile.android.lib.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/11.
 * 通知公告
 */

public class NoticeActivity extends  SuperFragmentActivity{
    private ViewPager viewPager;
    private PagerTabAdapter adapter;
    private int currentTab=0;
    private PagerSlidingTabStrip mTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_layout);
        initControl();
        init();
    }
    private void initControl(){
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.notice_layout_tabs);
        viewPager= (ViewPager) findViewById(R.id.notice_layout_viewPager);
    }
    private void init(){
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new NoticeFragment());
        fragmentList.add(new MessageFragment());
        List<String>   titleList    = new ArrayList<String>();
        titleList.add(getString(R.string.notice));
        titleList.add(getString(R.string.message));
        adapter=new PagerTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(currentTab, true);
        mTabs.setViewPager(viewPager);
        mTabs.setOnPageChangeListener(onPageChangeListener);

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
        }
    }
}
