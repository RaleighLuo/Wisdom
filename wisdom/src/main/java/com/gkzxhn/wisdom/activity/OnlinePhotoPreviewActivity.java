package com.gkzxhn.wisdom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.OnlinePhotoPreviewAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.starlight.mobile.android.lib.view.CusHeadView;
import com.starlight.mobile.android.lib.view.photoview.PhotoViewAttacher;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class OnlinePhotoPreviewActivity extends Activity {

    private CusHeadView chHead;
    private ViewPager mViewPager;
    private int currentPosition=0;
    private OnlinePhotoPreviewAdapter adapter;
    private boolean isUpdate=false;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.take_photo_preview_layout);
        initControls();
        init();
    }

    private void initControls(){
        chHead=(CusHeadView)findViewById(R.id.show_image_ch_head);
        mViewPager=(ViewPager)findViewById(R.id.show_image_layout_viewpager);
    }

    private void init(){
        try {
            Intent intent=getIntent();
            if(intent!=null){
                currentPosition=intent.getIntExtra(Constants.EXTRA_POSITION,0);
                List<String> list= (List<String>) intent.getSerializableExtra(Constants.EXTRAS);
                adapter=new OnlinePhotoPreviewAdapter(this,list,onShortTouchListener);
                mViewPager.setAdapter(adapter);
                mViewPager.setCurrentItem(currentPosition);
                mViewPager.addOnPageChangeListener(onPageChangeListener);
                chHead.getTvTitle().setText(String.format("%s/%s", (currentPosition + 1), list.size()));
                chHead.getRightTv().setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition=position;
            chHead.getTvTitle().setText(String.format("%s/%s", (currentPosition + 1), adapter.getCount()));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private PhotoViewAttacher.OnShortTouchListener onShortTouchListener=new PhotoViewAttacher.OnShortTouchListener() {
        @Override
        public void back(float upX,float upY) {
            if(chHead.getMeasuredHeight()<upY)
                showOrHidePanel(chHead.isShown());
        }

        @Override
        public void doubleTab() {//双击
            showOrHidePanel(true);
        }
    };
    private void showOrHidePanel(boolean isShown){
        if(isShown){
            Animation headHideAnim= AnimationUtils.loadAnimation(this,
                    com.starlight.mobile.android.lib.R.anim.slide_out_to_top);
            Animation bottomHideAnim= AnimationUtils.loadAnimation(this,
                    com.starlight.mobile.android.lib.R.anim.slide_out_to_bottom);
            //设置动画时间
            headHideAnim.setDuration(400);
            bottomHideAnim.setDuration(400);
            chHead.startAnimation(headHideAnim);
            chHead.setVisibility(View.GONE);
        }else{
            Animation headShowAnim= AnimationUtils.loadAnimation(this,
                    com.starlight.mobile.android.lib.R.anim.slide_in_from_top);
            Animation bottomShowAnim= AnimationUtils.loadAnimation(this,
                    com.starlight.mobile.android.lib.R.anim.slide_in_from_bottom);
            //设置动画时间
            headShowAnim.setDuration(400);
            bottomShowAnim.setDuration(400);
            chHead.startAnimation(headShowAnim);
            chHead.setVisibility(View.VISIBLE);
        }


    }
    public void onClickListener(View v){
        switch (v.getId()){
            case R.id.common_head_layout_iv_left:
                goBack();
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            goBack();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void goBack(){
        if(isUpdate){
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRAS, (Serializable) adapter.getList());
            setResult(RESULT_OK, intent);
        }
        this.finish();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
