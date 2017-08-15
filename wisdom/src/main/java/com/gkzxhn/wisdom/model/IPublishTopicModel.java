package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public interface IPublishTopicModel extends IBaseModel {
    public void publish(String content, List<String> imagUrls, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
