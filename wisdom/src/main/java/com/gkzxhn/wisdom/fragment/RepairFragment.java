package com.gkzxhn.wisdom.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.RepairDetailActivity;
import com.gkzxhn.wisdom.activity.RepairProgressActivity;
import com.gkzxhn.wisdom.activity.RepairRewardActivity;
import com.gkzxhn.wisdom.adapter.NoticeAdapter;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.adapter.OnItemLongClickListener;
import com.gkzxhn.wisdom.adapter.RepairAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CheckConfirmDialog;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.RecycleViewDivider;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

/**
 * Created by Raleigh.Luo on 17/7/11.
 */

public class RepairFragment extends Fragment  implements CusSwipeRefreshLayout.OnRefreshListener,
        CusSwipeRefreshLayout.OnLoadListener {
    private RecyclerView mRecyclerView;
    private CusSwipeRefreshLayout mSwipeRefresh;
    private View ivNodata;
    private DotsTextView tvLoading;
    private Context mActivity;
    private View parentView;
    private RepairAdapter adapter;
    private int TAB;
    private CheckConfirmDialog mCheckConfirmDialog;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity=activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.common_list_layout, container,false);
        initControls();
        init();
        return parentView;
    }
    private void initControls(){
        parentView.findViewById(R.id.common_list_layout_fl_root).setBackgroundResource(android.R.color.white);
        tvLoading= (DotsTextView) parentView.findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=parentView.findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) parentView.findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) parentView.findViewById(R.id.common_list_layout_swipeRefresh);

    }
    private void init(){
        TAB=getArguments().getInt(Constants.EXTRA_TAB);
        mCheckConfirmDialog = new CheckConfirmDialog(mActivity);
        mCheckConfirmDialog.setContent(getResources().getString(R.string.delete_repair_hint));
        mCheckConfirmDialog.setYesBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setOnLoadListener(this);
        mSwipeRefresh.setColor(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        mSwipeRefresh.setMode(CusSwipeRefreshLayout.Mode.BOTH);
        mSwipeRefresh.setLoadNoFull(false);
        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        //添加分割线
        int size=getResources().getDimensionPixelSize(R.dimen.recycler_view_line_light_height);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL, size, getResources().getColor(R.color.common_line_color)));
        adapter=new RepairAdapter(mActivity,TAB);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
        onRefresh();
    }
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            switch (convertView.getId()){
//                case R.id.repair_item_layout_tv_right:
//                    if(TAB== Constants.REPAIR_PROGRESSING_TAB){//查看流程
//                        startActivity(new Intent(mActivity,RepairProgressActivity.class));
//                    }else{//删除
//                        if(!mCheckConfirmDialog.isShowing())mCheckConfirmDialog.show();
//                    }
//                    break;
                default://查看详情
                    startActivity(new Intent(mActivity,RepairDetailActivity.class));
                    break;
            }


        }
    };
    private OnItemLongClickListener onItemLongClickListener=new OnItemLongClickListener() {
        @Override
        public void onLongClickListener(View convertView, int position) {

        }
    };

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
