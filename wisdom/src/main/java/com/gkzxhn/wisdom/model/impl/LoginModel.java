package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.ILoginModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.name;
import static android.R.attr.password;

/**
 * Created by Raleigh.Luo on 17/7/17.
 */

public class LoginModel extends BaseModel implements ILoginModel {
    /**获取验证码
     * @param phone
     * @param onFinishedListener
     */
    @Override
    public void requestCode(String phone, VolleyUtils.OnFinishedListener<String> onFinishedListener) {
        try {
            Map<String,String> params=new HashMap<>();
            params.put("phone",phone);
            volleyUtils.post(Constants.REQUEST_VERIFY_CODE_URL,params,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**登录
     * @param phone
     * @param verifyCode
     * @param onFinishedListener
     */
    @Override
    public void login(String phone, String verifyCode, VolleyUtils.OnFinishedListener<String> onFinishedListener) {
        try {
            Map<String,String> params=new HashMap<>();
            params.put("phone",phone);
            params.put("code",verifyCode);
            params.put("user_type","owner");
            volleyUtils.post(Constants.REQUEST_LOGIN_URL,params,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
