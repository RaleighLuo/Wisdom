package com.gkzxhn.wisdom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.OnlinePhotoPreviewAdapter;
import com.gkzxhn.wisdom.adapter.RepairDetailAdapter;
import com.gkzxhn.wisdom.customview.PageControlView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class RepairDetailActivity extends SuperActivity {
    private TextView tvTitle,tvDate,tvContent;
    private ViewPager mViewPager;
    private PageControlView mPageControlView;
    RepairDetailAdapter adapter;
    private int currentPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_detail_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvTitle= (TextView) findViewById(R.id.repair_detail_layout_tv_title);
        tvDate= (TextView) findViewById(R.id.repair_detail_layout_tv_date);
        tvContent= (TextView) findViewById(R.id.repair_detail_layout_tv_content);
        mViewPager= (ViewPager) findViewById(R.id.repair_detail_layout_view_page);
        mPageControlView= (PageControlView) findViewById(R.id.repair_detail_layout_cv_page);
    }
    private void init(){
        mPageControlView.setCount(4);
        mPageControlView.generatePageControl(currentPosition);//当前页面Index
        List<String> images=new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502446617187&di=4822472e0dd7e2b029cd666f5d68ab97&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F39%2F78%2F92N58PICQTZ_1024.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502446617187&di=4e0f79c9f26c7390ec76763bd62a35c6&imgtype=0&src=http%3A%2F%2Fimg.redocn.com%2Fsheji%2F20160724%2Fxiaoqushuijingxiaoguotu_6734451.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502446617187&di=a748b8abb61bdd34cb657583209e3e15&imgtype=0&src=http%3A%2F%2Fwww.taopic.com%2Fuploads%2Fallimg%2F110901%2F1667-110Z110045293.jpg");
        adapter=new RepairDetailAdapter(this,images);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.repair_detail_layout_btn_view_progress:
                startActivity(new Intent(this,RepairProgressActivity.class));
                break;
            case R.id.common_head_layout_iv_left:
                finish();
                break;
        }
    }
    private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition=position;
            mPageControlView.generatePageControl(currentPosition);//当前页面Index
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
