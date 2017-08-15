package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gkzxhn.wisdom.activity.OnlinePhotoPreviewActivity;
import com.gkzxhn.wisdom.common.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh.Luo on 17/8/11.
 */

public class RepairDetailAdapter extends PagerAdapter {
    private Context context;
    private List<String> list=new ArrayList<String>();
    public RepairDetailAdapter(Context context, List<String> list){
        this.context = context;
        this.list.addAll(list);//不要用赋值，否则就是同一个变量了
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
    public View instantiateItem(ViewGroup container, final int position) {
        ImageView photoView =new ImageView(context);
        try{
            photoView.setLayoutParams(new ViewPager.LayoutParams());
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context ,OnlinePhotoPreviewActivity.class);
                    intent.putExtra(Constants.EXTRAS, (Serializable) list);
                    intent.putExtra(Constants.EXTRA_POSITION, position);
                    context.startActivity(intent);
                }
            });
            ImageLoader.getInstance().displayImage(list.get(position),photoView);
            ((ViewPager) container).addView(photoView);
        }catch(Exception e){
            e.printStackTrace();
        }
        return photoView;
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
