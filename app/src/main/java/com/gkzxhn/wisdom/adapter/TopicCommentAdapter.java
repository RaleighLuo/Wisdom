package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/13.
 */

public class TopicCommentAdapter extends RecyclerView.Adapter<TopicCommentAdapter.ViewHolder> {
    private Context context;

    public TopicCommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.topic_comment_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvDate,tvContent,tvLikeNumber;
        private ImageView ivPortrait,ivLike,ivComment;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName= (TextView) itemView.findViewById(R.id.topic_comment_layout_tv_name);
            tvDate= (TextView) itemView.findViewById(R.id.topic_comment_layout_tv_date);
            tvContent= (TextView) itemView.findViewById(R.id.topic_comment_layout_tv_content);
            tvLikeNumber= (TextView) itemView.findViewById(R.id.topic_comment_layout_tv_like);
            ivPortrait= (ImageView) itemView.findViewById(R.id.topic_comment_layout_iv_portrait);
            ivLike= (ImageView) itemView.findViewById(R.id.topic_comment_layout_iv_date);
            ivComment= (ImageView) itemView.findViewById(R.id.topic_comment_layout_iv_portrait);

        }
    }
}