package com.example.yeniprojekotlin;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

public class SMSUtil {

    private static final String TAG = "SMSUtil";

    public static void sendSMS(Context context, String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.d(TAG, "SMS sent to " + phoneNumber);
        } catch (Exception e) {
            Log.e(TAG, "Failed to send SMS", e);
        }
    }
}
