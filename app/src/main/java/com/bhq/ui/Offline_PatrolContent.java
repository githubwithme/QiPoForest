package com.bhq.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.bhq.R;
import com.bhq.app.AppContext;
import com.bhq.bean.BHQ_XHQK;
import com.bhq.bean.BHQ_XHQK_GJ;
import com.bhq.common.CoordinateConvertUtil;
import com.bhq.common.Gps;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.lidroid.xutils.DbUtils;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.Polygon;
import com.tencent.mapsdk.raster.model.PolygonOptions;
import com.tencent.mapsdk.raster.model.Polyline;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.patrolcontent)
public class Offline_PatrolContent extends Activity
{
    Polygon polygon;
    double max;
    double min;
    List<Double> list_lat = new ArrayList<Double>();
    List<Double> list_lng = new ArrayList<Double>();
    List<LatLng> list_LatLng_Bounds = new ArrayList<LatLng>();
    AppContext appContext;
    Long newtime = 0l;
    Long lasttime = 0l;
    private List<Object> Overlays;
    Boolean isStart = false;
    // Track track;
    BHQ_XHQK bhq_XHQK;
    String uuid;
    DbUtils db;
    Marker marker;
    TencentMap tencentMap;
    String result = new String();
    // LatLng latLng = null;
    LatLng location_latLng = new LatLng(23.588219, 113.819251);
    int error;
    @ViewById
    ImageButton imgbtn_back;
    @ViewById
    MapView mapview;
    @ViewById
    TextView tv_starttime;
    @ViewById
    TextView tv_endtime;
    @ViewById
    TextView tv_xhsc;
    @ViewById
    TextView tv_xhlc;
    TableLayout tl_satellite;
    TableLayout tl_erwei;
    LatLng lastlatLng = null;

    @Click
    void imgbtn_back()
    {
        finish();
    }

    @AfterViews
    void afterOncreate()
    {
        tencentMap = mapview.getMap();
        // drawCustomPolygon();// 画出巡护范围
        GetBHQ_XHQK_GJList(bhq_XHQK.getXHID());// 画出轨迹
//        getAllGJ(bhq_XHQK.getXHID());
        showData();// 展示巡护信息

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        bhq_XHQK = (BHQ_XHQK) getIntent().getParcelableExtra("bean");
        appContext = (AppContext) getApplication();
        Overlays = new ArrayList<Object>();
    }

    private Polygon drawPolygon(List<LatLng> list_LatLng)
    {
        PolygonOptions polygonOp = new PolygonOptions();
        polygonOp.fillColor(0x55000077);
        polygonOp.strokeWidth(4);
        for (int i = 0; i < list_LatLng.size(); i++)
        {
            polygonOp.add(list_LatLng.get(i));
        }
        Polygon polygon = mapview.getMap().addPolygon(polygonOp);
        return polygon;
    }

    private Polyline drawPolyline()
    {
        final LatLng latLng1 = new LatLng(39.925961, 116.388171);
        final LatLng latLng2 = new LatLng(39.735961, 116.488171);
        final LatLng latLng3 = new LatLng(39.635961, 116.268171);

        // 如果要修改颜色，请直接使用4字节颜色或定义的变量
        PolylineOptions lineOpt = new PolylineOptions();
        lineOpt.add(latLng1);
        lineOpt.add(latLng2);
        lineOpt.add(latLng3);

        Polyline line = tencentMap.addPolyline(lineOpt);
        return line;
    }

    private void addMarker(LatLng latLng, int icon)
    {
        Drawable drawable = getResources().getDrawable(icon);
        Bitmap bitmap = utils.drawable2Bitmap(drawable);
        marker = tencentMap.addMarker(new MarkerOptions().position(latLng).title("").icon(new BitmapDescriptor(bitmap)));
        marker.hideInfoWindow();
    }

    private void showPatro(List<BHQ_XHQK_GJ> listNewData)
    {
        List<LatLng> list_latlng=new ArrayList<>();
        LatLng startlatLng = null;
        tencentMap.setZoom(15);// 设置地图显示大小
        List<Object> Overlays = new ArrayList<Object>();
        for (int i = 0; i < listNewData.size(); i++)
        {
            Gps gPS = CoordinateConvertUtil.gps84_To_Gcj02(Double.valueOf(listNewData.get(i).getY()), Double.valueOf(listNewData.get(i).getX()));
            LatLng latLng = new LatLng(gPS.getWgLat(), gPS.getWgLon());
            list_latlng.add(latLng);
            if (i == 0)
            {
                Gps gPS0 = CoordinateConvertUtil.gps84_To_Gcj02(Double.valueOf(listNewData.get(i).getY()), Double.valueOf(listNewData.get(i).getX()));
                startlatLng = new LatLng(gPS0.getWgLat(), gPS0.getWgLon());
                addMarker(startlatLng, R.drawable.location_start);
                tencentMap.animateTo(startlatLng);
                lastlatLng = latLng;
            }
            if (i == listNewData.size() - 1)
            {
                addMarker(latLng, R.drawable.location_end);
            }
            // latLng = new LatLng(utils.randomCommon(lastlatLng.getLatitude(),
            // lastlatLng.getLatitude() + 0.2, 1)[0],
            // utils.randomCommon(lastlatLng.getLongitude(),
            // lastlatLng.getLongitude() + 0.2, 1)[0]);

        }
        PolylineOptions lineOpt = new PolylineOptions();
        lineOpt.addAll(list_latlng);
        Polyline line = tencentMap.addPolyline(lineOpt);
        line.setColor(this.getResources().getColor(R.color.black));
        line.setWidth(4f);
        Overlays.add(line);
    }

