package com.bhq.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bhq.R;
import com.bhq.common.utils;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.showlocation)
public class ShowLocationInMap extends Activity
{

	Marker marker;
	TencentMap tencentMap;
	@ViewById
	com.tencent.tencentmap.mapsdk.map.MapView mapview;

	String lng;
	String lat;

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@AfterViews
	void afteronCreate()
	{
		LatLng latLng = new LatLng(Double.valueOf(lng), Double.valueOf(lat));
		tencentMap = mapview.getMap();
		tencentMap.setZoom(15);
		tencentMap.animateTo(latLng);

		Drawable drawable = getResources().getDrawable(R.drawable.location_start);
		Bitmap bitmap = utils.drawable2Bitmap(drawable);
		marker = tencentMap.addMarker(new MarkerOptions().position(latLng).title("").icon(new BitmapDescriptor(bitmap)));
		marker.hideInfoWindow();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		lat = getIntent().getStringExtra("lat");
		lng = getIntent().getStringExtra("lng");
	}
}
