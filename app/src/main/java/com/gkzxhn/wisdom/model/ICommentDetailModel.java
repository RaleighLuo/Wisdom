package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public interface ICommentDetailModel extends IBaseModel {
    void requestReplayList(String commentId,int currentPage, int pageSize,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void publishComment(String commentId,String content,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void like(String commentId,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void delete(String commentId,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
