package com.gkzxhn.wisdom.common;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.gkzxhn.wisdom.R;
import com.gkzxhn.wisdom.activity.LoginActivity;
import com.nostra13.universalimageloader.cache.disc.impl.BaseDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by baidu on 17/1/12.
 */

public class GKApplication extends Application {

    public Context mContext = null;

    private static GKApplication application;
    public static GKApplication getInstance() {
        return application;
    }
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoaderConfiguration config = null;
    private BaseDiskCache imageLoadCache;
    private RealmConfiguration mSystemRealmConfiguration;
    @Override
    public void onCreate() {
        super.onCreate();
        buildDBConfig();
        application = this;
        initImageLoader();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        mContext = getApplicationContext();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    public void exit(){
        SharedPreferences preferences=getSharedPreferences(Constants.USER_TABLE,Context.MODE_PRIVATE);
        preferences.edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void buildDBConfig(){
        mSystemRealmConfiguration = new RealmConfiguration.Builder(this)
                .name(Constants.SYSTEM_DATABASE)
                .schemaVersion(Constants.SYSTEM_DATABASE_VERSION).build();
    }
    public Realm getSystemRealm() {
        Realm realm = null;
        try {
            realm = Realm.getInstance(mSystemRealmConfiguration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return realm;
    }

    private void initImageLoader(){
        File pictureCacheDirFile = new File(Constants.SD_IMAGE_CACHE_PATH);
        if (!pictureCacheDirFile.exists()) {
            pictureCacheDirFile.mkdirs();
        }
        imageLoadCache=new BaseDiskCache(pictureCacheDirFile) {};
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(600, 800)// max width, max height，即保存的每个缓存文件的最大长宽
                // max width, max height
                // .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75) //
                // Can slow ImageLoader, use it carefully (Better don't use it)
                .threadPoolSize(3).////线程池内加载的数量
                threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(16 * 1024 * 1024) // 50 Mb
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                ////任务线程的执行方式  后进先出法
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // You can pass your own memory cache implementation
                //自定义缓存路径
                .diskCache(imageLoadCache)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new ImageLoaderWithCookie(getApplicationContext(), 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                //		  .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密 ,.new HashCodeFileNameGenerator()//使用HASHCODE对UIL进行加密命名
                // .imageDownloader(new Httpclie(5 * 1000, 20 * 1000)) //
                // connectTimeout (5 s), readTimeout (20 s)
                .defaultDisplayImageOptions(options).build();
        imageLoader.init(config);
        //	清除所有图片imageLoader.clearDiskCache();

        //下载图片ImageLoader.getInstance().displayImage(loadUri,imageView);
        //		如果经常出现OOM
        //	   ①减少配置之中线程池的大小，(.threadPoolSize).推荐1-5；
        //	   ②使用.bitmapConfig(Bitmap.config.RGB_565)代替ARGB_8888;
        //	   ③使用.imageScaleType(ImageScaleType.IN_SAMPLE_INT)或者        try.imageScaleType(ImageScaleType.EXACTLY)；
        //	   ④避免使用RoundedBitmapDisplayer.他会创建新的ARGB_8888格式的Bitmap对象；
        //	   ⑤使用.memoryCache(new WeakMemoryCache())，不要使用.cacheInMemory();
    }
    /**获取文件的缓存工具类
     * 通过url地址获取本地图片文件，通过文件就可以得到文件的路径 imageLoadCache.get(imageUri)
     * @return
     */
    public BaseDiskCache getImageLoadCache(){
        return imageLoadCache;
    }
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_imageloading)//默认加载的图片
            .showImageForEmptyUri(R.mipmap.ic_imageload_failed)//下载地址不存在
            .showImageOnFail(R.mipmap.ic_imageload_failed).cacheInMemory(false).cacheOnDisk(true)//加载失败的图
            //	.displayer(new RoundedBitmapDisplayer(0))  设置圆角，设置后不能使用loadimage方法，项目并不需要圆角
            .bitmapConfig(Bitmap.Config.RGB_565)    //设置图片的质量
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)    //设置图片的缩放类型，该方法可以有效减少内存的占用
            .build();

    public class ImageLoaderWithCookie extends BaseImageDownloader {
        public ImageLoaderWithCookie(Context context,int connectTimeout, int readTimeout) {
            super(context,connectTimeout,readTimeout);
        }

        @Override
        protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
            HttpURLConnection connection = super.createConnection(url, extra);
            //Header auth认证
            connection.setRequestProperty("Authorization",Constants.UPLOAD_FILE_AUTHORIZATION);//extra就是SessionId，何时传入，见第三步
            return connection;
        }
    }

}
