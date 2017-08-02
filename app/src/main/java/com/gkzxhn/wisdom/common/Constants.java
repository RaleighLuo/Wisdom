package com.gkzxhn.wisdom.common;

import android.os.Environment;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public interface Constants {
    /*-------------------------------Configration-------------------------------------------------*/
    final int REQUEST_TIMEOUT=60000;//超时时间1分钟
    public final String ATTACH_TYPE_IMAGE_POSTFIX_JPEG=".jpg";
    public final String SD_ROOT_PATH= Environment.getExternalStorageDirectory().getPath()+"/Wisdom";
    public final String SD_FILE_CACHE_PATH = SD_ROOT_PATH+"/cache/";

    public final String SD_IMAGE_CACHE_PATH = SD_ROOT_PATH+"/imageCache/";//图片下载的缓存
    public final String SD_ROOT_PHOTO_PATH = SD_ROOT_PATH+"/photo/";//图片，不自动删除
    public final String SD_PHOTO_PATH = SD_ROOT_PHOTO_PATH+"cutPhoto/";//拍照存储或压缩图片的图片路径,启动时自动删除
    public final String SD_AUDIO_PATH = SD_ROOT_PATH+"/audio/";
    /*-------------------------------User Tab-------------------------------------------------*/
    final String USER_TABLE="user_table";
    final String USER_IS_UNAUTHORIZED="isUnauthorized";
    final String USER_TOKEN="user_token";
    final String USER_ID="user_id";
    final String USER_PORTRAIT="user_portrait";

    final String USER_BUILDINGID="user_buildingId";
    final String USER_BUILDINGNAME="user_buildingName";
    final String USER_REGIONID="user_regionId";
    final String USER_REGIONNAME="user_regionName";
    final String USER_RESIDENTIALAREASID="user_residentialAreasId";
    final String USER_RESIDENTIALAREASNAME="user_residentialAreasName";
    final String USER_ROOMID="user_roomId";
    final String USER_ROOMNAME="user_roomName";
    final String USER_UNITSID="user_unitsId";
    final String USER_UNITSNAME="user_unitsName";
    /*-------------------------------Request URL-------------------------------------------------*/
    public final String RELEASE_DOMAIN="http://123.57.7.159:3000";//发布正式环境
    public final String DEMO_DOMAIN="http://10.10.10.119:3000";//开发环境
    public final String DOMAIN_NAME_XLS = RELEASE_DOMAIN;
    public final String RELEASE_FILE_DOMAIN="http://123.57.7.159:1339";//发布正式环境
    public final String DEMO_FILE_DOMAIN="http://10.10.10.119:1339";//开发环境
    public final String DOMAIN_NAME_FILE_XLS = RELEASE_FILE_DOMAIN;

    final String REQUEST_LOGIN_URL=DOMAIN_NAME_XLS+"/login";//登录
    final String UPLOAD_PROFILE_URL=DOMAIN_NAME_FILE_XLS+"/profile";//头像图片
    final String UPLOAD_TOPICS_URL=DOMAIN_NAME_FILE_XLS+"/topics";//话题图片
    final String REQUEST_VERIFY_CODE_URL=DOMAIN_NAME_XLS+"/request_code";//登录

    /*-------------------------------Request Code-------------------------------------------------*/
    final String EXTRA="extra";
    final String EXTRA_TAB="extra_tab";
    final String EXTRAS="extras";
    final String EXTRA_ENTITY="extra_entity";
    final String EXTRA_POSITION="extra_position";  public final int EXTRA_CODE=0x001;
    final int PREVIEW_PHOTO_CODE=0x102;
    final int SELECT_PHOTO_CODE=0x103;
    final int TAKE_PHOTO_CODE=0x104;
    final int RESIZE_REQUEST_CODE=0x105;
    final int EXTRAS_CODE=0x106;
    final int PHONE_CODE=0x107;
    /*-------------------------------msg what-------------------------------------------------*/
    public final int START_REFRESH_UI=1,STOP_REFRESH_UI=2;//msg what
     /*-------------------------------TAB-------------------------------------------------*/
     final int PAY_RECORD_TAB=0x201;//缴费记录
    final int REPAIR_PROGRESSING_TAB=0x202;//维修列表－正在进行
    final int REPAIR_FINISHED_TAB=0x203;//维修列表－完成
    final int HOUSE_LEASE_TAB=0x204;//房屋租售－租房
    final int HOUSE_SALE_TAB=0x205;//房屋租售－售房

}
