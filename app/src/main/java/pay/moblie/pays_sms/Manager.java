package pay.moblie.pays_sms;


import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class Manager {
    private static  Manager _inst;
    private static Object _syncInst = new Object();

    public static Manager getInst(){
        if( _inst == null){

            //synchronized 保證區塊內同時間只會被一個 Thread 執行
            synchronized ( _syncInst ){
                if( _inst == null ){
                    _inst = new Manager();
                }
            }

        }
        return _inst;
    }


    private MainActivity _context;
    public MainActivity mainActivity;

    public void setContext(MainActivity context) {
        _context = context;
        mainActivity = context;
    }


    //Default post url-----------------------------------------
    public String POST_URL = "127.0.0.1";
    public String KEY = "test123456789";
    SharedPreferences setting = null;



    public void setConf(String url, String key) {
        this.POST_URL = url;
        this.KEY = key;
    }



    public String getUrl(){
        if(setting == null){
            setting = Manager.getInst().mainActivity.getSharedPreferences("SMS_CONF", 0);
        }
        //Log.e(Constant.TONY_TAG,"ppppppp = "+ POST_URL);
        String url = setting.getString("URL", POST_URL);
        if(url == null || url =="" ){
            url = POST_URL;
        }
        return url;
    }

    public String getKey(){
        if(setting == null){
            setting = Manager.getInst().mainActivity.getSharedPreferences("SMS_CONF", 0);
        }
        String key = setting.getString("KEY", KEY);
        if(key == null || key == ""){
            key = KEY;
        }
        return key;
    }

    public String getName(){
        if(setting == null){
            setting = Manager.getInst().mainActivity.getSharedPreferences("SMS_CONF", 0);
        }
        String name = setting.getString("NAME", "Test123");
        Log.e(Constant.TONY_TAG,"ppppppp = "+ name);
        return name;
    }



    //Toast-----------------------------------------
    public void sysToast(final String message){
        _context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
