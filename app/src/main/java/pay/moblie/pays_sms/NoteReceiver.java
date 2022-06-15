package pay.moblie.pays_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NoteReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    public Boolean isRegister = false;

    public NoteReceiver() {
        this.isRegister = true;
    }


    //private List<Map<String, Object>> data;


    @Override
    public void onReceive(Context context, Intent intent) {

        /*--- self test ---*/
        String testData = intent.getStringExtra("sender_name");
        Log.e( Constant.TONY_TAG, "test get data. = " + testData );
        if (testData != null){
            Manager.getInst().mainActivity.ui_addRecord( String.format(
                Manager.getInst().mainActivity.getResources().getString(R.string.app_test_sms),
                testData
            ));
        }


        /*--- get sys sms ---*/
        String action = intent.getAction();
        if (action.equals(SMS_RECEIVED_ACTION)) {
            Bundle bundle = intent.getExtras();

            if (bundle != null){
                Manager.getInst().mainActivity.ui_addRecord( Manager.getInst().mainActivity.getResources().getString(R.string.app_get_sms) );

                Object pdusData[] = (Object[]) bundle.get("pdus");//將pdus裡面的內容轉化成Object[]陣列
                SmsMessage[] msg = new SmsMessage[pdusData.length];//解析簡訊

                for (int i = 0; i < msg.length; i++  ){
                    byte pdus[] = (byte[]) pdusData[i];
                    msg[i] = SmsMessage.createFromPdu(pdus);
                }

                StringBuffer content = new StringBuffer();//獲取簡訊內容
                StringBuffer phoneNumber = new StringBuffer();//獲取地址
                StringBuffer receiveData = new StringBuffer();//獲取時間

                //分析簡訊具體引數
                for (SmsMessage temp : msg){
                    content.append(temp.getMessageBody());
                    phoneNumber.append(temp.getOriginatingAddress());
                    receiveData.append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(new Date(temp.getTimestampMillis())));
                }


                /**
                 * 這裡還可以進行好多操作，比如我們根據手機號進行攔截（取消廣播繼續傳播）等等
                 */
                //String sms_string = phoneNumber.toString()+ content + receiveData;
                String sms_data = "Data:"+ content +" Time:"+ receiveData;
                //Toast.makeText( context, sms_data, Toast.LENGTH_LONG ).show();//簡訊內容
                //Toast.makeText( Manager.getInst().mainActivity, sms_data, Toast.LENGTH_LONG ).show();//簡訊內容
                Log.e("ttt", sms_data);


                Manager.getInst().mainActivity.ui_addRecord( String.format(
                    Manager.getInst().mainActivity.getResources().getString(R.string.app_analyze_sms),
                    content
                ));


                //new ConnServ( ""+ content ).start();
                Manager.getInst().mainActivity.connServ.postData( ""+content );


            }
        }

    }
}

