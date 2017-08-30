package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.entity.UserEntity;
import com.gkzxhn.wisdom.entity.VersionEntity;

/**
 * Created by Raleigh.Luo on 17/8/2.
 */

public interface IPersonInforView extends IBaseView{
    public void update(UserEntity entity);
    public void updateVersion(VersionEntity entity);
}
