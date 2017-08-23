package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.entity.TopicReplayEntity;
import com.gkzxhn.wisdom.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class TopicCommentDetailAdapter extends RecyclerView.Adapter<TopicCommentDetailAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<TopicReplayEntity> mDatas=new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TopicCommentDetailAdapter(Context context) {
        this.context = context;
    }
    public TopicReplayEntity getItem(int position){
        return mDatas.get(position);
    }

    /**移除项Byposition
     * @param position
     */
    public void removeItem(int position){
        if(position>=0&&position<mDatas.size()) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**添加项
     * @param entity
     */
    public void addItem(TopicReplayEntity entity){
        if(entity!=null){
            mDatas.add(0,entity);
            notifyDataSetChanged();
        }

    }

    /**获取某项id
     * @param position
     * @return
     */
    public String getItemsId(int position){
        return mDatas.get(position).getId();
    }

    /**获取某项的创建者userid
     * @param position
     * @return
     */
    public String getItemsUserId(int position){
        return position>=0&&position<mDatas.size()?mDatas.get(position).getUserId():"";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_detail_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        TopicReplayEntity entity=mDatas.get(position);
        ImageLoader.getInstance().displayImage(entity.getPortrait(),holder.ivPortrait, Utils.getOptions(R.mipmap.topic_portrait));
        holder.tvContent.setText(entity.getContent());
        holder.tvName.setText(entity.getNickname());
        holder.tvDate.setText(Utils.getFormateTime(entity.getDate(), new SimpleDateFormat("MM月dd日 HH:mm")));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });

    }

    /**更新数据
     * @param datas
     */
    public void updateItems(List<TopicReplayEntity> datas){
        this.mDatas.clear();
        if(datas!=null&&datas.size()>0){
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();

    }

    /**加载数据
     * @param datas
     */
    public void loadItems(List<TopicReplayEntity> datas){
        if(datas!=null&&datas.size()>0){
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }

    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivPortrait;
        public TextView tvName,tvContent,tvDate;
        public ViewHolder(View itemView) {
            super(itemView);
            ivPortrait= (ImageView) itemView.findViewById(R.id.comment_detail_item_layout_iv_portrait);
            tvName= (TextView) itemView.findViewById(R.id.comment_detail_item_layout_tv_name);
            tvContent= (TextView) itemView.findViewById(R.id.comment_detail_item_layout_tv_content);
            tvDate= (TextView) itemView.findViewById(R.id.comment_detail_item_layout_tv_date);
        }
    }
}
