package com.bhq.ui;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.Result;
import com.bhq.bean.Track;
import com.bhq.bean.dt_manager;
import com.bhq.common.utils;
import com.bhq.net.HttpUrlConnect;
import com.bhq.widget.NewDataToast;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.GroundOverlay;
import com.tencent.mapsdk.raster.model.GroundOverlayOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.Polygon;
import com.tencent.mapsdk.raster.model.PolygonOptions;
import com.tencent.mapsdk.raster.model.Polyline;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.Projection;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EFragment
public class PatrolControlFragment extends Fragment implements TencentLocationListener
{
	IntentFilter imageIntentFilter;
	private WindowManager mWindowManager;
	TimeThread timethread;
	// Polygon polygon;
	double max;
	double min;
	List<Double> list_lat = new ArrayList<Double>();
	List<Double> list_lng = new ArrayList<Double>();
	List<LatLng> list_LatLng_Bounds = new ArrayList<LatLng>();
	AppContext appContext;
	Long newtime = 0L;
	Long lasttime = 0L;
	private List<Object> Overlays;
	Boolean isStart = false;
	Track track;
	String uuid;
	DbUtils db;
	Marker marker;
	TencentMap tencentMap;
	String result = new String();
	// LatLng latLng = null;
	LatLng location_latLng = new LatLng(23.588219, 113.819251);// 初始化定位
	LatLng lastlatLng;// 初始化定位
	String XHID;
	int error;
	@ViewById
	MapView mapview;
	@ViewById
	TextView tv_gps;
	@ViewById
	ImageButton imgbtn_home;
	@ViewById
	Button btn_location;
	TableLayout tl_satellite;
	TableLayout tl_erwei;
	@ViewById
	Button btn_runcontrol;
	@ViewById
	TextView tv_runlength;
	@ViewById
	TextView tv_starttime;
	@ViewById
	TextView tv_runtime;
	@ViewById
	RelativeLayout rl_mapmore;
	@ViewById
	LinearLayout ll_GPS;
	@ViewById
	LinearLayout ll_pb_gps;
	@ViewById
	Button btn_complete;
	@ViewById
	Button btn_continue;
	private Projection mProjection;

	String starttime;
	Double XHLC = 0D;
	String XHXSS = "";
	String XHFZS = "";

	@Click
	void btn_location()
	{
		Toast.makeText(getActivity(), "定位中...", Toast.LENGTH_SHORT).show();
		tencentMap.setZoom(15);
		animateToLocation();
	}

	@Click
	void btn_continue()
	{
		isStart = true;
		btn_runcontrol.setVisibility(View.VISIBLE);
		btn_complete.setVisibility(View.GONE);
		btn_continue.setVisibility(View.GONE);
		btn_runcontrol.setText("暂停巡护");
		timethread.setStop(false);
		timethread.setSleep(false);
		ContinueBHQ_XHQK(XHID);
		AddNewBHQ_XHQK_ZTCZ(XHID, "1");// 设置进行中状态
	}

	@Click
	void btn_complete()
	{
		// 初始化
		btn_runcontrol.setText("开始巡护");
		btn_complete.setVisibility(View.GONE);
		btn_continue.setVisibility(View.GONE);
		timethread.setStop(true);
		timethread.interrupt();
		timethread = null;
		btn_runcontrol.setVisibility(View.VISIBLE);
		tv_runtime.setVisibility(View.GONE);
		tv_runlength.setVisibility(View.GONE);
		tv_starttime.setVisibility(View.GONE);
		rl_mapmore.setVisibility(View.GONE);
		isStart = false;
		tencentMap.clearAllOverlays();
		FinishBHQ_XHQK(XHID);
		AddNewBHQ_XHQK_ZTCZ(XHID, "3");// 设置结束状态
	}

