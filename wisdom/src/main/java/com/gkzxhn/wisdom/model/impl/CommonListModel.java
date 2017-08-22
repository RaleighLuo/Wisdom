package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.common.Constants;
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
                case Constants.REPAIR_LIST_TAB:
                    url=String.format("%s/%s/repairs?page=%s&limit=%s",Constants.REQUEST_BASE_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""),
                            currentPage,pageSize);
                    break;
            }
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
