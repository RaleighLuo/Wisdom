package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.adapter.OnItemLongClickListener;
import com.gkzxhn.wisdom.adapter.TopicCommentAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CheckConfirmDialog;
import com.gkzxhn.wisdom.customview.CommentDialog;
import com.gkzxhn.wisdom.customview.CommentShowDialog;
import com.gkzxhn.wisdom.entity.TopicCommentDetailEntity;
import com.gkzxhn.wisdom.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.entity.TopicReplayEntity;
import com.gkzxhn.wisdom.presenter.TopicDetailPresenter;
import com.gkzxhn.wisdom.view.ITopicDetailView;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/13.
 * 评论详情
 */

public class TopicDetailActivity extends SuperActivity implements CusSwipeRefreshLayout.OnRefreshListener,
        CusSwipeRefreshLayout.OnLoadListener,ITopicDetailView{
    private RecyclerView mRecyclerView;
    private CusSwipeRefreshLayout mSwipeRefresh;
    private View ivNodata;
    private DotsTextView tvLoading;
    private TopicCommentAdapter adapter;
    private CommentDialog mCommentDialog;
    private TopicDetailPresenter mPresenter;
    private String mTopicId =null;
    private TextView tvLike;
    private ImageView ivLike;
    private CheckConfirmDialog confirmDialog;
    private ProgressDialog mProgress;
    private String mUserId=null;
    private CommentShowDialog mCommentShowDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_detial_layout);
        initControls();
        init();
    }

    /**
     * 初始化控件
     */
    private void initControls(){
        tvLike = (TextView) findViewById(R.id.topic_detial_layout_tv_like);
        ivLike = (ImageView) findViewById(R.id.topic_detial_layout_iv_like);
        tvLoading= (DotsTextView) findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) findViewById(R.id.common_list_layout_swipeRefresh);
    }

    /**
     * 初始化值
     */
    private void init(){
        //初始化进度条
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        dismissProgress();
        //获取上一个页面的id
        mTopicId =getIntent().getStringExtra(Constants.EXTRA);
        //初始化presenter  网络请求
        mPresenter=new TopicDetailPresenter(this,this, mTopicId);
        //初始化评论输入对话框
        mCommentDialog=new CommentDialog(this);
        mCommentDialog.setOnClickListener(onClickListener);
        //初始化加载列表动画
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setOnLoadListener(this);
        mSwipeRefresh.setColor(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        mSwipeRefresh.setMode(CusSwipeRefreshLayout.Mode.BOTH);
        mSwipeRefresh.setLoadNoFull(false);
        // improve performance if you know that changes in content
        // do not change the size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置为横向LayoutManager
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        //添加分割线
//        int size=getResources().getDimensionPixelSize(R.dimen.recycler_view_line_light_height);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, size, getResources().getColor(R.color.common_bg_color)));
        //初始化评论列表适配器
        adapter=new TopicCommentAdapter(this,mPresenter.getSharedPreferences().getString(Constants.USER_ID,""));
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnItemLongClickListener(onItemLongClickListener);
        mRecyclerView.setAdapter(adapter);//设置适配器
        mUserId=mPresenter.getSharedPreferences().getString(Constants.USER_ID,"");
        //请求话题详情
        mPresenter.request();
    }

    /**
     * 长按评论监听器
     */
    private OnItemLongClickListener onItemLongClickListener=new OnItemLongClickListener() {
        @Override
        public void onLongClickListener(View convertView, int position) {
            //评论功能 删除 回复 复制
            if(mCommentShowDialog==null){
                mCommentShowDialog=new CommentShowDialog(TopicDetailActivity.this);
                mCommentShowDialog.setOnClickListener(onClickListener);
            }
            if(!mCommentShowDialog.isShowing())mCommentShowDialog.show();
            mCommentShowDialog.setPosition(position);
            mCommentShowDialog.showDelete(adapter.getItemsUserId(position).equals(mUserId));
        }
    };
    /**
     * 评论输入对话框监听器
     */
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.comment_dialog_layout_tv_send://发布评论
                    if(mCommentDialog.isShowing())mCommentDialog.dismiss();//关闭对话框
                    if(mCommentDialog.getPosition()==-1) {//评论话题
                        mPresenter.publishComments(mCommentDialog.getContent());
                    }else{//回复评论
                        mPresenter.publishReplay(mCommentDialog.getPosition(),adapter.getItemsId(mCommentDialog.getPosition()),mCommentDialog.getContent());
                    }
                    break;
                case R.id.comment_show_dialog_layout_tv_copy://评论－复制到剪贴板
                    String content=adapter.getItem(mCommentShowDialog.getPosition()).getContent();
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText(content,content));
                    showToast(R.string.has_copy_hint);
                    break;
                case R.id.comment_show_dialog_layout_tv_delete://评论－删除
                    //请求删除评论
                    mPresenter.deleteComment(adapter.getItemsId(mCommentShowDialog.getPosition()),mCommentShowDialog.getPosition(),-1);
                    break;
            }

        }
    };
    /**
     * 评论列表点击事件
     */
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            if(adapter.getTopicInfor()!=null) {
                switch (convertView.getId()) {
                    case R.id.topic_comment_layout_rb_like://评论点赞－发请求
                        mPresenter.requestCommentLike(adapter.getItemsId(position), position);
                        break;
                    case R.id.topic_comment_layout_iv_comment://评论回复－弹出对话框
                        if (!mCommentDialog.isShowing()) {
                            mCommentDialog.show();
                            mCommentDialog.setPosition(position);//记住位置
                            //设置默认提示
                            mCommentDialog.setHint(String.format("%s %s", getString(R.string.reply_colon), adapter.getItem(position).getNickname()));
                        }
                        break;
                    case R.id.topic_detial_layout_tv_delete://删除话题
                        if (confirmDialog == null) {//提示对话框
                            confirmDialog = new CheckConfirmDialog(TopicDetailActivity.this);
                            confirmDialog.setContent(getResources().getString(R.string.delete_topic_hint));
                            confirmDialog.setYesBtnListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (confirmDialog != null && confirmDialog.isShowing())
                                        confirmDialog.dismiss();
                                    mPresenter.delete();//点击确定后，请求删除话题
                                }
                            });
                        }
                        if (!confirmDialog.isShowing()) confirmDialog.show();//显示提示对话框
                        break;
                    case R.id.topic_comment_layout_rl_root://回复评论－弹出回复对话
                        if (!mCommentDialog.isShowing()) {
                            mCommentDialog.show();
                            mCommentDialog.setPosition(position);
                            mCommentDialog.setHint(String.format("%s %s", getString(R.string.reply_colon), adapter.getItem(position).getNickname()));
                        }
                        break;
                    default://跳转到评论详情页面
                        Intent intent = new Intent(TopicDetailActivity.this, TopicCommentDetailActivity.class);
                        intent.putExtra(Constants.EXTRA, adapter.getItemsId(position));
                        intent.putExtra(Constants.EXTRAS, mTopicId);
                        startActivity(intent);
                        break;
                }
            }

        }
    };

    /**刷新
     *
     */
    @Override
    public void onRefresh() {
        mPresenter.requestComments(true);
    }

    /**
     * 加载
     */
    @Override
    public void onLoad() {
        mPresenter.requestComments(false);
    }

    /**本页layout 点击事件
     * @param view
     */
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left://返回按钮
                if(adapter.getTopicInfor()!=null) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.EXTRA, adapter.getTopicInfor().getCommentCount());//评论数
                    intent.putExtra(Constants.EXTRAS, adapter.getTopicInfor().getLikesCount());//点赞数量
                    intent.putExtra(Constants.EXTRA_TAB, adapter.getTopicInfor().isLikeable());//是否能点赞
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
            case R.id.topic_detial_layout_ll_like://话题点赞 已点赞enabel＝false
                if(adapter.getTopicInfor()!=null) {
                    if (!adapter.commentIsLikeable()) {//已经点赞咯,->取消点赞
                        tvLike.setEnabled(false);
                        ivLike.setColorFilter(null);
                    } else {//没有点赞，－>点赞
                        tvLike.setEnabled(true);
                        ivLike.setColorFilter(getResources().getColor(R.color.orange_color));
                    }
                    mPresenter.requestLike();
                }
                break;
            case R.id.topic_detial_layout_ll_comment://话题评论
                if(adapter.getTopicInfor()!=null) {
                    if (!mCommentDialog.isShowing()) {
                        mCommentDialog.show();
                        mCommentDialog.setPosition(-1);
                        mCommentDialog.setHint(R.string.comment);
                    }
                }
                break;
        }
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
     * 关闭列表加载动画
     */
    @Override
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

    /**
     * 释放资源，避免窗口溢出
     */
    @Override
    protected void onDestroy() {
        mPresenter.onDestory();//释放presenter资源
        //关闭dialog 防止窗口溢出
        if(confirmDialog!=null&&confirmDialog.isShowing())confirmDialog.dismiss();
        if(mCommentDialog!=null&&mCommentDialog.isShowing())mCommentDialog.dismiss();
        if(mCommentShowDialog!=null&&mCommentShowDialog.isShowing())mCommentShowDialog.dismiss();
        super.onDestroy();
    }

    /**横竖屏切换时，调整Ui
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mCommentDialog.measureWindow();
    }

    /**更新话题详情
     * @param entity
     */
    @Override
    public void update(TopicDetailEntity entity) {
        adapter.updateHead(entity);
        if(!entity.isLikeable()){//已经点赞
            tvLike.setEnabled(true);
            ivLike.setColorFilter(getResources().getColor(R.color.orange_color));
        }
    }

    /**更新评论列表
     * @param comments
     */
    @Override
    public void updateComment(List<TopicCommentEntity> comments) {
        adapter.updateItems(comments);
    }

    /**加载评论列表
     * @param comments
     */
    @Override
    public void loadComment(List<TopicCommentEntity> comments) {
        adapter.loadItems(comments);
    }

    /**
     * 删除话题成功
     */
    @Override
    public void deleteTopicSuccess() {
        setResult(2);
        finish();
    }

    /**删除评论成功
     * @param position
     * @param subPosition
     */
    @Override
    public void deleteCommentSuccess(int position, int subPosition) {
        adapter.removeItem(position,subPosition);
    }

    /**
     * 显示加载进度条
     */
    @Override
    public void showProgress() {
        if(mProgress!=null&&!mProgress.isShowing())mProgress.show();

    }

    /**
     * 关闭加载进度条
     */
    @Override
    public void dismissProgress() {
        if(mProgress!=null&&mProgress.isShowing())mProgress.dismiss();
    }

    /**点赞完成
     * @param isSuccess
     */
    @Override
    public void likeFinished(boolean isSuccess) {
        if(!isSuccess) {//请求失败－恢复
            if (!adapter.commentIsLikeable()) {//已经点赞咯
                tvLike.setEnabled(true);
                ivLike.setColorFilter(getResources().getColor(R.color.orange_color));
            } else {//没有点赞
                tvLike.setEnabled(false);
                ivLike.setColorFilter(null);
            }
        }else{
            adapter.updateLikeNumber();
        }
    }

    /**评论点赞完成
     * @param isSuccess
     * @param commentId
     * @param position
     */
    @Override
    public void commentLikeFinished(boolean isSuccess, String commentId, int position) {
        adapter.commentLikeFinished(isSuccess,commentId,position);
    }

    /**发布评论成功
     * @param comment
     */
    @Override
    public void publishCommentSuccess(TopicCommentEntity comment) {
        adapter.addItem(comment);
    }

    /**发布评论回复
     * @param position
     * @param comment
     */
    @Override
    public void publishReplaySuccess(int position,TopicReplayEntity comment) {
        adapter.addReplay(position,comment);
    }

    @Override
    public void onBackPressed() {
        if(adapter.getTopicInfor()!=null) {
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA, adapter.getTopicInfor().getCommentCount());//评论数
            intent.putExtra(Constants.EXTRAS, adapter.getTopicInfor().getLikesCount());//点赞数量
            intent.putExtra(Constants.EXTRA_TAB, adapter.getTopicInfor().isLikeable());//是否能点赞
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }
}
