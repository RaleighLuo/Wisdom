package com.gkzxhn.wisdom.property.async;

import android.os.AsyncTask;

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
