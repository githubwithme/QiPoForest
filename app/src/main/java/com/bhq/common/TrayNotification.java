package com.bhq.common;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.bhq.ui.Login_;

@SuppressLint("deprecation")
public class TrayNotification
{

	private static NotificationManager manager = null;
	private static Notification notification = null;

	/** 显示任务栏通知 */
	public static void addNotification(Context context, int iconId, String title)
	{
		manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(iconId, title, System.currentTimeMillis());

		Intent intent = new Intent(context, Login_.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		Notification.Builder builder = new Notification.Builder(context)
				.setAutoCancel(true)
				.setContentTitle(title)
				.setContentText("欢迎使用")
				.setContentIntent(pendingIntent)
				.setSmallIcon(iconId)
				.setWhen(System.currentTimeMillis())
				.setOngoing(true);
		notification=builder.getNotification();
		manager.notify(0, notification);


	}

	/** 取消任务栏通知 */
//	public static void removeNotification(Context context)
//	{
//		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		manager.cancelAll();
//	}
}
