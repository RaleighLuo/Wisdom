package com.gkzxhn.wisdom.model.impl;

import com.gkzxhn.wisdom.async.VolleyUtils;
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

    @Override
    public void request(int currentPage, int pageSize,VolleyUtils.OnFinishedListener<JSONObject> onFinishedListener) {

    }
}
