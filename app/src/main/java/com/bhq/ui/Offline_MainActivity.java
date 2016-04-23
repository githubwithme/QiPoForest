package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppManager;
import com.bhq.bean.ExceptionInfo;
import com.bhq.bean.Result;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.SqliteDb;
import com.bhq.service.MarkLocationService;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;

@SuppressLint("NewApi")
@EActivity(R.layout.offline_activity_main)
public class Offline_MainActivity extends Activity
{
	MyDialog myDialog;
	Fragment mContent = new Fragment();
	Fragment mContentMore = new Fragment();
	MarkLocationService markLocationService;
	/** 是否绑定 */
	boolean mIsBound = false;
	Bundle savedInstanceState;
	Offline_MainFragment mainFragment;
	Offline_PatroFragment patroFragment;
	Offline_IServiceFragment iServiceFragment_;
	Offline_KnowledgeFragment knowledgeFragment;
	@ViewById
	ImageButton imgbtn_home;
	@ViewById
	ImageButton imgbtn_patrol;
	@ViewById
	ImageButton imgbtn_data;
	@ViewById
	ImageButton imgbtn_message;
	@ViewById
	TableLayout tl_home;
	@ViewById
	TableLayout tl_patrol;
	@ViewById
	TableLayout tl_data;
	@ViewById
	TableLayout tl_message;
	@ViewById
	TextView tv_home;
	@ViewById
	TextView tv_patro;
	@ViewById
	TextView tv_knowledge;
	@ViewById
	TextView tv_setting;

	@Click
	void tl_home()
	{
		tv_home.setTextColor(getResources().getColor(R.color.red));
		tv_knowledge.setTextColor(getResources().getColor(R.color.black));
		tv_patro.setTextColor(getResources().getColor(R.color.black));
		tv_setting.setTextColor(getResources().getColor(R.color.black));
		tl_home.setSelected(true);
		tl_patrol.setSelected(false);
		tl_data.setSelected(false);
		tl_message.setSelected(false);
		switchContent(mContent, mainFragment);
	}

	@Click
	void tl_patrol()
	{
		tv_home.setTextColor(getResources().getColor(R.color.black));
		tv_knowledge.setTextColor(getResources().getColor(R.color.black));
		tv_patro.setTextColor(getResources().getColor(R.color.red));
		tv_setting.setTextColor(getResources().getColor(R.color.black));
		tl_home.setSelected(false);
		tl_patrol.setSelected(true);
		tl_data.setSelected(false);
		tl_message.setSelected(false);
		switchContent(mContent, patroFragment);

	}

	@Click
	void tl_data()
	{
		tv_home.setTextColor(getResources().getColor(R.color.black));
		tv_knowledge.setTextColor(getResources().getColor(R.color.red));
		tv_patro.setTextColor(getResources().getColor(R.color.black));
		tv_setting.setTextColor(getResources().getColor(R.color.black));
		tl_home.setSelected(false);
		tl_patrol.setSelected(false);
		tl_data.setSelected(true);
		tl_message.setSelected(false);
		switchContent(mContent, knowledgeFragment);
	}

	@Click
	void tl_message()
	{
		tv_home.setTextColor(getResources().getColor(R.color.black));
		tv_knowledge.setTextColor(getResources().getColor(R.color.black));
		tv_patro.setTextColor(getResources().getColor(R.color.black));
		tv_setting.setTextColor(getResources().getColor(R.color.red));
		tl_home.setSelected(false);
		tl_patrol.setSelected(false);
		tl_data.setSelected(false);
		tl_message.setSelected(true);
		switchContent(mContent, iServiceFragment_);
	}

