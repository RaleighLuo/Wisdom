package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.presenter.LoginPresenter;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.ILoginView;
import com.starlight.mobile.android.lib.util.CommonHelper;

/**
 * Created by Raleigh.Luo on 17/7/14.
 */

public class LoginActivity extends SuperActivity implements ILoginView{
    private EditText etPhone,etVerifyCode;
    private LoginPresenter mPresenter;
    private ProgressDialog mProgress;
    private TextView tvGetVerifyCode;
    private final long DOWN_TIME=60000;//倒计时 60秒
    private CodeCountDownTimer mTimer;//倒计时器
    private boolean isTimerStop=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initControls();
        init();
    }
    private void initControls(){
        tvGetVerifyCode= (TextView) findViewById(R.id.login_layout_tv_get_verify_code);
        etPhone= (EditText) findViewById(R.id.login_layout_et_phone);
        etVerifyCode= (EditText) findViewById(R.id.login_layout_et_verfy_code);

    }
    private void init(){
        mTimer=new CodeCountDownTimer(DOWN_TIME, 1000);
        etPhone.setText("17303854825");
        mPresenter=new LoginPresenter(this,this);
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        stopRefreshAnim();
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.login_layout_btn_login://登录
                CommonHelper.clapseSoftInputMethod(this);
                String phone=etPhone.getText().toString().trim();
                String code=etVerifyCode.getText().toString().trim();
                if(phone.length()==0){
                    showToast(getString(R.string.please_input)+getString(R.string.phone_number));
                }else if(!Utils.isPhoneNumber(phone)){
                    showToast(R.string.error_phone);
//                }else if(code.length()==0){TODO
//                    showToast(getString(R.string.please_input)+getString(R.string.verfy_code));
                }else{
                    mPresenter.login(phone,code);
                }
                break;
            case R.id.login_layout_tv_get_verify_code://获取验证码
                phone=etPhone.getText().toString().trim();
                if(phone.length()==0){
                    showToast(getString(R.string.please_input)+getString(R.string.phone_number));
                }else if(!Utils.isPhoneNumber(phone)){
                    showToast(R.string.error_phone);
                }else{
                    mPresenter.requstCode(phone);
                    mTimer.start();
                    isTimerStop=false;
                    tvGetVerifyCode.setClickable(false);
                    tvGetVerifyCode.setTextColor(getResources().getColor(R.color.common_hint_text_color));
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
        CommonHelper.clapseSoftInputMethod(this);
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {
        CommonHelper.clapseSoftInputMethod(this);
        //回到Home主页
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        stopRefreshAnim();
        if(mPresenter!=null)mPresenter.onDestory();
        super.onDestroy();
    }

    /**
     * 继承 CountDownTimer 防范
     *
     * 重写 父类的方法 onTick() 、 onFinish()
     */

    class CodeCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public CodeCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {//倒计时完成
            isTimerStop=true;
            tvGetVerifyCode.setClickable(true);
            tvGetVerifyCode.setText(R.string.reset_send);
            tvGetVerifyCode.setTextColor(getColor(android.R.color.white));
        }

        @Override
        public void onTick(long millisUntilFinished) {//倒计时过程
            try {
                long second = millisUntilFinished / 1000;
                tvGetVerifyCode.setText(String.format("%s(%ss)", getString(R.string.reset_send), second));
            }catch (Exception e){}
        }
    }
}
