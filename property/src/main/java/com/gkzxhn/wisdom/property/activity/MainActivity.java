package com.gkzxhn.wisdom.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.fragment.HomeFragment;
import com.gkzxhn.wisdom.property.fragment.OrderFragment;
import com.starlight.mobile.android.lib.adapter.ViewPagerAdapter;
import com.starlight.mobile.android.lib.view.CusViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SuperActivity {
    private CusViewPager viewPager;
    private RadioGroup footRadioGroup;
    private final int FIRST_TAB = 0, SECOND_TAB = 1, THIRD_TAB = 2, FOUR_TAB = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initControls();
        init();
    }
    /**
     * 初始化控件
     */
    private void initControls(){
        viewPager = (CusViewPager) findViewById(R.id.main_layout_viewpager);
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(4);//设置缓存为4个界面
        footRadioGroup = (RadioGroup) findViewById(R.id.main_layout_radiogroup_foot);
    }

    /**
     * 初始化
     */
    private void init(){
        //若是从资格认证界面进入，则需提示用户 已成功认证
        footRadioGroup.setVisibility(View.VISIBLE);
        footRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        List<Fragment> fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,
                getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);//设置适配器
    }

    /**设置滑动界面Tab
     * @param tab
     */
    private void setCurrentItem(int tab) {
        viewPager.setCanScroll(true);
        viewPager.setCurrentItem(tab, false);
        viewPager.setCanScroll(false);
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.main_layout_radio_four:
                    setCurrentItem(FOUR_TAB);
                    break;
                case R.id.main_layout_radio_first:
                    setCurrentItem(FIRST_TAB);
                    break;
                case R.id.main_layout_radio_second:
                    setCurrentItem(SECOND_TAB);
                    break;
                case R.id.main_layout_radio_third:
                    setCurrentItem(THIRD_TAB);
                    break;
                default:
                    break;
            }

        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//点击返回键，返回到主页
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return false;
    }
}
