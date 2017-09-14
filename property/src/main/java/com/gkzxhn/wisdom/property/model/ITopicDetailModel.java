package com.gkzxhn.wisdom.property.model;


import com.gkzxhn.wisdom.property.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public interface ITopicDetailModel extends IBaseModel  {
    /**请求话题详情
     * @param onFinishedListener
     */
    public void request(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

    /**发布评论
     * @param content
     * @param onFinishedListener
     */
    public void publishComment(String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    /**发布回复
     * @param content
     * @param onFinishedListener
     */
    public void publishReplay(String commentId, String content, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

    /**请求评论列表
     * @param currentPage
     * @param pageSize
     * @param onFinishedListener
     */
    public void requestComments(int currentPage, int pageSize, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

    /**删除话题
     * @param onFinishedListener
     */
    public void delete(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

    /**删除评论
     * @param commentId
     * @param onFinishedListener
     */
    public void deleteComment(String commentId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

    /**话题点赞
     * @param onFinishedListener
     */
    public void requestLike(VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);//话题点赞

    /**评论点赞
     * @param onFinishedListener
     */
    public void requestCommentLike(String commentId, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);//话题点赞

}
