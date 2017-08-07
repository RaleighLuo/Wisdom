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
                    url=String.format("%s/%s/topics",Constants.REQUEST_TOPIC_URL,getSharedPreferences().getString(Constants.USER_RESIDENTIALAREASID,""));
                    break;
            }
            volleyUtils.get(JSONObject.class,url,REQUEST_TAG,onFinishedListener);
        } catch (Exception authFailureError) {
            authFailureError.printStackTrace();
        }
    }
}
