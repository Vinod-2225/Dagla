package com.dagla.android;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

//import ly.count.android.sdk.Countly;
//import ly.count.android.sdk.messaging.CountlyPush;

public class MyApplication extends Application {

    private String deliveryAddressId;

    final String serverUrl = "https://dagla.count.ly";
    final String appKey = "43744905b9d494a663d22beafbdaee47bcfb05f5";

    @Override
    public void onCreate() {

        super.onCreate();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            if (notificationManager != null) {
//                // Create the NotificationChannel
//                NotificationChannel channel = new NotificationChannel(CountlyPush.CHANNEL_ID, getString(R.string.countly_hannel_name), NotificationManager.IMPORTANCE_DEFAULT);
//                channel.setDescription(getString(R.string.countly_channel_description));
//                notificationManager.createNotificationChannel(channel);
//            }
//        }
//
//        Countly.sharedInstance()
//                .setRequiresConsent(true)
//                .setConsent(new String[]{Countly.CountlyFeatureNames.push, Countly.CountlyFeatureNames.sessions, Countly.CountlyFeatureNames.location, Countly.CountlyFeatureNames.attribution, Countly.CountlyFeatureNames.crashes, Countly.CountlyFeatureNames.events, Countly.CountlyFeatureNames.starRating, Countly.CountlyFeatureNames.users, Countly.CountlyFeatureNames.views}, true)
//                .setLoggingEnabled(true)
//                .setPushIntentAddMetadata(true);
//
//        Countly.sharedInstance().enableCrashReporting();
//        Countly.sharedInstance().setViewTracking(true);
//        Countly.sharedInstance().init(this, serverUrl, appKey);
//
////        CountlyPush.init(this, Countly.CountlyMessagingMode.TEST);
//        CountlyPush.init(this, Countly.CountlyMessagingMode.PRODUCTION);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();


                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("DeviceTokenId",token);
                        editor.commit();
                        GlobalFunctions.setPrefrences(getApplicationContext(), "token", token);
//                        CountlyPush.onTokenRefresh(token);
                    }
                });

    }

    public String getDeliveryAddressId() {
        return deliveryAddressId;

    }

    public void setDeliveryAddressId(String deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

}
