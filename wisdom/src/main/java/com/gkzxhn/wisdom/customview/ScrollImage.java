package com.gkzxhn.wisdom.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gkzxhn.wisdom.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 加载流动广告的自定义控件
 * @author 罗利
 *
 */

public class ScrollImage extends RelativeLayout {
	//存放流动广告图片的自定义控件
	private ImageScrollView imageScrollView = null;
	private PageControlView pageControlView = null;

	/**
	 * 加载布局，得到控件
	 * @param context  上下文
	 * @param attrs  属性值
	 */
	public ScrollImage(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.image_sroll_view_layout, ScrollImage.this);

		imageScrollView = (ImageScrollView) this.findViewById(R.id.imageScrollView);
		pageControlView = (PageControlView) this.findViewById(R.id.myPageControlView);
	}
	public void setPageControlViewGrivate(int grivate){
		pageControlView.setGravity(grivate);
	}
	public void hidePageControlView(){
		pageControlView.setVisibility(View.GONE);
	}

	/**
	 * 设置流动广告图片的地址，并加载图片到界面上
	 * @param adImageUris 流动广告的图片的地址
	 */
	public void setImageUriList(List<String> adImageUris){
		int num = adImageUris.size();
		imageScrollView.removeAllViews();
		for(int i = 0; i < num; i++){
			ImageView imageView = new ImageView(getContext());
			//设置高宽
			imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			//下载图片 TODO
//			ImageLoader.getInstance().displayImage(Utils.getImageUrl(adImageUris.get(i)), imageView, Utils.getOptions(R.mipmap.noimage));
			//将流动广告添加到控件组中
			imageScrollView.addView(imageView);
		}

		pageControlView.setCount(imageScrollView.getChildCount());
//
		pageControlView.generatePageControl(0);

		imageScrollView.setScrollToScreenCallback(mScrollToScreenCallback==null?pageControlView:mScrollToScreenCallback);

	}
	/**
	 * 设置流动广告图片的地址，并加载图片到界面上
	 * @param adImageUris 流动广告的图片的地址
	 */
	public void setImageUriList(List<String> adImageUris, int defaultRes){
		int num = adImageUris.size();
		imageScrollView.removeAllViews();
		for(int i = 0; i < num; i++){
			ImageView imageView = new ImageView(getContext());
			//设置高宽
			imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.FILL_PARENT));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			//下载图片TODO
//			ImageLoader.getInstance().displayImage(Utils.getImageUrl(adImageUris.get(i)), imageView, Utils.getOptions(defaultRes));
			//将流动广告添加到控件组中
			imageScrollView.addView(imageView);
		}

		pageControlView.setCount(imageScrollView.getChildCount());
//
		pageControlView.generatePageControl(0);

		imageScrollView.setScrollToScreenCallback(mScrollToScreenCallback==null?pageControlView:mScrollToScreenCallback);
	}
	private ImageScrollView.ScrollToScreenCallback mScrollToScreenCallback;
	public void setScrollToScreenCallback(ImageScrollView.ScrollToScreenCallback callback){
		this.mScrollToScreenCallback=callback;
	}
	/**
	 * 设置图片的ID,显示图片
	 * @param imageId 图片ID
	 */
	public void setImageId(int imageId){

		imageScrollView.removeAllViews();

		ImageView imageView = new ImageView(getContext());
		//设置高宽
		imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);

		//设置图片的Id
		imageView.setImageResource(imageId);

		imageScrollView.addView(imageView);


		pageControlView.setCount(imageScrollView.getChildCount());

		pageControlView.generatePageControl(0);

		imageScrollView.setScrollToScreenCallback(mScrollToScreenCallback==null?pageControlView:mScrollToScreenCallback);

	}

	/**
	 * 设置图片的高度
	 * @param height
	 */
	public void setHeight(int height){
		ViewGroup.LayoutParams la = getLayoutParams();
		la.height = height;
		ViewGroup.LayoutParams lap = imageScrollView.getLayoutParams();
		lap.height = height;
	}


	/**
	 * 设置图片的宽
	 * @param width
	 */
	public void setWidth(int width){
		ViewGroup.LayoutParams la = getLayoutParams();
		la.width = width;
		ViewGroup.LayoutParams lap = imageScrollView.getLayoutParams();
		lap.width = width;
	}


	/**
	 * 开始播放流动广告，并设置流动广告播放的时间间隔
	 * @param time 时间间隔
	 */
	public void start(int time){
		imageScrollView.start(time);
	}


	/**
	 * 停止播放流动广告
	 */
	public void stop(){
		imageScrollView.stop();
	}

	/**
	 * 得到流动广告的位置
	 * @return
	 */
	public int getPosition(){
		return imageScrollView.getCurrentScreenIndex();
	}


	/**
	 * 监听流动广告的点击事件
	 * @param clickListener
	 */
	public void setClickListener(OnClickListener clickListener) {
		imageScrollView.setClickListener(clickListener);
	}
}

