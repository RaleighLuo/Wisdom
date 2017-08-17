package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Configuration;
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
import com.gkzxhn.wisdom.adapter.TopicCommentDetailAdapter;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CommentDialog;
import com.gkzxhn.wisdom.customview.CommentShowDialog;
import com.gkzxhn.wisdom.entity.TopicCommentDetailEntity;
import com.gkzxhn.wisdom.entity.TopicReplayEntity;
import com.gkzxhn.wisdom.presenter.TopicCommentDetailPresenter;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.ITopicCommentDetailView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.RadioButtonPlus;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/10.
 * 评论详情
 */

public class TopicCommentDetailActivity extends SuperActivity implements CusSwipeRefreshLayout.OnRefreshListener,
        CusSwipeRefreshLayout.OnLoadListener,ITopicCommentDetailView {
    private TopicCommentDetailAdapter adapter;
    private ImageView ivPortrait;
    private TextView tvName,tvDate,tvContent,tvComment, tvReplayCount;
    private RadioButtonPlus rbLike;
    private RecyclerView mRecyclerView;
    private CusSwipeRefreshLayout mSwipeRefresh;
    private View ivNodata;
    private DotsTextView tvLoading;
    private ProgressDialog mProgress;
    private CommentDialog mCommentDialog;
    private TopicCommentDetailPresenter mPresenter;
    private CommentShowDialog mCommentShowDialog;
    private String mUserId=null;
    private TopicCommentDetailEntity mCommentEnity;
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
        tvReplayCount = (TextView) findViewById(R.id.comment_detail_layout_tv_comment_title);
        rbLike= (RadioButtonPlus) findViewById(R.id.comment_detail_layout_rb_like);
        tvLoading= (DotsTextView) findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) findViewById(R.id.common_list_layout_swipeRefresh);
    }
    private void init(){
        mPresenter=new TopicCommentDetailPresenter(this,this,getIntent().getStringExtra(Constants.EXTRA));
        mUserId=mPresenter.getSharedPreferences().getString(Constants.USER_ID,"");
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
        adapter=new TopicCommentDetailAdapter(this);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
        rbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbLike.setChecked(!rbLike.isChecked());
            }
        });
        mCommentDialog=new CommentDialog(this);
        mPresenter.request();
    }

    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            switch (convertView.getId()){
                case R.id.comment_detail_item_layout_rl_root:
                    if(mCommentShowDialog==null){
                        mCommentShowDialog=new CommentShowDialog(TopicCommentDetailActivity.this);
                        mCommentShowDialog.setOnClickListener(onClickListener);
                    }
                    if(!mCommentShowDialog.isShowing())mCommentShowDialog.show();
                    mCommentShowDialog.setPosition(position);
                    mCommentShowDialog.showDelete(adapter.getItemsUserId(position).equals(mUserId));
                    break;
            }

        }
    };

    /**
     * 评论输入对话框监听器
     */
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(checkReady()) {
                switch (v.getId()) {
                    case R.id.comment_dialog_layout_tv_send://回复评论
                        if(mCommentDialog.isShowing())mCommentDialog.dismiss();//关闭对话框
                        mPresenter.publishReplay(mCommentEnity.getTopicId(),mCommentDialog.getContent());
                        break;
                    case R.id.comment_show_dialog_layout_tv_copy://评论－复制到剪贴板
                        String content=adapter.getItem(mCommentShowDialog.getPosition()).getContent();
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText(content,content));
                        showToast(R.string.has_copy_hint);
                        break;
                    case R.id.comment_show_dialog_layout_tv_delete://回复－删除
                        //请求删除回复
                        mPresenter.delete(mCommentShowDialog.getPosition(), mCommentEnity.getTopicId());
                        break;
                }
            }
        }
    };
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.comment_detail_layout_tv_comment://回复评论
                if(checkReady()&&!mCommentDialog.isShowing()){
                    mCommentDialog.show();
                    mCommentDialog.setPosition(-1);
                    mCommentDialog.setHint(String.format("%s %s",getString(R.string.reply_colon),mCommentEnity.getNickname()));
                }
                break;
            case R.id.comment_detail_layout_rb_like://评论点赞
                if(checkReady()) {
                    rbLike.setChecked(mCommentEnity.isLikeable());
                    mPresenter.like(mCommentEnity.getTopicId());
                }
                break;
        }
    }

    /**
     * 检查条件是否满足
     */
    private boolean checkReady(){
        return mCommentEnity!=null;
    }

    @Override
    public void onRefresh() {
        if(checkReady()) {
            mPresenter.requestReplayList(true, mCommentEnity.getTopicId());
        }else {
            stopRefreshAnim();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mCommentDialog.measureWindow();
    }

    @Override
    public void onLoad() {
        if(checkReady()) {
            mPresenter.requestReplayList(false, mCommentEnity.getTopicId());
        }else{
            stopRefreshAnim();
        }
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
        mPresenter.onDestory();
        if(mCommentDialog!=null&&mCommentDialog.isShowing())mCommentDialog.dismiss();
        if(mCommentShowDialog!=null&&mCommentShowDialog.isShowing())mCommentShowDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void updateItems(List<TopicReplayEntity> mDatas) {
        adapter.updateItems(mDatas);
    }

    @Override
    public void loadItems(List<TopicReplayEntity> mDatas) {
        adapter.loadItems(mDatas);
    }

    @Override
    public void likeFinish(boolean isSuccess) {
        if(isSuccess) {
            mCommentEnity.setLikeable(!mCommentEnity.isLikeable());
            mCommentEnity.setLikesCount(mCommentEnity.isLikeable()?mCommentEnity.getLikesCount()-1:mCommentEnity.getLikesCount()+1);
            rbLike.setText(mCommentEnity.getCommentCount());
        }

        rbLike.setChecked(!mCommentEnity.isLikeable());

    }

    /**发布评论成功 添加到头部
     * @param entity
     */
    @Override
    public void commentSuccess(TopicReplayEntity entity) {
        adapter.addItem(entity);
    }

    /**更新评论详情
     * @param entity
     */
    @Override
    public void update(TopicCommentDetailEntity entity) {
        if(entity!=null) {
            this.mCommentEnity = entity;
            ImageLoader.getInstance().displayImage(entity.getPortrait(), ivPortrait, Utils.getOptions(R.mipmap.person_portrait));
            tvName.setText(entity.getNickname());
            tvDate.setText(Utils.getFormateTime(entity.getDate(), new SimpleDateFormat("MM月dd日 HH:mm")));
            tvContent.setText(entity.getContent());
            tvComment.setText(String.valueOf(entity.getCommentCount()));
            rbLike.setChecked(!entity.isLikeable());
            rbLike.setText(entity.getLikesCount());
            tvReplayCount.setText(String.format("%s(%s%s)", getString(R.string.replay_comment), entity.getReplayCount(),
                    getString(R.string.strip)));
        }
    }

    @Override
    public void deleteSuccess(int position) {
        adapter.removeItem(position);
    }
}