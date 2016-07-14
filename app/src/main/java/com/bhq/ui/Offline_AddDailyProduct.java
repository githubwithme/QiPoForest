package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
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
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.Dictionary;
import com.bhq.bean.FJ_SCFJ;
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
 * @author hc-sima
 * @version 1.0
 * @createTime：2015-8-14 下午6:44:17
 * @description :离线采集数据
 */
@SuppressLint("NewApi")
@EActivity(R.layout.offline_adddailyproduct)
public class Offline_AddDailyProduct extends Activity
{
	@ViewById
	ProgressBar pb_upload;
	@ViewById
	Button btn_upload;
	@ViewById
	EditText et_bz;
	@ViewById
	TextView tv_type;
	@ViewById
	ImageButton imgbtn_addpicture;
	@ViewById
	LinearLayout ll_picture;
	List<FJ_SCFJ> list_picture = new ArrayList<FJ_SCFJ>();
	List<Dictionary> list_dic;
	@ViewById
	LinearLayout ll_addpicture;

	String ZYDLID = "";
	String ZYXLID = "";
	String BHJB = "";
	String BWD = "";
	String ZYDLMC = "";
	String ZYXLMC = "";
	String BHJBMC = "";
	String BWDMC = "";
	MyDialog myDialog;
	String CJID;
	AppContext appContext;
	String witchphoto;
	String type;
	dt_manager_offline dt_manager_offline;

	@Click
	void imgbtn_back()
	{
		finish();
	}

