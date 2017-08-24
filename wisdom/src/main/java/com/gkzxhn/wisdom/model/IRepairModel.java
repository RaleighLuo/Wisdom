package com.gkzxhn.wisdom.model;

import com.gkzxhn.wisdom.async.VolleyUtils;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/24.
 */

public interface IRepairModel extends ICommonListModel{
    void cancel(String id,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
    void delete(String id,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener);
}
