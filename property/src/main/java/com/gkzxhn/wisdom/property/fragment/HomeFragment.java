package com.gkzxhn.wisdom.property.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.common.Constants;
import com.starlight.mobile.android.lib.adapter.PagerTabAdapter;
import com.starlight.mobile.android.lib.view.CusViewPager;
import com.starlight.mobile.android.lib.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/9/4.
 */

public class HomeFragment extends Fragment {
    private Context mActivity;
    private View parentView;
    private CusViewPager viewPager;
    private PagerSlidingTabStrip pagerTab;
    private PagerTabAdapter adapter;
    private boolean isScrolled=false;
    private int currentTab=0;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity=activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home_fragment_layout, container,false);
        initControls();
        init();
        return parentView;
    }
    private void initControls(){
        viewPager=(CusViewPager) parentView.findViewById(R.id.home_fragment_layout_viewPager);
        pagerTab=(PagerSlidingTabStrip) parentView.findViewById(R.id.home_fragment_layout_pagerSlidingTabStrip);
    }
    private void init(){
//        pagerTab.setUnderlineHeight(0);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        List<String>   titleList    = new ArrayList<String>();
        fragmentList.add(getFragment(Constants.REPAIR_UNASSIGNED_TAB));
        fragmentList.add(getFragment(Constants.REPAIR_ASSIGNED_TAB));
        titleList.add(getString(R.string.unassigned));
        titleList.add(getString(R.string.assigned));
        adapter=new PagerTabAdapter(getChildFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        pagerTab.setViewPager(viewPager);
        pagerTab.setOnPageChangeListener(onPageChangeListener);
        setCurrentItem(currentTab);
        adapter.setOnPageSelected(new PagerTabAdapter.OnPageSelected() {
            @Override
            public void OnPageSelected() {

            }
        });
    }
    private void setCurrentItem(int tab){
//        viewPager.setCanScroll(true);
        viewPager.setCurrentItem(currentTab,false);
//        viewPager.setCanScroll(false);
    }
    /**初始化Fragment 并传入tab值
     * @param TAB
     * @return
     */
    public OrderFragment getFragment(int TAB){
        OrderFragment fragment=new OrderFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.EXTRA_TAB,TAB);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int selectedTab) {
            isScrolled=true;
            currentTab=selectedTab;
        }

        @Override
        public void onPageScrolled(int selectedTab, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
}