	// 植物图片添加
	@Click
	void imgbtn_addpicture()
	{
		witchphoto = "ZW_HD";
		Intent intent = new Intent(Offline_AddDailyProduct.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}
	@Click
	void tv_type()
	{
		String[] firstItemid = new String[] {};
		String[] firstItemData = new String[] {};
		List<String> list_id = new ArrayList<String>();
		List<String> list_name = new ArrayList<String>();
		for (int j = 0; j < list_dic.size(); j++)
		{
			if (list_dic.get(j).getLX().equals("ZYXL") && list_dic.get(j).getPID().equals("02"))
			{
				Dictionary dictionary = list_dic.get(j);
				list_id.add(list_dic.get(j).getDID());
				list_name.add(list_dic.get(j).getNAME());
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
			Toast.makeText(Offline_AddDailyProduct.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
		}

	}


	@Click
	void btn_upload()
	{
		upload();
	}

	@AfterViews
	void afterOncreate()
	{
		getDICS();
	}

	/**
	 * 该类入口
	 * 功能：初始化界面
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		CJID = java.util.UUID.randomUUID().toString();
		dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(this, dt_manager_offline.class);
		appContext = (AppContext) getApplication();
		ZYDLID = "02";
		type="植物";
		IntentFilter imageIntentFilter = new IntentFilter(MediaChooser.IMAGE_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
		registerReceiver(imageBroadcastReceiver, imageIntentFilter);
	};

	BroadcastReceiver imageBroadcastReceiver = new BroadcastReceiver()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			List<String> list = intent.getStringArrayListExtra("list");
			addPicture("2", list, list_picture, ll_picture);
		}
	};
	/**
	 * 添加图片
	 */
	public void addPicture(String type, List<String> list, final List<FJ_SCFJ> list_pic, final LinearLayout ll_pic)
	{
		for (int i = 0; i < list.size(); i++)
		{
			String FJBDLJ = list.get(i);
			ImageView imageView = new ImageView(Offline_AddDailyProduct.this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
			lp.setMargins(25, 4, 0, 4);
			imageView.setLayoutParams(lp);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			BitmapHelper.setImageView(Offline_AddDailyProduct.this, imageView, FJBDLJ);
			imageView.setTag(FJBDLJ);

			FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
			String fjmc = FJBDLJ.substring(FJBDLJ.lastIndexOf("/") + 1, FJBDLJ.length());
			String fjlj = "/upload/" + utils.getToday() + "/" + fjmc;
			String LSTLJ = "/upload/" + utils.getToday() + "/" + "thumb_"+fjmc;
			String FJID = java.util.UUID.randomUUID().toString();

			fj_SCFJ.setFJID(FJID);
			fj_SCFJ.setGLID(CJID);
			fj_SCFJ.setGLBM("SJCJ");
			fj_SCFJ.setFJMC(fjmc);
			fj_SCFJ.setFJLJ(fjlj);
			fj_SCFJ.setLSTLJ(LSTLJ);
			fj_SCFJ.setSCR(dt_manager_offline.getid());
			fj_SCFJ.setSCSJ(utils.getTime());
			fj_SCFJ.setSCRXM(dt_manager_offline.getreal_name());
			fj_SCFJ.setFJLX("1");
			fj_SCFJ.setSFSC("0");
			fj_SCFJ.setSCZT("1");
			fj_SCFJ.setChange("0");
			fj_SCFJ.setFJBDLJ(FJBDLJ);
			fj_SCFJ.setFL(type);
			fj_SCFJ.setISUPLOAD("0");

			list_pic.add(fj_SCFJ);
			ll_pic.addView(imageView);

			imageView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					final int index_zp = ll_pic.indexOfChild(v);
					View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
					myDialog = new MyDialog(Offline_AddDailyProduct.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
					{
						@Override
						public void OnClick(View v)
						{
							switch (v.getId())
							{
							case R.id.btn_sure:
								File file = new File(list_pic.get(index_zp).getFJBDLJ());
								Intent intent = new Intent(Intent.ACTION_VIEW);
								intent.setDataAndType(Uri.fromFile(file), "image/*");
								startActivity(intent);
								break;
							case R.id.btn_cancle:
								ll_pic.removeViewAt(index_zp);
								list_pic.remove(index_zp);
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
	/**
	 * 保存数据
	 */
	public void upload()
	{
		if (tv_type.getText().equals(""))
		{
			Toast.makeText(Offline_AddDailyProduct.this, "类型不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		if ((Bundle) tv_type.getTag() != null)
		{
			ZYXLID = ((Bundle) tv_type.getTag()).getString("FI");// 资源小类
			ZYXLMC = ((Bundle) tv_type.getTag()).getString("FN");// 资源小类
		}

		pb_upload.setVisibility(View.VISIBLE);
		btn_upload.setVisibility(View.GONE);

		BHQ_XHSJCJ bhq_XHSJCJ = new BHQ_XHSJCJ();
		bhq_XHSJCJ.setCJID(CJID);
		bhq_XHSJCJ.setXHID(appContext.getXHID());
		bhq_XHSJCJ.setSSBHZ(dt_manager_offline.getDepartId());
		bhq_XHSJCJ.setCJR(dt_manager_offline.getid());
		bhq_XHSJCJ.setCJRXM(dt_manager_offline.getreal_name());
		bhq_XHSJCJ.setCJSJ(utils.getTime());
		bhq_XHSJCJ.setZYDL(ZYDLID);
		bhq_XHSJCJ.setZYDLMC(type);
		bhq_XHSJCJ.setZYXL(ZYXLID);
		bhq_XHSJCJ.setZYXLMC(ZYXLMC);
		bhq_XHSJCJ.setZM("");
		bhq_XHSJCJ.setGANG("");
		bhq_XHSJCJ.setMU("");
		bhq_XHSJCJ.setKE("");
		bhq_XHSJCJ.setBHJB(BHJB);
		bhq_XHSJCJ.setBHJBMC(BHJBMC);
		bhq_XHSJCJ.setBWD(BWD);
		bhq_XHSJCJ.setBWDMC(BWDMC);
		bhq_XHSJCJ.setTZ("");
		bhq_XHSJCJ.setXX("");
		bhq_XHSJCJ.setSZHJ("");
		bhq_XHSJCJ.setBWYS("");
		bhq_XHSJCJ.setBDZYBZ(et_bz.getText().toString());
		bhq_XHSJCJ.setX(appContext.getLOCATION_Y());
		bhq_XHSJCJ.setY(appContext.getLOCATION_X());
		bhq_XHSJCJ.setSFSC("0");
		bhq_XHSJCJ.setIsUpload("0");
		boolean issuccess = SqliteDb.save(Offline_AddDailyProduct.this, bhq_XHSJCJ);
		if (issuccess)
		{
			if (list_picture.size() > 0)
			{
				boolean success = SqliteDb.saveAll(Offline_AddDailyProduct.this, list_picture);
				if (success)
				{
					Toast.makeText(Offline_AddDailyProduct.this, "保存成功！", Toast.LENGTH_SHORT).show();
					finish();
				} else
				{
					pb_upload.setVisibility(View.GONE);
					btn_upload.setVisibility(View.VISIBLE);
					Toast.makeText(Offline_AddDailyProduct.this, "数据保存失败", Toast.LENGTH_SHORT).show();
				}
			} else
			{
				Toast.makeText(Offline_AddDailyProduct.this, "保存成功！", Toast.LENGTH_SHORT).show();
				finish();
			}
		} else
		{
			pb_upload.setVisibility(View.GONE);
			btn_upload.setVisibility(View.VISIBLE);
			Toast.makeText(Offline_AddDailyProduct.this, "数据保存失败", Toast.LENGTH_SHORT).show();
		}

	}
	/**
	 * 获取字典
	 */
	public void getDICS()
	{
		list_dic = SqliteDb.getDics(Offline_AddDailyProduct.this, Dictionary.class);
		if (list_dic == null)
		{
			list_dic = new ArrayList<Dictionary>();
		}

	}

}
