package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.IPublishTopicModel;

import org.json.JSONArray;
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
            if(imageUrls!=null&&imageUrls.size()>0){
                JSONArray imageArray=new JSONArray();
                for (String url:imageUrls){
                    JSONObject json=new JSONObject();
                    json.put("image_url",url);
                    imageArray.put(json);
                }
                params.put("topic_images_attributes",imageArray);
            }
            String url=String.format("%s/%s/topics",Constants.REQUEST_PUBLISH_TOPIC_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""));
            volleyUtils.post(url,params,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
