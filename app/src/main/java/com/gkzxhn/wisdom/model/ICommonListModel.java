package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public interface ICommonListModel extends IBaseModel {
    public int getTAB();
    public void request(int currentPage, int pageSize,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
