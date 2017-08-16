package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.PropertyFeeDetailActivity;

/**
 * Created by Raleigh.Luo on 17/7/13.
 */

public class PayRecordAdapter extends RecyclerView.Adapter<PayRecordAdapter.ViewHolder> {
    private Context context;

    public PayRecordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.pay_record_item_layout,null,false);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PropertyFeeDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

    public ViewHolder(View itemView) {
        super(itemView);
    }
}
}
