package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.entity.RepairEntity;
import com.gkzxhn.wisdom.model.ICommonListModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public class CommonListModel extends BaseModel implements ICommonListModel{
    protected final int TAB;

    public CommonListModel(int TAB) {
        this.TAB = TAB;
    }

    public int getTAB() {
        return TAB;
    }

    /**请求
     * @param currentPage 当前页面
     * @param pageSize 页数
     * @param onFinishedListener 请求回调监听器
     */
    @Override
    public void request(int currentPage, int pageSize,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {
        try {
            String url="";
            switch (TAB){
                case Constants.TOPIC_LIST_TAB:
                    url=String.format("%s/%s/topics?page=%s&limit=%s",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""),
                            currentPage,pageSize);
                    break;
                case Constants.OWN_TOPIC_LIST_TAB:
                    url=String.format("%s/%s/topics?post_user=true&page=%s&limit=%s",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""),
                            currentPage,pageSize);
                    break;
                case Constants.COMMUNITY_REPAIR_PROGRESSING_TAB://社区报修－正在进行
                    url=String.format("%s/%s/repairs?page=%s&limit=%s",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""),
                            currentPage,pageSize);
                    break;
                case Constants.COMMUNITY_REPAIR_FINISHED_TAB://社区报修－已完成
                    url=String.format("%s/%s/repairs?status=%s&page=%s&limit=%s",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""),
                            RepairEntity.STATUS_FINISHED,currentPage,pageSize);
                    break;
                case Constants.REPAIR_PROGRESSING_TAB://我的报修－正在进行
                    url=String.format("%s/%s/repairs?post_user=true&page=%s&limit=%s",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""),
                            currentPage,pageSize);
                    break;
                case Constants.REPAIR_FINISHED_TAB://我的报修－已完成
                    url=String.format("%s/%s/repairs?status=%s&post_user=true&page=%s&limit=%s",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""),
                            RepairEntity.STATUS_FINISHED,currentPage,pageSize);
                    break;
            }
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
