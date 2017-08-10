package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.CommentDetailAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.RadioButtonPlus;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class CommentDetailActivity extends SuperActivity implements CusSwipeRefreshLayout.OnRefreshListener,
        CusSwipeRefreshLayout.OnLoadListener{
    private CommentDetailAdapter adapter;
    private ImageView ivPortrait;
    private TextView tvName,tvDate,tvContent,tvComment,tvCommentCount;
    private RadioButtonPlus rbLike;
    private RecyclerView mRecyclerView;
    private CusSwipeRefreshLayout mSwipeRefresh;
    private View ivNodata;
    private DotsTextView tvLoading;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_detail_layout);
        initControls();
        init();
    }
    private void initControls(){
        ivPortrait= (ImageView) findViewById(R.id.comment_detail_layout_iv_portrait);
        tvName= (TextView) findViewById(R.id.comment_detail_layout_tv_name);
        tvDate= (TextView) findViewById(R.id.comment_detail_layout_tv_date);
        tvContent= (TextView) findViewById(R.id.comment_detail_layout_tv_content);
        tvComment= (TextView) findViewById(R.id.comment_detail_layout_tv_comment);
        tvCommentCount= (TextView) findViewById(R.id.comment_detail_layout_tv_comment_title);
        rbLike= (RadioButtonPlus) findViewById(R.id.comment_detail_layout_rb_like);
        tvLoading= (DotsTextView) findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) findViewById(R.id.common_list_layout_swipeRefresh);
    }
    private void init(){
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        dismissProgress();
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setOnLoadListener(this);
        mSwipeRefresh.setColor(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        mSwipeRefresh.setMode(CusSwipeRefreshLayout.Mode.BOTH);
        mSwipeRefresh.setLoadNoFull(false);
        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new CommentDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);
        rbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbLike.setChecked(!rbLike.isChecked());
            }
        });
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void onLoad() {
        mSwipeRefresh.setLoading(false);
    }
    public void showProgress() {
        if(mProgress!=null&&!mProgress.isShowing())mProgress.show();

    }

    public void dismissProgress() {
        if(mProgress!=null&&mProgress.isShowing())mProgress.dismiss();
    }

    public void startRefreshAnim() {
        //使用handler刷新页面状态,主要解决vNoDataHint显示问题
        handler.sendEmptyMessage(Constants.START_REFRESH_UI);
    }

    public void stopRefreshAnim() {
        handler.sendEmptyMessage(Constants.STOP_REFRESH_UI);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==Constants.START_REFRESH_UI){
                if (adapter == null || adapter.getItemCount() == 0) {
                    if (ivNodata.isShown()) {
                        ivNodata.setVisibility(View.GONE);
                    }
                    tvLoading.setVisibility(View.VISIBLE);
                    if (!tvLoading.isPlaying()) {

                        tvLoading.showAndPlay();
                    }
                    if (mSwipeRefresh.isRefreshing()) mSwipeRefresh.setRefreshing(false);
                } else {
                    if (!mSwipeRefresh.isRefreshing()) mSwipeRefresh.setRefreshing(true);
                }
            }else if(msg.what== Constants.STOP_REFRESH_UI){
                if (tvLoading.isPlaying() || tvLoading.isShown()) {
                    tvLoading.hideAndStop();
                    tvLoading.setVisibility(View.GONE);
                }
                if (!mSwipeRefresh.isShown()) mSwipeRefresh.setVisibility(View.VISIBLE);
                if (mSwipeRefresh.isRefreshing()) mSwipeRefresh.setRefreshing(false);
                if (mSwipeRefresh.isLoading()) mSwipeRefresh.setLoading(false);
                if (adapter == null || adapter.getItemCount() == 0) {

                    if (!ivNodata.isShown()) ivNodata.setVisibility(View.VISIBLE);
                } else {
                    if (ivNodata.isShown()) ivNodata.setVisibility(View.GONE);
                }
            }
        }
    };
    @Override
    protected void onDestroy() {
//        mPresenter.onDestory();
        super.onDestroy();
    }
}
