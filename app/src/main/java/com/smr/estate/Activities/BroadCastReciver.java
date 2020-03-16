package com.smr.estate.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.smr.estate.Activities.AuthActivity;

public class BroadCastReciver extends BroadcastReceiver {

    public String recivedCode;
    public String recivedMessage;
    public static int subInteger;
    public static boolean flag ;
    AuthActivity authActivity;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {

            authActivity = new AuthActivity();

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] objects = (Object[]) bundle.get("pdus");
                for (int i = 0; i < objects.length; i++) {

                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) objects[i]);
                    String number = smsMessage.getDisplayOriginatingAddress();
                    String subStr = smsMessage.getDisplayMessageBody();
                    if(subStr.length()==24){
                        String subStr1 = subStr.substring(20, 24);
                        int subIn = Integer.parseInt(subStr1);

                        String smsPanelNum = "+985000219030";
                        if(number.equals(smsPanelNum) ){
                            authActivity.pinEntry.setText(subIn+"");

                        }
                    }

                }
            }
        }

    }
    BroadCastReciver(){
    }
}
