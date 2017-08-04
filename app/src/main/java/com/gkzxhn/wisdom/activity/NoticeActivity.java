package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.fragment.MessageFragment;
import com.gkzxhn.wisdom.fragment.NoticeFragment;
import com.starlight.mobile.android.lib.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/11.
 * 通知公告
 */

public class NoticeActivity extends  SuperFragmentActivity{
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int currentTab=0;
    private RadioGroup mRadioGroup;
    private boolean isScrolling=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_layout);
        initControl();
        init();
    }
    private void initControl(){
        viewPager= (ViewPager) findViewById(R.id.notice_layout_viewPager);
        mRadioGroup= (RadioGroup) findViewById(R.id.notice_layout_radiogroup);
    }
    private void init(){
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new NoticeFragment());
        fragmentList.add(new MessageFragment());
        adapter=new ViewPagerAdapter(this,getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(currentTab, true);
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        viewPager.addOnPageChangeListener(onPageChangeListener);

    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(!isScrolling) {
                isScrolling=true;
                switch (checkedId){
                    case R.id.notice_layout_rb_first:
                        viewPager.setCurrentItem(0);//滑动
                        break;
                    case R.id.notice_layout_rb_second:
                        viewPager.setCurrentItem(1);//滑动
                        break;
                }
                isScrolling=false;
            }

        }
    };
    private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int selectedTab) {
            currentTab=selectedTab;
            if(!isScrolling) {
                isScrolling = true;
                switch (selectedTab){
                    case 0:
                        if(mRadioGroup.getCheckedRadioButtonId()!=R.id.notice_layout_rb_first)
                            mRadioGroup.check(R.id.notice_layout_rb_first);

                        break;
                    case 1:
                        if(mRadioGroup.getCheckedRadioButtonId()!=R.id.notice_layout_rb_second)
                            mRadioGroup.check(R.id.notice_layout_rb_second);
                        break;
                }
                isScrolling = false;
            }
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
