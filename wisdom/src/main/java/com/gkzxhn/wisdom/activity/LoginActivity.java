package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.common.GKApplication;
import com.gkzxhn.wisdom.entity.RoomEntity;
import com.gkzxhn.wisdom.presenter.LoginPresenter;
import com.gkzxhn.wisdom.util.Utils;
import com.gkzxhn.wisdom.view.ILoginView;
import com.starlight.mobile.android.lib.util.CommonHelper;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

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

    /**
     * 初始化控件
     */
    private void initControls(){
        tvGetVerifyCode= (TextView) findViewById(R.id.login_layout_tv_get_verify_code);
        etPhone= (EditText) findViewById(R.id.login_layout_et_phone);
        etVerifyCode= (EditText) findViewById(R.id.login_layout_et_verfy_code);

    }

    /**
     * 初始化
     */
    private void init(){
        //初始化Presenter
        mPresenter=new LoginPresenter(this,this);
        mTimer=new CodeCountDownTimer(DOWN_TIME, 1000);
        String phone=getSharedPreferences(Constants.FINAL_TABLE,Context.MODE_PRIVATE).getString(Constants.USER_PHONE,"");
        etPhone.setText(phone.length()==0?"17303854825":phone);

        //初始化加载进度条
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        stopRefreshAnim();//停止show
    }

    /**控件点击事件，对应xml布局的onClick配置
     * @param view
     */
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.login_layout_btn_login://点击登录
                CommonHelper.clapseSoftInputMethod(this);//关闭软键盘
                String phone=etPhone.getText().toString().trim();
                String code=etVerifyCode.getText().toString().trim();
                if(phone.length()==0){//没有输入手机号码
                    showToast(getString(R.string.please_input)+getString(R.string.phone_number));
                }else if(!Utils.isPhoneNumber(phone)){//手机号码格式不正确
                    showToast(R.string.error_phone);
//                }else if(code.length()==0){TODO
//                    showToast(getString(R.string.please_input)+getString(R.string.verfy_code));
                }else{//登录
                    mPresenter.login(phone,code);
                }
                break;
            case R.id.login_layout_tv_get_verify_code://点击获取验证码
                phone=etPhone.getText().toString().trim();
                if(phone.length()==0){//没有输入手机号码
                    showToast(getString(R.string.please_input)+getString(R.string.phone_number));
                }else if(!Utils.isPhoneNumber(phone)){//手机号码格式不正确
                    showToast(R.string.error_phone);
                }else{//请求验证码
                    mPresenter.requstCode(phone);
                    //开启倒计时60秒
                    mTimer.start();
                    isTimerStop=false;
                    //获取
                    tvGetVerifyCode.setClickable(false);
                    tvGetVerifyCode.setTextColor(getResources().getColor(R.color.common_hint_text_color));
                }
                break;
        }

    }

    /**
     * 显示加载进度条
     */
    @Override
    public void startRefreshAnim() {
        if(mProgress!=null&&!mProgress.isShowing())mProgress.show();
    }

    /**
     * 停止加载进度条
     */
    @Override
    public void stopRefreshAnim() {
        if(mProgress!=null&&mProgress.isShowing())mProgress.dismiss();
    }


    /**
     * 登录成功回调
     */
    @Override
    public void onSuccess(int roomSize) {

        CommonHelper.clapseSoftInputMethod(this);
        if (roomSize == 0) {
            showToast(R.string.has_not_house);
        } else if (roomSize == 1) {//一套房屋
            GKApplication.getInstance().setJpushTagsAndAlias();//设置极光推送的标签和别名
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {//多套房屋
            Intent intent = new Intent(this, ChangeCommunityActivity.class);
            intent.putExtra(Constants.EXTRA_TAB, Constants.LOGIN_TAB);
            startActivityForResult(intent, Constants.EXTRA_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.EXTRA_CODE){
            if(resultCode==RESULT_OK){
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }else{
                mPresenter.getSharedPreferences().edit().clear().commit();
            }
        }
    }

    @Override
    public void getCode(String code) {
        etVerifyCode.setText(code);
    }

    /**
     * 用户点击返回建
     */
    @Override
    public void onBackPressed() {
        CommonHelper.clapseSoftInputMethod(this);//关闭虚拟键盘
        //回到Home主页
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        stopRefreshAnim();//关闭Dialog,防止窗口溢出
        if(mPresenter!=null)mPresenter.onDestory();//释放presenter资源
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
            //获取验证码按钮重置 设置可点击状态
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
