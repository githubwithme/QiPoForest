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
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.app.AppManager;
import com.bhq.bean.dt_manager;
import com.bhq.common.BitmapHelper;
import com.bhq.common.SqliteDb;
import com.bhq.service.MarkLocationService;
import com.bhq.widget.CircleImageView;
import com.bhq.widget.DragLayout;
import com.bhq.widget.DragLayout.DragListener;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;
import com.service.UpdateApk;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@SuppressLint("NewApi")
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity
{
	MyDialog myDialog;
	Fragment mContent = new Fragment();
	Fragment mContentMore = new Fragment();
	MarkLocationService markLocationService;
	/** 是否绑定 */
	boolean mIsBound = false;
	Bundle savedInstanceState;
	MainFragment mainFragment;
	PatroFragment patroFragment;
	IServiceFragment iServiceFragment_;
	KnowledgeFragment knowledgeFragment;
	@ViewById
	DragLayout dl;
	@ViewById
	RelativeLayout rl_changepwd;
	@ViewById
	RelativeLayout rl_exist;
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
	@ViewById
	TextView tv_xm;
	@ViewById
	CircleImageView circle_img;

	// @Click
	// void btn_account()
	// {
	// dl.open();
	// }

	@Click
	void rl_exist()
	{
		CleanLoginInfo();
		AppManager.getAppManager().AppExit(MainActivity.this);
	}

	@Click
	void rl_changepwd()
	{
		Intent intent = new Intent(MainActivity.this, ChangePwd_.class);
		startActivity(intent);
	}

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
		Intent intent = new Intent(this, UpdateApk.class);
		intent.setAction(UpdateApk.ACTION_NOTIFICATION_CONTROL);
		startService(intent);

		dt_manager dt_manager = AppContext.getUserInfo(this);
		tv_xm.setText(dt_manager.getreal_name());
		BitmapHelper.setImageViewBackground(this, circle_img, AppConfig.url + dt_manager.getUserPhoto());
		tv_home.setTextColor(getResources().getColor(R.color.red));
		tv_knowledge.setTextColor(getResources().getColor(R.color.black));
		tv_patro.setTextColor(getResources().getColor(R.color.black));
		tv_setting.setTextColor(getResources().getColor(R.color.black));
		tl_home.setSelected(true);
		tl_patrol.setSelected(false);
		tl_data.setSelected(false);
		tl_message.setSelected(false);
		initDragLayout();
		// initView();
		switchContent(mContent, mainFragment);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		AppManager.getAppManager().addActivity(this);
		IntentFilter intentfilter_opendl = new IntentFilter(AppContext.BROADCAST_OPENDL);
		registerReceiver(receiver_opendl, intentfilter_opendl);
		// 开启记录行踪服务
		// startMarkLocationService();
		mainFragment = new MainFragment_();
		patroFragment = new PatroFragment_();
		knowledgeFragment = new KnowledgeFragment_();
		iServiceFragment_ = new IServiceFragment_();
		this.savedInstanceState = savedInstanceState;
	}

	BroadcastReceiver receiver_opendl = new BroadcastReceiver()// 从扩展页面返回信息
	{
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent)
		{
			dl.open();
		}
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

	private void initDragLayout()
	{
		dl.setDragListener(new DragListener()
		{
			@Override
			public void onOpen()
			{
				// lv.smoothScrollToPosition(new Random().nextInt(30));
			}

			@Override
			public void onClose()
			{
				shake();
			}

			@Override
			public void onDrag(float percent)
			{
				// ViewHelper.setAlpha(btn_account, 1 - percent);
			}
		});
	}

	// private void initView()
	// {
	// tv_xm = (TextView) findViewById(R.id.tv_xm);
	// lv = (ListView) findViewById(R.id.lv);
	// lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
	// R.layout.draglayout_lv, new String[] { "姓名", "在线时间" }));
	// lv.setOnItemClickListener(new OnItemClickListener()
	// {
	// @Override
	// public void onItemClick(AdapterView<?> arg0, View arg1, int position,
	// long arg3)
	// {
	// switch (position)
	// {
	// case 0:
	// break;
	// case 1:
	// SharedPreferences sp = MainActivity.this.getSharedPreferences("MY_PRE",
	// MODE_PRIVATE);
	// Editor editor = sp.edit();
	// editor.putString("password", "");
	// editor.commit();
	// AppManager.getAppManager().AppExit(getApplicationContext());
	// break;
	//
	// default:
	// break;
	// }
	// }
	// });
	// }

	private void shake()
	{
		// btn_previous.startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.shake));
	}

	/** 开启记录行踪服务 */
	public void startMarkLocationService()
	{
		bindService(new Intent(MainActivity.this, MarkLocationService.class), mConnection_location, MainActivity.this.BIND_AUTO_CREATE);
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
			Toast.makeText(MainActivity.this, "正在记录行走轨迹！", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			markLocationService = null;
			Toast.makeText(MainActivity.this, "记录行走轨迹服务未连接", Toast.LENGTH_SHORT).show();
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

	private void CleanLoginInfo()
	{
		dt_manager dt_manager = (dt_manager) SqliteDb.getCurrentUser(MainActivity.this, dt_manager.class);
		dt_manager.setpassword("");
		dt_manager.setAutoLogin("0");
		SqliteDb.existSystem(MainActivity.this, dt_manager);
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
		myDialog = new MyDialog(MainActivity.this, R.style.MyDialog, dialog_layout, "确定退出吗？", "确定退出吗？", "退出", "取消", new CustomDialogListener()
		{
			@Override
			public void OnClick(View v)
			{
				switch (v.getId())
				{
				case R.id.btn_sure:
					finish();
					break;
				case R.id.btn_cancle:
					myDialog.dismiss();
					break;
				}
			}
		});
		myDialog.show();
	}
}