	@Click
	void btn_runcontrol()
	{
		if (btn_runcontrol.getText().equals("开始巡护"))
		{
			if (isStart)// 已经开始
			{
				timethread.setStop(false);
				timethread.setSleep(false);
			} else
			{
				tv_runlength.setText("0m");
				XHLC = 0D;
				lasttime = System.currentTimeMillis();
				isStart = true;
				startTrack();
				tv_starttime.setText(utils.getHours());
				starttime = utils.getTime();
				timethread = new TimeThread();
				timethread.setStop(false);
				timethread.setSleep(false);
				timethread.start();
				XHID = java.util.UUID.randomUUID().toString();
				AddNewBHQ_XHQK(XHID);// 添加一条巡护
				AddNewBHQ_XHQK_ZTCZ(XHID, "1");// 设置开始状态
			}
			btn_runcontrol.setText("暂停巡护");
			tv_runtime.setVisibility(View.VISIBLE);
			tv_runlength.setVisibility(View.VISIBLE);
			tv_starttime.setVisibility(View.VISIBLE);
			ll_GPS.setVisibility(View.VISIBLE);
			rl_mapmore.setVisibility(View.VISIBLE);
			ll_GPS.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_left));
			rl_mapmore.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_right));
			tv_runtime.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_left));
			tv_starttime.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_left));
			tv_runlength.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_left));
		} else if (btn_runcontrol.getText().equals("暂停巡护"))
		{
			isStart = false;
			timethread.setStop(false);
			timethread.setSleep(true);
			btn_complete.setVisibility(View.VISIBLE);
			btn_continue.setVisibility(View.VISIBLE);
			btn_complete.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_right_map));
			btn_continue.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.in_left_map));
			btn_runcontrol.setVisibility(View.GONE);
			btn_runcontrol.setText("开始巡护");
			StopBHQ_XHQK(XHID);// 设置巡护状态为暂停
			AddNewBHQ_XHQK_ZTCZ(XHID, "2");// 设置暂停状态
		}

	}

	private void animateToLocation()
	{
		if (location_latLng != null)
		{
			tencentMap.removeOverlay(marker);
			tencentMap.animateTo(location_latLng);
			addMarker(location_latLng, R.drawable.ic_account);
		}
	}

	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.arg1)
			{
			case AppContext.TIME_STOPWATCH:
				tv_runtime.setText(msg.obj.toString());
				XHXSS = msg.obj.toString().substring(0, 2);
				XHFZS = msg.obj.toString().substring(3, 5);
				break;

			default:
				break;
			}
		};
	};

	@AfterViews
	void afterOncreate()
	{
		if (!utils.isOPen(getActivity()))
		{
			utils.openGPSSettings(getActivity());
		}
		tencentMap = mapview.getMap();
		tencentMap.setZoom(15);
		mProjection = mapview.getProjection();
		animateToLocation();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.patrolcontrolfragment, container, false);
		getActivity().getActionBar().hide();
		appContext = (AppContext) getActivity().getApplication();

		TencentLocationRequest request = TencentLocationRequest.create();
		TencentLocationManager locationManager = TencentLocationManager.getInstance(getActivity());
		error = locationManager.requestLocationUpdates(request, this);
		Overlays = new ArrayList<Object>();

		return rootView;
	}

	@Override
	public void onLocationChanged(TencentLocation location, int error, String reason)
	{
		if (TencentLocation.ERROR_OK == error) // 定位成功
		{
			// 用于定位
			location_latLng = new LatLng(location.getLatitude(), location.getLongitude());
			AppContext appContext = (AppContext) getActivity().getApplication();
			appContext.setLOCATION_X(String.valueOf(location_latLng.getLatitude()));
			appContext.setLOCATION_Y(String.valueOf(location_latLng.getLongitude()));
			// 每隔15秒记录轨迹
			newtime = System.currentTimeMillis();
			int diff = (int) (newtime - lasttime) / 1000;
			if (diff > 15)// 每隔15秒记录一次
			{
				lasttime = newtime;
				if (isStart)
				{

					AddNewBHQ_XHQK_GJ(XHID);// 添加开始点的轨迹
					// 画轨迹
					PolylineOptions lineOpt = new PolylineOptions();
					lineOpt.add(lastlatLng);
					lineOpt.add(location_latLng);
					Polyline line = tencentMap.addPolyline(lineOpt);
					line.setColor(getResources().getColor(R.color.black));
					line.setWidth(10f);
					Overlays.add(line);
					// 计算距离
					if (lastlatLng != null)
					{
						XHLC = XHLC + mProjection.distanceBetween(lastlatLng, location_latLng);

						if (String.valueOf(XHLC).length() > 5)
						{
							tv_runlength.setText(String.valueOf(XHLC).substring(0, String.valueOf(XHLC).lastIndexOf(".") + 3) + "m");
						} else
						{
							tv_runlength.setText(XHLC + "m");
						}

					}
					// 传递值放在最后
					lastlatLng = location_latLng;
					// if (!polygon.contains(location_latLng))
					// {
					// NewDataToast.makeText(getActivity(), "你已经走出巡护范围",
					// appContext.isAppSound(), R.raw.outofbounds).show();
					// }
				}
			}
		}

	}

	@Override
	public void onStatusUpdate(String name, int status, String desc)
	{

		if (status == 2)// 位置权限拒绝
		{
//			Toast.makeText(getActivity(), " 位置权限拒绝！", Toast.LENGTH_SHORT).show();
		} else if (status == 2)// 定位服务关闭
		{
//			Toast.makeText(getActivity(), " 定位服务关闭！", Toast.LENGTH_SHORT).show();
		} else if (status == 1)// 定位服务开启
		{
//			Toast.makeText(getActivity(), "定位服务开启！", Toast.LENGTH_SHORT).show();
		} else if (status == -1)// 定位服务未知
		{
//			Toast.makeText(getActivity(), "定位服务未知！", Toast.LENGTH_SHORT).show();
		}
	}

	private Polygon drawPolygon(List<LatLng> list_LatLng)
	{
		PolygonOptions polygonOp = new PolygonOptions();
		polygonOp.fillColor(0x00ffffff);// 填充色
		polygonOp.strokeWidth(0);// 线宽
		for (int i = 0; i < list_LatLng.size(); i++)
		{
			polygonOp.add(list_LatLng.get(i));
		}
		Polygon polygon = mapview.getMap().addPolygon(polygonOp);
		return polygon;
	}

	private void addMarker(LatLng latLng, int icon)
	{
		Drawable drawable = getResources().getDrawable(icon);
		Bitmap bitmap = utils.drawable2Bitmap(drawable);
		marker = tencentMap.addMarker(new MarkerOptions().position(latLng).title("").icon(new BitmapDescriptor(bitmap)));
		marker.hideInfoWindow();
	}

	private GroundOverlay drawGroundOverlay(String path)
	{
		GroundOverlayOptions groundOverlayOp = new GroundOverlayOptions();
		groundOverlayOp.anchor(0.1f, 0.1f);
		groundOverlayOp.image(BitmapDescriptorFactory.fromPath(path));
		groundOverlayOp.transparency(0.25f);
		groundOverlayOp.positionFromBounds(new LatLngBounds(new LatLng(location_latLng.getLatitude() - 0.02, location_latLng.getLongitude() - 0.02), new LatLng(location_latLng.getLatitude() + 0.02, location_latLng.getLongitude() + 0.02)));
		GroundOverlay groundOverlay = mapview.getMap().addGroundOverlay(groundOverlayOp);
		return groundOverlay;
	}

	BroadcastReceiver imageBroadcastReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String FJBDLJ = intent.getStringArrayListExtra("list").get(0);
			Overlays.add(drawGroundOverlay(FJBDLJ));
		}
	};

	public void onDestroyView()
	{
		super.onDestroy();
		TencentLocationManager locationManager = TencentLocationManager.getInstance(getActivity());
		locationManager.removeUpdates(this);
		if (imageIntentFilter != null)
		{
			getActivity().unregisterReceiver(imageBroadcastReceiver);
		}

	};

	class TimeThread extends Thread
	{
		private boolean isSleep = true;
		private boolean stop = false;

		public void run()
		{
			Long starttime = 0l;
			while (!stop)
			{
				if (isSleep)
				{
				} else
				{
					try
					{
						Thread.sleep(1000);
						starttime = starttime + 1000;
						Message message = handler.obtainMessage();
						message.arg1 = AppContext.TIME_STOPWATCH;
						message.obj = utils.formatDuring(starttime);
						message.sendToTarget();
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		public void setSleep(boolean sleep)
		{
			isSleep = sleep;
		}

		public void setStop(boolean stop)
		{
			this.stop = stop;
		}
	}

	private void startTrack()
	{
		// final LatLng swLatLng1 = new LatLng(location_latLng.getLatitude() -
		// 0.5, location_latLng.getLongitude() - 0.5);
		// final LatLng neLatLng2 = new LatLng(location_latLng.getLatitude() -
		// 0.2, location_latLng.getLongitude() - 0.25);
		// final LatLng neLatLng3 = new LatLng(location_latLng.getLatitude() +
		// 0.25, location_latLng.getLongitude() - 0.25);
		// final LatLng neLatLng4 = new LatLng(location_latLng.getLatitude() +
		// 0.5, location_latLng.getLongitude() + 0.5);
		// final LatLng neLatLng5 = new LatLng(location_latLng.getLatitude() +
		// 0.3, location_latLng.getLongitude() + 0.75);
		// final LatLng neLatLng6 = new LatLng(location_latLng.getLatitude() -
		// 0.5, location_latLng.getLongitude() + 0.25);
		// list_LatLng_Bounds.add(swLatLng1);
		// list_LatLng_Bounds.add(neLatLng2);
		// list_LatLng_Bounds.add(neLatLng3);
		// list_LatLng_Bounds.add(neLatLng4);
		// list_LatLng_Bounds.add(neLatLng5);
		// list_LatLng_Bounds.add(neLatLng6);
		// polygon = drawPolygon(list_LatLng_Bounds);
		// Overlays.add(polygon);
		NewDataToast.makeText(getActivity(), "正在记录轨迹，请保持行走", appContext.isAppSound(), R.raw.starttrack).show();
	}

	// 添加新的巡护情况
	private void AddNewBHQ_XHQK(String XHID)
	{
		dt_manager dt_manager = appContext.getUserInfo(getActivity());
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("XHID", XHID);
		hashMap.put("XHRY", dt_manager.getid());
		hashMap.put("XHRQ", utils.getTime());
		hashMap.put("XHRYXM", dt_manager.getreal_name());
		hashMap.put("XHKSSJ", utils.getTime());
		hashMap.put("XHZT", "1");
		hashMap.put("v_flag", "A");

		String params = HttpUrlConnect.setParams("APP.AddNewBHQ_XHQK", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					// Toast.makeText(getActivity(), "已保存轨迹！",
					// Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getActivity(), "AddNewBHQ_XHQK联网失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void FinishBHQ_XHQK(String XHID)
	{
		String LC = "";
		if (String.valueOf(XHLC).length() > 5)
		{
			LC = String.valueOf(XHLC).substring(0, String.valueOf(XHLC).lastIndexOf(".") + 3);
		}
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("XHJSSJ", utils.getTime());
		hashMap.put("XHLC", LC);
		hashMap.put("XHXSS", XHXSS);
		hashMap.put("XHFZS", XHFZS);
		hashMap.put("XHZT", "3");
		hashMap.put("XHID", XHID);
		hashMap.put("v_flag", "A");

		String params = HttpUrlConnect.setParams("APP.FinishBHQ_XHQK", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					// Toast.makeText(getActivity(), "已保存轨迹！",
					// Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getActivity(), "FinishBHQ_XHQK联网失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void StopBHQ_XHQK(String XHID)
	{
		String LC = "";
		if (String.valueOf(XHLC).length() > 5)
		{
			LC = String.valueOf(XHLC).substring(0, String.valueOf(XHLC).lastIndexOf(".") + 3);
		}
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("XHLC", LC);
		hashMap.put("XHXSS", XHXSS);
		hashMap.put("XHFZS", XHFZS);
		hashMap.put("XHZT", "2");
		hashMap.put("XHID", XHID);
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.StopBHQ_XHQK", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					// Toast.makeText(getActivity(), "已保存轨迹！",
					// Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getActivity(), "StopBHQ_XHQK联网失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void ContinueBHQ_XHQK(String XHID)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("XHZT", "1");
		hashMap.put("XHID", XHID);
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.ContinueBHQ_XHQK", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					// Toast.makeText(getActivity(), "已保存轨迹！",
					// Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getActivity(), "ContinueBHQ_XHQK联网失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void AddNewBHQ_XHQK_GJ(String XHID)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("XHID", XHID);
		hashMap.put("X", String.valueOf(location_latLng.getLongitude()));
		hashMap.put("Y", String.valueOf(location_latLng.getLatitude()));
		hashMap.put("JLSJ", utils.getTime());
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.AddNewBHQ_XHQK_GJ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					// Toast.makeText(getActivity(), "已保存轨迹！",
					// Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getActivity(), "AddNewBHQ_XHQK_GJ联网失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void AddNewBHQ_XHQK_ZTCZ(String XHID, String SZCZ)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("XHID", XHID);
		hashMap.put("ZTSJD", utils.getTime());
		hashMap.put("SZCZ", SZCZ);
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.AddNewBHQ_XHQK_ZTCZ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					// Toast.makeText(getActivity(), "已保存轨迹！",
					// Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(getActivity(), "AddNewBHQ_XHQK_ZTCZ联网失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
