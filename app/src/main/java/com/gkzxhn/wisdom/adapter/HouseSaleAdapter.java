package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.customview.cardslide.CardAdapter;

/**
 * Created by Raleigh.Luo on 17/7/21.
 */

public class HouseSaleAdapter  extends CardAdapter{
    private Context context;

    public HouseSaleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.house_sale_item_layout;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public void bindView(View view, int index) {

    }
}
