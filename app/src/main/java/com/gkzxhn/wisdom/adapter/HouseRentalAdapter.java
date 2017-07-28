package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.HouseLeaseDetailActivity;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.cardslide.CardAdapter;

/**
 * Created by Raleigh.Luo on 17/7/21.
 */

public class HouseRentalAdapter extends CardAdapter{
    private Context context;
    private int TAB;
    public HouseRentalAdapter(Context context, int TAB) {
        this.context = context;
        this.TAB=TAB;
    }

    @Override
    public int getLayoutId() {
        return R.layout.house_rental_item_layout;
    }

    @Override
    public int getCount() {
        return 40;
    }

    @Override
    public void bindView(View view, int index) {
        Object tag = view.getTag();
        ViewHolder viewHolder;
        if (null != tag) {
            viewHolder = (ViewHolder) tag;
        } else {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.maskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, HouseLeaseDetailActivity.class);
                intent.putExtra(Constants.EXTRA_TAB,TAB);
                context.startActivity(intent);
            }
        });
        viewHolder.tvAddress.setText(TAB==Constants.HOUSE_LEASE_TAB?"伍家岭南":"梅溪湖");
        viewHolder.tvTitle.setText(TAB==Constants.HOUSE_LEASE_TAB?"海威大夏 2室2厅1卫":"达美D6 3室2厅1卫");
        viewHolder.tvMoney.setText(TAB==Constants.HOUSE_LEASE_TAB?"500元/月":"86万");
        viewHolder.ivImage.setImageResource(TAB==Constants.HOUSE_LEASE_TAB?R.mipmap.lease_image:R.mipmap.sale_image);
    }
    @Override
    public Rect obtainDraggableArea(View view) {
        // 可滑动区域定制，该函数只会调用一次
        View contentView = view.findViewById(R.id.house_rental_item_layout_ll_root);
        View topLayout = view.findViewById(R.id.house_rental_item_layout_rl_top);
        View bottomLayout = view.findViewById(R.id.house_rental_item_layout_rl_bottom);
        int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
        int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
        int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
        int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
        return new Rect(left, top, right, bottom);
    }

    class ViewHolder {

        ImageView ivImage;
        View maskView;
        TextView tvImageNumber,tvMoney,tvTitle,tvAddress;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.house_rental_item_layout_tv_title);
            tvAddress = (TextView) view.findViewById(R.id.house_rental_item_layout_tv_address);

            tvMoney = (TextView) view.findViewById(R.id.house_rental_item_layout_tv_money);
            ivImage = (ImageView) view.findViewById(R.id.house_rental_item_layout_iv_image);
            maskView = view.findViewById(R.id.house_rental_item_layout_v_maskView);
            tvImageNumber = (TextView) view.findViewById(R.id.house_rental_item_layout_tv_image_number);
        }
    }
}
