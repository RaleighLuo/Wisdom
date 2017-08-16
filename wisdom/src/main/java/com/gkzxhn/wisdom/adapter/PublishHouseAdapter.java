package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.entity.PhotoEntity;
import com.starlight.mobile.android.lib.view.EqualImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/15.
 */

public class PublishHouseAdapter extends RecyclerView.Adapter<PublishHouseAdapter.ViewHolder> {
    private final int MAX_PHOTO_COUNT=6;
    private Context context;
    private List<PhotoEntity> mDatas=new ArrayList<>();

    public PublishHouseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.publish_house_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size()==MAX_PHOTO_COUNT?mDatas.size():mDatas.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ivImage= (ImageView) itemView.findViewById(R.id.publish_house_item_layout_iv_image);
        }
    }
}
