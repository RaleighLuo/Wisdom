package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.entity.RoomEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/8.
 */

public class ChangeCommunityAdapter extends RecyclerView.Adapter<ChangeCommunityAdapter.ViewHolder> {
    private Context context;
    private List<RoomEntity> mDatas=new ArrayList<>();
    private String mDefaultResidentialAreasId;
    private int mCheckedPosition=-1;
    private CheckBox mLastCheck=null;

    public ChangeCommunityAdapter(Context context,String residentialAreasId) {
        this.context = context;
        mDefaultResidentialAreasId=residentialAreasId;
    }

    public void updateItems(List<RoomEntity> mDatas) {
        this.mDatas = mDatas;
    }
    public RoomEntity getCheckItem(){
        return mCheckedPosition==-1?null:mDatas.get(mCheckedPosition);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.change_community_item_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RoomEntity entity=mDatas.get(position);
        holder.tvTitle.setText(entity.getResidentialAreasName());
        holder.tvContent.setText(entity.getRegionName()+entity.getBuildingName()+entity.getUnitsName()+entity.getRoomName());
        holder.cbCheck.setChecked(mDefaultResidentialAreasId.equals(entity.getResidentialAreasId()));
        if(holder.cbCheck.isChecked()){
            mCheckedPosition=position;
            mLastCheck=holder.cbCheck;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckedPosition!=position){
                    mDefaultResidentialAreasId=entity.getResidentialAreasId();
                    if(mLastCheck!=null)mLastCheck.setChecked(false);
                    mCheckedPosition=position;
                    notifyItemChanged(position);
                }
            }
        });
        holder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mCheckedPosition!=position){
                    mDefaultResidentialAreasId=entity.getResidentialAreasId();
                    if(mLastCheck!=null)mLastCheck.setChecked(false);
                    mCheckedPosition=position;
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle,tvContent;
        private CheckBox cbCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle= (TextView) itemView.findViewById(R.id.change_community_item_layout_tv_title);
            tvContent= (TextView) itemView.findViewById(R.id.change_community_item_layout_tv_content);
            cbCheck= (CheckBox) itemView.findViewById(R.id.change_community_item_layout_cb_check);
        }
    }
}
