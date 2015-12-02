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

import com.bhq.R;
import com.bhq.app.AppContext;
import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.Dictionary;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.dt_manager;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.BitmapHelper;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;
import com.media.HomeFragmentActivity;
import com.media.MediaChooser;
import com.wheel.OneWheel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:44:17
 * @description :
 */
@SuppressLint("NewApi")
@EActivity(R.layout.editevent)
public class Offline_EditEvent extends Activity
{
	dt_manager_offline dt_manager_offline;
	List<FJ_SCFJ> list_FJ_SCFJ_video = null;
	List<FJ_SCFJ> list_FJ_SCFJ_image = null;
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
	String SJLX = "";
	String SJLXMC = "";
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
			Toast.makeText(Offline_EditEvent.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
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
		Intent intent = new Intent(Offline_EditEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		startActivity(intent);
	}

	@Click
	void imgbtn_addvideo()
	{
		Intent intent = new Intent(Offline_EditEvent.this, HomeFragmentActivity.class);
		intent.putExtra("type", "video");
		startActivity(intent);
	}

	@Click
	void imgbtn_addluyin()
	{
		Intent intent = new Intent(Offline_EditEvent.this, HomeFragmentActivity.class);
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
		dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(this, dt_manager_offline.class);
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
				ImageView imageView = new ImageView(Offline_EditEvent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				BitmapHelper.setImageView(Offline_EditEvent.this, imageView, FJBDLJ);
				imageView.setTag(FJBDLJ);

				FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
				String fjmc = FJBDLJ.substring(FJBDLJ.lastIndexOf("/") + 1, FJBDLJ.length());
				String fjlj = "/upload/" + utils.getToday() + "/" + fjmc;
				String FJID = java.util.UUID.randomUUID().toString();

				fj_SCFJ.setFJID(FJID);
				fj_SCFJ.setGLID(bhq_XHSJ.getSJID());
				fj_SCFJ.setGLBM("SJCJ");
				fj_SCFJ.setFJMC(fjmc);
				fj_SCFJ.setFJLJ(fjlj);
				fj_SCFJ.setSCR(dt_manager_offline.getid());
				fj_SCFJ.setSCSJ(utils.getTime());
				fj_SCFJ.setSCRXM(dt_manager_offline.getreal_name());
				fj_SCFJ.setFJLX("1");
				fj_SCFJ.setSFSC("0");
				fj_SCFJ.setSCZT("1");
				fj_SCFJ.setChange("0");
				fj_SCFJ.setFJBDLJ(FJBDLJ);
				fj_SCFJ.setISUPLOAD("0");
				fj_SCFJ.setFL("");

				list_picture.add(fj_SCFJ);
				ll_picture.addView(imageView);

				imageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						final int index_zp = ll_picture.indexOfChild(v);
						View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
						myDialog = new MyDialog(Offline_EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
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
				ImageView imageView = new ImageView(Offline_EditEvent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setTag(FJBDLJ);
				imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(FJBDLJ, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));

				FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
				String fjmc = FJBDLJ.substring(FJBDLJ.lastIndexOf("/") + 1, FJBDLJ.length());
				String fjlj = "/upload/" + utils.getToday() + "/" + fjmc;
				String FJID = java.util.UUID.randomUUID().toString();

				fj_SCFJ.setFJID(FJID);
				fj_SCFJ.setGLID(bhq_XHSJ.getSJID());
				fj_SCFJ.setGLBM("SJCJ");
				fj_SCFJ.setFJMC(fjmc);
				fj_SCFJ.setFJLJ(fjlj);
				fj_SCFJ.setSCR(dt_manager_offline.getid());
				fj_SCFJ.setSCSJ(utils.getTime());
				fj_SCFJ.setSCRXM(dt_manager_offline.getreal_name());
				fj_SCFJ.setFJLX("2");
				fj_SCFJ.setSFSC("0");
				fj_SCFJ.setSCZT("1");
				fj_SCFJ.setChange("0");
				fj_SCFJ.setFJBDLJ(FJBDLJ);
				fj_SCFJ.setISUPLOAD("0");
				fj_SCFJ.setFL("");

				list_video.add(fj_SCFJ);
				ll_video.addView(imageView);

				imageView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						final int index_zp = ll_video.indexOfChild(v);
						View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
						myDialog = new MyDialog(Offline_EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该视频?", "查看", "删除", new CustomDialogListener()
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
			SJLXMC = ((Bundle) tv_type.getTag()).getString("FN");// 濒危级别
		}
		bhq_XHSJ.setSJLX(SJLX);
		bhq_XHSJ.setSJLXMC(SJLXMC);
		bhq_XHSJ.setIsUpload("0");
		bhq_XHSJ.setSJMS(et_sjms.getText().toString());
		bhq_XHSJ.setCLQK(et_clqk.getText().toString());
		boolean issuccess = SqliteDb.save(Offline_EditEvent.this, bhq_XHSJ);
		if (issuccess)
		{
			list_allfj.addAll(list_picture);
			list_allfj.addAll(list_video);
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
				boolean success = SqliteDb.saveAll(Offline_EditEvent.this, list_allpicture_notload);
				if (success)
				{
					Toast.makeText(Offline_EditEvent.this, "保存成功！", Toast.LENGTH_SHORT).show();
					finish();
				} else
				{
					pb_upload.setVisibility(View.GONE);
					btn_upload.setVisibility(View.VISIBLE);
					Toast.makeText(Offline_EditEvent.this, "数据保存失败", Toast.LENGTH_SHORT).show();
				}
			} else
			{
				Toast.makeText(Offline_EditEvent.this, "保存成功！", Toast.LENGTH_SHORT).show();
				finish();
			}
		} else
		{
			pb_upload.setVisibility(View.GONE);
			btn_upload.setVisibility(View.VISIBLE);
			Toast.makeText(Offline_EditEvent.this, "保存失败！", Toast.LENGTH_SHORT).show();
		}

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
		ImageView imageView = new ImageView(Offline_EditEvent.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
		lp.setMargins(25, 4, 0, 4);
		imageView.setLayoutParams(lp);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		BitmapHelper.loadImage(Offline_EditEvent.this, imageView, fj_SCFJ.getFJBDLJ());

		ll_picture.addView(imageView);
		list_picture.add(fj_SCFJ);
		imageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final int index_zp = ll_picture.indexOfChild(v);
				View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
				myDialog = new MyDialog(Offline_EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
				{
					@Override
					public void OnClick(View v)
					{
						switch (v.getId())
						{
						case R.id.btn_sure:
							Intent intent = new Intent(Offline_EditEvent.this, ShowPhoto_.class);
							intent.putExtra("url", list_picture.get(index_zp).getFJLJ());
							startActivity(intent);
							break;
						case R.id.btn_cancle:
							deletePhotos(list_picture.get(index_zp), list_picture, ll_picture, index_zp);
							myDialog.dismiss();
							break;
						}
					}
				});
				myDialog.show();
			}
		});
	}

