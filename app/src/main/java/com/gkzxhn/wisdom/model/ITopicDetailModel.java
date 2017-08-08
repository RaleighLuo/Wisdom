package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public interface ITopicDetailModel extends IBaseModel  {
    public void request(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    public void publishComments(String content,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    public void requestComments(int currentPage, int pageSize,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    public void delete(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    public void deleteComment(String commentId,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

}
