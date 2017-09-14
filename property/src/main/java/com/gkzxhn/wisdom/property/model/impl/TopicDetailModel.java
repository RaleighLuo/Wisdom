package com.gkzxhn.wisdom.property.model.impl;


import com.gkzxhn.wisdom.property.async.VolleyUtils;
import com.gkzxhn.wisdom.property.common.Constants;
import com.gkzxhn.wisdom.property.model.ITopicDetailModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class TopicDetailModel extends BaseModel implements ITopicDetailModel {
    private String topicId=null;
    public TopicDetailModel(String topicId) {
        this.topicId = topicId;
    }

    public TopicDetailModel() {
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }


    /**请求话题详情
     * @param onFinishedListener
     */
    @Override
    public void request(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/topics/%s", Constants.REQUEST_BASE_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
                    topicId);
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**发布评论
     * @param content
     * @param onFinishedListener
     */
    @Override
    public void publishComment(String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments", Constants.REQUEST_TOPIC_OPERATE_URL, topicId);
            JSONObject params=new JSONObject();
            params.put("content",content);
            volleyUtils.post(url,new JSONObject().put("comment",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**发布回复
     * @param commentId
     * @param content
     * @param onFinishedListener
     */
    @Override
    public void publishReplay(String commentId,String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments/%s/subcomments", Constants.REQUEST_TOPIC_OPERATE_URL, topicId,commentId);
            JSONObject params=new JSONObject();
            params.put("content",content);
            volleyUtils.post(url,new JSONObject().put("comment",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    @Override
    public void requestComments(int currentPage, int pageSize, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments", Constants.REQUEST_TOPIC_OPERATE_URL, topicId);
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**删除话题
     * @param onFinishedListener
     */
    @Override
    public void delete( VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/topics/%s", Constants.REQUEST_BASE_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
                    topicId);
            volleyUtils.delete(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**删除评论
     * @param commentId
     * @param onFinishedListener
     */
    @Override
    public void deleteComment(String commentId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/comments/%s", Constants.REQUEST_TOPIC_OPERATE_URL,topicId, commentId);
            volleyUtils.delete(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**请求话题点赞／取消点赞
     * @param onFinishedListener
     */
    @Override
    public void requestLike(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/topics/%s/likes", Constants.REQUEST_BASE_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
                    topicId);
            volleyUtils.post(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**请求评论点赞／取消点赞
     * @param commentId
     * @param onFinishedListener
     */
    @Override
    public void requestCommentLike(String commentId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try{
            String url=String.format("%s/%s/topics/%s/comments/%s/likes", Constants.REQUEST_BASE_URL,preferences.getString(Constants.USER_RESIDENTIALAREASID,""),
                    topicId,commentId);
            volleyUtils.post(url,new JSONObject(),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }


}
