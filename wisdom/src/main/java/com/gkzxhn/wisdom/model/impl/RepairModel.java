package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.model.IRepairModel;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/24.
 */

public class RepairModel extends CommonListModel implements IRepairModel {
    public RepairModel(int TAB) {
        super(TAB);
    }

    @Override
    public void cancel(String id, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {

    }

    @Override
    public void delete(String id, VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {

    }
}
