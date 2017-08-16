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
import com.starlight.mobile.android.lib.album.AlbumImageLoader;
import com.starlight.mobile.android.lib.view.EqualImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/15.
 */

public class PublishHouseAdapter extends RecyclerView.Adapter<PublishHouseAdapter.ViewHolder> {
    private final int MAX_PHOTO_COUNT=8;
    private Context context;
    private List<PhotoEntity> mDatas=new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private AlbumImageLoader mAlbumImageLoader;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public int getMaxPhotoCount(){
        return MAX_PHOTO_COUNT;
    }

    public PublishHouseAdapter(Context context) {
        this.context = context;
        mAlbumImageLoader=new AlbumImageLoader(3, AlbumImageLoader.Type.LIFO);
    }
    public List<String> getLocalPaths(){
        List<String> paths=new ArrayList<>();
        for(PhotoEntity entity:mDatas){
            paths.add(entity.getLocalPath());
        }
        return  paths;

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
    /**添加项
     * @param path
     */
    public void addItem(String path){
        mDatas.add(new PhotoEntity(path));
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
    /**添加url
     * @param url
     * @param position
     */
    public void addUrl(String url,int position){
        mDatas.get(position).setImageUrl(url);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.publish_house_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(position<mDatas.size()) {
            mAlbumImageLoader.loadImage(mDatas.get(position).getLocalPath(), holder.ivImage);
            holder.flPanel.setBackgroundResource(R.color.border_color);
        }else{
            holder.ivImage.setImageResource(R.mipmap.repair_take_photo);
            holder.flPanel.setBackground(null);
        }
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });

    }
    public boolean checkTakePhoto(int position){
        boolean result=false;
        if(position==getItemCount()-1&&getPhotoCount()<getItemCount()){
            //最后一个，并且还没拍照满
            result=true;
        }
        return result;
    }
    public int getPhotoCount(){
        return mDatas.size();
    }

    @Override
    public int getItemCount() {
        return mDatas.size()==MAX_PHOTO_COUNT?mDatas.size():mDatas.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        View flPanel;
        public ViewHolder(View itemView) {
            super(itemView);
            flPanel=itemView.findViewById(R.id.publish_house_item_layout_fl_image);
            ivImage= (ImageView) itemView.findViewById(R.id.publish_house_item_layout_iv_image);
        }
    }
}
