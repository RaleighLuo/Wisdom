package com.gkzxhn.wisdom.property.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.activity.OrderDetailActivity;
import com.gkzxhn.wisdom.property.common.Constants;

/**
 * Created by Raleigh.Luo on 17/9/4.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private final int TAB;

    public OrderAdapter(Context context,int tab) {
        this.context = context;
        this.TAB=tab;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(TAB== Constants.REPAIR_ASSIGNED_TAB?R.layout.order_assigned_item_layout:
                R.layout.order_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (TAB){
            case Constants.REPAIR_UNASSIGNED_TAB://未指派
                holder.tvOperate.setText(R.string.assign);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, OrderDetailActivity.class);
                        intent.putExtra(Constants.EXTRA,Constants.ORDER_STATUS_UNASSIGN);
                        context.startActivity(intent);
                    }
                });
                holder.tvOperate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case Constants.REPAIR_UNACCEPT_TAB://待接受
                holder.tvOperate.setText(R.string.accept);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, OrderDetailActivity.class);
                        intent.putExtra(Constants.EXTRA,Constants.ORDER_STATUS_UNACCEPT);
                        context.startActivity(intent);
                    }
                });
                holder.tvOperate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case Constants.REPAIR_ACCEPTED_TAB://已接受
            case Constants.REPAIR_ASSIGNED_TAB://已指派
                holder.tvOperate.setBackgroundResource(0);
                if (position==0){
                    holder.tvOperate.setText("维修中");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, OrderDetailActivity.class);
                            intent.putExtra(Constants.EXTRA,Constants.ORDER_STATUS_REPAIRING);
                            context.startActivity(intent);
                        }
                    });
                }else if(position==1){
                    holder.tvOperate.setText("已维修");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, OrderDetailActivity.class);
                            intent.putExtra(Constants.EXTRA,Constants.ORDER_STATUS_REPAIRED);
                            context.startActivity(intent);
                        }
                    });
                }else if(position==2){
                    holder.tvOperate.setText("已拒绝");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, OrderDetailActivity.class);
                            intent.putExtra(Constants.EXTRA,Constants.ORDER_STATUS_REFUSED);
                            context.startActivity(intent);
                        }
                    });
                }else{
                    holder.tvOperate.setText("已接受");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, OrderDetailActivity.class);
                            intent.putExtra(Constants.EXTRA,Constants.ORDER_STATUS_ACCEPTED);
                            context.startActivity(intent);
                        }
                    });
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvOperate,tvType,tvDate,tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOperate = (TextView) itemView.findViewById(R.id.order_item_layout_tv_operate);
            if(tvOperate==null)tvOperate=(TextView) itemView.findViewById(R.id.order_item_layout_tv_status);
            tvType= (TextView) itemView.findViewById(R.id.order_item_layout_tv_type);
            tvDate= (TextView) itemView.findViewById(R.id.order_item_layout_tv_date);
            tvContent= (TextView) itemView.findViewById(R.id.order_item_layout_tv_content);
        }
    }
}
