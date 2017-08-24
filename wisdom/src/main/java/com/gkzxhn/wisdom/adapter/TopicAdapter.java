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
import com.gkzxhn.wisdom.entity.TopicEntity;
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

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<TopicEntity> mDatas=new ArrayList<>();


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TopicAdapter(Context context) {
        this.context = context;

    }

    /**点赞话题
     * @param position 位置
     * @param isSuccess 是否请求成功
     */
    public void like(int position,boolean isSuccess){
        if(position>=0&&position<mDatas.size()) {
            if (isSuccess) {
                TopicEntity entity=mDatas.get(position);
                entity.setLikeable(!entity.isLikeable());
                entity.setLikesCount(entity.isLikeable()?entity.getLikesCount()-1:entity.getLikesCount()+1);
            }
            notifyItemChanged(position);
        }
    }


    /**更新话题项
     * @param list
     */
    public void upateItems(List<TopicEntity> list){
        mDatas.clear();
        if(list!=null&&list.size()>0)mDatas.addAll(list);
        notifyDataSetChanged();

    }

    /**加载话题项
     * @param list
     */
    public void loadItems(List<TopicEntity> list){
        if(list!=null&&list.size()>0) {
            mDatas.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void removeItem(int position){
        if(position>=0&&position<mDatas.size()) {
            mDatas.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * @param position
     * @param commentCount
     * @param likeCount
     */
    public void updateItem(int position,int commentCount,int likeCount,boolean isLikeable){
        if(position>=0&&position<mDatas.size()) {
            TopicEntity entity=mDatas.get(position);
            if(entity.getCommentCount()!=commentCount||entity.getLikesCount()!=likeCount) {
                entity.setCommentCount(commentCount);
                entity.setLikesCount(likeCount);
                entity.setLikeable(isLikeable);
                notifyItemChanged(position);
            }
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.topic_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TopicEntity entity=mDatas.get(position);
        holder.tvComment.setText(String.valueOf(entity.getCommentCount()));
        holder.rbLike.setText(String.valueOf(entity.getLikesCount()));
        holder.rbLike.setChecked(!entity.isLikeable());
        holder.tvContent.setText(entity.getContent());
        holder.tvName.setText(entity.getNickname());
        ImageLoader.getInstance().displayImage(entity.getPortraitUrl(),holder.ivPortrait,Utils.getOptions(R.mipmap.topic_portrait));
       String date=Utils.getFormateTime(entity.getDate(),new SimpleDateFormat(String.format("MM%sdd%s HH:mm",context.getString(R.string.month),context.getString(R.string.day))));
        String viewTime=context.getString(R.string.browse)+Utils.getViewedTime(entity.getViewed())+context.getString(R.string.time);
        holder.tvDate.setText(date+"\u3000"+viewTime);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
        holder.flComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
        holder.flLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rbLike.setChecked(entity.isLikeable());
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
        holder.rbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RadioButtonPlus)v).setChecked(entity.isLikeable());
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
        if(entity.getImages()!=null&&entity.getImages().size()>0){
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams)holder. mRecyclerView.getLayoutParams();
            params.height=RelativeLayout.LayoutParams.WRAP_CONTENT;
            holder.mRecyclerView.setLayoutParams(params);
            holder.adapter.updateItems(entity.getImages());
        }else{
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams)holder. mRecyclerView.getLayoutParams();
            params.height=0;
            holder.mRecyclerView.setLayoutParams(params);
            holder.adapter.updateItems(null);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public String getItemsId(int position){
        return mDatas.get(position).getId();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivPortrait;
        private TextView tvName,tvDate,tvContent,tvComment;
        private RadioButtonPlus rbLike;
        private RecyclerView mRecyclerView;
        private OnlineTopicAdapter adapter;
        private View flComment,flLike;

        public ViewHolder(View itemView) {
            super(itemView);
            flComment=itemView.findViewById(R.id.topic_item_layout_fl_comment);
            flLike=itemView.findViewById(R.id.topic_item_layout_fl_like);
            ivPortrait= (ImageView) itemView.findViewById(R.id.topic_item_layout_iv_portrait);
            mRecyclerView= (RecyclerView) itemView.findViewById(R.id.topic_item_layout_rv_image);
            tvName= (TextView) itemView.findViewById(R.id.topic_item_layout_tv_name);
            tvDate= (TextView) itemView.findViewById(R.id.topic_item_layout_tv_date);
            tvContent= (TextView) itemView.findViewById(R.id.topic_item_layout_tv_content);
            rbLike= (RadioButtonPlus) itemView.findViewById(R.id.topic_item_layout_rb_like);
            tvComment= (TextView) itemView.findViewById(R.id.topic_item_layout_tv_comment);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new FullyGridLayoutManager(context,4));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter=new OnlineTopicAdapter(context,false);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
