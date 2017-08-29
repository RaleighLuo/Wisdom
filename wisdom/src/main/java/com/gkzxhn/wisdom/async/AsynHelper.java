package com.gkzxhn.wisdom.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.gkzxhn.wisdom.common.Constants;
import com.gkzxhn.wisdom.common.GKApplication;
import com.gkzxhn.wisdom.entity.RepairEntity;
import com.gkzxhn.wisdom.entity.RoomEntity;
import com.gkzxhn.wisdom.entity.RoomTempEntity;
import com.gkzxhn.wisdom.entity.TopicEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.starlight.mobile.android.lib.util.JSONUtil;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;


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
    private int TAB;
    public AsynHelper(int TAB) {
        super();
        this.TAB=TAB;
    }
    public void setOnTaskFinishedListener(
            TaskFinishedListener taskFinishedListener) {
        this.taskFinishedListener = taskFinishedListener;
    }
    /** On load task finished listener */
    public interface TaskFinishedListener {
        public void back(Object object);
    }
    @Override
    protected Object doInBackground(Object... params) {
        Object result=null;
        try {
            switch (TAB){
                case Constants.LOGIN_TAB:
                    SharedPreferences preferences= GKApplication.getInstance().getSharedPreferences(Constants.USER_TABLE,Context.MODE_PRIVATE);
                    String response = (String) params[0];
                    JSONObject json=JSONUtil.getJSONObject(response);

                    List<RoomTempEntity> datas = new Gson().fromJson(JSONUtil.getJSONObjectStringValue(json,"room_list"), new TypeToken<List<RoomTempEntity>>() {
                    }.getType());
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(Constants.USER_ID,JSONUtil.getJSONObjectStringValue(json,"id"));
                    editor.putString(Constants.USER_PORTRAIT,JSONUtil.getJSONObjectStringValue(json,"user_image"));
                    editor.putString(Constants.USER_NAME,JSONUtil.getJSONObjectStringValue(json,"name"));
                    editor.putString(Constants.USER_PHONE,JSONUtil.getJSONObjectStringValue(json,"phone"));
                    editor.putString(Constants.USER_NICKNAME,JSONUtil.getJSONObjectStringValue(json,"nickname"));
                    editor.putString(Constants.USER_GENDER,JSONUtil.getJSONObjectStringValue(json,"gender"));
                    //清除RoomEntity表数据
                    Realm realm=GKApplication.getInstance().getSystemRealm();
                    realm.beginTransaction();
                    realm.clear(RoomEntity.class);
                    realm.commitTransaction();
                    if(datas!=null&&datas.size()==1){
                        RoomTempEntity roomEntity=datas.get(0);
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
                    }
                    result=datas!=null?datas.size():0;
                    editor.commit();
                    if(datas!=null) {
                        //复制到数据库
                        realm.beginTransaction();
                        for(RoomTempEntity roomTemp:datas){
                            RoomEntity room=realm.createObject(RoomEntity.class);
                            room.setBuildingId(roomTemp.getBuildingId());
                            room.setBuildingName(roomTemp.getBuildingName());
                            room.setFloorArea(roomTemp.getFloorArea());
                            room.setRegionId(roomTemp.getRegionId());
                            room.setRegionName(roomTemp.getRegionName());
                            room.setResidentialAreasId(roomTemp.getResidentialAreasId());
                            room.setResidentialAreasName(roomTemp.getResidentialAreasName());
                            room.setRoomId(roomTemp.getRoomId());
                            room.setRoomName(roomTemp.getRoomName());
                            room.setUnitsId(roomTemp.getUnitsId());
                            room.setUnitsName(roomTemp.getUnitsName());
                            room.setUsedArea(roomTemp.getUsedArea());
                        }
                        realm.commitTransaction();
                    }
                    realm.close();
                    break;
                case Constants.OWN_TOPIC_LIST_TAB:
                case Constants.TOPIC_LIST_TAB:
                    response= (String) params[0];
                    result = new Gson().fromJson(response, new TypeToken<List<TopicEntity>>() {}.getType());
                    break;
                case Constants.COMMUNITY_REPAIR_PROGRESSING_TAB:
                case Constants.COMMUNITY_REPAIR_FINISHED_TAB:
                case Constants.REPAIR_FINISHED_TAB:
                case Constants.REPAIR_PROGRESSING_TAB:
                    response= (String) params[0];
                    result = new Gson().fromJson(response, new TypeToken<List<RepairEntity>>() {}.getType());
                    break;
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
            if (taskFinishedListener != null) {
                taskFinishedListener.back(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
