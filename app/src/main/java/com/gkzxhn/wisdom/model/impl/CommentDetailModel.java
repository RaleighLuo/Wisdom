package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.ICommentDetailModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/10.
 */

public class CommentDetailModel extends BaseModel implements ICommentDetailModel {

    @Override
    public void requestReplayList(String commentId, int currentPage, int pageSize, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments?page=%s&limit=%s", Constants.REQUEST_TOPIC_OPERATE_URL, commentId,currentPage,pageSize);
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void publishComment(String commentId, String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments", Constants.REQUEST_TOPIC_OPERATE_URL, commentId);
            JSONObject params=new JSONObject();
            params.put("content",content);
            volleyUtils.post(url,new JSONObject().put("comment",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void like(String commentId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
//        try{
//            String url=String.format("%s/%s/topics/%s/comments/%s/likes", Constants.REQUEST_TOPIC_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
//                    topicId,commentId);
//            volleyUtils.post(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
//        } catch (Exception authFailureError) {
//            authFailureError.printStackTrace();
//        }
    }

    @Override
    public void delete(String commentId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
//        try{
//            String url=String.format("%s/%s/comments/%s", Constants.REQUEST_TOPIC_OPERATE_URL,topicId, commentId);
//            volleyUtils.delete(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
//        } catch (Exception authFailureError) {
//            authFailureError.printStackTrace();
//        }
    }
}
