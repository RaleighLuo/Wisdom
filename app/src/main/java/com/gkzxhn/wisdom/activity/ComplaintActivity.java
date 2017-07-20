package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.presenter.CompaintPresenter;
import com.gkzxhn.wisdom.view.IComplaintView;
import com.starlight.mobile.android.lib.util.CommonHelper;

/**
 * Created by Raleigh.Luo on 17/7/13.
 */

public class ComplaintActivity extends SuperActivity implements IComplaintView {
    private CompaintPresenter mPresenter;
    private TextView tvTel;//物业电话
    private EditText etContent;//投诉内容
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvTel= (TextView) findViewById(R.id.complaint_layout_tv_tel);
        etContent= (EditText) findViewById(R.id.complaint_layout_et_content);
    }
    private void init(){
        mPresenter=new CompaintPresenter(this,this);
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        stopRefreshAnim();
    }
    public void onClickListener(View view){
        CommonHelper.clapseSoftInputMethod(this);
        switch (view.getId()){
            case R.id.complaint_layout_iv_back://返回
                finish();
                break;
            case R.id.complaint_layout_btn_submit://提交
                String content=etContent.getText().toString().trim();
                if(content.length()==0){//未输入内容
                    showToast(R.string.please_input_complaint_content);
                }else{
                    mPresenter.publish(content);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        CommonHelper.clapseSoftInputMethod(this);
        super.onBackPressed();
    }

    @Override
    public void startRefreshAnim() {
        if(mProgress!=null&&!mProgress.isShowing())mProgress.show();
    }

    @Override
    public void stopRefreshAnim() {
        if(mProgress!=null&&mProgress.isShowing())mProgress.dismiss();
    }


    @Override
    public void onSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        stopRefreshAnim();
        if(mPresenter!=null)mPresenter.onDestory();
        super.onDestroy();
    }
}
