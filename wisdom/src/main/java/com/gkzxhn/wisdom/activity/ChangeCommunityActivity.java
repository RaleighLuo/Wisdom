package com.gkzxhn.wisdom.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.ChangeCommunityAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.entity.RoomEntity;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/8.
 * 切换小区
 */

public class ChangeCommunityActivity extends SuperActivity {
    private RecyclerView mRecyclerView;
    private ChangeCommunityAdapter adapter;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_community_layout);
        mRecyclerView= (RecyclerView) findViewById(R.id.change_community_layout_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        preferences=getSharedPreferences(Constants.USER_TABLE, Context.MODE_PRIVATE);
        adapter=new ChangeCommunityAdapter(this,preferences.getString(Constants.USER_RESIDENTIALAREASID,""));
        mRecyclerView.setAdapter(adapter);
        adapter.updateItems((List<RoomEntity>) getIntent().getSerializableExtra(Constants.EXTRA));
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.common_head_layout_tv_right:
                setResult(RESULT_OK);
                RoomEntity roomEntity=adapter.getCheckItem();
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString(Constants.USER_BUILDINGID,roomEntity.getBuildingId());
                editor.putString(Constants.USER_BUILDINGNAME,roomEntity.getBuildingName());
                editor.putString(Constants.USER_REGIONID,roomEntity.getRegionId());
                editor.putString(Constants.USER_REGIONNAME,roomEntity.getRegionName());
                editor.putString(Constants.USER_RESIDENTIALAREASID,roomEntity.getResidentialAreasId());
                editor.putString(Constants.USER_RESIDENTIALAREASNAME,roomEntity.getResidentialAreasName());
                editor.putString(Constants.USER_ROOMID,roomEntity.getRoomId());
                editor.putString(Constants.USER_ROOMNAME,roomEntity.getRoomName());
                editor.putString(Constants.USER_UNITSID,roomEntity.getUnitsId());
                editor.putString(Constants.USER_UNITSNAME,roomEntity.getUnitsName());
                editor.putFloat(Constants.USER_USEDAREA,roomEntity.getUsedArea());
                editor.putFloat(Constants.USER_FLOORAREA,roomEntity.getFloorArea());
                editor.commit();
                finish();
                break;
        }
    }
}
