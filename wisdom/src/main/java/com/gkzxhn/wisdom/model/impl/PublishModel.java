package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.model.IPublishModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/4.
 */

public class PublishModel extends BaseModel implements IPublishModel {
    /**发布报修
     * @param repairType 报修类型
     * @param content 报修内容
     * @param imagUrls 报修图片
     * @param onFinishedListener 请求回调
     */
    @Override
    public void publishRepair(String repairType, String content, List<String> imagUrls, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            JSONObject params=new JSONObject();
            params.put("content",content);
            params.put("title",content);
            params.put("repair_type",repairType);
            if(imagUrls!=null&&imagUrls.size()>0){
                JSONArray imageArray=new JSONArray();
                for (String url:imagUrls){
                    imageArray.put(url);
                }
                params.put("images",imageArray);
            }
            String url=String.format("%s/%s/repairs",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""));
            volleyUtils.post(url,new JSONObject().put("repair",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }

    /**话题发布
     * @param content 话题内容
     * @param imageUrls 话题图片
     * @param onFinishedListener 请求回调
     */
    @Override
    public void publishTopic(String content, List<String> imageUrls, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            JSONObject params=new JSONObject();
            params.put("content",content);
            params.put("title",content);
            if(imageUrls!=null&&imageUrls.size()>0){
                JSONArray imageArray=new JSONArray();
                for (String url:imageUrls){
                    imageArray.put(url);
                }
                params.put("images",imageArray);
            }
            String url=String.format("%s/%s/topics",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""));
            volleyUtils.post(url,new JSONObject().put("topic",params),REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
