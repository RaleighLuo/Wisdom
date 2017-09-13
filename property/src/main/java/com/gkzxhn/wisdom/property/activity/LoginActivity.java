package com.gkzxhn.wisdom.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.gkzxhn.wisdom.property.R;


/**
 * Created by Raleigh.Luo on 17/9/1.
 */

public class LoginActivity extends SuperActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }
    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.login_layout_btn_login_in://登录
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
