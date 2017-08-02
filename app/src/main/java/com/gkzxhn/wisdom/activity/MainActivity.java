package com.gkzxhn.wisdom.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.MainAdapter;
import com.gkzxhn.wisdom.adapter.NoticeAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.common.GKApplication;
import com.gkzxhn.wisdom.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.starlight.mobile.android.lib.util.ConvertUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    AppBarLayout mAblAppBar; // 整个可以滑动的AppBar
    NestedScrollView mNestedScrollView; // 标题栏Title
    RecyclerView mRecyclerView; // 工具栏
    Toolbar mToolbar; // 工具栏
    //Full screen
    private CircleImageView ivPortrait;
    private TextView tvCurrentDate,tvWeek,tvCommunity,tvCommunityDetail,
            tvCarpetArea,tvHousingArea;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        init();
        initDetail();
    }
    private void initControls(){
        mAblAppBar= (AppBarLayout) findViewById(R.id.main_layout_app_bar);
        mNestedScrollView= (NestedScrollView) findViewById(R.id.main_layout_nestedscrollview);
        mRecyclerView= (RecyclerView) findViewById(R.id.main_layout_recyclerView);
        mToolbar= (Toolbar) findViewById(R.id.main_layout_toolbar);
        ivPortrait= (CircleImageView) findViewById(R.id.main_layout_iv_portrait);
        tvCurrentDate= (TextView) findViewById(R.id.home_full_screen_layout_tv_date);
        tvWeek= (TextView) findViewById(R.id.home_full_screen_layout_tv_week);
        tvCommunity= (TextView) findViewById(R.id.home_full_screen_layout_tv_community_name);
        tvCommunityDetail= (TextView) findViewById(R.id.home_full_screen_layout_tv_home_number);
        tvCarpetArea= (TextView) findViewById(R.id.home_full_screen_layout_tv_carpet_area_value);
        tvHousingArea= (TextView) findViewById(R.id.home_full_screen_layout_tv_housing_area_value);
    }
    private void init(){
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        // AppBar的监听
        mAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                if(percentage>1)percentage=1;
                if(percentage<0)percentage=0;
                if(percentage==1){
                    isExpanded=false;
                } else if(percentage==0){
                    isExpanded=true;
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MainAdapter(this));
        mNestedScrollView.setFillViewport(true);//NestedScrollView子项全屏
//        mRecyclerView.setNestedScrollingEnabled(false);//解决滑动不流畅的问题
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivityForResult(new Intent(MainActivity.this,PersonInforActivity.class),Constants.EXTRA_CODE);
                return true;
            }
        });
        updatePortrait();
    }
    private void updatePortrait(){
        //下载头像
        String url=getSharedPreferences(Constants.USER_TABLE,Context.MODE_PRIVATE).getString(Constants.USER_PORTRAIT,"");
        if(url.length()>0){
            File file=GKApplication.getInstance().getImageLoadCache().get(url);
            if(file!=null&&file.exists()){
                Bitmap mBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                ivPortrait.setImageBitmap(mBitmap);
            }else {
                ImageLoader.getInstance().displayImage(url, ivPortrait, Utils.getOptions(R.mipmap.person_home));
            }
        }
    }
    private void initDetail(){
        Calendar cal=Calendar.getInstance();
        tvCurrentDate.setText(Utils.getDateFromTimeInMillis(cal.getTimeInMillis(),new SimpleDateFormat(
                "yyyy年MM月dd日")));
        String[] weeks=getResources().getStringArray(R.array.weeks);
        tvWeek.setText(weeks[cal.get(Calendar.DAY_OF_WEEK)-1]);
        preferences=getSharedPreferences(Constants.USER_TABLE, Context.MODE_PRIVATE);
        tvCommunity.setText(preferences.getString(Constants.USER_RESIDENTIALAREASNAME,""));
        tvCommunityDetail.setText(preferences.getString(Constants.USER_REGIONNAME,"")+preferences.getString(Constants.USER_BUILDINGNAME,"")+
                preferences.getString(Constants.USER_UNITSNAME,"")+preferences.getString(Constants.USER_ROOMNAME,""));
    }
    private boolean isExpanded=true;
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.main_layout_tv_repair:
                startActivity(new Intent(this,PublishRepairActivity.class));
                break;
            case R.id.home_full_screen_layout_tv_pay_record://缴费记录
                startActivity(new Intent(this,PayRecordActivity.class));
                break;
            case R.id.home_full_screen_layout_tv_repair_record://报修记录
                startActivity(new Intent(this,RepairRecordActivity.class));
                break;
            case R.id.main_layout_tv_talking://话题
                startActivity(new Intent(this,TopicActivity.class));
                break;
            case R.id.main_layout_iv_portrait://头像
                isExpanded = !isExpanded;
                mAblAppBar.setExpanded(isExpanded);
                break;
            case R.id.main_layout_ib_complaint://投诉建议
                startActivity(new Intent(this,ComplaintActivity.class));
                break;
            case R.id.main_layout_ib_message://消息
                startActivity(new Intent(this,NoticeActivity.class));
                break;
            case R.id.main_layout_ib_service://服务
                break;
            case R.id.main_layout_ib_sign://签到
                startActivity(new Intent(this,SignActivity.class));
                break;
            case R.id.main_layout_tv_pay://缴费
                startActivity(new Intent(this,PropertyPaymentActivity.class));
                break;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //回到Home主页
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.EXTRA_CODE){
            updatePortrait();
        }
    }
}
