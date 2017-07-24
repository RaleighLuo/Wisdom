package com.gkzxhn.wisdom.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.adapter.HouseSaleAdapter;
import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.customview.cardslide.CardSlidePanel;
import com.starlight.mobile.android.lib.view.CusSwipeRefreshLayout;

/**
 * Created by Raleigh.Luo on 17/7/21.
 */

public class HouseSaleFragment extends Fragment {
    private View parentView;
    private Context mActivity;
    private CardSlidePanel mCardSlidePanel;
    private HouseSaleAdapter adapter;
    private int TAB;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.mActivity=activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.house_sale_fragment_layout, container,false);
        initControls();
        init();
        return parentView;
    }
    private void initControls(){
        mCardSlidePanel= (CardSlidePanel) parentView.findViewById(R.id.house_sale_fragment_layout_image_slide_panel);
    }
    private void init(){
        TAB=getArguments().getInt(Constants.EXTRA_TAB);
        adapter=new HouseSaleAdapter(mActivity,TAB);
        mCardSlidePanel.setAdapter(adapter);
        mCardSlidePanel.setCardSwitchListener(mCardSwitchListener);
    }

    CardSlidePanel.CardSwitchListener mCardSwitchListener = new CardSlidePanel.CardSwitchListener() {

        @Override
        public void onShow(int index) {
//            Log.d("Card", "正在显示-" + dataList.get(index).userName);
        }

        @Override
        public void onCardVanish(int index, int type) {
//            Log.d("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
        }
    };
}
