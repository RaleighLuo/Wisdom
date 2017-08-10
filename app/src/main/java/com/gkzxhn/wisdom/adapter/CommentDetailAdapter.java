package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.entity.TopicReplayEntity;
import com.starlight.mobile.android.lib.view.RadioButtonPlus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class CommentDetailAdapter extends RecyclerView.Adapter<CommentDetailAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<TopicReplayEntity> mDatas=new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommentDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_detail_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
        holder.rbLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.rbLike.setChecked(!holder.rbLike.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivPortrait;
        public TextView tvName,tvContent,tvDate;
        public RadioButtonPlus rbLike;
        public ViewHolder(View itemView) {
            super(itemView);
            ivPortrait= (ImageView) itemView.findViewById(R.id.comment_detail_item_layout_iv_portrait);
            tvName= (TextView) itemView.findViewById(R.id.comment_detail_item_layout_tv_name);
            tvContent= (TextView) itemView.findViewById(R.id.comment_detail_item_layout_tv_content);
            tvDate= (TextView) itemView.findViewById(R.id.comment_detail_item_layout_tv_date);
            rbLike= (RadioButtonPlus) itemView.findViewById(R.id.comment_detail_item_layout_rb_like);
        }
    }
}
