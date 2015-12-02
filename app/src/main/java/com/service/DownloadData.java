package com.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.alibaba.fastjson.JSON;
import com.bhq.app.AppConfig;
import com.bhq.bean.BHQ_ZSK;
import com.bhq.bean.RW_CYR;
import com.bhq.bean.RW_RW;
import com.bhq.bean.RW_YQB;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.SqliteDb;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DownloadData extends Service
{

	public static final String ACTION_DOWNLOADDATA = "ACTION_DOWNLOADDATA";
	String action;

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

		if (ACTION_DOWNLOADDATA.equals(action))
		{
			startInitData();
		} else
		{
		}

		return super.onStartCommand(intent, flags, startId);
	}

	private void startInitData()
	{
//		InitTable("APP.InitDictionaryTable", Dictionary.class);
		InitTable("APP.InitZSKTable", BHQ_ZSK.class);
		InitTable("APP.getRW_RW", RW_RW.class);
		InitTable("APP.getRW_CYR", RW_CYR.class);
		InitTable("APP.getRW_YQB", RW_YQB.class);
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
							boolean issuccess = SqliteDb.saveAll(DownloadData.this, listData);
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

							} else
							{
							}
						}
					}
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
					boolean issuccess = SqliteDb.updateZSNR(DownloadData.this, bhq_ZSK);
					if (issuccess)
					{

					} else
					{
					}
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
}
