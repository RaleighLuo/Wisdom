package com.gkzxhn.wisdom.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gkzxhn.wisdom.R;
import com.starlight.mobile.android.lib.album.AlbumImageLoader;
import com.starlight.mobile.android.lib.view.photoview.PhotoView;
import com.starlight.mobile.android.lib.view.photoview.PhotoViewAttacher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raleigh on 15/9/18.
 */
public class TakePhotoPreviewAdapter extends PagerAdapter {
    private Context context;
    private AlbumImageLoader mAlbumImageLoader;
    private List<String> list=new ArrayList<String>();
    private PhotoViewAttacher.OnShortTouchListener onShortTouchListener;
    public TakePhotoPreviewAdapter(Context context, List<String> list, PhotoViewAttacher.OnShortTouchListener onShortTouchListener){
        this.context = context;
        this.list.addAll(list);//不要用赋值，否则就是同一个变量了
        this.onShortTouchListener=onShortTouchListener;
        mAlbumImageLoader=new AlbumImageLoader(3, AlbumImageLoader.Type.LIFO);
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
            ProgressBar progress=(ProgressBar) view.findViewById(R.id.preview_item_layout_progress);
            String path=list.get(position);
            File file=new File(path);
            if(file!=null&&file.exists())
                mAlbumImageLoader.loadImage(path, photoView);
            photoView.setOnShortTouchListener(onShortTouchListener);
            ((ViewPager) container).addView(view);
        }catch(Exception e){
            e.printStackTrace();
        }
        return view;
    }
    public void onDestory(){
        mAlbumImageLoader.onDestory();
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
