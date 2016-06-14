package com.bhq.ui;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.adapter.XHXL_Adapter;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.BHQ_XHQK;
import com.bhq.bean.BHQ_XHQK_GJ;
import com.bhq.bean.BHQ_XHQK_ZTCZ;
import com.bhq.bean.BHQ_XHXL_GJ;
import com.bhq.bean.Result;
import com.bhq.bean.Track;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.CoordinateConvertUtil;
import com.bhq.common.Gps;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.net.HttpUrlConnect;
import com.bhq.widget.CustomDialog_xhxl;
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
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
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
public class Offline_PatrolControlFragment extends Fragment implements TencentLocationListener
{
    String hb = "";
    String sd = "";
    List<Circle> list_Circle = new ArrayList<>();
    String XLID = "-1";
    dt_manager_offline dt_manager_offline;
    XHXL_Adapter xhxl_adapter;
    CustomDialog_xhxl customDialog_xhxl;
    IntentFilter imageIntentFilter;
    private WindowManager mWindowManager;
    TimeThread timethread;
    // Polygon polygon;
    double max;
    double min;
    List<String> list_xlid = new ArrayList<String>();
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
    LatLng location_latLng = new LatLng(24.430833, 113.298611);// 初始化定位
    LatLng lastlatLng;// 初始化定位
    LatLng lastlatLng_xl;// 初始化定位
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
    TextView tv_yx;
    @ViewById
    TextView tv_starttime;
    @ViewById
    TextView tv_runtime;
    @ViewById
    RelativeLayout rl_mapmore;
    @ViewById
    RelativeLayout rl_selectlx;
    @ViewById
    LinearLayout ll_GPS;
    @ViewById
    LinearLayout ll_pb_gps;
    @ViewById
    Button btn_complete;
    @ViewById
    Button btn_selectLX;
    @ViewById
    Button btn_continue;
    private Projection mProjection;

    String starttime;
    Double XHLC = 0D;
    //    Double lastXHLC = 0D;
    String XHXSS = "";
    String XHFZS = "";

    @Click
    void tv_yx()
    {
        if (tv_yx.getText().equals("影像"))
        {
            tv_yx.setText("2D");
            tencentMap.setSatelliteEnabled(true);
        } else
        {
            tv_yx.setText("影像");
            tencentMap.setSatelliteEnabled(false);
        }

    }

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
        rl_selectlx.setVisibility(View.VISIBLE);
        btn_selectLX.setVisibility(View.VISIBLE);
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
        lastlatLng = null;
        tencentMap.clearAllOverlays();
        FinishBHQ_XHQK(XHID);
        AddNewBHQ_XHQK_ZTCZ(XHID, "3");// 设置结束状态
    }

    @Click
    void btn_selectLX()
    {
        showdialog_xhxl();
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
                AppContext appContext = (AppContext) getActivity().getApplication();
                appContext.setXHID(XHID);
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
//            tencentMap.removeOverlay(marker);
            tencentMap.animateTo(location_latLng);
//            addMarker(location_latLng, R.drawable.location1);
        }
    }

    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.arg1)
            {
                case AppContext.TIME_STOPWATCH:
                    String aa = msg.obj.toString();
                    tv_runtime.setText(msg.obj.toString());
                    XHXSS = msg.obj.toString().substring(0, 2);
                    XHFZS = msg.obj.toString().substring(3, 5);
                    break;

                default:
                    break;
            }
        }

        ;
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
        dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(getActivity(), dt_manager_offline.class);
        TencentLocationRequest request = TencentLocationRequest.create();
        TencentLocationManager locationManager = TencentLocationManager.getInstance(getActivity());
        locationManager.setCoordinateType(1);//设置坐标系为gcj02坐标，1为GCJ02，0为WGS84
        error = locationManager.requestLocationUpdates(request, this);
        Overlays = new ArrayList<Object>();
