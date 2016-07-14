package com.bhq.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.adapter.Offline_Adapter_DailyProduct;
import com.bhq.app.AppContext;
import com.bhq.app.AppManager;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.SqliteDb;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-19 下午2:34:29
 * @description :信息采集类
 */
@EActivity(R.layout.offline_dailyproductactivity)
public class Offline_DailyProductActivity extends Activity
{
	private AppContext appContext;// 全局Context
	private List<BHQ_XHSJCJ> listData = new ArrayList<BHQ_XHSJCJ>();
	private Offline_Adapter_DailyProduct offline_adapter_dailyProduct;
	private View list_footer;
	private TextView list_foot_more;
	private ProgressBar list_foot_progress;
	private int listSumData;

	String ZYDL="";
	@ViewById
	ListView lv;
	@ViewById
	ProgressBar main_head_progress;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	ImageButton imgbtn_add;


	@Click
	void imgbtn_back()
	{
		finish();
	}


	@Click
	void imgbtn_add()
	{
		Intent intent = new Intent(Offline_DailyProductActivity.this, Offline_AddDailyProduct_.class);
		intent.putExtra("type", "植物");
		startActivity(intent);
	}

	Fragment mContent = new Fragment();
	PopupWindow popupWindow;
	View popupWindowView;

	@Override
	protected void onResume()
	{
		super.onResume();
		// btn_plant.setSelected(true);
		// ZYDL = "02";
		getListData();
	}

	@AfterViews
	void afterOncreate()
	{
		ZYDL = "02";
		getListData();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		AppManager.getAppManager().addActivity(this);
		appContext = (AppContext) getApplication();
	}




	private void getListData()
	{
		dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(Offline_DailyProductActivity.this, dt_manager_offline.class);
		listData = SqliteDb.getCJXXList(Offline_DailyProductActivity.this, BHQ_XHSJCJ.class, dt_manager_offline.getid(), ZYDL);
		if (listData != null)
		{
			offline_adapter_dailyProduct=new Offline_Adapter_DailyProduct(Offline_DailyProductActivity.this,listData);
			lv.setAdapter(offline_adapter_dailyProduct);
		}
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(Offline_DailyProductActivity.this, Offline_ShowDailyProduct_.class);
				intent.putExtra("bean", listData.get(position));// 因为list中添加了头部,因此要去掉一个
				Offline_DailyProductActivity.this.startActivity(intent);
			}
		});

	}


}
