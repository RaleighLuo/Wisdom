package com.gkzxhn.wisdom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.fragment.NoticeFragment;
import com.gkzxhn.wisdom.fragment.TopicFragment;
import com.starlight.mobile.android.lib.adapter.PagerTabAdapter;
import com.starlight.mobile.android.lib.adapter.ViewPagerAdapter;
import com.starlight.mobile.android.lib.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/13.
 */

public class TopicActivity extends SuperFragmentActivity{
    private ViewPager viewPager;
    private int currentTab=0;
    private PagerTabAdapter adapter;
    private PagerSlidingTabStrip mTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_layout);
        initControl();
        init();
    }
    private void initControl(){
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.topic_layout_tabs);
        viewPager= (ViewPager) findViewById(R.id.topic_layout_viewPager);
    }
    private void init(){
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new TopicFragment());
        fragmentList.add(new TopicFragment());
        List<String>   titleList    = new ArrayList<String>();
        titleList.add(getString(R.string.own));
        titleList.add(getString(R.string.community));
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
            case R.id.common_head_layout_iv_right://发布话题
                startActivityForResult(new Intent(this,PublishTopicActivity.class), Constants.EXTRA_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.EXTRA_CODE&&resultCode==RESULT_OK){
            showToast(R.string.publish_success);
            onRefresh();
        }
    }

    /**
     * 每个页面都刷新数据
     */
    public void onRefresh(){
        ((TopicFragment)adapter.getItem(0)).onRefresh();
        ((TopicFragment)adapter.getItem(1)).onRefresh();
    }
}
