package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.entity.UserEntity;
import com.gkzxhn.wisdom.presenter.PersonInforPresenter;
import com.gkzxhn.wisdom.view.IPersonInforView;
import com.starlight.mobile.android.lib.util.CommonHelper;

/**
 * Created by Raleigh.Luo on 17/8/8.
 */

public class AlterNicknameActivity extends SuperActivity implements IPersonInforView{
    private EditText etContent;
    private PersonInforPresenter mPresenter;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alter_nickname_layout);
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        stopRefreshAnim();
        etContent= (EditText) findViewById(R.id.alter_nickname_layout_et_content);
        mPresenter=new PersonInforPresenter(this,this);
        etContent.setText(mPresenter.getSharedPreferences().getString(Constants.USER_NICKNAME,""));
    }
    public void onClickListener(View view){
        CommonHelper.clapseSoftInputMethod(this);
        switch (view.getId()){
            case R.id.common_head_layout_iv_left:
                finish();
                break;
            case R.id.common_head_layout_tv_right:
                String content=etContent.getText().toString().trim();
                if(content.length()>0) {
                    mPresenter.updateUserInfor(content, null);
                }else{
                    showToast(R.string.please_input_nickname);
                }
                break;
        }
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
    protected void onDestroy() {
        stopRefreshAnim();
        super.onDestroy();
    }

    @Override
    public void update(UserEntity entity) {
        setResult(RESULT_OK);
        finish();
    }
}
