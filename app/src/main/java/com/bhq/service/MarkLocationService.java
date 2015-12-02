package com.bhq.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.bhq.bean.Points;
import com.bhq.bean.Track;
import com.bhq.common.utils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

public class MarkLocationService extends Service implements TencentLocationListener
{
	Track track;
	String uuid;
	DbUtils db;
	int error;

	@Override
	public IBinder onBind(Intent arg0)
	{
		init();
		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent)
	{
		return super.onUnbind(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// init();
		return super.onStartCommand(intent, flags, startId);
	}

	/** 绑定的IBinder */
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder
	{
		public MarkLocationService getService()
		{
			return MarkLocationService.this;
		}
	}

	/**
	 * 初始化定位
	 */
	private void init()
	{
		// 初始化定位，只采用网络定位
		TencentLocationRequest request = TencentLocationRequest.create();
		TencentLocationManager locationManager = TencentLocationManager.getInstance(this);
		error = locationManager.requestLocationUpdates(request, this);

		db = DbUtils.create(getApplicationContext(), "map.db");
		uuid = java.util.UUID.randomUUID().toString().substring(0, 36);

		track = new Track();
		track.setXHSJ(utils.getTime());
		track.setDescription("巡护保护区");
		track.setTrackid(uuid);
		track.setXHR("1");
		track.setXHRXM("张三");
		track.setXXZT("0");
		try
		{
			db.save(track);
		} catch (DbException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onLocationChanged(TencentLocation location, int arg1, String arg2)
	{
		String lat = "";
		String lon = "";
		if (TencentLocation.ERROR_OK == error)
		{
			// 定位成功
			lat = Double.toString(location.getLatitude());
			lon = Double.toString(location.getLongitude());

			Points points = new Points();
			points.setLat(String.valueOf(lat));
			points.setLon(String.valueOf(lon));
			points.setTime(utils.getTime());
			points.setUuid(uuid);

			try
			{
				db.save(points);
			} catch (DbException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onStatusUpdate(String arg0, int arg1, String arg2)
	{
	}

}
