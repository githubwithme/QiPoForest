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

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.BHQ_XHSJCJ;
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
@EActivity(R.layout.editcjxx)
public class EditCJXX extends Activity
{

	@ViewById
	ProgressBar pb_upload;
	@ViewById
	Button btn_upload;
	@ViewById
	TextView tv_title;
	@ViewById
	TextView tv_type;
	@ViewById
	TextView tv_bhjb;
	@ViewById
	TextView tv_bwd;

	@ViewById
	EditText et_zm;
	@ViewById
	EditText et_gang;
	@ViewById
	EditText et_mu;
	@ViewById
	EditText et_ke;
	@ViewById
	EditText et_tz;
	@ViewById
	EditText et_xx;
	@ViewById
	EditText et_szhj;
	@ViewById
	EditText et_bwys;
	@ViewById
	EditText et_bz;
	// 子项植物添加按钮ImageButton
	@ViewById
	ImageButton imgbtn_addflowerpicture;
	@ViewById
	ImageButton imgbtn_addfruitpicture;
	@ViewById
	ImageButton imgbtn_addwholepicture;
	@ViewById
	ImageButton imgbtn_addLeafpicture;
	// 子项动物添加按钮ImageButton
	@ViewById
	ImageButton imgbtn_animal_addwholepicture;
	@ViewById
	ImageButton imgbtn_animal_addJYpicture;
	@ViewById
	ImageButton imgbtn_animal_addFBpicture;
	// 子项微生物添加按钮ImageButton
	@ViewById
	ImageButton imgbtn_wsw_addpicture;
	// 存放植物LinearLayout
	@ViewById
	LinearLayout ll_Leafpicture;
	@ViewById
	LinearLayout ll_flowerpicture;
	@ViewById
	LinearLayout ll_fruitpicture;
	@ViewById
	LinearLayout ll_wholepicture;
	// 存放动物LinearLayout
	@ViewById
	LinearLayout ll_animal_wholepicture;
	@ViewById
	LinearLayout ll_animal_JYpicture;
	@ViewById
	LinearLayout ll_animal_FBpicture;
	// 存放微生物LinearLayout
	@ViewById
	LinearLayout ll_wsw_picture;
	// 存放植物FJ_SCFJ
	List<FJ_SCFJ> list_plant_flower = new ArrayList<FJ_SCFJ>();
	List<FJ_SCFJ> list_plant_fruit = new ArrayList<FJ_SCFJ>();
	List<FJ_SCFJ> list_plant_whole = new ArrayList<FJ_SCFJ>();
	List<FJ_SCFJ> list_plant_leaf = new ArrayList<FJ_SCFJ>();
	// 存放植物FJ_SCFJ
	List<FJ_SCFJ> list_animal_jy = new ArrayList<FJ_SCFJ>();
	List<FJ_SCFJ> list_animal_fb = new ArrayList<FJ_SCFJ>();
	List<FJ_SCFJ> list_animal_whole = new ArrayList<FJ_SCFJ>();
	// 存放微生物FJ_SCFJ
	List<FJ_SCFJ> list_wsw_picture = new ArrayList<FJ_SCFJ>();
	// 存放所有图片FJ_SCFJ
	List<FJ_SCFJ> list_allpicture = new ArrayList<FJ_SCFJ>();
	List<Dictionary> list_dic;
	// 图片最外层布局，用于显示隐藏
	@ViewById
	LinearLayout ll_addanimalpicture;
	@ViewById
	LinearLayout ll_addplantpicture;
	@ViewById
	LinearLayout ll_addwswpicture;
	String ZYXLID = "";
	String BHJB = "";
	String BWD = "";
	MyDialog myDialog;
	CountDownLatch latch;
	AppContext appContext;
	String witchphoto;
	String type;
	dt_manager dt_manager;
	BHQ_XHSJCJ bhq_XHSJCJ;
	List<FJ_SCFJ> list_FJ_SCFJ_FROMSERVER;

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@Click
	void tv_bwd()
	{
		String[] firstItemid = new String[] {};
		String[] firstItemData = new String[] {};
		List<String> list_id = new ArrayList<String>();
		List<String> list_name = new ArrayList<String>();
		for (int i = 0; i < list_dic.size(); i++)
		{
			if (list_dic.get(i).getLX().equals("BWD"))
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
			OneWheel.showWheel(this, firstItemid, firstItemData, tv_bwd);
		} else
		{
			Toast.makeText(EditCJXX.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
		}

	}

	@Click
	void tv_bhjb()
	{
		String[] firstItemid = new String[] {};
		String[] firstItemData = new String[] {};
		List<String> list_id = new ArrayList<String>();
		List<String> list_name = new ArrayList<String>();
		for (int i = 0; i < list_dic.size(); i++)
		{
			if (list_dic.get(i).getLX().equals("BHJB"))
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
			OneWheel.showWheel(this, firstItemid, firstItemData, tv_bhjb);
		} else
		{
			Toast.makeText(EditCJXX.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
		}

	}

	@Click
	void tv_type()
	{
		String[] firstItemid = new String[] {};
		String[] firstItemData = new String[] {};
		List<String> list_id = new ArrayList<String>();
		List<String> list_name = new ArrayList<String>();
		if (type.equals("动物"))
		{
			for (int j = 0; j < list_dic.size(); j++)
			{

				if (list_dic.get(j).getLX().equals("ZYXL") && list_dic.get(j).getPID().equals("01"))
				{
					Dictionary dictionary = list_dic.get(j);
					list_id.add(list_dic.get(j).getDID());
					list_name.add(list_dic.get(j).getNAME());
				}
			}
		} else if (type.equals("植物"))
		{
			for (int j = 0; j < list_dic.size(); j++)
			{
				if (list_dic.get(j).getLX().equals("ZYXL") && list_dic.get(j).getPID().equals("02"))
				{
					list_id.add(list_dic.get(j).getDID());
					list_name.add(list_dic.get(j).getNAME());
				}
			}
		} else if (type.equals("微生物"))
		{
			for (int j = 0; j < list_dic.size(); j++)
			{
				if (list_dic.get(j).getLX().equals("ZYXL") && list_dic.get(j).getPID().equals("03"))
				{
					list_id.add(list_dic.get(j).getDID());
					list_name.add(list_dic.get(j).getNAME());
				}
			}
		}

		if (list_id.size() != 0)
		{
			firstItemid = new String[list_id.size()];
			firstItemData = new String[list_id.size()];
			for (int i = 0; i < list_id.size(); i++)
			{
				firstItemid[i] = list_id.get(i);
				firstItemData[i] = list_name.get(i);
			}
			OneWheel.showWheel(this, firstItemid, firstItemData, tv_type);
		} else
		{
			Toast.makeText(EditCJXX.this, "数据获取异常，请重试！", Toast.LENGTH_SHORT).show();
		}

	}

	// 植物图片添加
	@Click
	void imgbtn_addflowerpicture()
	{
		witchphoto = "ZW_HD";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	@Click
	void imgbtn_addfruitpicture()
	{
		witchphoto = "ZW_GS";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	@Click
	void imgbtn_addwholepicture()
	{
		witchphoto = "ZW_ZT";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	@Click
	void imgbtn_addLeafpicture()
	{
		witchphoto = "ZW_YZ";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	// 动物图片添加
	@Click
	void imgbtn_animal_addwholepicture()
	{
		witchphoto = "DW_ZT";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	@Click
	void imgbtn_animal_addJYpicture()
	{
		witchphoto = "DW_JY";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	@Click
	void imgbtn_animal_addFBpicture()
	{
		witchphoto = "DW_FB";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	// 微生物图片添加
	@Click
	void imgbtn_wsw_addpicture()
	{
		witchphoto = "WSW_ZT";
		Intent intent = new Intent(EditCJXX.this, HomeFragmentActivity.class);
		intent.putExtra("type", "picture");
		intent.putExtra("From", "plant");
		startActivity(intent);
	}

	@Click
	void btn_upload()
	{
		pb_upload.setVisibility(View.VISIBLE);
		btn_upload.setVisibility(View.GONE);
		updateData();
	}

	@AfterViews
	void afterOncreate()
	{
		getDICS();
		if (type.equals("植物"))
		{
			ll_addplantpicture.setVisibility(View.VISIBLE);
			ll_addanimalpicture.setVisibility(View.GONE);
			ll_addwswpicture.setVisibility(View.GONE);
		} else if (type.equals("动物"))
		{
			ll_addanimalpicture.setVisibility(View.VISIBLE);
			ll_addplantpicture.setVisibility(View.GONE);
			ll_addwswpicture.setVisibility(View.GONE);
		} else if (type.equals("微生物"))
		{
			ll_addwswpicture.setVisibility(View.VISIBLE);
			ll_addanimalpicture.setVisibility(View.GONE);
			ll_addplantpicture.setVisibility(View.GONE);
		}
		showData();
		if (list_FJ_SCFJ_FROMSERVER != null)
		{
			showServerphotos();
		}

	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		bhq_XHSJCJ = getIntent().getParcelableExtra("bean");
		list_FJ_SCFJ_FROMSERVER = getIntent().getParcelableArrayListExtra("beanlist");
		dt_manager = AppContext.getUserInfo(this);
		appContext = (AppContext) getApplication();
		if (bhq_XHSJCJ.getZYDL().equals("01"))
		{
			type = "动物";
		} else if (bhq_XHSJCJ.getZYDL().equals("02"))
		{
			type = "植物";
		} else if (bhq_XHSJCJ.getZYDL().equals("03"))
		{
			type = "微生物";
		}
		ZYXLID = bhq_XHSJCJ.getZYXL();
		IntentFilter imageIntentFilter = new IntentFilter(MediaChooser.IMAGE_SELECTED_ACTION_FROM_MEDIA_CHOOSER);
		registerReceiver(imageBroadcastReceiver, imageIntentFilter);
	};

	BroadcastReceiver imageBroadcastReceiver = new BroadcastReceiver()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			List<String> list = intent.getStringArrayListExtra("list");
			// 植物
			if (witchphoto.equals("ZW_HD"))
			{
				addPicture("1", list, list_plant_flower, ll_flowerpicture);
			} else if (witchphoto.equals("ZW_GS"))
			{
				addPicture("2", list, list_plant_fruit, ll_fruitpicture);
			} else if (witchphoto.equals("ZW_ZT"))
			{
				addPicture("0", list, list_plant_whole, ll_wholepicture);
			} else if (witchphoto.equals("ZW_YZ"))
			{
				addPicture("3", list, list_plant_leaf, ll_Leafpicture);
			}
			// 动物
			if (witchphoto.equals("DW_ZT"))
			{
				addPicture("0", list, list_animal_whole, ll_animal_wholepicture);
			} else if (witchphoto.equals("DW_JY"))
			{
				addPicture("1", list, list_animal_jy, ll_animal_JYpicture);
			} else if (witchphoto.equals("DW_FB"))
			{
				addPicture("2", list, list_animal_fb, ll_animal_FBpicture);
			}
			// 微生物
			if (witchphoto.equals("WSW_ZT"))
			{
				addPicture("1", list, list_wsw_picture, ll_wsw_picture);
			}

		}
	};

	private void showData()
	{
		if (bhq_XHSJCJ.getBHJBMC().equals("null"))
		{
			tv_bhjb.setText("");
		} else
		{
			tv_bhjb.setText(bhq_XHSJCJ.getBHJBMC());
		}
		if (bhq_XHSJCJ.getBWDMC().equals("null"))
		{
			tv_bwd.setText("");
		} else
		{
			tv_bwd.setText(bhq_XHSJCJ.getBWDMC());
		}
		tv_type.setText(bhq_XHSJCJ.getZYDLMC() + "" + bhq_XHSJCJ.getZYXLMC());
		et_zm.setText(bhq_XHSJCJ.getZM());
		et_gang.setText(bhq_XHSJCJ.getGANG());
		et_mu.setText(bhq_XHSJCJ.getMU());
		et_ke.setText(bhq_XHSJCJ.getKE());

		et_tz.setText(bhq_XHSJCJ.getTZ());
		et_xx.setText(bhq_XHSJCJ.getXX());
		et_szhj.setText(bhq_XHSJCJ.getSZHJ());
		et_bwys.setText(bhq_XHSJCJ.getBWYS());
		et_bz.setText(bhq_XHSJCJ.getBDZYBZ());
	}

	private void showServerphotos()// 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
	{
		if (type.equals("动物"))
		{
			for (int i = 0; i < list_FJ_SCFJ_FROMSERVER.size(); i++)
			{
				if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("0"))// 整体
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_animal_whole, ll_animal_wholepicture);
				} else if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("1"))// 脚印
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_animal_jy, ll_animal_JYpicture);
				} else if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("2"))// 粪便
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_animal_fb, ll_animal_FBpicture);
				}
			}
		} else if (type.equals("植物"))
		{
			for (int i = 0; i < list_FJ_SCFJ_FROMSERVER.size(); i++)
			{
				if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("0"))// 整体
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_plant_whole, ll_wholepicture);
				} else if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("1"))// 花朵
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_plant_flower, ll_flowerpicture);
				} else if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("2"))// 果实
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_plant_fruit, ll_fruitpicture);
				} else if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("3"))// 叶子
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_plant_leaf, ll_Leafpicture);
				}
			}
		} else if (type.equals("微生物"))
		{
			for (int i = 0; i < list_FJ_SCFJ_FROMSERVER.size(); i++)
			{
				if (list_FJ_SCFJ_FROMSERVER.get(i).getFL().equals("0"))// 整体
				{
					addServerPicture(list_FJ_SCFJ_FROMSERVER.get(i), list_wsw_picture, ll_wsw_picture);
				}
			}
		}

	}

	private void addServerPicture(FJ_SCFJ fj_SCFJ, final List<FJ_SCFJ> list_pic, final LinearLayout ll_pic)
	{
		ImageView imageView = new ImageView(EditCJXX.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
		lp.setMargins(25, 4, 0, 4);
		imageView.setLayoutParams(lp);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		BitmapHelper.setImageView(EditCJXX.this, imageView, AppConfig.url + fj_SCFJ.getFJLJ());// ?

		fj_SCFJ.setISUPLOAD("1");// 必须设置
		ll_pic.addView(imageView);
		list_pic.add(fj_SCFJ);
		imageView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final int index_zp = ll_pic.indexOfChild(v);
				View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
				myDialog = new MyDialog(EditCJXX.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
				{
					@Override
					public void OnClick(View v)
					{
						switch (v.getId())
						{
						case R.id.btn_sure:
							Intent intent = new Intent(EditCJXX.this, ShowPhoto_.class);
							intent.putExtra("url", list_pic.get(index_zp).getFJLJ());
							startActivity(intent);
							break;
						case R.id.btn_cancle:
							deletePhotos(list_pic.get(index_zp).getFJID(), list_pic, ll_pic, index_zp);
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
					Toast.makeText(EditCJXX.this, "删除成功！", Toast.LENGTH_SHORT).show();
				} else
				{
					Toast.makeText(EditCJXX.this, "删除失败！", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(EditCJXX.this, "删除失败！", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void addPicture(String type, List<String> list, final List<FJ_SCFJ> list_pic, final LinearLayout ll_pic)
	{
		for (int i = 0; i < list.size(); i++)
		{
			String FJBDLJ = list.get(i);
			ImageView imageView = new ImageView(EditCJXX.this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180, LayoutParams.MATCH_PARENT, 0);
			lp.setMargins(25, 4, 0, 4);
			imageView.setLayoutParams(lp);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			BitmapHelper.setImageView(EditCJXX.this, imageView, FJBDLJ);
			imageView.setTag(FJBDLJ);

			FJ_SCFJ fj_SCFJ = new FJ_SCFJ();
			fj_SCFJ.setFJBDLJ(FJBDLJ);
			fj_SCFJ.setFL(type);
			fj_SCFJ.setISUPLOAD("0");

			list_pic.add(fj_SCFJ);
			// list_allpicture.add(fj_SCFJ);
			ll_pic.addView(imageView);

			imageView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					final int index_zp = ll_pic.indexOfChild(v);
					View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
					myDialog = new MyDialog(EditCJXX.this, R.style.MyDialog, dialog_layout, "图片", "查看该图片?", "查看", "删除", new CustomDialogListener()
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

	private void updateData()
	{
		if ((Bundle) tv_type.getTag() != null)
		{
			ZYXLID = ((Bundle) tv_type.getTag()).getString("FI");// 资源小类
		}
		if ((Bundle) tv_bhjb.getTag() != null)
		{
			BHJB = ((Bundle) tv_bhjb.getTag()).getString("FI");// 濒危级别
		}
		if ((Bundle) tv_bwd.getTag() != null)
		{
			BWD = ((Bundle) tv_bwd.getTag()).getString("FI");// 濒危度
		}

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("CJID", bhq_XHSJCJ.getCJID());
		hashMap.put("ZYXL", ZYXLID);
		hashMap.put("ZM", et_zm.getText().toString());
		hashMap.put("GANG", et_gang.getText().toString());
		hashMap.put("MU", et_mu.getText().toString());
		hashMap.put("KE", et_ke.getText().toString());
		hashMap.put("BHJB", BHJB);
		hashMap.put("BWD", BWD);
		hashMap.put("TZ", et_tz.getText().toString());
		hashMap.put("XX", et_xx.getText().toString());
		hashMap.put("SZHJ", et_szhj.getText().toString());
		hashMap.put("BWYS", et_bwys.getText().toString());
		hashMap.put("BDZYBZ", et_bz.getText().toString());
		hashMap.put("v_flag", "A");
		String params = HttpUrlConnect.setParams("APP.UpdateCJXX", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					list_allpicture.addAll(list_animal_whole);
					list_allpicture.addAll(list_animal_jy);
					list_allpicture.addAll(list_animal_fb);
					list_allpicture.addAll(list_plant_whole);
					list_allpicture.addAll(list_plant_flower);
					list_allpicture.addAll(list_plant_fruit);
					list_allpicture.addAll(list_plant_leaf);
					list_allpicture.addAll(list_wsw_picture);

					List<FJ_SCFJ> list_allpicture_notload = new ArrayList<FJ_SCFJ>();
					for (int i = 0; i < list_allpicture.size(); i++)
					{
						if (list_allpicture.get(i).getISUPLOAD().equals("0"))
						{
							list_allpicture_notload.add(list_allpicture.get(i));
						}
					}
					if (list_allpicture_notload.size() > 0)
					{
						latch = new CountDownLatch(list_allpicture_notload.size() + list_allpicture_notload.size());
						uploadFJ_SCFJ(list_allpicture_notload);
						uploadPhotos(list_allpicture_notload);
					} else
					{
						Toast.makeText(EditCJXX.this, "保存成功！", Toast.LENGTH_SHORT).show();
						finish();
					}

				} else
				{
					Toast.makeText(EditCJXX.this, "采集信息上传失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(EditCJXX.this, "采集信息上传失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void uploadFJ_SCFJ(List<FJ_SCFJ> list_allpicture)
	{
		int aa = list_allpicture.size();
		for (int i = 0; i < list_allpicture.size(); i++)
		{
			FJ_SCFJ fj_SCFJ = list_allpicture.get(i);
			String fjmc = fj_SCFJ.getFJBDLJ().substring(fj_SCFJ.getFJBDLJ().lastIndexOf("/") + 1, fj_SCFJ.getFJBDLJ().length());
			String fjlj = "upload/" + utils.getToday() + "/" + fjmc;
			String FJID = java.util.UUID.randomUUID().toString();
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("FJID", FJID);
			hashMap.put("GLID", bhq_XHSJCJ.getCJID());
			hashMap.put("GLBM", "Plant");
			hashMap.put("FJMC", fjmc);
			hashMap.put("FJLJ", fjlj);
			hashMap.put("SCR", "1");
			hashMap.put("SCSJ", utils.getTime());
			hashMap.put("SCRXM", dt_manager.getreal_name());
			hashMap.put("FJLX", "1");
			hashMap.put("SFSC", "0");
			hashMap.put("SCZT", "1");
			hashMap.put("Change", "0");
			hashMap.put("FL", fj_SCFJ.getFL());
			hashMap.put("v_flag", "A");
			String params = HttpUrlConnect.setParams("APP.InsertFJ_SCFJ", "0", hashMap);
			new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, HttpUrlConnect.getParas(params), new RequestCallBack<String>()
			{
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo)
				{
					String aaa = responseInfo.result;
					Result result = JSON.parseObject(responseInfo.result, Result.class);
					if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
					{
						showProgress();
					} else
					{
						Toast.makeText(EditCJXX.this, "附件数据上传失败", Toast.LENGTH_SHORT).show();
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
			// params.addQueryStringParameter("param", "plant/" +
			// utils.getToday());
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
					Toast.makeText(EditCJXX.this, "附件图片上传失败！" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
			Toast.makeText(EditCJXX.this, "上传成功！", Toast.LENGTH_SHORT).show();
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
					AppContext.makeToast(EditCJXX.this, "error_connectDataBase");
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				AppContext.makeToast(EditCJXX.this, "error_connectServer");
			}
		});
	}

}
