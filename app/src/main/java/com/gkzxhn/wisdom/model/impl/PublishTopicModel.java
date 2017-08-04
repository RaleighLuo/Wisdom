package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.IPublishTopicModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public class PublishTopicModel extends BaseModel implements IPublishTopicModel {
    @Override
    public void publish(String content, List<String> imageUrls, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            JSONObject params=new JSONObject();
            params.put("content",content);
            if(imageUrls!=null&&imageUrls.size()>0)params.put("topic_images_attributes",imageUrls);
            volleyUtils.post(Constants.REQUEST_VERIFY_CODE_URL,params,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
