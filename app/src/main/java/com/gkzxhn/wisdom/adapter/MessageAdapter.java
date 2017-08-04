package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.TopicDetailActivity;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.message_comment_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==0){
            holder.tvContent.setVisibility(View.GONE);
            holder.ivLike.setVisibility(View.VISIBLE);
        }

        if(position==5){
            holder.tvContent.setVisibility(View.GONE);
            holder.ivLike.setVisibility(View.VISIBLE);
            holder.tvTopicContent.setVisibility(View.VISIBLE);
            holder.ivTopicImage.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TopicDetailActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName,tvContent,tvDate,tvTopicContent;
        private ImageView ivPortrait,ivTopicImage,ivLike;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName= (TextView) itemView.findViewById(R.id.message_comment_item_layout_tv_name);
            tvContent= (TextView) itemView.findViewById(R.id.message_comment_item_layout_tv_content);
            tvDate= (TextView) itemView.findViewById(R.id.message_comment_item_layout_tv_date);
            tvTopicContent= (TextView) itemView.findViewById(R.id.message_comment_item_layout_tv_topic_content);
            ivPortrait= (ImageView) itemView.findViewById(R.id.message_comment_item_layout_iv_portrait);
            ivTopicImage= (ImageView) itemView.findViewById(R.id.message_comment_item_layout_iv_topic_image);
            ivLike=(ImageView) itemView.findViewById(R.id.message_comment_item_layout_iv_like);
        }
    }
}

