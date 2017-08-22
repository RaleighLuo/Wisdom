package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public interface IPublishModel extends IBaseModel {
    /**发布报修
     * @param repairType
     * @param content
     * @param imagUrls
     * @param onFinishedListener
     */
    public void publishRepair(String repairType,String content, List<String> imagUrls, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);

    /**发布话题
     * @param content
     * @param imagUrls
     * @param onFinishedListener
     */
    public void publishTopic(String content, List<String> imagUrls, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
