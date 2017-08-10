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
    private OnItemClickListener onItemClickListener;
    private int mShowDelPosition=-1;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PublishTopicAdapter(Context context) {
        this.context = context;
        mAlbumImageLoader=new AlbumImageLoader(3, AlbumImageLoader.Type.LIFO);
    }

    /**添加项
     * @param path
     */
    public void addItem(String path){
        mDatas.add(new PhotoEntity(path));
        notifyDataSetChanged();
    }

    /**添加数组项
     * @param paths
     */
    public void addItems(List<String> paths){
        for(String path:paths){
            mDatas.add(new PhotoEntity(path));
        }
        notifyDataSetChanged();
    }

    /**更新所有数据
     * @param paths
     */
    public void updateItems(List<String> paths){
        mDatas.clear();
        for(String path:paths){
            mDatas.add(new PhotoEntity(path));
        }
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear(){
        mDatas.clear();
        notifyDataSetChanged();
    }
    public boolean  recoveryDelPosition(){
        if(mShowDelPosition!=-1) {
            mShowDelPosition = -1;
            notifyDataSetChanged();
            return true;
        }else{
            return  false;
        }
    }

    /**添加url
     * @param url
     * @param position
     */
    public void addUrl(String url,int position){
        mDatas.get(position).setImageUrl(url);
    }
    public void removeItem(int position){
        mShowDelPosition=-1;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvDel.setVisibility(mShowDelPosition==position?View.VISIBLE:View.GONE);
        if(position<mDatas.size()) {
            mAlbumImageLoader.loadImage(mDatas.get(position).getLocalPath(), holder.ivImage);
        }else{
            holder.ivImage.setImageResource(R.mipmap.default_image);
        }
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<mDatas.size())
                    if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<mDatas.size())
                    if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
        holder.ivImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position<mDatas.size()){
                    mShowDelPosition=position;
                    holder.tvDel.setVisibility(View.VISIBLE);
                    return true;
                }else {
                    return false;
                }
            }
        });
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
    public List<String> getLocalPaths(){
        List<String> paths=new ArrayList<>();
        for(PhotoEntity entity:mDatas){
            paths.add(entity.getLocalPath());
        }
        return  paths;
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
