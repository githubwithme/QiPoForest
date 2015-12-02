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
import com.bhq.bean.BHQ_XHSJ;
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
@EActivity(R.layout.editevent)
public class EditEvent extends Activity
{
	List<FJ_SCFJ> list_FJ_SCFJ_FROMSERVER;
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
	List<Dictionary> list_dic;
	BHQ_XHSJ bhq_XHSJ;

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
			Toast.makeText(EditEvent.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
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
		Intent intent = new Intent(EditEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		startActivity(intent);
	}

	@Click
	void imgbtn_addvideo()
	{
		Intent intent = new Intent(EditEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "video");
		startActivity(intent);
	}

	@Click
	void imgbtn_addluyin()
	{
		Intent intent = new Intent(EditEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "luyin");
		startActivity(intent);
	}

	@AfterViews
	void afterOncreate()
	{
		showData();
		getVideo(bhq_XHSJ.getSJID());
		if (list_FJ_SCFJ_FROMSERVER != null)
		{
			showServerphotos();
		}
		getDICS();
	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();

		bhq_XHSJ = getIntent().getParcelableExtra("bean");
		SJLX = bhq_XHSJ.getSJLX();
		list_FJ_SCFJ_FROMSERVER = getIntent().getParcelableArrayListExtra("beanlist");
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
				ImageView imageView = new ImageView(EditEvent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				BitmapHelper.setImageView(EditEvent.this, imageView, FJBDLJ);
				imageView.setTag(FJBDLJ);

				FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
				fj_SCFJ.setFJBDLJ(FJBDLJ);
				fj_SCFJ.setFJLX("1");
				fj_SCFJ.setISUPLOAD("0");

				list_picture.add(fj_SCFJ);
				ll_picture.addView(imageView);

				imageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						final int index_zp = ll_picture.indexOfChild(v);
						View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
						myDialog = new MyDialog(EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
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
				ImageView imageView = new ImageView(EditEvent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setTag(FJBDLJ);
				imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(FJBDLJ, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));

				FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
				fj_SCFJ.setFJBDLJ(FJBDLJ);
				fj_SCFJ.setFJLX("2");
				fj_SCFJ.setISUPLOAD("0");

				list_video.add(fj_SCFJ);
				ll_video.addView(imageView);

				imageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						final int index_zp = ll_video.indexOfChild(v);
						View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
						myDialog = new MyDialog(EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该视频?", "查看", "删除", new CustomDialogListener()
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
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("SJID", bhq_XHSJ.getSJID());
		hashMap.put("SJLX", SJLX);
		hashMap.put("SJMS", et_sjms.getText().toString());
		hashMap.put("CLQK", et_clqk.getText().toString());
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.UpdateXHSJ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					list_allfj.addAll(list_picture);
					list_allfj.addAll(list_video);
					if (list_allfj.size() > 0)
					{
						List<FJ_SCFJ> list_allpicture_notload = new ArrayList<FJ_SCFJ>();
						for (int i = 0; i < list_allfj.size(); i++)
						{
							if (list_allfj.get(i).getISUPLOAD().equals("0"))
							{
								list_allpicture_notload.add(list_allfj.get(i));
							}
						}
						if (list_allpicture_notload.size() > 0)
						{
							latch = new CountDownLatch(list_allpicture_notload.size() + list_allpicture_notload.size());
							uploadFJ_SCFJ(list_allpicture_notload);
							uploadPhotos(list_allpicture_notload);
						} else
						{
							Toast.makeText(EditEvent.this, "保存成功！", Toast.LENGTH_SHORT).show();
							finish();
						}
					} else
					{
						Toast.makeText(EditEvent.this, "保存成功！", Toast.LENGTH_SHORT).show();
						finish();
					}

				} else
				{
					Toast.makeText(EditEvent.this, "采集信息上传失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(EditEvent.this, "上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void showServerphotos()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
	{
		for (int i = 0; i < list_FJ_SCFJ_FROMSERVER.size(); i++)
		{
			addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i));
		}
	}

	private void addServerPicture(FJ_SCFJ fj_SCFJ)
	{
		ImageView imageView = new ImageView(EditEvent.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
		lp.setMargins(25, 4, 0, 4);
		imageView.setLayoutParams(lp);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		BitmapHelper.setImageView(EditEvent.this, imageView, AppConfig.url + fj_SCFJ.getFJLJ());// ?

		fj_SCFJ.setISUPLOAD("1");// 必须设置
		ll_picture.addView(imageView);
		list_picture.add(fj_SCFJ);
		imageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final int index_zp = ll_picture.indexOfChild(v);
				View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
				myDialog = new MyDialog(EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
				{
					@Override
					public void OnClick(View v)
					{
						switch (v.getId())
						{
						case R.id.btn_sure:
							Intent intent = new Intent(EditEvent.this, ShowPhoto_.class);
							intent.putExtra("url", list_picture.get(index_zp).getFJLJ());
							startActivity(intent);
							break;
						case R.id.btn_cancle:
							deletePhotos(list_picture.get(index_zp).getFJID(), list_picture, ll_picture, index_zp);
							myDialog.dismiss();
							break;
						}
					}
				});
				myDialog.show();
			}
		});
	}

	private void deletePhotos(String fjid, final List<FJ_SCFJ> list_pic, final LinearLayout ll_pic, final int index_zp)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("FJID", fjid);
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.deletePhotos", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String aa = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					ll_pic.removeViewAt(index_zp);
					list_pic.remove(index_zp);
					Toast.makeText(EditEvent.this, "删除成功！", Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(EditEvent.this, "删除失败！", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(EditEvent.this, "删除失败！", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void uploadFJ_SCFJ(List<FJ_SCFJ> list_allpicture)
	{
		for (int i = 0; i < list_allpicture.size(); i++)
		{
			FJ_SCFJ fj_SCFJ = list_allpicture.get(i);
			String fjmc = fj_SCFJ.getFJBDLJ().substring(fj_SCFJ.getFJBDLJ().lastIndexOf("/") + 1, fj_SCFJ.getFJBDLJ().length());
			String fjlj = "upload/" + utils.getToday() + "/" + fjmc;
			String FJID = java.util.UUID.randomUUID().toString();
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("FJID", FJID);
			hashMap.put("GLID", bhq_XHSJ.getSJID());
			hashMap.put("GLBM", "xhsj");
			hashMap.put("FJMC", fjmc);
			hashMap.put("FJLJ", fjlj);
			hashMap.put("SCR", dt_manager.getid());
			hashMap.put("SCRXM", dt_manager.getreal_name());
			hashMap.put("FJLX", fj_SCFJ.getFJLX());
			hashMap.put("SFSC", "0");
			hashMap.put("SCZT", "1");
			hashMap.put("SCSJ", utils.getTime());
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
						Toast.makeText(EditEvent.this, "附件数据上传失败", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(EditEvent.this, "附件图片上传失败！" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
			Toast.makeText(EditEvent.this, "上传成功！", Toast.LENGTH_SHORT).show();
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
					AppContext.makeToast(EditEvent.this, "error_connectDataBase");
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				AppContext.makeToast(EditEvent.this, "error_connectServer");
			}
		});
	}

	private void showData()
	{
		tv_type.setText(bhq_XHSJ.getSJLXMC());
		et_sjms.setText(bhq_XHSJ.getSJMS());
		et_clqk.setText(bhq_XHSJ.getCLQK());
	}

	private void getVideo(String glid)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("GLID", glid);
		hashMap.put("FJLX", "2");
		String params = ConnectionHelper.setParams("APP.SelectFJ_SCFJ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				List<FJ_SCFJ> list_videofj = new ArrayList<FJ_SCFJ>();
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					list_videofj = JSON.parseArray(ResultDeal.getAllRow(result), FJ_SCFJ.class);
					if (list_videofj == null)
					{
						list_videofj = new ArrayList<FJ_SCFJ>();
					}
				} else
				{
					AppContext.makeToast(EditEvent.this, "error_connectDataBase");
					return;
				}
				for (int i = 0; i < list_videofj.size(); i++)
				{
					ProgressBar progressBar = new ProgressBar(EditEvent.this, null, android.R.attr.progressBarStyleHorizontal);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, LayoutParams.MATCH_PARENT, 0);
					lp.setMargins(25, 4, 0, 4);
					progressBar.setLayoutParams(lp);
					ll_video.addView(progressBar);
					downloadVideo(list_videofj.get(i), AppConfig.url + list_videofj.get(i).getFJLJ(), AppConfig.DOWNLOADPATH_VIDEO + list_videofj.get(i).getFJMC(), progressBar);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(EditEvent.this, "获取失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void downloadVideo(final FJ_SCFJ fj_SCFJ, String path, final String target, final ProgressBar progressBar)
	{
		HttpUtils http = new HttpUtils();
		http.download(path, target, true, true, new RequestCallBack<File>()
		{
			@Override
			public void onLoading(long total, long current, boolean isUploading)
			{
				if (total > 0)
				{
					progressBar.setProgress((int) ((double) current / (double) total * 100));
				} else
				{
					progressBar.setProgress(0);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				if (msg.equals("maybe the file has downloaded completely"))
				{
					ll_video.removeView(progressBar);
					ImageView imageView = new ImageView(EditEvent.this);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, LayoutParams.MATCH_PARENT, 0); // ,
																														// 1是可选写的
					lp.setMargins(25, 4, 0, 4);
					imageView.setLayoutParams(lp);// 显示图片的大小
					imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(target, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));

					fj_SCFJ.setFJBDLJ(target);
					fj_SCFJ.setFJLX("2");
					fj_SCFJ.setISUPLOAD("1");

					list_video.add(fj_SCFJ);
					ll_video.addView(imageView);

					imageView.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							final int index_zp = ll_video.indexOfChild(v);
							View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
							myDialog = new MyDialog(EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该视频?", "查看", "删除", new CustomDialogListener()
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
										FJ_SCFJ fj_SCFJ =list_video.get(index_zp);
										deletePhotos(list_video.get(index_zp).getFJID(), list_video, ll_video, index_zp);
										myDialog.dismiss();
										break;
									}
								}
							});
							myDialog.show();
						}
					});
				} else
				{
					Toast.makeText(EditEvent.this, "下载失败！找不到文件!", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo)
			{
				// progressBar.setVisibility(View.INVISIBLE);
				ll_video.removeView(progressBar);
				Toast.makeText(EditEvent.this, "下载成功！", Toast.LENGTH_SHORT).show();

				ImageView imageView = new ImageView(EditEvent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setTag(target);
				imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(target, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));

				fj_SCFJ.setFJBDLJ(target);
				fj_SCFJ.setFJLX("2");
				fj_SCFJ.setISUPLOAD("1");

				list_video.add(fj_SCFJ);
				ll_video.addView(imageView);

				imageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						final int index_zp = ll_video.indexOfChild(v);
						View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
						myDialog = new MyDialog(EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该视频?", "查看", "删除", new CustomDialogListener()
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
									FJ_SCFJ fj_SCFJ =list_video.get(index_zp);
									deletePhotos(list_video.get(index_zp).getFJID(), list_video, ll_video, index_zp);
									myDialog.dismiss();
									break;
								}
							}
						});
						myDialog.show();
					}
				});
			}
		});

	}

}
