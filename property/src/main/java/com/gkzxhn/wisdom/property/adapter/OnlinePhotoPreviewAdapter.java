package com.gkzxhn.wisdom.property.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.gkzxhn.wisdom.property.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.starlight.mobile.android.lib.view.photoview.PhotoView;
import com.starlight.mobile.android.lib.view.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/7.
 */

public class OnlinePhotoPreviewAdapter extends PagerAdapter {
    private Context context;
    private List<String> list=new ArrayList<String>();
    private PhotoViewAttacher.OnShortTouchListener onShortTouchListener;
    public OnlinePhotoPreviewAdapter(Context context, List<String> list, PhotoViewAttacher.OnShortTouchListener onShortTouchListener){
        this.context = context;
        this.list.addAll(list);//不要用赋值，否则就是同一个变量了
        this.onShortTouchListener=onShortTouchListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    public List<String> getList() {
        return list;
    }
    public String getItem(int position){
        return list.get(position);
    }

    public boolean remove(int position){
        boolean result=false;
        if(list!=null&&position<list.size()){
            list.remove(position);
            result=true;
            //直接使用notifyDataSetChanged是无法更新，需要同时重写getItemPosition返回常量 POSITION_NONE (此常量为viewpager带的)。
            notifyDataSetChanged();
        }
        return result;
    }
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view= View.inflate(context, R.layout.album_preview_item_layout, null);
        try{
            PhotoView photoView =(PhotoView) view.findViewById(R.id.preview_item_layout_pv_image);
            ImageLoader.getInstance().displayImage(list.get(position),photoView);
            photoView.setOnShortTouchListener(onShortTouchListener);
            ((ViewPager) container).addView(view);
        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}