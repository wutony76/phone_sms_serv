package pay.moblie.pays_sms;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;




public class Tools {
    // 判斷 Serv Status.
    public static boolean isServiceRunning(Context mContext, String className) {
        Log.e( Constant.TONY_TAG, "className =" + className );

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        //List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);

        Log.e( Constant.TONY_TAG, "serviceList =" + serviceList.size() );
        if (!(serviceList.size() > 0)) {
            return false;
        }


        for (int i = 0; i < serviceList.size(); i++) {
            String name = serviceList.get(i).service.getClassName();
            Log.e( Constant.TONY_TAG, "getClassName =" + name );

            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }


    // 判斷 App 是否啟動
    public static boolean getCurrentTask(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取当前所有存活task的信息
        List<ActivityManager.RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(Integer.MAX_VALUE);
        //遍历，若task的name与当前task的name相同，则返回true，否则，返回false
        for (ActivityManager.RunningTaskInfo process : appProcessInfos) {
            if (process.baseActivity.getPackageName().equals(context.getPackageName())
                    || process.topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }





}
