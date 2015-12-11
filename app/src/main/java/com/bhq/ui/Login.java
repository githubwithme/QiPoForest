package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppManager;
import com.bhq.bean.BHQ_ZSK;
import com.bhq.bean.Dictionary;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.dt_manager;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.CryptoTools;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.widget.CircleImageView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SuppressLint("NewApi")
@EActivity(R.layout.logintemp)
public class Login extends Activity
{
    private Animation animationTop;
	String model = "";
	int ThreadNumber = 0;
	boolean isfail = false;
	CountDownLatch latch;
	@ViewById
	Button btn_again;
	@ViewById
	ProgressBar pb;
	@ViewById
	RelativeLayout rl_inittip;
	@ViewById
	RelativeLayout rl_pb;
	@ViewById
	CheckBox cb_offline;
	@ViewById
	CheckBox cb_autologin;
	@ViewById
	View line;
	@ViewById
	EditText et_name;
	@ViewById
	EditText et_psw;
	@ViewById
	CircleImageView btn_login;
    @ViewById
    CircleImageView image;
    @ViewById
	LinearLayout ll_login;
    @ViewById
    LinearLayout ll_txt;
	@ViewById
	ProgressBar pb_logining;
	@ViewById
	ImageView iv_down;
	PopupWindow popupWindow_tab;
	View popupWindowView_tab;
	List<String> list = new ArrayList<String>();
	SharedPreferences sp;
	String hasInit;

	@CheckedChange(R.id.cb_offline)
	void button_two(CompoundButton hello, boolean isChecked)
	{
		if (isChecked)
		{
			model = "0";
			markmodel("0");// 离线模式0
		} else
		{
			model = "0";
			markmodel("0");// 在线模式1
		}
	}

	@Click
	void btn_again()
	{
		getAllCount();
	}

	@Click
	void iv_down()
	{
		if (model.equals("0"))
		{
			List<dt_manager_offline> list_dt = SqliteDb.getUserList(this, dt_manager_offline.class);
			for (int i = 0; i < list_dt.size(); i++)
			{
				list.add(list_dt.get(i).getuser_name());
			}
		} else
		{
			List<dt_manager> list_dt = SqliteDb.getUserList(this, dt_manager.class);
			for (int i = 0; i < list_dt.size(); i++)
			{
				list.add(list_dt.get(i).getuser_name());
			}
		}
		showPop_tab();
	}

