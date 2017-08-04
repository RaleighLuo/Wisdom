package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.entity.PhotoEntity;
import com.starlight.mobile.android.lib.album.AlbumImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/3.
 */

public class PublishTopicAdapter extends RecyclerView.Adapter<PublishTopicAdapter.ViewHolder> {
    private Context context;
    private List<PhotoEntity> mDatas =new ArrayList<>();
    private AlbumImageLoader mAlbumImageLoader;
    public PublishTopicAdapter(Context context) {
        this.context = context;
        mAlbumImageLoader=new AlbumImageLoader(3, AlbumImageLoader.Type.LIFO);
    }
    public void addItem(String path){
        mDatas.add(new PhotoEntity(path));
        notifyDataSetChanged();
    }
    public void addItems(List<String> paths){
        for(String path:paths){
            mDatas.add(new PhotoEntity(path));
        }
        notifyDataSetChanged();
    }
    public void addUrl(String url,int position){
        mDatas.get(position).setImageUrl(url);
    }
    public void removeItem(int position){
        mDatas.remove(position);
        notifyDataSetChanged();
    }
    public String getLocalPath(int position){
        return mDatas.get(position).getLocalPath();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.publish_topic_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position<mDatas.size()) {
            mAlbumImageLoader.loadImage(mDatas.get(position).getLocalPath(), holder.ivImage);
        }else{
            holder.ivImage.setImageResource(R.mipmap.ic_imageloading);
        }
    }
    public int getPhotoCount(){
        return mDatas.size();
    }
    public List<String> getUrls(){
        List<String> url=new ArrayList<>();
        for(PhotoEntity entity:mDatas){
            if(entity.getImageUrl().length()>0)url.add(entity.getImageUrl());
        }
        return  url;
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
