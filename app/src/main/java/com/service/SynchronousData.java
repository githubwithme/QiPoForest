package com.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.alibaba.fastjson.JSON;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.BHQ_XHQK;
import com.bhq.bean.BHQ_XHQK_GJ;
import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.Result;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.net.HttpUrlConnect;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.entity.FileUploadEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SynchronousData extends Service
{
	int ThreadCount = 0;
	boolean isfail = false;
	CountDownLatch latch;
	AlertDialog dialog;
	private static final String TAG = "NotificationService";

	public static final String ACTION_UPLOADDATA = "ACTION_UPLOADDATA";
	public static final String ACTION_NOTIFICATION_CONTROL = "action_notification_control";
	public static final String KEY_COMMAND_SHOW = "show_notification";
	public static final String KEY_COMMAND_UPDATE = "update_notification";
	public static final String KEY_COMMAND_REMOVE = "remove_notification";

	public static final String TIME_KEY = "time_key";
	public static final int NOTIFICATIN_ID = 100;
	String action;

	List<dt_manager_offline> list_dt_manager_offline = new ArrayList<dt_manager_offline>();
	List<BHQ_XHSJCJ> list_BHQ_XHSJCJ = new ArrayList<BHQ_XHSJCJ>();
	List<BHQ_XHSJ> list_BHQ_XHSJ = new ArrayList<BHQ_XHSJ>();
	List<BHQ_XHQK> list_BHQ_XHQK = new ArrayList<BHQ_XHQK>();
	List<BHQ_XHQK_GJ> list_BHQ_XHQK_GJ = new ArrayList<BHQ_XHQK_GJ>();
	List<FJ_SCFJ> list_FJ_SCFJ = new ArrayList<FJ_SCFJ>();

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		action = intent.getAction();
		ThreadCount = getUploadCount();
		if (ThreadCount > 0)
		{
			uploadData(ThreadCount);
		} else
		{

		}

		return super.onStartCommand(intent, flags, startId);
	}

	private int getUploadCount()
	{
		list_dt_manager_offline = SqliteDb.getNotUploadData(SynchronousData.this, dt_manager_offline.class);
		list_BHQ_XHSJCJ = SqliteDb.getNotUploadData(SynchronousData.this, BHQ_XHSJCJ.class);
		list_BHQ_XHSJ = SqliteDb.getNotUploadData(SynchronousData.this, BHQ_XHSJ.class);
		list_BHQ_XHQK = SqliteDb.getNotUploadData(SynchronousData.this, BHQ_XHQK.class);
		list_BHQ_XHQK_GJ = SqliteDb.getNotUploadData(SynchronousData.this, BHQ_XHQK_GJ.class);
		list_FJ_SCFJ = SqliteDb.getNotUploadData(SynchronousData.this, FJ_SCFJ.class);
		int count = list_dt_manager_offline.size() + list_BHQ_XHSJCJ.size() + list_BHQ_XHSJ.size() + list_BHQ_XHQK_GJ.size() + list_BHQ_XHQK.size() + list_FJ_SCFJ.size() * 2;
		return count;
	}

	private void uploadData(int count)
	{
		latch = new CountDownLatch(count);
		uploadUser();
		uploadXXCJ();
		uploadSJ();
		uploadFJ();
		uploadGJ();
		uploadXHQK();
		uploadMedia(list_FJ_SCFJ);
	}

	private void uploadUser()
	{
		for (int i = 0; i < list_dt_manager_offline.size(); i++)
		{
			final dt_manager_offline dt_manager_offline = list_dt_manager_offline.get(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("id", dt_manager_offline.getid());
			hashMap.put("password", dt_manager_offline.getpassword());
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.updatepassword", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						dt_manager_offline.setIsUpload("1");
						SqliteDb.save(SynchronousData.this, dt_manager_offline);
						showProgress();
					} else
					{
						isfail = true;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					isfail = true;
				}
			});
		}
	}

	private void uploadXXCJ()
	{
		for (int i = 0; i < list_BHQ_XHSJCJ.size(); i++)
		{
			final BHQ_XHSJCJ bhq_XHSJCJ = list_BHQ_XHSJCJ.get(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("CJID", bhq_XHSJCJ.getCJID());
			hashMap.put("XHID", bhq_XHSJCJ.getXHID());
			hashMap.put("SSBHZ", bhq_XHSJCJ.getSSBHZ());// ?
			hashMap.put("CJR", bhq_XHSJCJ.getCJR());
			hashMap.put("CJRXM", bhq_XHSJCJ.getCJRXM());
			hashMap.put("CJSJ", bhq_XHSJCJ.getCJSJ());
			hashMap.put("ZYDL", bhq_XHSJCJ.getZYDL());
			hashMap.put("ZYXL", bhq_XHSJCJ.getZYXL());
			hashMap.put("ZM", bhq_XHSJCJ.getZM());
			hashMap.put("GANG", bhq_XHSJCJ.getGANG());
			hashMap.put("MU", bhq_XHSJCJ.getMU());
			hashMap.put("KE", bhq_XHSJCJ.getKE());
			hashMap.put("BHJB", bhq_XHSJCJ.getBHJB());
			hashMap.put("BWD", bhq_XHSJCJ.getBWD());
			hashMap.put("TZ", bhq_XHSJCJ.getTZ());
			hashMap.put("XX", bhq_XHSJCJ.getXX());
			hashMap.put("SZHJ", bhq_XHSJCJ.getSZHJ());
			hashMap.put("BWYS", bhq_XHSJCJ.getBWYS());
			hashMap.put("BDZYBZ", bhq_XHSJCJ.getBDZYBZ());
			hashMap.put("X", bhq_XHSJCJ.getX());
			hashMap.put("Y", bhq_XHSJCJ.getY());
			hashMap.put("SFSC", bhq_XHSJCJ.getSFSC());
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.AddCJXX", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						bhq_XHSJCJ.setIsUpload("1");
						SqliteDb.save(SynchronousData.this, bhq_XHSJCJ);
						showProgress();
					} else
					{
						isfail = true;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					isfail = true;
				}
			});
		}
	}

	private void uploadSJ()
	{
		for (int i = 0; i < list_BHQ_XHSJ.size(); i++)
		{
			final BHQ_XHSJ bhq_XHSJ = list_BHQ_XHSJ.get(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("SJID", bhq_XHSJ.getSJID());
			hashMap.put("SJLX", bhq_XHSJ.getSJLX());
			hashMap.put("SJMS", bhq_XHSJ.getSJMS());
			hashMap.put("CLQK", bhq_XHSJ.getCLQK());
			hashMap.put("SBR", bhq_XHSJ.getSBR());
			hashMap.put("SBRXM", bhq_XHSJ.getSBRXM());
			hashMap.put("SBSJ", bhq_XHSJ.getSBSJ());
			hashMap.put("SSBHZ", bhq_XHSJ.getSSBHZ());
			hashMap.put("LCZT", bhq_XHSJ.getLCZT());
			hashMap.put("X", bhq_XHSJ.getX());
			hashMap.put("Y", bhq_XHSJ.getY());
			hashMap.put("XHID", bhq_XHSJ.getXHID());
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.InsertBHQ_XHSJ", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						bhq_XHSJ.setIsUpload("1");
						SqliteDb.save(SynchronousData.this, bhq_XHSJ);
						showProgress();
					} else
					{
						isfail = true;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					isfail = true;
				}
			});
		}
	}

	private void uploadFJ()
	{
		for (int i = 0; i < list_FJ_SCFJ.size(); i++)
		{
			final FJ_SCFJ fj_SCFJ = list_FJ_SCFJ.get(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("FJID", fj_SCFJ.getFJID());
			hashMap.put("GLID", fj_SCFJ.getGLID());
			hashMap.put("SCSJ", fj_SCFJ.getSCSJ());
			hashMap.put("GLBM", fj_SCFJ.getGLBM());
			hashMap.put("FJMC", fj_SCFJ.getFJMC());
			hashMap.put("FJLJ", fj_SCFJ.getFJLJ());
			hashMap.put("SCR", fj_SCFJ.getSCR());
			hashMap.put("SCRXM", fj_SCFJ.getSCRXM());
			hashMap.put("FJLX", fj_SCFJ.getFJLX());
			hashMap.put("SFSC", fj_SCFJ.getSFSC());
			hashMap.put("SCZT", fj_SCFJ.getSCZT());
			hashMap.put("Change", fj_SCFJ.getChange());
			hashMap.put("FL", fj_SCFJ.getFL());
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.InsertFJ_SCFJ", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						fj_SCFJ.setISUPLOAD("1");
						SqliteDb.save(SynchronousData.this, fj_SCFJ);
						showProgress();
					} else
					{
						isfail = true;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					isfail = true;
				}
			});
		}
	}

	private void uploadXHQK()
	{
		for (int i = 0; i < list_BHQ_XHQK.size(); i++)
		{
			final BHQ_XHQK bhq_XHQK = list_BHQ_XHQK.get(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("XHID", bhq_XHQK.getXHID());
			hashMap.put("XHRY", bhq_XHQK.getXHRY());
			hashMap.put("XHRQ", bhq_XHQK.getXHRQ());
			hashMap.put("XHRYXM", bhq_XHQK.getXHRYXM());
			hashMap.put("XHKSSJ", bhq_XHQK.getXHKSSJ());
			hashMap.put("XHJSSJ", bhq_XHQK.getXHJSSJ());
			hashMap.put("XHLC", bhq_XHQK.getXHLC());
			hashMap.put("XHXSS", bhq_XHQK.getXHXSS());
			hashMap.put("XHFZS", bhq_XHQK.getXHFZS());
			hashMap.put("XHZT", bhq_XHQK.getXHZT());
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.AddBHQ_XHQK", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						bhq_XHQK.setIsUpload("1");
						SqliteDb.save(SynchronousData.this, bhq_XHQK);
						showProgress();
					} else
					{
						isfail = true;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					isfail = true;
				}
			});
		}
	}

	private void uploadGJ()
	{
		for (int i = 0; i < list_BHQ_XHQK_GJ.size(); i++)
		{
			final BHQ_XHQK_GJ bhq_XHQK_GJ = list_BHQ_XHQK_GJ.get(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("XHID", bhq_XHQK_GJ.getXHID());
			hashMap.put("X", bhq_XHQK_GJ.getX());
			hashMap.put("Y", bhq_XHQK_GJ.getY());
			hashMap.put("JLSJ", bhq_XHQK_GJ.getJLSJ());
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.AddNewBHQ_XHQK_GJ", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						bhq_XHQK_GJ.setIsUpload("1");
						SqliteDb.save(SynchronousData.this, bhq_XHQK_GJ);
						showProgress();
					} else
					{
						isfail = true;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					isfail = true;
				}
			});
		}
	}

	private void uploadMedia(List<FJ_SCFJ> list_allpicture)
	{
		for (int i = 0; i < list_allpicture.size(); i++)
		{
			File file = new File(list_allpicture.get(i).getFJBDLJ());
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("param", list_allpicture.get(i).getSCSJ());
			params.addQueryStringParameter("first", "true");
			params.addQueryStringParameter("last", "true");
			params.addQueryStringParameter("offset", "0");
			params.addQueryStringParameter("file", file.getName());
			params.setBodyEntity(new FileUploadEntity(file, "text/html"));
			HttpUtils http = new HttpUtils();
			http.send(HttpRequest.HttpMethod.POST, AppConfig.uploadUrl, params, new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						showProgress();
					} else
					{
						isfail = true;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					isfail = true;
				}
			});
		}
	}

	private void showProgress()
	{
		latch.countDown();
		Long l = latch.getCount();
		if (isfail)
		{
			Intent intent = new Intent();
			intent.putExtra("progress", Integer.valueOf(utils.getRate(ThreadCount - Integer.valueOf(String.valueOf(latch.getCount())), ThreadCount)));
			intent.putExtra("status", 0);
			intent.setAction(AppContext.Progress_SynchronousData);
			SynchronousData.this.sendBroadcast(intent);
		} else
		{
			Intent intent = new Intent();
			intent.putExtra("progress", Integer.valueOf(utils.getRate(ThreadCount - Integer.valueOf(String.valueOf(latch.getCount())), ThreadCount)));
			intent.setAction(AppContext.Progress_SynchronousData);
			SynchronousData.this.sendBroadcast(intent);
		}

		if (l.intValue() == 0) // 全部线程是否已经结束
		{
			Intent intent = new Intent();
			intent.putExtra("progress", Integer.valueOf(utils.getRate(ThreadCount - Integer.valueOf(String.valueOf(latch.getCount())), ThreadCount)));
			intent.putExtra("status", 1);
			intent.setAction(AppContext.Progress_SynchronousData);
			SynchronousData.this.sendBroadcast(intent);
		}
	}
}
