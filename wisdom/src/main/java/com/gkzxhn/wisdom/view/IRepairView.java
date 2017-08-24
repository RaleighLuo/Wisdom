package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.entity.RepairEntity;

/**
 * Created by Raleigh.Luo on 17/8/24.
 */

public interface IRepairView extends ICommonListView<RepairEntity>{
    public void deleteSuccess(int position);
    public void cancelSuccess(int position);
}
