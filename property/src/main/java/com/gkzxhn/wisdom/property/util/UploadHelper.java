package com.gkzxhn.wisdom.property.util;

import android.os.AsyncTask;
import android.os.Build;

import com.gkzxhn.wisdom.property.common.Constants;
import com.gkzxhn.wisdom.property.common.GKApplication;
import com.starlight.mobile.android.lib.util.HttpStatus;
import com.starlight.mobile.android.lib.util.ImageHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.Executors;


/**
 * Created by Raleigh.Luo on 17/3/11.
 */

public class UploadHelper {
    private  final int TIME_OUT = 10*10000000; //超时时间
    private  final String CHARSET = "utf-8"; //设置编码
    public String filePath;//文件路径
    private GKApplication application;
    private UploadFinishListener listener;//回调监听器
    private UploadAsyn uploadAsyn;//异步请求
    private boolean isPhoto=true;
    private final String RESPONSE_CODE="responseCode";
    private final String RESPONSE_MESSAGE="responseMessage";
    private String mUploadUrl=null;//上传地址
    private String mUploadFileName=null;//文件名
    public UploadHelper(){
        application= GKApplication.getInstance();
    }

    public UploadHelper(UploadFinishListener listener){
        application= GKApplication.getInstance();
        this.listener=listener;

    }
    public void setUploadFinishListener(UploadFinishListener listener){
        this.listener=listener;
    }

    /**上传图片
     * @param filePath
     * @param uploadUrl
     * @param uploadFileName
     */
    public void upload(String filePath,String uploadUrl,String uploadFileName){
        this.filePath=filePath;
        isPhoto=true;
        this.mUploadUrl=uploadUrl;
        this.mUploadFileName=uploadFileName;
        startAsynTask();
    }

    /**
     * 停止上传
     */
    public void onStop(){
        if (uploadAsyn != null) {
            if (uploadAsyn.getStatus() == AsyncTask.Status.RUNNING) uploadAsyn.cancel(true);
            uploadAsyn = null;
        }
    }
    /**
     * 开始异步请求任务
     *
     */
    private void startAsynTask() {
        try {
            if (uploadAsyn != null) {
                if (uploadAsyn.getStatus() == AsyncTask.Status.RUNNING) uploadAsyn.cancel(true);
                uploadAsyn = null;
            }
            uploadAsyn = new UploadAsyn();
            uploadAsyn.setOnAsynFinishListener( onAsynFinishListener);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                uploadAsyn.executeOnExecutor(Executors.newCachedThreadPool());
            } else {
                uploadAsyn.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步请求回调
     */
    private OnAsynFinishListener onAsynFinishListener=new OnAsynFinishListener() {
        @Override
        public void onFinish(JSONObject result) {
            int code= 0;
            try {
                code = result.getInt(RESPONSE_CODE);

                String message=result.getString(RESPONSE_MESSAGE);
                if(code== HttpStatus.SC_CREATED||code== HttpStatus.SC_OK)
                {
                    listener.onSuccess(message,filePath);
                }else if(code==HttpStatus.SC_REQUEST_TIMEOUT){
                    startAsynTask();
                }else{
                    listener.onFailed(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onFailed(null);
            }
        }
    };

    public interface UploadFinishListener{
        public void onSuccess(String message, String filePath);
        public void onFailed(String error);
    }

    /**
     * 异步上传任务
     */
    class UploadAsyn extends AsyncTask<Void, Integer, JSONObject>{

        private OnAsynFinishListener listener;
        public void setOnAsynFinishListener(OnAsynFinishListener listener){
            this.listener=listener;
        }
        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject json=new JSONObject();
            //压缩图片
            String path=new ImageHelper().compressImage(filePath, Constants.SD_PHOTO_PATH,mUploadFileName);//鍘嬬缉鍥剧墖
            if(path!=null)filePath=path;
            File file=new File(filePath);
            try {
                String BOUNDARY = UUID.randomUUID().toString(); //杈圭晫鏍囪瘑 闅忔満鐢熸垚
                String PREFIX = "--" , LINE_END = "\r\n";
                String CONTENT_TYPE = "multipart/form-data"; //多部分方式上传
                URL url = new URL(mUploadUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIME_OUT);
                conn.setConnectTimeout(TIME_OUT);
                conn.setDoInput(true); // 允许输入流
                conn.setDoOutput(true);  // 允许输出流
                conn.setUseCaches(false); // 不允许使用缓存
                conn.setRequestMethod("POST");  // 请求方式
                conn.setRequestProperty("Charset", CHARSET);// 设置编码

                conn.setRequestProperty("connection", "keep-alive");
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
                conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
                //Header auth认证
                conn.setRequestProperty("Authorization", Constants.UPLOAD_FILE_AUTHORIZATION);
                if(file!=null) {

                    OutputStream outputSteam=conn.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(outputSteam);
                    StringBuffer sb = new StringBuffer();
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    /**
                     * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                     * filename是文件的名字，包含后缀名的 比如:abc.png
                     */
                    String key=Constants.UPLOAD_KEY_MAP.get(mUploadUrl);

                    sb.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\""+file.getName()+"\""+LINE_END);
//                    sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                    sb.append("Content-Type: image/jpeg; charset="+CHARSET+LINE_END);
                    sb.append(LINE_END);
                    dos.write(sb.toString().getBytes());
                    InputStream is = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while((len=is.read(bytes))!=-1)
                    {
                        dos.write(bytes, 0, len);
                    }

                    is.close();
                    dos.write(LINE_END.getBytes());
                    byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                    dos.write(end_data);
                    dos.flush();
                    //杩斿洖鐨勬暟鎹�
                    BufferedReader bf=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String lines="";
                    StringBuffer result=new StringBuffer();
                    while ((lines=bf.readLine())!=null){
                        result.append(lines);
                    }
                    /**
                     * 获取响应码 200=成功 当响应成功，获取响应的流
                     */
                    json.put(RESPONSE_CODE,conn.getResponseCode());
                    json.put(RESPONSE_MESSAGE,result);
                    if(file.exists())file.delete();
                }
            } catch (Exception e){
                e.printStackTrace();
                if(file.exists())file.delete();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                if(listener!=null)listener.onFinish(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public interface OnAsynFinishListener{
        public void onFinish(JSONObject result);
    };

}
