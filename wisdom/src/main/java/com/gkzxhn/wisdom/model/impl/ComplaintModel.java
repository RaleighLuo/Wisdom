package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.ICompaintModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class ComplaintModel extends BaseModel implements ICompaintModel {
    @Override
    public void publish(String content, VolleyUtils.OnFinishedListener<String> onFinishedListener) {
        try {
//            Map<String,String> params=new HashMap<>();
//            params.put("content",content);
//            volleyUtils.post(Constants.REQUEST_VERIFY_CODE_URL,params,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
