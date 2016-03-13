package com.bhq.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.adapter.Offline_ListViewCJXXAdapter;
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
@EActivity(R.layout.discoveryactivity)
public class Offline_DiscoveryActivity extends Activity implements OnClickListener
{
	private AppContext appContext;// 全局Context
	private List<BHQ_XHSJCJ> listData = new ArrayList<BHQ_XHSJCJ>();
	private Offline_ListViewCJXXAdapter listAdapter;
	private View list_footer;
	private TextView list_foot_more;
	private ProgressBar list_foot_progress;
	private int listSumData;

	@ViewById
	ListView lv;
	@ViewById
	ProgressBar main_head_progress;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	ImageButton imgbtn_add;
	@ViewById
	Button btn_animal;
	@ViewById
	Button btn_plant;
	@ViewById
	Button btn_pest;

	String ZYDL;

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@Click
	void btn_animal()
	{
		ZYDL = "01";
		getListData();
		btn_animal.setSelected(true);
		btn_plant.setSelected(false);
		btn_pest.setSelected(false);
	}

	@Click
	void btn_plant()
	{
		ZYDL = "02";
		getListData();
		btn_plant.setSelected(true);
		btn_animal.setSelected(false);
		btn_pest.setSelected(false);
	}

	@Click
	void btn_pest()
	{
		ZYDL = "03";
		getListData();
		btn_pest.setSelected(true);
		btn_animal.setSelected(false);
		btn_plant.setSelected(false);
	}

	@Click
	void imgbtn_add()
	{
		showPop();
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
		btn_plant.setSelected(true);
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

	private void showPop()
	{
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popupWindowView = layoutInflater.inflate(R.layout.popup_addinfo, null);// 外层
		popupWindowView.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if ((keyCode == KeyEvent.KEYCODE_MENU) && (popupWindow.isShowing()))
				{
					popupWindow.dismiss();
					return true;
				}
				return false;
			}
		});
		popupWindowView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (popupWindow.isShowing())
				{
					popupWindow.dismiss();
				}
				return false;
			}
		});
		popupWindow = new PopupWindow(popupWindowView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.showAsDropDown(imgbtn_add, 0, 0);
		popupWindow.setOutsideTouchable(true);
		popupWindowView.findViewById(R.id.btn_animal).setOnClickListener(this);
		popupWindowView.findViewById(R.id.btn_plant).setOnClickListener(this);
		popupWindowView.findViewById(R.id.btn_pest).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = null;
		switch (v.getId())
		{
			case R.id.btn_animal:
				intent = new Intent(Offline_DiscoveryActivity.this, Offline_AddCJXX_.class);
				intent.putExtra("type", "动物");
				popupWindow.dismiss();
				break;
			case R.id.btn_plant:
				intent = new Intent(Offline_DiscoveryActivity.this, Offline_AddCJXX_.class);
				intent.putExtra("type", "植物");
				popupWindow.dismiss();
				break;
			case R.id.btn_pest:
				intent = new Intent(Offline_DiscoveryActivity.this, Offline_AddCJXX_.class);
				intent.putExtra("type", "微生物");
				popupWindow.dismiss();
				break;
			default:
				break;
		}
		startActivity(intent);
	}

	private void getListData()
	{
		dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(Offline_DiscoveryActivity.this, dt_manager_offline.class);
		listData = SqliteDb.getCJXXList(Offline_DiscoveryActivity.this, BHQ_XHSJCJ.class, dt_manager_offline.getid(), ZYDL);
		if (listData != null)
		{
			 listAdapter=new Offline_ListViewCJXXAdapter(Offline_DiscoveryActivity.this,listData);
			lv.setAdapter(listAdapter);
		}
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(Offline_DiscoveryActivity.this, Offline_ShowCJXX_.class);
				intent.putExtra("bean", listData.get(position));// 因为list中添加了头部,因此要去掉一个
				Offline_DiscoveryActivity.this.startActivity(intent);
			}
		});

	}


}
