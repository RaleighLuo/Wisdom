package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.graphics.Rect;
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
        return 40;
    }

    @Override
    public void bindView(View view, int index) {

    }
    @Override
    public Rect obtainDraggableArea(View view) {
        // 可滑动区域定制，该函数只会调用一次
        View contentView = view.findViewById(R.id.card_item_content);
        View topLayout = view.findViewById(R.id.card_top_layout);
        View bottomLayout = view.findViewById(R.id.card_bottom_layout);
        int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
        int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
        int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
        int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
        return new Rect(left, top, right, bottom);
    }
}