    private void showAllGJ(String allgj)
    {
        LatLng startlatLng = null;
        tencentMap.setZoom(15);// 设置地图显示大小
        List<Object> Overlays = new ArrayList<Object>();
        JSONArray listNewData = JSONArray.parseArray(allgj);
        for (int i = 0; i < listNewData.size(); i++)
        {
            String x = listNewData.getJSONObject(i).getString("X");
            String y = listNewData.getJSONObject(i).getString("Y");
            Gps gPS = CoordinateConvertUtil.gps84_To_Gcj02(Double.valueOf(y), Double.valueOf(x));
            LatLng latLng = new LatLng(gPS.getWgLat(), gPS.getWgLon());
            if (i == 0)
            {
                Gps gPS0 = CoordinateConvertUtil.gps84_To_Gcj02(Double.valueOf(y), Double.valueOf(x));
                startlatLng = new LatLng(gPS0.getWgLat(), gPS0.getWgLon());
                addMarker(startlatLng, R.drawable.location_start);
                tencentMap.animateTo(startlatLng);
                lastlatLng = latLng;
            }
            if (i == listNewData.size() - 1)
            {
                addMarker(latLng, R.drawable.location_end);
            }
            // latLng = new LatLng(utils.randomCommon(lastlatLng.getLatitude(),
            // lastlatLng.getLatitude() + 0.2, 1)[0],
            // utils.randomCommon(lastlatLng.getLongitude(),
            // lastlatLng.getLongitude() + 0.2, 1)[0]);
            PolylineOptions lineOpt = new PolylineOptions();
            lineOpt.add(lastlatLng);
            lastlatLng = latLng;
            lineOpt.add(latLng);
            Polyline line = tencentMap.addPolyline(lineOpt);
            line.setColor(this.getResources().getColor(R.color.black));
            line.setWidth(4f);
            Overlays.add(line);
        }
    }

    private void showData()
    {
        tv_starttime.setText(bhq_XHQK.getXHKSSJ());
        tv_endtime.setText(bhq_XHQK.getXHJSSJ());
        tv_xhsc.setText(bhq_XHQK.getXHXSS().toString() + "时" + bhq_XHQK.getXHFZS().toString() + "分");
        tv_xhlc.setText(bhq_XHQK.getXHLC().toString() + "m");
    }

    private void drawCustomPolygon()
    {
        addMarker(location_latLng, R.drawable.location_start);
        isStart = true;
        lastlatLng = location_latLng;
        final LatLng swLatLng1 = new LatLng(location_latLng.getLatitude() - 0.5, location_latLng.getLongitude() - 0.5);
        final LatLng neLatLng2 = new LatLng(location_latLng.getLatitude() - 0.2, location_latLng.getLongitude() - 0.25);
        final LatLng neLatLng3 = new LatLng(location_latLng.getLatitude() + 0.25, location_latLng.getLongitude() - 0.25);
        final LatLng neLatLng4 = new LatLng(location_latLng.getLatitude() + 0.5, location_latLng.getLongitude() + 0.5);
        final LatLng neLatLng5 = new LatLng(location_latLng.getLatitude() + 0.3, location_latLng.getLongitude() + 0.75);
        final LatLng neLatLng6 = new LatLng(location_latLng.getLatitude() - 0.5, location_latLng.getLongitude() + 0.25);
        list_LatLng_Bounds.add(swLatLng1);
        list_LatLng_Bounds.add(neLatLng2);
        list_LatLng_Bounds.add(neLatLng3);
        list_LatLng_Bounds.add(neLatLng4);
        list_LatLng_Bounds.add(neLatLng5);
        list_LatLng_Bounds.add(neLatLng6);
        polygon = drawPolygon(list_LatLng_Bounds);
        Overlays.add(polygon);
    }

    private void GetBHQ_XHQK_GJList(String XHID)
    {
        List<BHQ_XHQK_GJ> listNewData = SqliteDb.GetBHQ_XHQK_GJList(Offline_PatrolContent.this, BHQ_XHQK_GJ.class, XHID);
        if (listNewData == null)
        {
            listNewData = new ArrayList<BHQ_XHQK_GJ>();
            Toast.makeText(Offline_PatrolContent.this, "暂无轨迹！", Toast.LENGTH_SHORT).show();
        } else
        {
            showPatro(listNewData);
        }
    }

    private void getAllGJ(String XHID)
    {
        String allgj = SqliteDb.getAllGJ();
        if (allgj.equals(""))
        {
            Toast.makeText(Offline_PatrolContent.this, "暂无轨迹！", Toast.LENGTH_SHORT).show();
        } else
        {
            showAllGJ(allgj);
        }
    }
}
