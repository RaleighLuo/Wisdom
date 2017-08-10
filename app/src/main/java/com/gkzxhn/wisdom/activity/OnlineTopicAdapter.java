package com.gkzxhn.wisdom.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.DivisionImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class OnlineTopicAdapter extends RecyclerView.Adapter<OnlineTopicAdapter.ViewHolder>{
    private Context context;
    private List<String> mDatas =new ArrayList<>();
    private boolean needChangeSize=false;

    /**
     * @param context
     * @param needChangeSize
     */
    public OnlineTopicAdapter(Context context,boolean needChangeSize) {
        this.context = context;
        this.needChangeSize=needChangeSize;
    }
    /**更新所有数据
     * @param paths
     */
    public void updateItems(List<String> paths){
        mDatas.clear();
        if(paths!=null&&paths.size()>0)
            mDatas.addAll(paths);
        notifyDataSetChanged();
    }


    @Override
    public OnlineTopicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.online_topic_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(needChangeSize) {
            if (getItemCount() <= 2) {//heigh=width*1/1
                holder.ivImage.setDivisor(1, 1);
            } else {//heigh=width*1/2
                holder.ivImage.setDivisor(2, 3);
            }
        }else{
            holder.ivImage.setDivisor(3, 4);
        }
        ImageLoader.getInstance().displayImage(mDatas.get(position),holder.ivImage);
        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//查看大图
                Intent intent=new Intent(context ,OnlinePhotoPreviewActivity.class);
                intent.putExtra(Constants.EXTRAS, (Serializable) mDatas);
                intent.putExtra(Constants.EXTRA_POSITION, position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private DivisionImageView ivImage;
        public ViewHolder(View itemView) {
            super(itemView);
            ivImage= (DivisionImageView) itemView.findViewById(R.id.publish_topic_item_layout_iv_image);
        }
    }
}
