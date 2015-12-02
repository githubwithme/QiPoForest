package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.Dictionary;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.dt_manager;
import com.bhq.common.BitmapHelper;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.utils;
import com.bhq.net.HttpUrlConnect;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.entity.FileUploadEntity;
import com.media.HomeFragmentActivity;
import com.media.MediaChooser;
import com.wheel.OneWheel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:44:17
 * @description :
 */
@SuppressLint("NewApi")
@EActivity(R.layout.addevent)
public class AddEvent extends Activity
{
	@ViewById
	ProgressBar pb_upload;
	@ViewById
	EditText et_clqk;
	@ViewById
	TextView tv_type;
	@ViewById
	EditText et_sjms;
	@ViewById
	ImageButton imgbtn_addvideo;
	@ViewById
	ImageButton imgbtn_addpicture;
	@ViewById
	LinearLayout ll_picture;
	@ViewById
	LinearLayout ll_video;
	List<FJ_SCFJ> list_picture = new ArrayList<FJ_SCFJ>();
	List<FJ_SCFJ> list_video = new ArrayList<FJ_SCFJ>();
	List<FJ_SCFJ> list_allfj = new ArrayList<FJ_SCFJ>();
	dt_manager dt_manager;
	MyDialog myDialog;
	AppContext appContext;
	CountDownLatch latch;
	String SJLX = "";
	String SJID;
	List<Dictionary> list_dic;

