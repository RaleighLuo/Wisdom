package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;

import static com.gkzxhn.wisdom.R.id.repair_item_layout_tv_status;

/**
 * Created by Raleigh.Luo on 17/7/11.
 * 我的报修记录
 */

public class RepairRecordAdapter extends RecyclerView.Adapter<RepairRecordAdapter.ViewHolder> {

    private Context context;
    private final int TAB;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
    }

    public RepairRecordAdapter(Context context, int TAB) {
        this.context = context;
        this.TAB=TAB;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.repair_record_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
            }
        });
//        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
//            }
//        });
//        holder.tvRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onItemClickListener!=null)onItemClickListener.onClickListener(v,position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle,tvDate,tvDelete,tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDelete= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_delete);
            tvStatus= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_delete);
            if(TAB== Constants.REPAIR_FINISHED_TAB){
                tvDelete.setText(R.string.delete);
                tvStatus.setText("已完成");
            }
        }
    }
}
