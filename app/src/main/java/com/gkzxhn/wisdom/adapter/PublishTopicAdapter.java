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
 * Created by Raleigh.Luo on 17/8/3.
 */

public class PublishTopicAdapter extends RecyclerView.Adapter<PublishTopicAdapter.ViewHolder> {
    private Context context;

    public PublishTopicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.publish_topic_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImage;
        private TextView tvDel;
        public ViewHolder(View itemView) {
            super(itemView);
            ivImage= (ImageView) itemView.findViewById(R.id.publish_topic_item_layout_iv_image);
            tvDel= (TextView) itemView.findViewById(R.id.publish_topic_item_layout_tv_del);

        }
    }
}