	@Click
	void tv_type()
	{
		String[] firstItemid = new String[] {};
		String[] firstItemData = new String[] {};
		List<String> list_id = new ArrayList<String>();
		List<String> list_name = new ArrayList<String>();
		for (int i = 0; i < list_dic.size(); i++)
		{
			if (list_dic.get(i).getLX().equals("SJLX"))
			{
				list_id.add(list_dic.get(i).getDID());
				list_name.add(list_dic.get(i).getNAME());
			}
		}
		firstItemid = new String[list_id.size()];
		firstItemData = new String[list_id.size()];
		for (int i = 0; i < list_id.size(); i++)
		{
			firstItemid[i] = list_id.get(i);
			firstItemData[i] = list_name.get(i);
		}
		if (list_id.size() != 0)
		{
			OneWheel.showWheel(this, firstItemid, firstItemData, tv_type);
		} else
		{
			Toast.makeText(AddEvent.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
		}
	}

	@ViewById
	Button btn_upload;

	@Click
	void btn_upload()
	{
		pb_upload.setVisibility(View.VISIBLE);
		btn_upload.setVisibility(View.GONE);
		uploadData();
	}

	@Click
	void imgbtn_addpicture()
	{
		Intent intent = new Intent(AddEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		startActivity(intent);
	}

	@Click
	void imgbtn_addvideo()
	{
		Intent intent = new Intent(AddEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "video");
		startActivity(intent);
	}

	@Click
	void imgbtn_addluyin()
	{
		Intent intent = new Intent(AddEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "luyin");
		startActivity(intent);
	}

	@AfterViews
	void afterOncreate()
	{
		getDICS();
	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		IntentFilter videoIntentFilter = new IntentFilter(MediaChooser.VIDEO_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
		registerReceiver(videoBroadcastReceiver, videoIntentFilter);

		IntentFilter imageIntentFilter = new IntentFilter(MediaChooser.IMAGE_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
		registerReceiver(imageBroadcastReceiver, imageIntentFilter);

		dt_manager = AppContext.getUserInfo(this);
		appContext = (AppContext) getApplication();
	};

	BroadcastReceiver imageBroadcastReceiver = new BroadcastReceiver()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			List<String> list = intent.getStringArrayListExtra("list");
			for (int i = 0; i < list.size(); i++)
			{
				String FJBDLJ = list.get(i);
				ImageView imageView = new ImageView(AddEvent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				BitmapHelper.setImageView(AddEvent.this, imageView, FJBDLJ);
				imageView.setTag(FJBDLJ);

				FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
				fj_SCFJ.setFJBDLJ(FJBDLJ);
				fj_SCFJ.setFJLX("1");

				list_picture.add(fj_SCFJ);
				list_allfj.add(fj_SCFJ);
				ll_picture.addView(imageView);

				imageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						final int index_zp = ll_picture.indexOfChild(v);
						View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
						myDialog = new MyDialog(AddEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
						{
							@Override
							public void OnClick(View v)
							{
								switch (v.getId())
								{
								case R.id.btn_sure:
									File file = new File(list_picture.get(index_zp).getFJBDLJ());
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setDataAndType(Uri.fromFile(file), "image/*");
									startActivity(intent);
									break;
								case R.id.btn_cancle:
									ll_picture.removeViewAt(index_zp);
									list_picture.remove(index_zp);
									myDialog.dismiss();
									break;
								}
							}
						});
						myDialog.show();
					}
				});
			}
		}
	};
	BroadcastReceiver videoBroadcastReceiver = new BroadcastReceiver()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			List<String> list = intent.getStringArrayListExtra("list");
			for (int i = 0; i < list.size(); i++)
			{
				String FJBDLJ = list.get(i);
				ImageView imageView = new ImageView(AddEvent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setTag(FJBDLJ);
				imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(FJBDLJ, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));

				FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
				fj_SCFJ.setFJBDLJ(FJBDLJ);
				fj_SCFJ.setFJLX("2");

				list_video.add(fj_SCFJ);
				list_allfj.add(fj_SCFJ);
				ll_video.addView(imageView);

				imageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						final int index_zp = ll_video.indexOfChild(v);
						View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
						myDialog = new MyDialog(AddEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该视频?", "查看", "删除", new CustomDialogListener()
						{
							@Override
							public void OnClick(View v)
							{
								switch (v.getId())
								{
								case R.id.btn_sure:
									File file = new File(list_video.get(index_zp).getFJBDLJ());
									Intent intent = new Intent(Intent.ACTION_VIEW);
									intent.setDataAndType(Uri.fromFile(file), "video/*");
									startActivity(intent);
									break;
								case R.id.btn_cancle:
									ll_video.removeViewAt(index_zp);
									list_video.remove(index_zp);
									myDialog.dismiss();
									break;
								}
							}
						});
						myDialog.show();
					}
				});
			}
		}
	};

	private void uploadData()
	{
		if ((Bundle) tv_type.getTag() != null)
		{
			SJLX = ((Bundle) tv_type.getTag()).getString("FI");// 濒危级别
		}
		SJID = java.util.UUID.randomUUID().toString();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("SJID", SJID);
		hashMap.put("SJLX", SJLX);
		hashMap.put("SJMS", et_sjms.getText().toString());
		hashMap.put("CLQK", et_clqk.getText().toString());
		hashMap.put("SBR", dt_manager.getid());
		hashMap.put("SBRXM", dt_manager.getreal_name());
		hashMap.put("SBSJ", utils.getTime());
		hashMap.put("SSBHZ", dt_manager.getDepartId());
		hashMap.put("LCZT", "1");
		hashMap.put("X", appContext.getLOCATION_Y());
		hashMap.put("Y", appContext.getLOCATION_X());
		hashMap.put("XHID", appContext.getXHID());
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.InsertBHQ_XHSJ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					if (list_allfj.size() > 0)
					{
						latch = new CountDownLatch(list_allfj.size() + list_allfj.size());
						uploadFJ_SCFJ(list_allfj);
						uploadPhotos(list_allfj);
					} else
					{
						Toast.makeText(AddEvent.this, "保存成功！", Toast.LENGTH_SHORT).show();
						finish();
					}

				} else
				{
					Toast.makeText(AddEvent.this, "采集信息上传失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(AddEvent.this, "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void uploadFJ_SCFJ(List<FJ_SCFJ> list_allpicture)
	{
		for (int i = 0; i < list_allpicture.size(); i++)
		{
			FJ_SCFJ fj_SCFJ = list_allpicture.get(i);
			String fjmc = fj_SCFJ.getFJBDLJ().substring(fj_SCFJ.getFJBDLJ().lastIndexOf("/") + 1, fj_SCFJ.getFJBDLJ().length());
			String fjlj = "/upload/" + utils.getToday() + "/" + fjmc;
			String FJID = java.util.UUID.randomUUID().toString();
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("FJID", FJID);
			hashMap.put("GLID", SJID);
			hashMap.put("GLBM", "XHSJ");
			hashMap.put("FJMC", fjmc);
			hashMap.put("FJLJ", fjlj);
			hashMap.put("SCSJ", utils.getTime());
			hashMap.put("SCR", "1");
			hashMap.put("SCRXM", dt_manager.getreal_name());
			hashMap.put("FJLX", fj_SCFJ.getFJLX());
			hashMap.put("SFSC", "0");
			hashMap.put("SCZT", "1");
			hashMap.put("Change", "0");
			hashMap.put("FL", "");
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.InsertFJ_SCFJ", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
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
						Toast.makeText(AddEvent.this, "附件数据上传失败", Toast.LENGTH_SHORT).show();
						return;
					}
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
				}
			});
		}

	}

	private void uploadPhotos(List<FJ_SCFJ> list_allpicture)
	{
		for (int i = 0; i < list_allpicture.size(); i++)
		{
			File file = new File(list_allpicture.get(i).getFJBDLJ());
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("param", utils.getToday());
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
					showProgress();
				}

				@Override
				public void onFailure(HttpException error, String msg)
				{
					Toast.makeText(AddEvent.this, "附件图片上传失败！" + error.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	private void showProgress()
	{
		latch.countDown();
		Long l = latch.getCount();
		if (l.intValue() == 0) // 全部线程是否已经结束
		{
			Toast.makeText(AddEvent.this, "上传成功！", Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	private void getDICS()
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String params = ConnectionHelper.setParams("APP.GetDic", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					list_dic = JSON.parseArray(ResultDeal.getAllRow(result), Dictionary.class);
					if (list_dic == null)
					{
						list_dic = new ArrayList<Dictionary>();
					}
				} else
				{
					AppContext.makeToast(AddEvent.this, "error_connectDataBase");
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				AppContext.makeToast(AddEvent.this, "error_connectServer");
			}
		});
	}
}
