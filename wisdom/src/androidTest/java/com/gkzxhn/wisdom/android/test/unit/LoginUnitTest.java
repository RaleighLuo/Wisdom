package com.gkzxhn.wisdom.android.test.unit;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.LoginActivity;
import com.gkzxhn.wisdom.android.test.TestConstants;
import com.gkzxhn.wisdom.android.test.util.Utils;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Raleigh.Luo on 17/8/28.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginUnitTest {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void before(){
        //监听错误日志
        Espresso.setFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable error, Matcher<View> viewMatcher) {
                //将日志写到SD卡里面
                ReportUtil.getInstance().saveInfo2File(error);
                error.printStackTrace();
            }
        });
    }

    @Test
    public void login() throws Exception {
        Utils.inputTextById(R.id.login_layout_et_phone,"17303854825");
        Thread.sleep(TestConstants.WAIT_DELAY);
        Utils.clickById(R.id.login_layout_tv_get_verify_code);
        Thread.sleep(TestConstants.WAIT_DELAY);
        Utils.clickById(R.id.login_layout_btn_login);

    }
}
