package com.jwt;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class MyCustomReceiver extends BroadcastReceiver {
	private static final String TAG = "MyCustomReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			String action = intent.getAction();
			String channel = intent.getExtras().getString("com.parse.Channel");
			JSONObject json = new JSONObject(intent.getExtras().getString(
					"com.parse.Data"));

			Log.d(TAG, "got action " + action + " on channel " + channel
					+ " with:");

			//CODIGO ANDROID 4.2
			// Notification noti = new Notification.Builder(context)
			// .setContentTitle("CHEGOU UM PUSH DA NUVEM")
			// .setContentText(json.getString("mensagem"))
			// .setSmallIcon(R.drawable.icon).build();

			// ANDROID 2.3
			Notification notification = new Notification(R.drawable.icon,
					json.getString("mensagem"), System.currentTimeMillis());
			
			Intent myIntent = new Intent(intent);
			myIntent.setClass(context, MyPushActivity.class);
			
			notification.setLatestEventInfo(context, "Push Notification",
					json.getString("mensagem"),
					PendingIntent.getActivity(context, 1, myIntent, 0));
			notification.vibrate = new long[]{100, 200, 100, 500}; 
			// FINAL ANDROID 2.3
						
			NotificationManager mNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);

			mNotificationManager.notify(1, notification);

		} catch (JSONException e) {
			Log.d(TAG, "JSONException: " + e.getMessage());
		}
	}
}