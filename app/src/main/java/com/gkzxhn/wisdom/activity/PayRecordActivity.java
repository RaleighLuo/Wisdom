package com.gkzxhn.wisdom.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.PayRecordAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.RecycleViewDivider;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

/**
 * Created by Raleigh.Luo on 17/7/13.
 */

public class PayRecordActivity extends SuperActivity   implements CusSwipeRefreshLayout.OnRefreshListener,
        CusSwipeRefreshLayout.OnLoadListener{
    private RecyclerView mRecyclerView;
    private CusSwipeRefreshLayout mSwipeRefresh;
    private View ivNodata;
    private DotsTextView tvLoading;
    private PayRecordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_record_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvLoading= (DotsTextView) findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) findViewById(R.id.common_list_layout_swipeRefresh);

    }
    private void init(){
        findViewById(R.id.common_list_layout_fl_root).setBackgroundResource(android.R.color.white);
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
//        //添加分割线
//        int size=getResources().getDimensionPixelSize(R.dimen.recycler_view_line_light_height);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, size, getResources().getColor(R.color.common_bg_color)));
        adapter=new PayRecordAdapter(this);
        mRecyclerView.setAdapter(adapter);
        onRefresh();
    }
    public void onClickListenter(View view){
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

    public void startRefreshAnim() {
        //使用handler刷新页面状态,主要解决vNoDataHint显示问题
        handler.sendEmptyMessage(Constants.START_REFRESH_UI);
    }

    public void stopRefreshUI() {
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
}
