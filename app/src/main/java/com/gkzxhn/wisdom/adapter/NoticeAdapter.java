package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/6/26.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private Context mContext;
    public NoticeAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.notice_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==1||position==4||position==7){
            holder.tvIcon.setText(R.string.ju);
            holder.tvIcon.setBackgroundResource(R.drawable.committee_oval_shape);
        }else{
            holder.tvIcon.setText(R.string.wu);
            holder.tvIcon.setBackgroundResource(R.drawable.property_oval_shape);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvIcon= (TextView) itemView.findViewById(R.id.notice_item_layout_tv_icon);
        }
    }
}