	@Click
	void btn_login()
	{
		if (!et_name.getText().toString().equals("") && !et_psw.getText().toString().equals(""))
		{
			ll_login.setVisibility(View.GONE);
			pb_logining.setVisibility(View.VISIBLE);
			if (model.equals("0"))
			{
				startLoginOffLine(et_name.getText().toString(), et_psw.getText().toString());
			} else
			{
				startLogin(et_name.getText().toString(), et_psw.getText().toString());
			}

		} else
		{
			Toast.makeText(this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	@AfterViews
	void afterOncreate()
	{
//        animationTop = AnimationUtils.loadAnimation(this, R.anim.tutorail_scalate_top);
//        ll_txt.startAnimation(animationTop);
//        image.startAnimation(animationTop);
		if (model.equals("0"))
		{
			cb_offline.setChecked(true);
		} else
		{
			cb_offline.setChecked(false);
		}
		initData();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		AppManager.getAppManager().addActivity(this);
		sp = this.getSharedPreferences("MY_PRE", MODE_PRIVATE);
		model = sp.getString("model", "0");

	}

	private void initData()
	{
		hasInit = sp.getString("hasInit", "");
		if (hasInit.equals("true"))
		{
			if (model.equals("0"))
			{
				dt_manager_offline dt_manager_offline = (dt_manager_offline) SqliteDb.getAutoLoginUser(Login.this, dt_manager_offline.class);
				if (dt_manager_offline != null && !dt_manager_offline.getpassword().equals(""))
				{
					ll_login.setVisibility(View.GONE);
					cb_autologin.setChecked(true);
					pb_logining.setVisibility(View.VISIBLE);
					et_name.setText(dt_manager_offline.getuser_name());
					et_psw.setText(dt_manager_offline.getpassword());
					AutoLoginOffLine(dt_manager_offline.getuser_name(), dt_manager_offline.getpassword());
				} else
				{

				}

			} else
			{
				dt_manager dt_manager = (dt_manager) SqliteDb.getCurrentUser(Login.this, dt_manager.class);
				if (dt_manager != null && !dt_manager.getpassword().equals(""))
				{
					ll_login.setVisibility(View.GONE);
					cb_autologin.setChecked(true);
					pb_logining.setVisibility(View.VISIBLE);
					et_name.setText(dt_manager.getuser_name());
					et_psw.setText(dt_manager.getpassword());
					startLogin(dt_manager.getuser_name(), dt_manager.getpassword());
				} else
				{

				}

			}

		} else
		{
//			getAllCount();
            startInitData();
		}
	}

	private void startInitData()
	{
        ThreadNumber =2;
        latch = new CountDownLatch(ThreadNumber);
        SqliteDb.dropTable(Login.this, dt_manager_offline.class);
        SqliteDb.dropTable(Login.this, Dictionary.class);
//        SqliteDb.dropTable(Login.this, BHQ_ZSK.class);
//        SqliteDb.dropTable(Login.this, RW_RW.class);
//        SqliteDb.dropTable(Login.this, RW_CYR.class);
//        SqliteDb.dropTable(Login.this, RW_YQB.class);
		rl_inittip.setVisibility(View.GONE);
		rl_pb.setVisibility(View.VISIBLE);
		InitTable("APP.InitUserTable", dt_manager_offline.class);
		InitTable("APP.InitDictionaryTable", Dictionary.class);
//		InitTable("APP.InitZSKTable", BHQ_ZSK.class);
//		InitTable("APP.getRW_RW", RW_RW.class);
//		InitTable("APP.getRW_CYR", RW_CYR.class);
//		InitTable("APP.getRW_YQB", RW_YQB.class);
	}

	private void startLogin(final String username, final String psw)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("name", username);
		hashMap.put("pwd", psw);
		String params = ConnectionHelper.setParams("APP.ValLogin", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String a = responseInfo.result;
				List<dt_manager> listData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// -1出错；0结果集数量为0；结果列表
				{
					if (result.getAffectedRows() != 0)
					{
						listData = JSON.parseArray(ResultDeal.getAllRow(result), dt_manager.class);
						if (listData == null)
						{
							Toast.makeText(Login.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
						} else
						{
							Intent intent = new Intent(Login.this, MainActivity_.class);
							startActivity(intent);
							finish();
							dt_manager dt_manager = listData.get(0);
							dt_manager.setOnceUsed("1");
							if (cb_autologin.isChecked())
							{
								dt_manager.setAutoLogin("1");
								dt_manager.setpassword(psw);
								dt_manager.setOnceUsed("1");
								SqliteDb.save(Login.this, dt_manager);
							} else
							{
								dt_manager.setAutoLogin("0");
								dt_manager.setpassword("");
								dt_manager.setOnceUsed("1");
								SqliteDb.save(Login.this, dt_manager);
							}
						}
					} else
					{
						Toast.makeText(Login.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
						ll_login.setVisibility(View.VISIBLE);
						pb_logining.setVisibility(View.GONE);
					}
				} else
				{
					Toast.makeText(Login.this, "连接数据服务器出错了!", Toast.LENGTH_SHORT).show();
					ll_login.setVisibility(View.VISIBLE);
					pb_logining.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(Login.this, "连接服务器出错了!", Toast.LENGTH_SHORT).show();
				ll_login.setVisibility(View.VISIBLE);
				pb_logining.setVisibility(View.GONE);
			}
		});

	}

	private void AutoLoginOffLine(final String username, final String psw)
	{
		dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.login(Login.this, dt_manager_offline.class, username, psw);
		if (dt_manager_offline == null)
		{
			Toast.makeText(Login.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
			ll_login.setVisibility(View.VISIBLE);
			pb_logining.setVisibility(View.GONE);
		} else
		{
			Intent intent = new Intent(Login.this, Offline_MainActivity_.class);
			startActivity(intent);
			finish();
			SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("userName", dt_manager_offline.getuser_name());
			editor.commit();
			dt_manager_offline.setOnceUsed("1");
			dt_manager_offline.setIsLogin("1");
			if (cb_autologin.isChecked())
			{
				dt_manager_offline.setAutoLogin("1");
				SqliteDb.save(Login.this, dt_manager_offline);
			} else
			{
				dt_manager_offline.setAutoLogin("0");
				SqliteDb.save(Login.this, dt_manager_offline);
			}
		}

	}

	private void startLoginOffLine(final String username, final String psw)
	{
		String encodePdw = "";
		dt_manager_offline dt_manager = (com.bhq.bean.dt_manager_offline) SqliteDb.getSalt(Login.this, dt_manager_offline.class, username);
		byte[] DESkey;
		try
		{
			DESkey = dt_manager.getsalt().getBytes("UTF-8");
			CryptoTools des = new CryptoTools(DESkey);// 自定义密钥
			encodePdw = des.encode(psw);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.login(Login.this, dt_manager_offline.class, username, encodePdw);
		if (dt_manager_offline == null)
		{
			Toast.makeText(Login.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
			ll_login.setVisibility(View.VISIBLE);
			pb_logining.setVisibility(View.GONE);
		} else
		{
			Intent intent = new Intent(Login.this, Offline_MainActivity_.class);
			startActivity(intent);
			finish();
			SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("userName", dt_manager_offline.getuser_name());
			editor.commit();
			dt_manager_offline.setOnceUsed("1");
			dt_manager_offline.setIsLogin("1");
			if (cb_autologin.isChecked())
			{
				dt_manager_offline.setAutoLogin("1");
				SqliteDb.save(Login.this, dt_manager_offline);
			} else
			{
				dt_manager_offline.setAutoLogin("0");
				SqliteDb.save(Login.this, dt_manager_offline);
			}
		}

	}

	public void showPop_tab()
	{
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popupWindowView_tab = layoutInflater.inflate(R.layout.popup_userlist, null);// 外层
		popupWindowView_tab.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if ((keyCode == KeyEvent.KEYCODE_MENU) && (popupWindow_tab.isShowing()))
				{
					popupWindow_tab.dismiss();
					iv_down.setBackground(getResources().getDrawable(R.drawable.downward));
					return true;
				}
				return false;
			}
		});
		popupWindowView_tab.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if (popupWindow_tab.isShowing())
				{
					popupWindow_tab.dismiss();
					iv_down.setBackground(getResources().getDrawable(R.drawable.downward));
				}
				return false;
			}
		});
		popupWindow_tab = new PopupWindow(popupWindowView_tab, et_name.getWidth(), LayoutParams.WRAP_CONTENT, true);
		popupWindow_tab.showAsDropDown(et_name, 0, 0);
		popupWindow_tab.setOutsideTouchable(true);
		ListView listview = (ListView) popupWindowView_tab.findViewById(R.id.lv_yq);
		listview.setAdapter(new yqAdapter(this, list));
		listview.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int postion, long arg3)
			{
				et_name.setText(list.get(postion));
				iv_down.setBackground(getResources().getDrawable(R.drawable.downward));
				popupWindow_tab.dismiss();
			}
		});
	}

	public class yqAdapter extends BaseAdapter
	{
		List<String> listItems;
		private LayoutInflater listContainer;

		class ListItemView
		{
			public TextView tv_yq;
		}

		public yqAdapter(Context context, List<String> list)
		{
			this.listContainer = LayoutInflater.from(context);
			this.listItems = list;
		}

		HashMap<Integer, View> lmap = new HashMap<Integer, View>();

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ListItemView listItemView = null;
			if (lmap.get(position) == null)
			{
				convertView = listContainer.inflate(R.layout.userlist_item, null);
				listItemView = new ListItemView();
				listItemView.tv_yq = (TextView) convertView.findViewById(R.id.tv_yq);
				lmap.put(position, convertView);
				convertView.setTag(listItemView);
			} else
			{
				convertView = lmap.get(position);
				listItemView = (ListItemView) convertView.getTag();
			}
			listItemView.tv_yq.setText(listItems.get(position));
			return convertView;
		}

		@Override
		public int getCount()
		{
			return listItems.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			return null;
		}

		@Override
		public long getItemId(int arg0)
		{
			return 0;
		}
	}

	private <T> void InitTable(final String action, final Class<T> className)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String params = ConnectionHelper.setParams(action, "0", hashMap);
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
						listData = JSON.parseArray(ResultDeal.getAllRow(result), className);
						if (listData == null)
						{
							isfail = true;
							showProgress();
						} else
						{
							if (action.equals("APP.InitZSKTable"))
							{
								for (int i = 0; i < listData.size(); i++)
								{
									BHQ_ZSK bhq_ZSK = (BHQ_ZSK) listData.get(i);
									String bdlj = AppConfig.MEDIA_PATH + bhq_ZSK.getimgurl().subSequence(bhq_ZSK.getimgurl().lastIndexOf("/") + 1, bhq_ZSK.getimgurl().length());
									bhq_ZSK.setBDLJ(bdlj);
									getPhotos(AppConfig.url + bhq_ZSK.getimgurl(), bdlj);
								}
							}
							if (action.equals("APP.InitUserTable"))
							{
								for (int i = 0; i < listData.size(); i++)
								{
									dt_manager_offline dt_manager_offline = (dt_manager_offline) listData.get(i);
									String BDLJ = AppConfig.MEDIA_PATH + dt_manager_offline.getUserPhoto().subSequence(dt_manager_offline.getUserPhoto().lastIndexOf("/") + 1, dt_manager_offline.getUserPhoto().length());
									dt_manager_offline.setBDLJ(BDLJ);
									getUserPhotos(AppConfig.url + dt_manager_offline.getUserPhoto(), BDLJ);
								}
							}
							boolean issuccess = SqliteDb.saveAll(Login.this, listData);
							if (issuccess)
							{
								if (action.equals("APP.InitZSKTable"))
								{
									for (int i = 0; i < listData.size(); i++)
									{
										BHQ_ZSK bhq_ZSK = (BHQ_ZSK) listData.get(i);
										getZSNR(bhq_ZSK.getZSID());
									}
								}
								showProgress();
								int aa = Integer.valueOf(utils.getRate(ThreadNumber - Integer.valueOf(String.valueOf(latch.getCount())), ThreadNumber));
								pb.setProgress(Integer.valueOf(utils.getRate(ThreadNumber - Integer.valueOf(String.valueOf(latch.getCount())), ThreadNumber)));

							} else
							{
								isfail = true;
								showProgress();
							}
						}
					}
				} else
				{
					isfail = true;
					showProgress();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				String aa = error.getMessage();
				isfail = true;
				showProgress();
			}
		});

	}

	private void getZSNR(final String zsid)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("ZSID", zsid);
		String params = ConnectionHelper.setParams("APP.GetZSNR", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String ba = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					String zsnr = result.getRows().getJSONArray(0).get(0).toString();
					BHQ_ZSK bhq_ZSK = new BHQ_ZSK();
					bhq_ZSK.setZSID(zsid);
					bhq_ZSK.setZSNR(zsnr);
					boolean issuccess = SqliteDb.updateZSNR(Login.this, bhq_ZSK);
					if (issuccess)
					{
						showProgress();
						int aa = Integer.valueOf(utils.getRate(ThreadNumber - Integer.valueOf(String.valueOf(latch.getCount())), ThreadNumber));
						pb.setProgress(Integer.valueOf(utils.getRate(ThreadNumber - Integer.valueOf(String.valueOf(latch.getCount())), ThreadNumber)));

					} else
					{
						isfail = true;
						showProgress();
					}
				} else
				{
					isfail = true;
					showProgress();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				isfail = true;
				showProgress();
			}
		});
	}

	public void getPhotos(String path, final String target)
	{
		HttpUtils http = new HttpUtils();
		http.download(path, target, true, true, new RequestCallBack<File>()
		{
			@Override
			public void onFailure(HttpException error, String msg)
			{
				if (msg.equals("maybe the file has downloaded completely"))
				{
					showProgress();
					pb.setProgress(Integer.valueOf(utils.getRate(ThreadNumber - Integer.valueOf(String.valueOf(latch.getCount())), ThreadNumber)));

				} else
				{
					isfail = true;
					showProgress();
				}
			}

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo)
			{
				showProgress();
				pb.setProgress(Integer.valueOf(utils.getRate(ThreadNumber - Integer.valueOf(String.valueOf(latch.getCount())), ThreadNumber)));

			}
		});

	}

	public void getUserPhotos(String path, final String target)
	{
		HttpUtils http = new HttpUtils();
		http.download(path, target, true, true, new RequestCallBack<File>()
		{
			@Override
			public void onFailure(HttpException error, String msg)
			{
				if (msg.equals("maybe the file has downloaded completely"))
				{

				} else
				{
				}
			}

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo)
			{

			}
		});

	}

	private void getAllCount()
	{
		rl_pb.setVisibility(View.VISIBLE);
		rl_inittip.setVisibility(View.GONE);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String params = ConnectionHelper.setParams("APP.getAllCount", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String a = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					ThreadNumber = 5 + (Integer.valueOf(result.getRows().getJSONArray(0).get(0).toString())) * 2;
					latch = new CountDownLatch(ThreadNumber);
					startInitData();
				} else
				{
					showInitFailDialog();
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				String aa = error.getMessage();
				showInitFailDialog();
			}
		});
	}

	private void showProgress()
	{
		latch.countDown();
		Long l = latch.getCount();
		if (l.intValue() == 0) // 全部线程是否已经结束
		{
			if (isfail)
			{
				showInitFailDialog();
			} else
			{
				rl_pb.setVisibility(View.GONE);
				rl_inittip.setVisibility(View.GONE);
				markTag();
				Toast.makeText(Login.this, "初始化成功！请登陆使用", Toast.LENGTH_SHORT).show();
			}

		}
	}

	private void showInitFailDialog()
	{
		rl_pb.setVisibility(View.GONE);
		rl_inittip.setVisibility(View.VISIBLE);
	}

	private void markTag()
	{
		Editor editor = sp.edit();
		editor.putString("hasInit", "true");
		editor.commit();
	}

	private void markmodel(String model)
	{
		Editor editor = sp.edit();
		editor.putString("model", model);
		editor.commit();
	}
}
