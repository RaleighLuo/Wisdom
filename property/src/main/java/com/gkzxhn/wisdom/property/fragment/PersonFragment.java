package com.gkzxhn.wisdom.property.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.activity.MessageActivity;
import com.gkzxhn.wisdom.property.activity.PersonInforActivity;
import com.gkzxhn.wisdom.property.activity.SettingActivity;

/**
 * Created by Raleigh.Luo on 17/9/11.
 */

public class PersonFragment extends Fragment {
    private Context mActivity;
    private View parentView;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity=activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.person_fragment_layout, container,false);
        initControls();
        init();
        return parentView;
    }
    private void initControls(){

    }
    private void init(){
        parentView.findViewById(R.id.person_fragment_layout_ll_header).setOnClickListener(onClickListener);
        parentView.findViewById(R.id.person_fragment_layout_tv_message).setOnClickListener(onClickListener);
        parentView.findViewById(R.id.person_fragment_layout_tv_setting).setOnClickListener(onClickListener);

    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.person_fragment_layout_ll_header://个人信息
                    startActivity(new Intent(mActivity, PersonInforActivity.class));
                    break;
                case R.id.person_fragment_layout_tv_message://消息
                    startActivity(new Intent(mActivity, MessageActivity.class));
                    break;
                case R.id.person_fragment_layout_tv_setting://设置
                    startActivity(new Intent(mActivity, SettingActivity.class));
                    break;
            }
        }
    };
}
