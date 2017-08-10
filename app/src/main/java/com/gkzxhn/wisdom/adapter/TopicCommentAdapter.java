package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.OnlineTopicAdapter;
import com.gkzxhn.wisdom.entity.TopicCommentEntity;
import com.gkzxhn.wisdom.entity.TopicDetailEntity;
import com.gkzxhn.wisdom.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.starlight.mobile.android.lib.view.FullyGridLayoutManager;
import com.starlight.mobile.android.lib.view.RadioButtonPlus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/13.
 */

public class TopicCommentAdapter extends RecyclerView.Adapter<TopicCommentAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private final int HEADER_TAG=0,DEFUALT_TAG=1;
    private List<TopicCommentEntity> mDatas=new ArrayList<>();
    private TopicDetailEntity mTopicInfor;
    private String mUserId;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void updateHead(TopicDetailEntity entity){
        this.mTopicInfor=entity;
        notifyItemChanged(0);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TopicCommentAdapter(Context context,String userId) {
        this.context = context;
        mUserId=userId;
    }
    public void addItem(TopicCommentEntity entity){
        mDatas.add(0,entity);
        notifyDataSetChanged();
    }

    /**
     *
     * @param commentId
     * @param position  mData中的position
     */
    public void commentLikeFinished(boolean isSuccess, String commentId, int position){
        int mPositon=position;
        if(!commentId.equals(getItemsId(position))) {
            mPositon=-1;
            for(int i=0;i<mDatas.size();i++){
                TopicCommentEntity entity=mDatas.get(i);
                if(entity.getId().equals(commentId)){
                    mPositon=i;
                    break;
                }
            }
        }
        if(mPositon!=-1) {
            if(isSuccess){
                //已经点赞－》取消点赞
                if (mDatas.get(mPositon).getLikeUsers().contains(mUserId))
                    mDatas.get(mPositon).getLikeUsers().remove(mUserId);
                else// 点赞
                    mDatas.get(mPositon).getLikeUsers().add(mUserId);
            }
            notifyItemChanged(mPositon + 1);//第一项是头部，所以＋1
        }

    }
    public void updateItems(List<TopicCommentEntity> comments){
        mDatas.clear();
        if(comments!=null&&comments.size()>0)
            mDatas.addAll(comments);
        notifyDataSetChanged();
    }
    public void loadItems(List<TopicCommentEntity> comments){
        if(comments!=null&&comments.size()>0) {
            mDatas.addAll(comments);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(
                viewType==HEADER_TAG?R.layout.topic_detail_header_layout:R.layout.topic_comment_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?HEADER_TAG:DEFUALT_TAG;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position==0){
            if(mTopicInfor!=null) {
                ImageLoader.getInstance().displayImage(mTopicInfor.getUser()==null?"":mTopicInfor.getUser().getUserPortrait(), holder.ivHeaderPortrait, Utils.getOptions(R.mipmap.topic_portrait));
                holder.tvHeaderContent.setText(mTopicInfor.getContent());
                holder.tvHeaderLikeCount.setText(mTopicInfor.getLikeUsers().size() + "");
                holder.tvHeaderCommentCount.setText(mTopicInfor.getCommentCount() + "");
                holder.mOnlineTopicAdapter.updateItems(mTopicInfor.getImages());
                if (holder.mOnlineTopicAdapter.getItemCount() > 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.rvHeaderTopicImages.getLayoutParams();
                    params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                    holder.rvHeaderTopicImages.setLayoutParams(params);
                }
                String date=Utils.getFormateTime(mTopicInfor.getCreatedDate(), new SimpleDateFormat("MM月dd日 HH:mm"));

                String viewTime=context.getString(R.string.browse) + mTopicInfor.getViewed() + context.getString(R.string.time);
                holder.tvHeaderDate.setText(date+"\u3000"+viewTime);
                holder.tvHeaderName.setText(mTopicInfor.getUser()==null?"":mTopicInfor.getUser().getNickname());
                if(mTopicInfor.getUserId().equals(mUserId)){//是本人发布的话题，才可以删除
                    holder.tvHeaderDel.setVisibility(View.VISIBLE);
                    holder.tvHeaderDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onItemClickListener!=null)onItemClickListener.onClickListener(v,0);
                        }
                    });
                }else{
                    holder.tvHeaderDel.setVisibility(View.GONE);
                }
            }
        }else {
            final int mPosition=position-1;
            final TopicCommentEntity entity = mDatas.get(mPosition);
            holder.tvContent.setText(entity.getContent());
            holder.rbLike.setText(String.valueOf(entity.getLikeUsers().size()));
            if(entity.getLikeUsers().contains(mUserId)){//已经点赞咯
                holder.rbLike.setChecked(true);
            }else{//没有点赞
                holder.rbLike.setChecked(false);
            }
            holder.tvDate.setText("8月9日 11:10");
//            TODO holder.tvDate.setText(Utils.getFormateTime(entity.getDate(), new SimpleDateFormat("MM月dd日 HH:mm")));
            //设置点击事件
            holder.rbLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(entity.getLikeUsers().contains(mUserId)){//已经点赞咯->取消点赞
                        holder.rbLike.setChecked(false);
                    }else{//没有点赞－点赞
                        holder.rbLike.setChecked(true);
                    }
                    if (onItemClickListener != null)
                        onItemClickListener.onClickListener(v, mPosition);
                }
            });
            holder.ivComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onClickListener(v, mPosition);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//是本人发布的评论，可以进行删除
                    if (onItemClickListener != null)
                        onItemClickListener.onClickListener(v, mPosition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onItemLongClickListener!=null)onItemLongClickListener.onLongClickListener(v,mPosition);
                    return true;
                }
            });
        }

    }
    public String getItemsId(int position){
        return mDatas.get(position).getId();
    }
    public TopicCommentEntity getItem(int position){
        return mDatas.get(position);
    }

    /**
     * @param position mData中的position
     * @param subPosition
     */
    public void removeItem(int position,int subPosition){
        if(subPosition==-1){
            mDatas.remove(position+1);//头部＋1
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //header
        private ImageView ivHeaderPortrait;
        private TextView tvHeaderName,tvHeaderDate,tvHeaderContent,tvHeaderCommentCount,
                tvHeaderLikeCount, tvHeaderDel;
        private RecyclerView rvHeaderTopicImages;
        private OnlineTopicAdapter mOnlineTopicAdapter;
        //Comment
        private TextView tvName,tvDate,tvContent,tvLikeNumber;
        private ImageView ivPortrait,ivComment;
        private RadioButtonPlus rbLike;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView.getId() == R.id.topic_comment_layout_rl_root) {
                tvName = (TextView) itemView.findViewById(R.id.topic_comment_layout_tv_name);
                tvDate = (TextView) itemView.findViewById(R.id.topic_comment_layout_tv_date);
                tvContent = (TextView) itemView.findViewById(R.id.topic_comment_layout_tv_content);
                tvLikeNumber = (TextView) itemView.findViewById(R.id.topic_comment_layout_rb_like);
                ivPortrait = (ImageView) itemView.findViewById(R.id.topic_comment_layout_iv_portrait);
                rbLike = (RadioButtonPlus) itemView.findViewById(R.id.topic_comment_layout_rb_like);
                ivComment = (ImageView) itemView.findViewById(R.id.topic_comment_layout_iv_comment);
            }else{
                ivHeaderPortrait= (ImageView) itemView.findViewById(R.id.topic_detial_layout_iv_portrait);
                tvHeaderName= (TextView) itemView.findViewById(R.id.topic_detial_layout_tv_name);
                tvHeaderContent= (TextView) itemView.findViewById(R.id.topic_detial_layout_tv_content);
                tvHeaderDate= (TextView) itemView.findViewById(R.id.topic_detial_layout_tv_date);
                rvHeaderTopicImages= (RecyclerView) itemView.findViewById(R.id.topic_detial_layout_rv_image);
                tvHeaderCommentCount= (TextView) itemView.findViewById(R.id.topic_detial_layout_tv_comment_number);
                tvHeaderLikeCount= (TextView) itemView.findViewById(R.id.topic_detial_layout_tv_like_number);
                tvHeaderDel = (TextView) itemView.findViewById(R.id.topic_detial_layout_tv_delete);
                rvHeaderTopicImages.setHasFixedSize(true);
                rvHeaderTopicImages.setLayoutManager(new FullyGridLayoutManager(context,2));
                rvHeaderTopicImages.setItemAnimator(new DefaultItemAnimator());
                mOnlineTopicAdapter=new OnlineTopicAdapter(context,true);
                rvHeaderTopicImages.setAdapter(mOnlineTopicAdapter);
            }
        }
    }
}
