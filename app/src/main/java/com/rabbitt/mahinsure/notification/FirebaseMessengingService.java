package com.rabbitt.mahinsure.notification;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rabbitt.mahinsure.Config;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Map;

public class FirebaseMessengingService extends FirebaseMessagingService {

    String TAG = "remote";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.i("remote", "Data Payload: " + remoteMessage.getData());
            try {
                Map<String, String> params = remoteMessage.getData();
                JSONObject json = new JSONObject(params);
                Log.i(TAG, "onMessageReceived: " + json);
                sendPushNotification(json);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("remote", "Exception: " + e.getMessage() +e.toString());
            }
        }
    }

    private void sendPushNotification(JSONObject json) {
        NotificationHelper no = new NotificationHelper(this);
        no.createNotification("New Inspection");
    }

    @Override
    public void onNewToken(@NotNull String token) {
        Log.i(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.TOKEN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.apply();
        editor.commit();
    }
}