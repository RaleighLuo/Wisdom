package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.PublishTopicAdapter;
import com.starlight.mobile.android.lib.util.CommonHelper;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public class PublishTopicActivity extends SuperActivity{
    private ImageView ivAdd;
    private RecyclerView mRecyclerView;
    private PublishTopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_topic_layout);
        initControls();
        init();
    }
    private void initControls(){
        mRecyclerView= (RecyclerView) findViewById(R.id.publish_topic_layout_recycleview);
        ivAdd= (ImageView) findViewById(R.id.publish_topic_layout_iv_add);

    }
    private void init(){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new PublishTopicAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }
    public void onClickListener(View view){
        CommonHelper.clapseSoftInputMethod(this);
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.common_head_layout_tv_right://发布
                break;
            case R.id.publish_topic_layout_iv_add://添加图片
                break;
        }
    }
}
