package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
