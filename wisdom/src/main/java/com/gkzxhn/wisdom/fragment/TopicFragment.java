package com.gkzxhn.wisdom.fragment;

import android.app.Activity;
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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.SuperFragmentActivity;
import com.gkzxhn.wisdom.activity.TopicActivity;
import com.gkzxhn.wisdom.activity.TopicDetailActivity;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.adapter.TopicAdapter;
import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.entity.TopicEntity;
import com.gkzxhn.wisdom.model.impl.TopicDetailModel;
import com.gkzxhn.wisdom.presenter.CommonListPresenter;
import com.gkzxhn.wisdom.view.ICommonListView;
import com.starlight.mobile.android.lib.util.ConvertUtil;
import com.starlight.mobile.android.lib.util.JSONUtil;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.RecycleViewDivider;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/13.
 */

public class TopicFragment extends Fragment implements CusSwipeRefreshLayout.OnRefreshListener,
        CusSwipeRefreshLayout.OnLoadListener,ICommonListView<TopicEntity>{
    private RecyclerView mRecyclerView;
    private CusSwipeRefreshLayout mSwipeRefresh;
    private View ivNodata;
    private DotsTextView tvLoading;
    private Context mActivity;
    private View parentView;
    private TopicAdapter adapter;
    private CommonListPresenter<TopicEntity> mPresenter;
    private TopicDetailModel mModel;
    private int currentPosition=-1;
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

    /**
     * 初始化控件
     */
    private void initControls(){
        parentView.findViewById(R.id.common_list_layout_fl_root).setBackgroundResource(R.color.common_bg_color);
        tvLoading= (DotsTextView) parentView.findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=parentView.findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) parentView.findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) parentView.findViewById(R.id.common_list_layout_swipeRefresh);

    }

    /**
     * 初始化
     */
    private void init(){
        mModel=new TopicDetailModel();
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
        int size=getResources().getDimensionPixelSize(R.dimen.topic_line_height);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.HORIZONTAL, size, getResources().getColor(R.color.common_bg_color)));
        adapter=new TopicAdapter(mActivity);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
        int Tab=getArguments().getInt(Constants.EXTRA_TAB);
        mPresenter=new CommonListPresenter<>(mActivity,this, Tab);
        onRefresh();
    }
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            switch (convertView.getId()){
                case R.id.topic_item_layout_rb_like://点赞
                case R.id.topic_item_layout_fl_like:
                    requestLike(position);
                    break;
                default://跳转话题详情
                    currentPosition=position;
                    Intent intent=new Intent(mActivity, TopicDetailActivity.class);
                    intent.putExtra(Constants.EXTRA,adapter.getItemsId(position));
                    startActivityForResult(intent,Constants.EXTRA_CODE);
                    break;
            }

        }
    };

    /**点赞
     * @param position
     */
    private void requestLike(final int position){
        mModel.setTopicId(adapter.getItemsId(position));
        mModel.requestLike(new VolleyUtils.OnFinishedListener<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                int code = ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(response, "code"));
                if (code == 200) {
                    adapter.like(position,true);
                } else {
                    adapter.like(position,false);
                    showToast(JSONUtil.getJSONObjectStringValue(response, "message"));
                }
            }

            @Override
            public void onFailed(VolleyError error) {
                adapter.like(position,false);
                showToast(R.string.unexpected_errors);
            }
        });
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestory();
        mModel.stopAllReuqst();
        super.onDestroy();
    }

    /**
     * 刷新数据
     */
    @Override
    public void onRefresh() {
        mPresenter.request(true);
    }

    /**
     * 加载数据
     */
    @Override
    public void onLoad() {
        mPresenter.request(false);
    }

    /**
     * 开始列表加载动画
     */
    @Override
    public void startRefreshAnim() {
//使用handler刷新页面状态,主要解决vNoDataHint显示问题
        handler.sendEmptyMessage(Constants.START_REFRESH_UI);
    }

    /**
     * 停止列表加载动画
     */
    @Override
    public void stopRefreshAnim() {
//使用handler刷新页面状态,主要解决vNoDataHint显示问题
        handler.sendEmptyMessage(Constants.STOP_REFRESH_UI);
    }

    @Override
    public void showToast(int testResId) {
        ((SuperFragmentActivity)mActivity).showToast(testResId);
    }

    @Override
    public void showToast(String showText) {
        ((SuperFragmentActivity)mActivity).showToast(showText);
    }

    /**更新数据
     * @param datas
     */
    @Override
    public void updateItems(List<TopicEntity> datas) {
        adapter.upateItems(datas);

    }

    /**加载数据
     * @param datas
     */
    @Override
    public void loadItems(List<TopicEntity> datas) {
        adapter.loadItems(datas);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.EXTRA_CODE){
            if(resultCode== Activity.RESULT_OK){
                adapter.updateItem(currentPosition,data.getIntExtra(Constants.EXTRA,0),data.getIntExtra(Constants.EXTRAS,0),
                        data.getBooleanExtra(Constants.EXTRA_TAB,false));
            }else if(resultCode==2){//删除话题数据
                adapter.removeItem(currentPosition);
                showToast(R.string.delete_topic_success);
            }
        }
    }
}
