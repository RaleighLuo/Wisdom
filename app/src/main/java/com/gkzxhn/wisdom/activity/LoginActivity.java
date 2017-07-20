package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.presenter.LoginPresenter;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.ILoginView;

/**
 * Created by Raleigh.Luo on 17/7/14.
 */

public class LoginActivity extends SuperActivity implements ILoginView{
    private EditText etPhone,etVerifyCode;
    private LoginPresenter mPresenter;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initControls();
        init();
    }
    private void initControls(){
        etPhone= (EditText) findViewById(R.id.login_layout_et_phone);
        etVerifyCode= (EditText) findViewById(R.id.login_layout_et_verfy_code);

    }
    private void init(){
        etPhone.setText("18163657553");
        mPresenter=new LoginPresenter(this,this);
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        stopRefreshAnim();
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.login_layout_btn_login://登录
                String phone=etPhone.getText().toString().trim();
                String code=etVerifyCode.getText().toString().trim();
//                if(phone.length()==0){
//                    showToast(getString(R.string.please_input)+getString(R.string.phone_number));
//                }else if(!Utils.isPhoneNumber(phone)){
//                    showToast(R.string.error_phone);
//                }else if(code.length()==0){
//                    showToast(getString(R.string.please_input)+getString(R.string.verfy_code));
//                }else{
//                    mPresenter.login(phone,code);
//                }
                onSuccess();
                break;
            case R.id.login_layout_tv_get_verify_code://获取验证码
                phone=etPhone.getText().toString().trim();
                if(phone.length()==0){
                    showToast(getString(R.string.please_input)+getString(R.string.phone_number));
                }else if(!Utils.isPhoneNumber(phone)){
                    showToast(R.string.error_phone);
                }else{
                    mPresenter.requstCode(phone);
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
    public void onSuccess() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {
        //回到Home主页
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}
