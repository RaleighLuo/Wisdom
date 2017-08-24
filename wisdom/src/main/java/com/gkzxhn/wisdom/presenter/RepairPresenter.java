package com.gkzxhn.wisdom.presenter;

import android.content.Context;

import com.gkzxhn.wisdom.async.VolleyUtils;
import com.gkzxhn.wisdom.entity.RepairEntity;
import com.gkzxhn.wisdom.view.ICommonListView;
import com.gkzxhn.wisdom.view.IRepairView;

import org.json.JSONObject;

/**
 * Created by Raleigh.Luo on 17/8/24.
 */

public class RepairPresenter extends CommonListPresenter<RepairEntity> {
    public RepairPresenter(Context context, IRepairView view, int TAB) {
        super(context, view, TAB);
    }
    public void cancel(String id,int position) {

    }

    public void delete(String id,int position) {

    }
}
