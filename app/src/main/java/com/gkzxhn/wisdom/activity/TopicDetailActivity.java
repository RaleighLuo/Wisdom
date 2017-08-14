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
import android.widget.Toast;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.adapter.OnItemLongClickListener;
import com.gkzxhn.wisdom.adapter.TopicCommentAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CheckConfirmDialog;
import com.gkzxhn.wisdom.customview.CommentDialog;
import com.gkzxhn.wisdom.customview.CommentShowDialog;
import com.gkzxhn.wisdom.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.presenter.TopicDetailPresenter;
import com.gkzxhn.wisdom.view.ITopicDetailView;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/13.
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
    private String id=null;
    private TextView tvLike;
    private ImageView ivLike;
    private CheckConfirmDialog confirmDialog;
    private ProgressDialog mProgress;
    private boolean isLike=false;
    private String mUserId=null;
    private CommentShowDialog mCommentShowDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_detial_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvLike = (TextView) findViewById(R.id.topic_detial_layout_tv_like);
        ivLike = (ImageView) findViewById(R.id.topic_detial_layout_iv_like);
        tvLoading= (DotsTextView) findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) findViewById(R.id.common_list_layout_swipeRefresh);
    }
    private void init(){
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        dismissProgress();
        id=getIntent().getStringExtra(Constants.EXTRA);
        mPresenter=new TopicDetailPresenter(this,this,id);
        mCommentDialog=new CommentDialog(this);
        mCommentDialog.setOnClickListener(onClickListener);
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
        adapter=new TopicCommentAdapter(this,mPresenter.getSharedPreferences().getString(Constants.USER_ID,""));
        adapter.setOnItemClickListener(onItemClickListener);
        adapter.setOnItemLongClickListener(onItemLongClickListener);
        mRecyclerView.setAdapter(adapter);
        mUserId=mPresenter.getSharedPreferences().getString(Constants.USER_ID,"");
        mPresenter.request();
    }
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
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.comment_dialog_layout_tv_send:
                    if(mCommentDialog.isShowing())mCommentDialog.dismiss();
                    if(mCommentDialog.getPosition()==-1) {//评论话题
                        mPresenter.publishComments(mCommentDialog.getContent());
                    }else{//回复评论

                    }
                    break;
                case R.id.comment_show_dialog_layout_tv_copy://评论－复制
                    String content=adapter.getItem(mCommentShowDialog.getPosition()).getContent();
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText(content,content));
                    showToast(R.string.has_copy_hint);
                    break;
                case R.id.comment_show_dialog_layout_tv_delete://评论－删除
                    mPresenter.deleteComment(adapter.getItemsId(mCommentShowDialog.getPosition()),mCommentShowDialog.getPosition(),-1);
                    break;
            }

        }
    };
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            switch (convertView.getId()){
                case R.id.topic_comment_layout_rb_like://评论点赞
                    mPresenter.requestCommentLike(adapter.getItemsId(position),position);
                    break;
                case R.id.topic_comment_layout_iv_comment://评论回复
                    if(!mCommentDialog.isShowing()){
                        mCommentDialog.show();
                        mCommentDialog.setPosition(position);
                        mCommentDialog.setHint(String.format("%s %s",getString(R.string.reply_colon),adapter.getItem(position).getNickname()));
                    }
                    break;
                case R.id.topic_detial_layout_tv_delete://删除话题
                    if(confirmDialog==null) {
                        confirmDialog = new CheckConfirmDialog(TopicDetailActivity.this);
                        confirmDialog.setContent(getResources().getString(R.string.delete_topic_hint));
                        confirmDialog.setYesBtnListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (confirmDialog != null && confirmDialog.isShowing())
                                    confirmDialog.dismiss();
                                mPresenter.delete();
                            }
                        });
                    }
                    if(!confirmDialog.isShowing())confirmDialog.show();
                    break;
                case R.id.topic_comment_layout_rl_root:
                    if(!mCommentDialog.isShowing()){
                        mCommentDialog.show();
                        mCommentDialog.setPosition(position);
                        mCommentDialog.setHint(String.format("%s %s",getString(R.string.reply_colon),adapter.getItem(position).getNickname()));
                    }
                    break;
            }

        }
    };

    @Override
    public void onRefresh() {
        mPresenter.requestComments(true);
    }

    @Override
    public void onLoad() {
        mPresenter.requestComments(false);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.topic_detial_layout_ll_like://话题点赞 已点赞enabel＝false
                if(isLike){//已经点赞咯,->取消点赞
                    tvLike.setEnabled(false);
                    ivLike.setColorFilter(null);
                }else{//没有点赞，－>点赞
                    tvLike.setEnabled(true);
                    ivLike.setColorFilter(getResources().getColor(R.color.orange_color));
                }
                mPresenter.requestLike();
                break;
            case R.id.topic_detial_layout_ll_comment://话题评论
                if(!mCommentDialog.isShowing()){
                    mCommentDialog.show();
                    mCommentDialog.setPosition(-1);
                    mCommentDialog.setHint(R.string.comment);
                }
                break;
        }
    }

    public void startRefreshAnim() {
        //使用handler刷新页面状态,主要解决vNoDataHint显示问题
        handler.sendEmptyMessage(Constants.START_REFRESH_UI);
    }

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

    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        if(confirmDialog!=null&&confirmDialog.isShowing())confirmDialog.dismiss();
        if(mCommentDialog!=null&&mCommentDialog.isShowing())mCommentDialog.dismiss();
        if(mCommentShowDialog!=null&&mCommentShowDialog.isShowing())mCommentShowDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mCommentDialog.measureWindow();
    }

    @Override
    public void update(TopicDetailEntity entity) {
        adapter.updateHead(entity);
        if(!entity.isLikeable()){//已经点赞
            isLike=true;
            tvLike.setEnabled(true);
            ivLike.setColorFilter(getResources().getColor(R.color.orange_color));
        }
    }

    @Override
    public void updateComment(List<TopicCommentEntity> comments) {
        adapter.updateItems(comments);
    }

    @Override
    public void loadComment(List<TopicCommentEntity> comments) {
        adapter.loadItems(comments);
    }

    @Override
    public void deleteTopicSuccess() {
        Toast.makeText(getApplicationContext(),R.string.delete_topic_success,Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void deleteCommentSuccess(int position, int subPosition) {
        adapter.removeItem(position,subPosition);
    }

    @Override
    public void showProgress() {
        if(mProgress!=null&&!mProgress.isShowing())mProgress.show();

    }

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
            if (isLike) {//已经点赞咯
                tvLike.setEnabled(true);
                ivLike.setColorFilter(getResources().getColor(R.color.orange_color));
            } else {//没有点赞
                tvLike.setEnabled(false);
                ivLike.setColorFilter(null);
            }
        }else{
            isLike=!isLike;
        }
    }

    @Override
    public void commentLikeFinished(boolean isSuccess, String commentId, int position) {
        adapter.commentLikeFinished(isSuccess,commentId,position);
    }

    @Override
    public void publishCommentSuccess(TopicCommentEntity comment) {
        adapter.addItem(comment);
    }
}
