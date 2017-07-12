package com.gkzxhn.wisdom.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.gkzxhn.wisdom.R;

/**
 * Created by Raleigh.Luo on 17/7/11.
 * 发布报修
 */

public class PublishRepairActivity extends SuperActivity{
    private EditText etContent;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_repair_layout);
        initControls();
        init();
    }
    private void initControls(){
        etContent= (EditText) findViewById(R.id.publish_repair_layout_et_content);
        btnSubmit.findViewById(R.id.publish_repair_layout_btn_submit);
    }
    private void init(){
    }
}
