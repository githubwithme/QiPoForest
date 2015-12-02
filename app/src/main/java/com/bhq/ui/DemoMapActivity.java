package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;

import org.androidannotations.annotations.EFragment;

@SuppressLint("NewApi")
@EFragment
public class DemoMapActivity extends Fragment implements TencentLocationListener
{

	@Override
	public void onLocationChanged(TencentLocation arg0, int arg1, String arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusUpdate(String arg0, int arg1, String arg2)
	{
		// TODO Auto-generated method stub

	}
	// TencentMap tencentMap;
	// IntentFilter imageIntentFilter;
	// private WindowManager mWindowManager;
	// double max;
	// double min;
	// List<Double> list_lat = new ArrayList<Double>();
	// List<Double> list_lng = new ArrayList<Double>();
	// AppContext appContext;
	// Long newtime = 0l;
	// Long lasttime = 0l;
	// private List<Object> Overlays;
	// Boolean isStart = false;
	// Track track;
	// String uuid;
	// DbUtils db;
	// String result = new String();
	// TimeThread timethread;
	// int error;
	// @ViewById
	// MapView mapview;
	// @ViewById
	// TextView tv_gps;
	// @ViewById
	// ImageButton imgbtn_home;
	// @ViewById
	// Button btn_location;
	// TableLayout tl_satellite;
	// TableLayout tl_erwei;
	//
	// @ViewById
	// Button btn_runcontrol;
	// @ViewById
	// TextView tv_runlength;
	// @ViewById
	// TextView tv_starttime;
	// @ViewById
	// TextView tv_runtime;
	// @ViewById
	// RelativeLayout rl_mapmore;
	// @ViewById
	// LinearLayout ll_GPS;
	// @ViewById
	// LinearLayout ll_pb_gps;
	// @ViewById
	// Button btn_complete;
	// @ViewById
	// Button btn_continue;
	// private Projection mProjection;
	// String starttime;
	// private TencentLocationManager mLocationManager;
	// TencentLocationListener listener;
	// TencentLocationRequest request;
	// private TencentLocation mLocation;
	// private LocationOverlay mLocationOverlay;
	// private String mRequestParams;
	// @ViewById
	// TextView mStatus;
	// @ViewById
	// MapView mMapView;
	//
	// Handler handler = new Handler()
	// {
	// public void handleMessage(android.os.Message msg)
	// {
	// switch (msg.arg1)
	// {
	// case AppContext.TIME_STOPWATCH:
	// tv_runtime.setText(msg.obj.toString());
	// break;
	// case AppContext.DISTANCEBETWEEN:
	// tv_runlength.setText(msg.obj.toString());
	// break;
	//
	// default:
	// break;
	// }
	// };
	// };
	//
	// @Click
	// void btn_location()
	// {
	// if (mLocation != null)
	// {
	// mMapView.getController().animateTo(of(mLocation));
	// }
	// }
	//
	// @Click
	// void btn_runcontrol()
	// {
	// if (btn_runcontrol.getText().equals("开始巡护"))
	// {
	// if (isStart)// 已经开始
	// {
	// timethread.setStop(false);
	// timethread.setSleep(false);
	// } else
	// {
	// isStart = true;
	// startTrack();
	// start_latlng = location_latLng;
	// tv_starttime.setText(utils.getHours());
	// starttime = utils.getTime();
	// timethread = new TimeThread();
	// timethread.setStop(false);
	// timethread.setSleep(false);
	// timethread.start();
	// if (utils.openGPSSettings(getActivity()) == 1)// gps已打开
	// {
	// // if (utils.openGPSSettings(getActivity()) == 0)// 定位成功
	// // {
	// // isStart = true;
	// // startTrack();
	// // start_latlng = location_latLng;
	// // tv_starttime.setText(utils.getHours());
	// // timethread = new TimeThread();
	// // timethread.setStop(false);
	// // timethread.setSleep(false);
	// // timethread.start();
	// // }
	//
	// } else
	// // gps还没打开
	// {
	// // ll_GPS.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// // R.anim.in_left));
	// // btn_runcontrol.setVisibility(View.GONE);
	// // ll_pb_gps.setVisibility(View.VISIBLE);
	// // ll_pb_gps.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// // R.anim.zoomin));
	// // return;
	// }
	//
	// }
	// btn_runcontrol.setText("暂停巡护");
	// tv_runtime.setVisibility(View.VISIBLE);
	// tv_runlength.setVisibility(View.VISIBLE);
	// tv_starttime.setVisibility(View.VISIBLE);
	// ll_GPS.setVisibility(View.VISIBLE);
	// rl_mapmore.setVisibility(View.VISIBLE);
	// ll_GPS.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.in_left));
	// rl_mapmore.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.in_right));
	// tv_runtime.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.in_left));
	// tv_starttime.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.in_left));
	// tv_runlength.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.in_left));
	// } else if (btn_runcontrol.getText().equals("暂停巡护"))
	// {
	// timethread.setStop(false);
	// timethread.setSleep(true);
	// btn_complete.setVisibility(View.VISIBLE);
	// btn_continue.setVisibility(View.VISIBLE);
	// btn_complete.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.in_right_map));
	// btn_continue.setAnimation(AnimationUtils.loadAnimation(getActivity(),
	// R.anim.in_left_map));
	// btn_runcontrol.setVisibility(View.GONE);
	// btn_runcontrol.setText("开始巡护");
	// }
	//
	// }
	//
	// private void animateToLocation()
	// {
	// if (location_latLng != null)
	// {
	// tencentMap.removeOverlay(marker);
	// tencentMap.animateTo(location_latLng);
	// addMarker(location_latLng, R.drawable.ic_account);
	// }
	// }
	//
	// @AfterViews
	// void afterOncreate()
	// {
	// initMapView();
	// mStatus.setTextColor(Color.RED);
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState)
	// {
	// View rootView = inflater.inflate(R.layout.activity_demo_map, container,
	// false);
	// getActivity().getActionBar().hide();
	// mLocationManager = TencentLocationManager.getInstance(getActivity());
	// mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
	// // 已默认，通常不必进行此调用
	// return rootView;
	// }
	//
	// @Override
	// public void onCreate(Bundle savedInstanceState)
	// {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// }
	//
	// @Override
	// public void onPause()
	// {
	// // TODO Auto-generated method stub
	// super.onPause();
	// mMapView.onPause();
	// stopLocation();
	// }
	//
	// @Override
	// public void onResume()
	// {
	// // TODO Auto-generated method stub
	// super.onResume();
	// mMapView.onResume();
	// startLocation();
	// }
	//
	// @Override
	// public void onStop()
	// {
	// // TODO Auto-generated method stub
	// super.onStop();
	// mMapView.onStop();
	// }
	//
	// @Override
	// public void onDestroy()
	// {
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// mMapView.onDestroy();
	// }
	//
	// private void initMapView()
	// {
	// mMapView.setBuiltInZoomControls(true);
	// mMapView.getController().setZoom(9);
	// Bitmap bmpMarker = BitmapFactory.decodeResource(getResources(),
	// R.drawable.mark_location);
	// mLocationOverlay = new LocationOverlay(bmpMarker);
	// mMapView.addOverlay(mLocationOverlay);
	//
	// tencentMap = mapview.getMap();
	// tencentMap.setZoom(15);
	// animateToLocation();
	// }
	//
	// @Override
	// public void onLocationChanged(TencentLocation location, int error, String
	// reason)
	// {
	// if (error == TencentLocation.ERROR_OK)
	// {
	// mLocation = location;
	// // 定位成功
	// StringBuilder sb = new StringBuilder();
	// sb.append("定位参数=").append(mRequestParams).append("\n");
	// sb.append("(纬度=").append(location.getLatitude()).append(",经度=").append(location.getLongitude()).append(",精度=").append(location.getAccuracy()).append("), 来源=").append(location.getProvider()).append(", 地址=").append(location.getAddress());
	// // 更新 status
	// mStatus.setText(sb.toString());
	// // 更新 location 图层
	// mLocationOverlay.setAccuracy(mLocation.getAccuracy());
	// mLocationOverlay.setGeoCoords(of(mLocation));
	// mMapView.invalidate();
	// Toast.makeText(getActivity(), sb.toString(), Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	// @Override
	// public void onStatusUpdate(String name, int status, String desc)
	// {
	// // ignore
	// }
	//
	// private void startLocation()
	// {
	// TencentLocationRequest request = TencentLocationRequest.create();
	// request.setInterval(5000);
	// mLocationManager.requestLocationUpdates(request, this);
	//
	// mRequestParams = request.toString();
	// }
	//
	// private void stopLocation()
	// {
	// mLocationManager.removeUpdates(this);
	// }
	//
	// private static GeoPoint of(TencentLocation location)
	// {
	// GeoPoint ge = new GeoPoint((int) (location.getLatitude() * 1E6), (int)
	// (location.getLongitude() * 1E6));
	// return ge;
	// }
	// }
	//
	// class LocationOverlay extends Overlay
	// {
	//
	// GeoPoint geoPoint;
	// Bitmap bmpMarker;
	// float fAccuracy = 0f;
	//
	// public LocationOverlay(Bitmap mMarker)
	// {
	// bmpMarker = mMarker;
	// }
	//
	// public void setGeoCoords(GeoPoint point)
	// {
	// if (geoPoint == null)
	// {
	// geoPoint = new GeoPoint(point.getLatitudeE6(), point.getLongitudeE6());
	// } else
	// {
	// geoPoint.setLatitudeE6(point.getLatitudeE6());
	// geoPoint.setLongitudeE6(point.getLongitudeE6());
	// }
	// }
	//
	// public void setAccuracy(float fAccur)
	// {
	// fAccuracy = fAccur;
	// }
	//
	// @Override
	// public void draw(Canvas canvas, MapView mapView)
	// {
	// if (geoPoint == null)
	// {
	// return;
	// }
	// Projection mapProjection = mapView.getProjection();
	// Paint paint = new Paint();
	// Point ptMap = mapProjection.toPixels(geoPoint, null);
	// paint.setColor(Color.BLUE);
	// paint.setAlpha(8);
	// paint.setAntiAlias(true);
	//
	// float fRadius = mapProjection.metersToEquatorPixels(fAccuracy);
	// canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);
	// paint.setStyle(Style.STROKE);
	// paint.setAlpha(200);
	// canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);
	//
	// if (bmpMarker != null)
	// {
	// paint.setAlpha(255);
	// canvas.drawBitmap(bmpMarker, ptMap.x - bmpMarker.getWidth() / 2, ptMap.y
	// - bmpMarker.getHeight() / 2, paint);
	// }
	//
	// super.draw(canvas, mapView);
	// }
	//
	// class TimeThread extends Thread
	// {
	// private boolean isSleep = true;
	// private boolean stop = false;
	//
	// public void run()
	// {
	// Long starttime = 0l;
	// while (!stop)
	// {
	// if (isSleep)
	// {
	// } else
	// {
	// try
	// {
	// Thread.sleep(1000);
	// starttime = starttime + 1000;
	// Message message = handler.obtainMessage();
	// message.arg1 = AppContext.TIME_STOPWATCH;
	// message.obj = utils.formatDuring(starttime);
	// message.sendToTarget();
	//
	// mProjection = mapview.getProjection();
	// Message message2 = handler.obtainMessage();
	// message2.arg1 = AppContext.DISTANCEBETWEEN;
	// message2.obj = mProjection.distanceBetween(start_latlng,
	// location_latLng);
	// message2.sendToTarget();
	// } catch (InterruptedException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	//
	// public void setSleep(boolean sleep)
	// {
	// isSleep = sleep;
	// }
	//
	// public void setStop(boolean stop)
	// {
	// this.stop = stop;
	// }
	// }

}
