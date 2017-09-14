package com.gkzxhn.wisdom.property.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.gkzxhn.wisdom.property.R;
import com.gkzxhn.wisdom.property.common.Constants;


/**
 * Created by Raleigh.Luo on 17/9/1.
 */

public class LoginActivity extends SuperActivity {
    private RadioGroup mRadioGroup;
    private int mRole= Constants.REPAIRMANE_ROLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initControls();
        init();
    }
    private void initControls(){
        mRadioGroup= (RadioGroup) findViewById(R.id.login_layout_rg_role);
    }
    private void init(){
        mRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
    }
    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            mRole=checkedId==R.id.login_layout_rb_manager_role?Constants.MANAGER_ROLE:Constants.REPAIRMANE_ROLE;
        }
    };
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.login_layout_btn_login_in://登录
                //保存角色
                getSharedPreferences(Constants.USER_TABLE, Context.MODE_PRIVATE).edit().
                        putInt(Constants.USER_ROLE,mRole).commit();
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            case R.id.login_layout_tv_get_verify_code://获取验证码
                break;

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//点击返回键，返回到主页
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);

        }
        return false;
    }

}