	private void deletePhotos(FJ_SCFJ fj_SCFJ, final List<FJ_SCFJ> list_pic, final LinearLayout ll_pic, final int index_zp)
	{
		fj_SCFJ.setISUPLOAD("0");
		fj_SCFJ.setSFSC("1");
		boolean issuccess = SqliteDb.deletePhotos(Offline_EditEvent.this, fj_SCFJ);
		if (issuccess)
		{
			ll_pic.removeViewAt(index_zp);
			list_pic.remove(index_zp);
			Toast.makeText(Offline_EditEvent.this, "删除成功！", Toast.LENGTH_SHORT).show();
		} else
		{
			Toast.makeText(Offline_EditEvent.this, "删除失败！", Toast.LENGTH_SHORT).show();
		}

	}

	private void getDICS()
	{
		list_dic = SqliteDb.getDics(Offline_EditEvent.this, Dictionary.class);
		if (list_dic == null)
		{
			list_dic = new ArrayList<Dictionary>();
		}
	}

	private void showData()
	{
		tv_type.setText(bhq_XHSJ.getSJLXMC());
		et_sjms.setText(bhq_XHSJ.getSJMS());
		et_clqk.setText(bhq_XHSJ.getCLQK());
	}

	private void getVideo(String glid)
	{
		list_FJ_SCFJ_video = SqliteDb.getFJ_SCFJList(Offline_EditEvent.this, FJ_SCFJ.class, glid, "2");
		if (list_FJ_SCFJ_video == null)
		{
			list_FJ_SCFJ_video = new ArrayList<FJ_SCFJ>();
		}
		for (int i = 0; i < list_FJ_SCFJ_video.size(); i++)
		{
			downloadVideo(list_FJ_SCFJ_video.get(i));
		}

	}

	public void downloadVideo(final FJ_SCFJ fj_SCFJ)
	{
		ImageView imageView = new ImageView(Offline_EditEvent.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, LayoutParams.MATCH_PARENT, 0); // ,
																											// 1是可选写的
		lp.setMargins(25, 4, 0, 4);
		imageView.setLayoutParams(lp);// 显示图片的大小
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(fj_SCFJ.getFJBDLJ(), 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));

		list_video.add(fj_SCFJ);
		ll_video.addView(imageView);

		imageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final int index_zp = ll_video.indexOfChild(v);
				View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
				myDialog = new MyDialog(Offline_EditEvent.this, R.style.MyDialog, dialog_layout, "图片", "查看该视频?", "查看", "删除", new CustomDialogListener()
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
							deletePhotos(list_video.get(index_zp), list_video, ll_video, index_zp);
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
