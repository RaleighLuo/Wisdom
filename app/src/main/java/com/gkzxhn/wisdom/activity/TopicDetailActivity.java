package com.gkzxhn.wisdom.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.OnItemClickListener;
import com.gkzxhn.wisdom.adapter.TopicCommentAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.CommentDialog;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.presenter.TopicDetailPresenter;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.ITopicDetailView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;
import com.starlight.mobile.android.lib.view.FullyGridLayoutManager;
import com.starlight.mobile.android.lib.view.RecycleViewDivider;
import com.starlight.mobile.android.lib.view.dotsloading.DotsTextView;

import java.text.SimpleDateFormat;

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
    private TextView tvLike;
    private ImageView ivLike;
    private TopicDetailPresenter mPresenter;
    private ImageView ivPortrait;
    private TextView tvName,tvDate,tvContent,tvCommentCount,tvLikeCount,tvViewTime;
    private RecyclerView rvTopicImages;
    private String id=null;
    private OnlineTopicAdapter mOnlineTopicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_detial_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvLike= (TextView) findViewById(R.id.topic_detial_layout_tv_like);
        ivLike= (ImageView) findViewById(R.id.topic_detial_layout_iv_like);
        tvLoading= (DotsTextView) findViewById(R.id.common_loading_layout_tv_load);
        ivNodata=findViewById(R.id.common_no_data_layout_iv_image);
        mRecyclerView= (RecyclerView) findViewById(R.id.common_list_layout_rv_list);
        mSwipeRefresh= (CusSwipeRefreshLayout) findViewById(R.id.common_list_layout_swipeRefresh);
        ivPortrait= (ImageView) findViewById(R.id.topic_detial_layout_iv_portrait);
        tvName= (TextView) findViewById(R.id.topic_detial_layout_tv_name);
        tvContent= (TextView) findViewById(R.id.topic_detial_layout_tv_content);
        tvDate= (TextView) findViewById(R.id.topic_detial_layout_tv_date);
        rvTopicImages= (RecyclerView) findViewById(R.id.topic_detial_layout_rv_image);
        tvCommentCount= (TextView) findViewById(R.id.topic_detial_layout_tv_comment_number);
        tvLikeCount= (TextView) findViewById(R.id.topic_detial_layout_tv_like_number);
        tvViewTime= (TextView) findViewById(R.id.topic_detial_layout_tv_view_time);
    }
    private void init(){
        id=getIntent().getStringExtra(Constants.EXTRA);
        mPresenter=new TopicDetailPresenter(this,this);
        mCommentDialog=new CommentDialog(this);
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
        int size=getResources().getDimensionPixelSize(R.dimen.recycler_view_line_light_height);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, size, getResources().getColor(R.color.common_bg_color)));
        adapter=new TopicCommentAdapter(this);
        adapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setAdapter(adapter);
        rvTopicImages.setHasFixedSize(true);
        rvTopicImages.setLayoutManager(new FullyGridLayoutManager(this,2));
        rvTopicImages.setItemAnimator(new DefaultItemAnimator());
        mOnlineTopicAdapter=new OnlineTopicAdapter(this);
        rvTopicImages.setAdapter(mOnlineTopicAdapter);
        onRefresh();

    }
    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        @Override
        public void onClickListener(View convertView, int position) {
            if(!mCommentDialog.isShowing()){
                mCommentDialog.show();
                mCommentDialog.setHint(R.string.reply_colon);
            }
        }
    };

    @Override
    public void onRefresh() {
        mPresenter.request(id);
    }

    @Override
    public void onLoad() {
        mSwipeRefresh.setLoading(false);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.topic_detial_layout_ll_like://点赞 已点赞enabel＝false
                if(tvLike.isEnabled()){//已经点赞咯
                    tvLike.setEnabled(false);
                    ivLike.setColorFilter(null);
                }else{//没有点赞
                    tvLike.setEnabled(true);
                    ivLike.setColorFilter(getResources().getColor(R.color.orange_color));
                }
                break;
            case R.id.topic_detial_layout_ll_comment://评论
                if(!mCommentDialog.isShowing()){
                    mCommentDialog.show();
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
        if(mCommentDialog!=null&&mCommentDialog.isShowing())mCommentDialog.dismiss();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mCommentDialog.measureWindow();
    }

    @Override
    public void update(TopicDetailEntity entity) {
        ImageLoader.getInstance().displayImage(entity.getUser().getUserPortrait(),ivPortrait,Utils.getOptions(R.mipmap.topic_portrait));
        tvContent.setText(entity.getContent());
        tvLikeCount.setText(entity.getLikeCount()+"");
        tvCommentCount.setText(entity.getCommentCount()+"");
        mOnlineTopicAdapter.updateItems(entity.getImages());
        if(mOnlineTopicAdapter.getItemCount()>0){
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) rvTopicImages.getLayoutParams();
            params.height=RelativeLayout.LayoutParams.WRAP_CONTENT;
            rvTopicImages.setLayoutParams(params);
        }

        tvDate.setText(Utils.getFormateTime(entity.getCreatedDate(),new SimpleDateFormat("MM月dd日 HH:mm")));
        tvName.setText(entity.getUser().getNickname());
        tvViewTime.setText(getString(R.string.browse)+entity.getViewed()+getString(R.string.time));
    }
}
