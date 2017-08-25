package com.gkzxhn.wisdom.view;

import com.gkzxhn.wisdom.entity.RoomEntity;

import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/17.
 */

public interface ILoginView extends IBaseView{
    public void onSuccess(List<RoomEntity> rooms);
    public void getCode(String code);
}
