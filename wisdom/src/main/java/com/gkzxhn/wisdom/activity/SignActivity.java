package com.gkzxhn.wisdom.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.presenter.SignPresenter;
import com.gkzxhn.wisdom.view.ISignView;
import com.starlight.mobile.android.lib.util.ConvertUtil;

import java.util.Calendar;

/**
 * Created by Raleigh.Luo on 17/7/14.
 */

public class SignActivity extends SuperActivity implements ISignView{
    private SignPresenter mPresenter;
    private ProgressDialog mProgress;
    private TextView tvSignHint,tvSeriesDay;
    private int mDistanceDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_layout);
        initControls();
        init();
    }

    /**
     * 初始化控件
     */
    private void initControls(){
        tvSignHint= (TextView) findViewById(R.id.sign_layout_tv_sign);
        tvSeriesDay= (TextView) findViewById(R.id.sign_layout_tv_series_sign_day);

    }

    /**
     * 初始化
     */
    private void init(){
        //初始化presenter
        mPresenter=new SignPresenter(this,this);
        mProgress = ProgressDialog.show(this, null, getString(R.string.please_waiting));
        stopRefreshAnim();
        long lastDay=mPresenter.getSharedPreferences().getLong(Constants.LAST_SERIES_SIGN_DATE,0);
        if(lastDay>0){
            int disDay=ConvertUtil.compareWithCurDate(lastDay);//相差的天数
            mDistanceDay=disDay>1?0:mPresenter.getSharedPreferences().getInt(Constants.SERIES_SIGN_DAY,0);
            tvSeriesDay.setText(String.valueOf(mDistanceDay));
        }else{     //从未签到过
            tvSeriesDay.setText("0");
        }
        boolean isSign=mPresenter.getSharedPreferences().getBoolean(Constants.TODAY_IS_SIGN,false);
        if(isSign) {
            tvSignHint.setText(R.string.today_has_sign);
            tvSignHint.setEnabled(false);
            findViewById(R.id.sign_layout_iv_sign).setEnabled(false);
        }
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.common_head_layout_tv_right:
                finish();
                break;
            case R.id.sign_layout_iv_sign://签到
                mPresenter.sign();
                break;
            case R.id.sign_layout_tv_sign:
                findViewById(R.id.sign_layout_iv_sign).performClick();
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
        showToast(R.string.sign_success);
        tvSignHint.setText(R.string.today_has_sign);
        tvSignHint.setEnabled(false);
        findViewById(R.id.sign_layout_iv_sign).setEnabled(false);
        SharedPreferences.Editor editor=mPresenter.getSharedPreferences().edit();
        editor.putBoolean(Constants.TODAY_IS_SIGN,true);
        editor.putInt(Constants.SERIES_SIGN_DAY,mDistanceDay+1);
        editor.putLong(Constants.LAST_SERIES_SIGN_DATE, Calendar.getInstance().getTimeInMillis());
        editor.commit();
    }
    @Override
    protected void onDestroy() {
        mPresenter.onDestory();
        super.onDestroy();
    }
}
