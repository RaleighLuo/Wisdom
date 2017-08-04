package com.gkzxhn.wisdom.async;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.common.GKApplication;
import com.gkzxhn.wisdom.entity.RoomEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Raleigh on 15/8/20.
 */
public class AsynHelper extends AsyncTask<Object, Integer, Object> {
    /*  * Params 启动任务执行的输入参数，比如HTTP请求的URL。
	 * Progress 后台任务执行的百分比。
	 * Result 后台执行任务最终返回的结果，比如String,Integer等。
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
								asynHelper.executeOnExecutor(Executors.newCachedThreadPool(),args..);
							}else{
								asynHelper.execute(args...);
							}
	 * */
    private TaskFinishedListener taskFinishedListener;
    private AsynHelperTag target;
    public AsynHelper(AsynHelperTag tag) {
        super();
        this.target=tag;
    }
    public void setOnTaskFinishedListener(
            TaskFinishedListener taskFinishedListener) {
        this.taskFinishedListener = taskFinishedListener;
    }
    /** On load task finished listener */
    public interface TaskFinishedListener {
        public void back(Object object);
    }
    public enum AsynHelperTag{
        DEFUALT_TAG,
        LOGIN_TAG,

    }
    @Override
    protected Object doInBackground(Object... params) {
        Object result=null;
        try {
            if(target!=null) {
                switch (target){
                    case LOGIN_TAG:
                        SharedPreferences preferences= GKApplication.getInstance().getSharedPreferences(Constants.USER_TABLE,Context.MODE_PRIVATE);
                        String response = (String) params[0];
                        JSONObject json=JSONUtil.getJSONObject(response);
                        List<RoomEntity> datas = new Gson().fromJson(JSONUtil.getJSONObjectStringValue(json,"room_list"), new TypeToken<List<RoomEntity>>() {
                        }.getType());
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString(Constants.USER_ID,JSONUtil.getJSONObjectStringValue(json,"id"));
                        editor.putString(Constants.USER_PORTRAIT,JSONUtil.getJSONObjectStringValue(json,"user_image"));
                        editor.putString(Constants.USER_NAME,JSONUtil.getJSONObjectStringValue(json,"name"));
                        editor.putString(Constants.USER_PHONE,JSONUtil.getJSONObjectStringValue(json,"phone"));
                        editor.putString(Constants.USER_NICKNAME,JSONUtil.getJSONObjectStringValue(json,"nickname"));
                        editor.putString(Constants.USER_GENDER,JSONUtil.getJSONObjectStringValue(json,"gender"));
                        if(datas.size()==1){
                            RoomEntity roomEntity=datas.get(0);
                            editor.putString(Constants.USER_BUILDINGID,roomEntity.getBuildingId());
                            editor.putString(Constants.USER_BUILDINGNAME,roomEntity.getBuildingName());
                            editor.putString(Constants.USER_REGIONID,roomEntity.getRegionId());
                            editor.putString(Constants.USER_REGIONNAME,roomEntity.getRegionName());
                            editor.putString(Constants.USER_RESIDENTIALAREASID,roomEntity.getResidentialAreasId());
                            editor.putString(Constants.USER_RESIDENTIALAREASNAME,roomEntity.getResidentialAreasName());
                            editor.putString(Constants.USER_ROOMID,roomEntity.getRoomId());
                            editor.putString(Constants.USER_ROOMNAME,roomEntity.getRoomName());
                            editor.putString(Constants.USER_UNITSID,roomEntity.getUnitsId());
                            editor.putString(Constants.USER_UNITSNAME,roomEntity.getUnitsName());
                            editor.putFloat(Constants.USER_USEDAREA,roomEntity.getUsedArea());
                            editor.putFloat(Constants.USER_FLOORAREA,roomEntity.getFloorArea());
                        }else{//选择
                            result=datas;
                        }
                        editor.commit();
                        break;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
        }
        return result;
    }


    @Override
    protected void onPostExecute(Object result) {
        try {
            if (target!=null&&taskFinishedListener != null) {
                taskFinishedListener.back(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}