//        getLoacion();//仅仅使用gps模块，测试可用（可能需要走动到信号好的地方测试）
        return rootView;
    }

    public void getLoacion()
    {
        String serviceString = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(serviceString);
        String provider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);
        getLocationInfo(location);
        locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
    }

    private final LocationListener locationListener = new LocationListener()
    {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            switch (status)
            {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Toast.makeText(getActivity(), "当前GPS状态为可见状态", Toast.LENGTH_SHORT).show();
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(getActivity(), "当前GPS状态为服务区外状态", Toast.LENGTH_SHORT).show();
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(getActivity(), "当前GPS状态为暂停服务状态", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            getLocationInfo(null);

        }

        @Override
        public void onProviderDisabled(String provider)
        {
            getLocationInfo(null);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            getLocationInfo(location);
            //            Toast.makeText(getActivity(), "位置改变了::::::::::::", Toast.LENGTH_SHORT).show();
        }
    };

    private void getLocationInfo(Location location)
    {
        String latLongInfo;
        if (location != null)
        {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongInfo = "Lat:" + lat + "\nLong:" + lng + "\nlat:" + location_latLng.getLatitude() + "\nLong:" + location_latLng.getLongitude();
            Toast.makeText(getActivity(), latLongInfo, Toast.LENGTH_LONG).show();
            //            lo.setText(latLongInfo);
        } else
        {
            latLongInfo = "No location found";
            //            lo.setText(latLongInfo);
            Toast.makeText(getActivity(), "无法定位", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error, String reason)
    {
        if (TencentLocation.ERROR_OK == error) // 定位成功
        {
            location_latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Gps gPS_84 = CoordinateConvertUtil.gcj_To_Gps84(location_latLng.getLatitude(), location_latLng.getLongitude());
            AppContext appContext = (AppContext) getActivity().getApplication();
            appContext.setLOCATION_X(String.valueOf(gPS_84.getWgLat()));
            appContext.setLOCATION_Y(String.valueOf(gPS_84.getWgLon()));
            // 每隔15秒记录轨迹
            newtime = System.currentTimeMillis();
            if (lasttime == 0l)
            {
                lasttime = System.currentTimeMillis();
            }
            int diff = (int) (newtime - lasttime) / 1000;
            if (diff > 15)
            {
//                if (utils.isCurrentTimeBetWeen())//判断当前时间是否在指定时间段内
//                {
//                    uploadLocationInfo(location, gPS_84);// 每隔15秒上次一次最新位置
//                }
                uploadLocationInfo(location, gPS_84);// 每隔15秒上次一次最新位置
                lasttime = newtime;//传递值
            }
            //1记录轨迹点2更新UI信息
            if (isStart)
            {
                tencentMap.animateTo(location_latLng);
                if (lastlatLng != null)
                {
//                    XHLC = XHLC + mProjection.distanceBetween(lastlatLng, location_latLng); // 计算总里程
//                    if (String.valueOf(XHLC).length() > 5)   //实时更新ui中的里程信息
//                    {
//                        tv_runlength.setText(String.valueOf(XHLC).substring(0, String.valueOf(XHLC).lastIndexOf(".") + 3) + "m");
//                    } else
//                    {
//                        tv_runlength.setText(XHLC + "m");
//                    }
                    // 计算两点距离
                    Double distance = mProjection.distanceBetween(lastlatLng, location_latLng);
                    if (distance >= 50f)
                    {
                        AddNewBHQ_XHQK_GJ(location, XHID, String.valueOf(distance));//添加满足条件的轨迹点
//                        lastXHLC = XHLC;//上一段里程（以最后一次保存轨迹点为终点）
                        // 画轨迹
                        PolylineOptions lineOpt = new PolylineOptions();
                        lineOpt.add(lastlatLng);
                        lineOpt.add(location_latLng);
                        Polyline line = tencentMap.addPolyline(lineOpt);
                        line.setColor(getResources().getColor(R.color.black));
                        line.setWidth(8f);
                        Overlays.add(line);
                        //实时更新ui中的里程信息
                        XHLC = XHLC + distance; // 计算总里程
                        if (String.valueOf(XHLC).length() > 5)
                        {
                            tv_runlength.setText(String.valueOf(XHLC).substring(0, String.valueOf(XHLC).lastIndexOf(".") + 3) + "m");
                        } else
                        {
                            tv_runlength.setText(XHLC + "m");
                        }

                        lastlatLng = location_latLng;     // 传递值放在最后
                    }
                } else
                {
                    AddNewBHQ_XHQK_GJ(location, XHID, "0");// 添加开始点的轨迹
                    lastlatLng = location_latLng;
                }


            }
        }

    }

    @Override
    public void onStatusUpdate(String name, int status, String desc)
    {

        if (status == 2)// 位置权限拒绝
        {
            // Toast.makeText(getActivity(), " 位置权限拒绝！",
            // Toast.LENGTH_SHORT).show();
        } else if (status == 2)// 定位服务关闭
        {
            // Toast.makeText(getActivity(), " 定位服务关闭！",
            // Toast.LENGTH_SHORT).show();
        } else if (status == 1)// 定位服务开启
        {
            // Toast.makeText(getActivity(), "定位服务开启！",
            // Toast.LENGTH_SHORT).show();
        } else if (status == -1)// 定位服务未知
        {
            // Toast.makeText(getActivity(), "定位服务未知！",
            // Toast.LENGTH_SHORT).show();
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

    }

    ;

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
        dt_manager_offline dt_manager_offline = (dt_manager_offline) SqliteDb.getCurrentUser(appContext, dt_manager_offline.class);
        BHQ_XHQK bhq_XHQK = new BHQ_XHQK();
        bhq_XHQK.setXHID(XHID);
        bhq_XHQK.setXLID(XLID);
        bhq_XHQK.setXHRY(dt_manager_offline.getid());
        bhq_XHQK.setXHRQ(utils.getTime());
        bhq_XHQK.setXHRYXM(dt_manager_offline.getreal_name());
        bhq_XHQK.setXHKSSJ(utils.getTime());
        bhq_XHQK.setIsUpload("0");
        bhq_XHQK.setXHZT("1");
        SqliteDb.save(getActivity(), bhq_XHQK);
    }

    private void FinishBHQ_XHQK(String XHID)
    {
        String LC = "";
        if (String.valueOf(XHLC).length() > 5)
        {
            LC = String.valueOf(XHLC).substring(0, String.valueOf(XHLC).lastIndexOf(".") + 3);
        }
        BHQ_XHQK bhq_XHQK = (BHQ_XHQK) SqliteDb.getBHQ_XHQK(getActivity(), BHQ_XHQK.class, XHID);
        bhq_XHQK.setXHJSSJ(utils.getTime());
        bhq_XHQK.setXHLC(LC);
        bhq_XHQK.setXHXSS(XHXSS);
        bhq_XHQK.setXHFZS(XHFZS);
        bhq_XHQK.setXHZT("3");
        bhq_XHQK.setIsUpload("0");
        bhq_XHQK.setXHID(XHID);
        SqliteDb.save(getActivity(), bhq_XHQK);
    }

    private void StopBHQ_XHQK(String XHID)
    {
        String LC = "";
        if (String.valueOf(XHLC).length() > 5)
        {
            LC = String.valueOf(XHLC).substring(0, String.valueOf(XHLC).lastIndexOf(".") + 3);
        }
        BHQ_XHQK bhq_XHQK = (BHQ_XHQK) SqliteDb.getBHQ_XHQK(getActivity(), BHQ_XHQK.class, XHID);
        bhq_XHQK.setXHLC(LC);
        bhq_XHQK.setXHXSS(XHXSS);
        bhq_XHQK.setXHFZS(XHFZS);
        bhq_XHQK.setIsUpload("0");
        bhq_XHQK.setXHZT("2");
        bhq_XHQK.setXHID(XHID);
        SqliteDb.save(getActivity(), bhq_XHQK);
    }

    private void ContinueBHQ_XHQK(String XHID)
    {
        BHQ_XHQK bhq_XHQK = (BHQ_XHQK) SqliteDb.getBHQ_XHQK(getActivity(), BHQ_XHQK.class, XHID);
        bhq_XHQK.setXHZT("1");
        bhq_XHQK.setIsUpload("0");
        bhq_XHQK.setXHID(XHID);
        SqliteDb.save(getActivity(), bhq_XHQK);
    }

    private void AddNewBHQ_XHQK_GJ(TencentLocation location, String XHID, String distance)
    {
//        if (Double.valueOf(distance)<0f)
//        {
//            distance="0";
//        }
        Gps gPS = CoordinateConvertUtil.gcj_To_Gps84(location_latLng.getLatitude(), location_latLng.getLongitude());
        String GJID = java.util.UUID.randomUUID().toString();
        BHQ_XHQK_GJ bhq_XHQK_GJ = new BHQ_XHQK_GJ();
        bhq_XHQK_GJ.setGJID(GJID);
        bhq_XHQK_GJ.setXHID(XHID);
        bhq_XHQK_GJ.setIsUpload("0");
        bhq_XHQK_GJ.setX(String.valueOf(gPS.getWgLon()));
        bhq_XHQK_GJ.setY(String.valueOf(gPS.getWgLat()));
        bhq_XHQK_GJ.setJLSJ(utils.getTime());
        bhq_XHQK_GJ.setIntervaldistance(distance);
        bhq_XHQK_GJ.setSpeed(String.valueOf(location.getSpeed()));
        bhq_XHQK_GJ.setBearing(String.valueOf(location.getBearing()));
        bhq_XHQK_GJ.setAccuracy(String.valueOf(location.getAccuracy()));
        bhq_XHQK_GJ.setAltitude(String.valueOf(location.getAltitude()));
        SqliteDb.save(getActivity(), bhq_XHQK_GJ);
    }

    private void AddNewBHQ_XHQK_ZTCZ(String XHID, String SZCZ)
    {
        String ZTCZID = java.util.UUID.randomUUID().toString();
        BHQ_XHQK_ZTCZ bhq_XHQK_ZTCZ = new BHQ_XHQK_ZTCZ();
        bhq_XHQK_ZTCZ.setZTID(ZTCZID);
        bhq_XHQK_ZTCZ.setXHID(XHID);
        bhq_XHQK_ZTCZ.setIsUpload("0");
        bhq_XHQK_ZTCZ.setZTSJD(utils.getTime());
        bhq_XHQK_ZTCZ.setSZCZ(SZCZ);
        SqliteDb.save(getActivity(), bhq_XHQK_ZTCZ);
    }

    public void uploadLocationInfo(TencentLocation location, Gps gPS_84)
    {
        dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(getActivity(), dt_manager_offline.class);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("userid", dt_manager_offline.getid());
        hashMap.put("username", dt_manager_offline.getreal_name());
        hashMap.put("lat", String.valueOf(gPS_84.getWgLat()));
        hashMap.put("lng", String.valueOf(gPS_84.getWgLon()));
        hashMap.put("altitude", String.valueOf(location.getAltitude()));
        hashMap.put("accuracy", String.valueOf(location.getAccuracy()));
        hashMap.put("jlsj", utils.getTime());
        hashMap.put("v_flag", "A");
        String params = HttpUrlConnect.setParams("APP.uploadLocationInfo", "0", hashMap);
        new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
        {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo)
            {
                String a = responseInfo.result;
                Result result = JSON.parseObject(responseInfo.result, Result.class);
                if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                {

                } else
                {
                }
            }

            @Override
            public void onFailure(HttpException error, String msg)
            {

            }
        });
    }

    private void showdialog_xhxl()
    {
        View dialog_layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.customdialog_xhxl, null);
        customDialog_xhxl = new CustomDialog_xhxl(getActivity(), R.style.MyDialog, dialog_layout);
        ListView lv_department = (ListView) dialog_layout.findViewById(R.id.lv_department);
        Button btn_cancle = (Button) dialog_layout.findViewById(R.id.btn_cancle);
        list_xlid = SqliteDb.getAllXHLXID(getActivity(), dt_manager_offline.getid());
        xhxl_adapter = new XHXL_Adapter(getActivity(), list_xlid);
        lv_department.setAdapter(xhxl_adapter);
        btn_cancle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                customDialog_xhxl.dismiss();
            }
        });
        lv_department.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                rl_selectlx.setVisibility(View.GONE);
                btn_selectLX.setVisibility(View.GONE);
                customDialog_xhxl.dismiss();
                XLID = list_xlid.get(position);
                List<BHQ_XHXL_GJ> list = SqliteDb.getXHXL_GJ(getActivity(), XLID);
                for (int i = 0; i < list.size(); i++)
                {
                    LatLng latlng = new LatLng(Double.valueOf(list.get(i).getY()), Double.valueOf(list.get(i).getX()));
                    Gps gPS = CoordinateConvertUtil.gps84_To_Gcj02(latlng.getLatitude(), latlng.getLongitude());
                    latlng = new LatLng(gPS.getWgLat(), gPS.getWgLon());
                    if (list.get(i).getQZD().equals("1"))
                    {
//                        addCircle(latlng,R.color.bg_start);
                        Circle circle = tencentMap.addCircle(new CircleOptions().center(latlng).radius(150d).fillColor(Color.argb(100, 177, 15, 0)).strokeColor(getResources().getColor(R.color.bg_start)).strokeWidth(1f));
                        Drawable drawable = getResources().getDrawable(R.drawable.qd);
                        Bitmap bitmap = utils.drawable2Bitmap(drawable);
                        tencentMap.addMarker(new MarkerOptions().position(latlng).title("").icon(new BitmapDescriptor(bitmap)));
                        tencentMap.animateTo(latlng);
                    } else if (list.get(i).getQZD().equals("2"))
                    {
//                        addCircle(latlng,R.color.bg_end);
//                        Circle circle = tencentMap.addCircle(new CircleOptions().center(latlng).radius(150d).fillColor(getResources().getColor(R.color.bg_end)).strokeColor(getResources().getColor(R.color.bg_end)).strokeWidth(1f));
                        Circle circle = tencentMap.addCircle(new CircleOptions().center(latlng).radius(150d).fillColor(Color.argb(100, 4, 181, 0)).strokeColor(getResources().getColor(R.color.bg_end)).strokeWidth(1f));
                        Drawable drawable = getResources().getDrawable(R.drawable.zd);
                        Bitmap bitmap = utils.drawable2Bitmap(drawable);
                        tencentMap.addMarker(new MarkerOptions().position(latlng).title("").icon(new BitmapDescriptor(bitmap)));
                    } else
                    {
//                        addCircle(latlng,R.color.bg_middle);
                        Circle circle = tencentMap.addCircle(new CircleOptions().center(latlng).radius(150d).fillColor(Color.argb(100, 0, 202, 215)).strokeColor(getResources().getColor(R.color.bg_middle)).strokeWidth(1f));
                        list_Circle.add(circle);
                        Drawable drawable = getResources().getDrawable(R.drawable.zj);
                        Bitmap bitmap = utils.drawable2Bitmap(drawable);
                        tencentMap.addMarker(new MarkerOptions().position(latlng).title("").icon(new BitmapDescriptor(bitmap)));
                    }

//                    PolylineOptions lineOpt = new PolylineOptions();
//                    lineOpt.add(lastlatLng_xl);
//                    lineOpt.add(latlng);
//                    lastlatLng_xl = latlng;
//                    Polyline line = tencentMap.addPolyline(lineOpt);
//                    line.setColor(getResources().getColor(R.color.black));
//                    line.setWidth(10f);
//                    Overlays.add(line);
                }
            }
        });
        customDialog_xhxl.setCanceledOnTouchOutside(false);
        customDialog_xhxl.show();
    }

    protected void addCircle(LatLng latlng, int color)
    {
        Circle circle = tencentMap.addCircle(new CircleOptions().center(latlng).radius(150d).fillColor(getResources().getColor(color)).strokeColor(0xff000000).strokeWidth(0f));
        list_Circle.add(circle);
    }
}
