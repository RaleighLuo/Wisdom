package com.gkzxhn.wisdom.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.MainAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.common.GKApplication;
import com.gkzxhn.wisdom.customview.UpdateDialog;
import com.gkzxhn.wisdom.entity.VersionEntity;
import com.gkzxhn.wisdom.presenter.MainPresenter;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.IMainView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends SuperActivity implements IMainView{

    AppBarLayout mAblAppBar; // 整个可以滑动的AppBar
    NestedScrollView mNestedScrollView; // 标题栏Title
    RecyclerView mRecyclerView; // 工具栏
    Toolbar mToolbar; // 工具栏
    //Full screen
    private CircleImageView ivPortrait;
    private TextView tvCurrentDate,tvWeek,tvCommunity,tvCommunityDetail,
            tvCarpetArea,tvHousingArea;
    private SharedPreferences preferences;
    private MainPresenter mPresenter;
    private UpdateDialog updateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControls();
        init();

    }

    /**
     * 初始化控件
     */
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

    /**
     * 初始化
     */
    private void init(){
        mPresenter=new MainPresenter(this,this);
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
        //右上角设置按钮点击事件监听
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //进入个人中心
                startActivityForResult(new Intent(MainActivity.this,PersonInforActivity.class),Constants.EXTRA_CODE);
                return true;
            }
        });
        //当日时间显示 年月日 周
        Calendar cal=Calendar.getInstance();
        tvCurrentDate.setText(Utils.getDateFromTimeInMillis(cal.getTimeInMillis()));
        String[] weeks=getResources().getStringArray(R.array.weeks);
        tvWeek.setText(weeks[cal.get(Calendar.DAY_OF_WEEK)-1]);
        //更新个人信息
        updatePersonInfor();
        mPresenter.requestVersion();
    }

    /**
     * 根据保存的sharepreference中的值，显示个人信息
     */
    private void updatePersonInfor(){
        preferences=getSharedPreferences(Constants.USER_TABLE, Context.MODE_PRIVATE);
        //小区
        tvCommunity.setText(preferences.getString(Constants.USER_RESIDENTIALAREASNAME,""));
        //小区详情
        tvCommunityDetail.setText(preferences.getString(Constants.USER_REGIONNAME,"")+preferences.getString(Constants.USER_BUILDINGNAME,"")+
                preferences.getString(Constants.USER_UNITSNAME,"")+preferences.getString(Constants.USER_ROOMNAME,""));
        //使用面积
        tvCarpetArea.setText(preferences.getFloat(Constants.USER_USEDAREA,0)+getString(R.string.square_meter));
        //房屋面积
        tvHousingArea.setText(preferences.getFloat(Constants.USER_FLOORAREA,0)+getString(R.string.square_meter));
        //下载头像
        String url= preferences.getString(Constants.USER_PORTRAIT,"");
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

    private boolean isExpanded=true;

    /**点击事件监听
     * @param view
     */
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.main_layout_tv_repair://社区报修
                Intent intent=new Intent(this,RepairRecordActivity.class);
                intent.putExtra(Constants.EXTRA_TAB,Constants.COMMUNITY_REPAIR_TAB);
                startActivity(intent);
                break;
            case R.id.home_full_screen_layout_tv_pay_record://缴费记录
                startActivity(new Intent(this,PayRecordActivity.class));
                break;
            case R.id.home_full_screen_layout_tv_repair_record://报修记录
                intent=new Intent(this,RepairRecordActivity.class);
                intent.putExtra(Constants.EXTRA_TAB,Constants.REPAIR_TAB);
                startActivity(intent);
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


    /**右上角菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 用户点击了返回按钮
     */
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
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case Constants.EXTRA_CODE://个人信息回调
                    updatePersonInfor();
                    break;
            }
        }
    }

    @Override
    public void startRefreshAnim() {

    }

    @Override
    public void stopRefreshAnim() {

    }

    @Override
    public void updateVersion(VersionEntity version) {
        //新版本
        int newVersion = version.getVersionCode();
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            int currentVersion=packageInfo.versionCode;//当前App版本
            int lastIgnoreVersion= mPresenter.getSharedPreferences().getInt(Constants.LAST_IGNORE_VERSION,0);
            boolean isIgoreVersion=lastIgnoreVersion==newVersion;//若是已忽略的版本，则不弹出升级对话框
            if(version.isForce())isIgoreVersion=false;
            if (newVersion > currentVersion&&!isIgoreVersion) {//新版本大于当前版本，则弹出更新下载到对话框
                //版本名
                String versionName =  version.getVersionName();
                // 下载地址
                String downloadUrl =  version.getDownloadUrl();
                //是否强制更新
                boolean isForceUpdate= version.isForce();
                if(updateDialog==null)updateDialog=new UpdateDialog(this);
                updateDialog.setForceUpdate(isForceUpdate);
                updateDialog.setDownloadInfor(versionName,newVersion,downloadUrl);
                updateDialog.show();//显示对话框
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        if(updateDialog!=null&&updateDialog.isShowing())updateDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(updateDialog!=null&&updateDialog.isShowing())updateDialog.measureWindow();
    }
}
