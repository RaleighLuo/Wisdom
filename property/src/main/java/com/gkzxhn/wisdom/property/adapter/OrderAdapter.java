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
        View view= LayoutInflater.from(context).inflate(TAB== Constants.REPAIR_UNASSIGNED_TAB?R.layout.order_item_layout:R.layout.order_assigned_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              context.startActivity(new Intent(context, OrderDetailActivity.class));
          }
      });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStatus,tvType,tvDate,tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStatus= (TextView) itemView.findViewById(R.id.order_item_layout_tv_status);
            tvType= (TextView) itemView.findViewById(R.id.order_item_layout_tv_type);
            tvDate= (TextView) itemView.findViewById(R.id.order_item_layout_tv_date);
            tvContent= (TextView) itemView.findViewById(R.id.order_item_layout_tv_content);
        }
    }
}