	@AfterViews
	void afterOncreate()
	{
		//将错误信息提交
		List<ExceptionInfo> list_exception = SqliteDb.getExceptionInfo(Offline_MainActivity.this);
		if (list_exception != null)
		{
			for (int i = 0; i < list_exception.size(); i++)
			{
				sendExceptionInfoToServer(list_exception.get(i));
			}
		}

		tv_home.setTextColor(getResources().getColor(R.color.red));
		tv_knowledge.setTextColor(getResources().getColor(R.color.black));
		tv_patro.setTextColor(getResources().getColor(R.color.black));
		tv_setting.setTextColor(getResources().getColor(R.color.black));
		tl_home.setSelected(true);
		tl_patrol.setSelected(false);
		tl_data.setSelected(false);
		tl_message.setSelected(false);
		switchContent(mContent, mainFragment);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		AppManager.getAppManager().addActivity(this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(ConnectionChangeReceiver, filter);

		mainFragment = new Offline_MainFragment_();
		patroFragment = new Offline_PatroFragment_();
		knowledgeFragment = new Offline_KnowledgeFragment_();
		iServiceFragment_ = new Offline_IServiceFragment_();
		this.savedInstanceState = savedInstanceState;

//        Intent intenttemp = new Intent(Offline_MainActivity.this, UpdateData.class);
//        intenttemp.setAction(DownloadData.ACTION_DOWNLOADDATA);
//        Offline_MainActivity.this.startService(intenttemp);

//        Intent intent = new Intent(this, UpdateApk.class);
//        intent.setAction(UpdateApk.ACTION_NOTIFICATION_CONTROL);
//        startService(intent);
	}

	BroadcastReceiver ConnectionChangeReceiver = new BroadcastReceiver()
	{
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent)
		{
//			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//			NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//			if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected())
//			{
//				// 改变背景或者 处理网络的全局变量
//			} else
//			{
//				Intent intenttemp = new Intent(Offline_MainActivity.this, UpdateData.class);
//				intenttemp.setAction(DownloadData.ACTION_DOWNLOADDATA);
//				Offline_MainActivity.this.startService(intenttemp);
//			}
		}
	};

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(ConnectionChangeReceiver);
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** 开启记录行踪服务 */
	public void startMarkLocationService()
	{
		bindService(new Intent(Offline_MainActivity.this, MarkLocationService.class), mConnection_location, Offline_MainActivity.this.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	/** 解除绑定服务 */
	public void doUnbindService()
	{
		if (mIsBound)
		{
			unbindService(mConnection_location);
			mIsBound = false;
		}
	}

	private ServiceConnection mConnection_location = new ServiceConnection()
	{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			markLocationService = ((MarkLocationService.LocalBinder) service).getService();
			Toast.makeText(Offline_MainActivity.this, "正在记录行走轨迹！", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			markLocationService = null;
			Toast.makeText(Offline_MainActivity.this, "记录行走轨迹服务未连接", Toast.LENGTH_SHORT).show();
		}
	};

	public void switchContent(Fragment from, Fragment to)
	{
		if (mContent != to)
		{
			mContent = to;
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			if (!to.isAdded())
			{ // 先判断是否被add过
				transaction.hide(from).add(R.id.container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else
			{
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			showExistTip();
		}
		return false;

	}

	private void showExistTip()
	{
		View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
		myDialog = new MyDialog(Offline_MainActivity.this, R.style.MyDialog, dialog_layout, "确定退出吗？", "确定退出吗？", "退出", "取消", new CustomDialogListener()
		{
			@Override
			public void OnClick(View v)
			{
				switch (v.getId())
				{
				case R.id.btn_sure:
//					finish();
                    AppManager.getAppManager().AppExit(Offline_MainActivity.this);
					break;
				case R.id.btn_cancle:
					myDialog.dismiss();
					break;
				}
			}
		});
		myDialog.show();
	}
	private void sendExceptionInfoToServer(final ExceptionInfo exception)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("exceptionid", exception.getExceptionid());
		hashMap.put("uuid", exception.getUuid());
		hashMap.put("exceptionInfo", exception.getExceptionInfo());
		hashMap.put("regtime", exception.getRegtime());
		hashMap.put("userid", exception.getUserid());
		hashMap.put("username", exception.getUsername());
		hashMap.put("isSolve", exception.getIsSolve());
		String params = ConnectionHelper.setParams("APP.saveAppException", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String a = responseInfo.result;
				List<?> listData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)
				{

					if (result.getAffectedRows() != 0)
					{
						SqliteDb.deleteExceptionInfo(Offline_MainActivity.this, exception.getExceptionid());
					}

				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				String a = error.getMessage();
			}
		});

	}
}
