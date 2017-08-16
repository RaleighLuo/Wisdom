package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.RepairDetailActivity;
import com.gkzxhn.wisdom.common.Constants;

/**
 * Created by Raleigh.Luo on 17/7/11.
 */

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder> {

    private Context context;
    private final int TAB;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
    }

    public RepairAdapter(Context context, int TAB) {
        this.context = context;
        this.TAB=TAB;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.repair_item_layout,null,false);
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemLongClickListener!=null)onItemLongClickListener.onLongClickListener(v,position);
                return true;
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
        public TextView tvTitle,tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_date);
            tvTitle= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_title);
//            tvLeft= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_left);
//            tvRight= (TextView) itemView.findViewById(R.id.repair_item_layout_tv_right);
//            if(TAB== Constants.REPAIR_PROGRESSING_TAB){
//                tvRight.setText(R.string.progress);
//                tvRight.setBackgroundResource(R.drawable.repair_progress_btn_selector);
//                tvRight.setTextColor(context.getResources().getColor(R.color.pay_color));
//            }
        }
    }
}
