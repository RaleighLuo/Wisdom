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
import com.gkzxhn.wisdom.activity.HouseRentalActivity;
import com.gkzxhn.wisdom.activity.NoticeActivity;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context context;
    private final int TITLE_TYPE=1,NOTICE_TYPE=2,HOUSE_TYPE=3;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case TITLE_TYPE:
                view= LayoutInflater.from(context).inflate(R.layout.main_title_item_layout,null,false);
                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                break;
            case NOTICE_TYPE:
                view= LayoutInflater.from(context).inflate(R.layout.notice_item_layout,null,false);
                break;
            default://HOUSE_TYPE
                view= LayoutInflater.from(context).inflate(R.layout.notice_item_layout,null,false);
                break;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case TITLE_TYPE://标题
                ((TextView)holder.itemView.findViewById(R.id.main_layout_tv_newst_notice)).
                setText(position==0?R.string.newest_notice:R.string.house_message);
                holder.itemView.findViewById(R.id.main_layout_tv_more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      context.startActivity(new Intent(context,position==0?
                              NoticeActivity.class: HouseRentalActivity.class));
                    }
                });
                break;
            case NOTICE_TYPE:
                break;
            case HOUSE_TYPE:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        int type=HOUSE_TYPE;
        switch (position){
            case 0:
            case 3:
                type=TITLE_TYPE;
                break;
            case 1:
            case 2:
                type=NOTICE_TYPE;
                break;
            default://访问
                type=HOUSE_TYPE;
                break;
        }
        return type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
