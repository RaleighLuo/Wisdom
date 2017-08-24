package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.OnlinePhotoPreviewActivity;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.DivisionImageView;
import com.gkzxhn.wisdom.entity.RepairEntity;
import com.gkzxhn.wisdom.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Raleigh.Luo on 17/8/22.
 */

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder>{
    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<RepairEntity> mData=new ArrayList<>();
    private String[] mStatusArray;
    private final int TAB;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RepairAdapter(Context context,int TAB) {
        this.context = context;
        this.TAB=TAB;
        mStatusArray=context.getResources().getStringArray(R.array.repair_status);
    }
    public void updateItems(List<RepairEntity> datas){
        mData.clear();
        if(datas!=null&&datas.size()>0){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void loadItems(List<RepairEntity> datas){
        if(datas!=null&&datas.size()>0){
            mData.addAll(datas);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
        View view=null;
        if(TAB==Constants.REPAIR_FINISHED_TAB||TAB==Constants.REPAIR_PROGRESSING_TAB) {//报修记录，我的报修
            view = LayoutInflater.from(context).inflate(R.layout.repair_record_item_layout, null, false);
        }else{//社区报修
            view= LayoutInflater.from(context).inflate(R.layout.repair_item_layout,null,false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RepairEntity entity=mData.get(position);
        holder.tvContent.setText(entity.getContent());
        String viewTime=context.getString(R.string.browse)+Utils.getViewedTime(entity.getViewed())+context.getString(R.string.time);
        String date=Utils.getFormateTime(entity.getCreatedDate(),new SimpleDateFormat(String.format("yyyy%sMM%sdd%s",context.getString(R.string.year),
                context.getString(R.string.month),context.getString(R.string.day))));
        holder.tvDate.setText(date+"\u3000"+viewTime);
        holder.tvRepairType.setText(getRepairType(entity.getRepairType()));
        holder.tvNicknameOrDel.setText(entity.getNickname());
        String status=mStatusArray[0];
        //报修状态
        switch (entity.getStatus()){
            case RepairEntity.STATUS_UNDISPOSED:
                status=mStatusArray[0];
                break;
            case RepairEntity.STATUS_PROCESSING:
                status=mStatusArray[1];
                break;
            case RepairEntity.STATUS_FINISHED:
                status=mStatusArray[2];
                break;

        }
        holder.tvStatus.setText(status);

        if(TAB== Constants.REPAIR_PROGRESSING_TAB){
            holder.tvNicknameOrDel.setText(R.string.revoke);
            holder.tvNicknameOrDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
                }
            });
        }else if(TAB== Constants.REPAIR_FINISHED_TAB){
            holder.tvNicknameOrDel.setText(R.string.delete);
            holder.tvNicknameOrDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
                }
            });
        }
        //图片
        if(entity.getImages().size()==0){
            holder.llImagesPanel.setVisibility(View.GONE);
        }else{
            holder.llImagesPanel.setVisibility(View.VISIBLE);
            for(int i=0;i<holder.llImagesPanel.getChildCount();i++){
                DivisionImageView ivImage= (DivisionImageView) holder.llImagesPanel.getChildAt(i);
                if(i<entity.getImages().size()){//显示图片
                    ivImage.setVisibility(View.VISIBLE);
                    if(entity.getImages().size()==1){
                        ivImage.setDivisor(2,3);
                    }
                    ImageLoader.getInstance().displayImage(entity.getImages().get(i),ivImage,Utils.getOptions(R.mipmap.ic_imageloading));
                    final int index=i;
                    ivImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context ,OnlinePhotoPreviewActivity.class);
                            intent.putExtra(Constants.EXTRAS, (Serializable) entity.getImages());
                            intent.putExtra(Constants.EXTRA_POSITION, index);
                            context.startActivity(intent);
                        }
                    });

                }else{//隐藏
                    ivImage.setVisibility(View.GONE);
                    ivImage.setOnClickListener(null);
                }

            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
    }
    private String getRepairType(String repairTypeParam){
        String[] mRepairTypeParams = context.getResources().getStringArray(R.array.repair_type_params);
        String[] mRepairType = context.getResources().getStringArray(R.array.repair_type);
        String repairType="";
        for(int i=0;i<mRepairTypeParams.length;i++){
            if(repairTypeParam.equals(repairTypeParam)){
                repairType=mRepairType[i];
                break;
            }
        }
//
        return repairType;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvRepairType,tvContent,tvDate,tvStatus, tvNicknameOrDel;
        private LinearLayout llImagesPanel;
        public ViewHolder(View itemView) {
            super(itemView);
            llImagesPanel= (LinearLayout) itemView.findViewById(R.id.repair_item_layout_ll_image);
            tvRepairType= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_title);
            tvContent= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_content);
            tvDate= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_date);
            tvStatus= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_status);
            tvNicknameOrDel = (TextView) itemView.findViewById(R.id.repair_item_layout_tv_publisher_or_del);
        }
    }
}
