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
import com.gkzxhn.wisdom.fragment.HouseRentalFragment;
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

    /**
     * 初始化控件
     */
    private void initControl(){
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.topic_layout_tabs);
        viewPager= (ViewPager) findViewById(R.id.topic_layout_viewPager);
    }

    /**
     * 初始化
     */
    private void init(){
        //初始化两个page的标题
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(getFragment(Constants.OWN_TOPIC_LIST_TAB));
        fragmentList.add(getFragment(Constants.TOPIC_LIST_TAB));
        List<String>   titleList    = new ArrayList<String>();
        titleList.add(getString(R.string.own));
        titleList.add(getString(R.string.community));
        adapter=new PagerTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        //设置缓存页面为2个
        viewPager.setOffscreenPageLimit(2);
        //滑动到当前页面
        viewPager.setCurrentItem(currentTab, true);
        mTabs.setViewPager(viewPager);//pagerTab绑定viewpager
        //设置页面滑动监听器
        mTabs.setOnPageChangeListener(onPageChangeListener);
    }

    /**初始化Fragment 并传入tab值
     * @param TAB
     * @return
     */
    public TopicFragment getFragment(int TAB){
        TopicFragment fragment=new TopicFragment();
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

    /**点击事件监听  布局页面配置的onclick
     * @param view
     */
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

    /**页面返回回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
