package com.gkzxhn.wisdom.view;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/7/20.
 */

public interface ICommonListView<T> extends IBaseView {
    public void updateItems(List<T> datas);//更新
    public void loadItems(List<T> datas);//加载
}
