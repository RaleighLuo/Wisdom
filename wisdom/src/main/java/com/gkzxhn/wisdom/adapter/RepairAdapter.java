package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.entity.RepairEntity;
import com.gkzxhn.wisdom.util.Utils;

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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RepairAdapter(Context context) {
        this.context = context;
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
        View view= LayoutInflater.from(context).inflate(R.layout.repair_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        RepairEntity entity=mData.get(position);
        holder.tvContent.setText(entity.getContent());
        holder.tvDate.setText(Utils.getFormateTime(entity.getCreatedDate(),new SimpleDateFormat("MM月dd日 HH:mm")));
        holder.tvRepairType.setText(getRepairType(entity.getRepairType()));
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
        return repairType;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvRepairType,tvContent,tvDate;
        public ViewHolder(View itemView) {
            super(itemView);
            tvRepairType= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_title);
            tvContent= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_content);
            tvDate= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_date);
        }
    }
}
