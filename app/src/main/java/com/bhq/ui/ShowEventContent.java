package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.common.BitmapHelper;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.utils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:33:24
 * @description :展示动物类
 */
@SuppressLint("NewApi")
@EActivity(R.layout.showeventcontent)
public class ShowEventContent extends Activity
{
	AlertDialog dialog;
	List<FJ_SCFJ> list_FJ_SCFJ = null;
	LinearLayout ll_video;
	Fragment mContent_container = new Fragment();
	Fragment mContent_container_more = new Fragment();
	BHQ_XHSJ bhq_XHSJ;
	TextView tv_description;
	OtherFragment otherFragment;
	FoundationFragment foundationFragment;
	VideoFragment videoFragment;
	@ViewById
	Button btn_edit;
	@ViewById
	Button btn_foundation;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	Button btn_other;
	@ViewById
	Button btn_video;
	@ViewById
	TextView tv_number;
	@ViewById
	TextView tv_name;
	@ViewById
	FrameLayout fl_contain;
	@ViewById
	FrameLayout img_container;
	@ViewById
	FrameLayout contain_more;
	List<String> imgUrl = new ArrayList<String>();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		bhq_XHSJ = (BHQ_XHSJ) getIntent().getParcelableExtra("bean");
		otherFragment = new OtherFragment();
		foundationFragment = new FoundationFragment();
		videoFragment = new VideoFragment();
	}

	@Click
	void tv_delete()
	{
		showDialog("删除信息", "删除信息？", "确定", "取消");
	}

	@AfterViews
	void afterOncreate()
	{
		btn_video.setSelected(true);
		btn_foundation.setSelected(true);
		btn_foundation.setTextColor(getResources().getColor(R.color.black));
		switchcontainer(R.id.fl_contain, mContent_container, foundationFragment);
		switchcontainermore(R.id.contain_more, mContent_container_more, videoFragment);

		show(bhq_XHSJ);
	}

	@Click
	void btn_edit()
	{
		Intent intent = new Intent(ShowEventContent.this, EditEvent_.class);
		intent.putExtra("bean", bhq_XHSJ);
		intent.putParcelableArrayListExtra("beanlist", (ArrayList<? extends Parcelable>) list_FJ_SCFJ);
		startActivity(intent);
		finish();
	}

	@Click
	void imgbtn_back()
	{
		finish();
	}

	@Click
	void btn_foundation()
	{
		switchcontainer(R.id.fl_contain, mContent_container, foundationFragment);
		btn_other.setSelected(false);
		btn_foundation.setSelected(true);
		btn_foundation.setTextColor(getResources().getColor(R.color.black));
		btn_other.setTextColor(getResources().getColor(R.color.black));
	}

	private void show(BHQ_XHSJ bhq_XHSJ)
	{
		if (bhq_XHSJ.getLCZT().equals("3"))// 所处流程(0为新增,1为巡护人员上报，2为保护站处理，3为处理完成)
		{
			tv_number.setText("已处理");
		}

		tv_name.setText(bhq_XHSJ.getSJMS());
		setImage(bhq_XHSJ.getSJID());
	}

	@Click
	void btn_video()
	{
		switchcontainermore(R.id.contain_more, mContent_container_more, videoFragment);
		btn_video.setSelected(true);
		// btn_foundation.setTextColor(getResources().getColor(R.color.black));
		// btn_other.setTextColor(getResources().getColor(R.color.black));
	}

	@Click
	void btn_other()
	{
		switchcontainer(R.id.fl_contain, mContent_container, otherFragment);
		btn_foundation.setSelected(false);
		btn_other.setSelected(true);
		btn_other.setTextColor(getResources().getColor(R.color.black));
		btn_foundation.setTextColor(getResources().getColor(R.color.black));
	}

	class FoundationFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.foundationfragment_event, container, false);
			tv_description = (TextView) rootView.findViewById(R.id.tv_description);
			tv_description.setText(bhq_XHSJ.getSJMS());
			return rootView;
		}

	}

	class OtherFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.otherinfomationfragment_event, container, false);
			TextView tv_cjr = (TextView) rootView.findViewById(R.id.tv_cjr);
			TextView tv_cjsj = (TextView) rootView.findViewById(R.id.tv_cjsj);
			TextView tv_xgr = (TextView) rootView.findViewById(R.id.tv_xgr);
			TextView tv_xgsj = (TextView) rootView.findViewById(R.id.tv_xgsj);
			tv_cjr.setText(bhq_XHSJ.getSBRXM());
			tv_cjsj.setText(utils.DateString2Date(bhq_XHSJ.getSBSJ()));
			tv_xgr.setText(bhq_XHSJ.getSBRXM());
			tv_xgsj.setText(utils.DateString2Date(bhq_XHSJ.getSBSJ()));
			return rootView;
		}
	}

	class VideoFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.videofragment, container, false);
			ll_video = (LinearLayout) rootView.findViewById(R.id.ll_video);
			getVideo(bhq_XHSJ.getSJID());
			return rootView;
		}
	}

	public void switchcontainer(int resource, Fragment from, Fragment to)
	{
		if (mContent_container != to)
		{
			mContent_container = to;
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			if (!to.isAdded())
			{ // 先判断是否被add过
				transaction.hide(from).add(resource, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else
			{
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

	public void switchcontainermore(int resource, Fragment from, Fragment to)
	{
		if (mContent_container_more != to)
		{
			mContent_container_more = to;
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			if (!to.isAdded())
			{ // 先判断是否被add过
				transaction.hide(from).add(resource, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else
			{
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}

	private void setImage(String glid)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("GLID", glid);
		hashMap.put("FJLX", "1");
		String params = ConnectionHelper.setParams("APP.SelectFJ_SCFJ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{

				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					list_FJ_SCFJ = JSON.parseArray(ResultDeal.getAllRow(result), FJ_SCFJ.class);
					if (list_FJ_SCFJ == null)
					{
						list_FJ_SCFJ = new ArrayList<FJ_SCFJ>();
					}
					// for (int i = 0; i < list_FJ_SCFJ.size(); i++)
					// {
					// imgUrl.add(list_FJ_SCFJ.get(i).getFJLJ());
					// }
				} else
				{
					AppContext.makeToast(ShowEventContent.this, "error_connectDataBase");
					return;
				}
				PictureScrollFragment pictureScrollFragment = new PictureScrollFragment();
				Bundle bundle = new Bundle();
				bundle.putParcelableArrayList("imgurl", (ArrayList<? extends Parcelable>) list_FJ_SCFJ);
				pictureScrollFragment.setArguments(bundle);
				getFragmentManager().beginTransaction().replace(R.id.img_container, pictureScrollFragment).commit();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ShowEventContent.this, "获取失败", Toast.LENGTH_SHORT).show();
			}
		});

	}

	// private void getVideo()
	// {
	//
	// for (int i = 0; i < list_SJ_SBXXFJ_video.size(); i++)
	// {
	// ImageView imageView = new ImageView(ShowAnimal.this);
	// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(180,
	// LayoutParams.MATCH_PARENT, 0); // ,
	// lp.setMargins(25, 4, 0, 4);
	// imageView.setLayoutParams(lp);// 显示图片的大小
	// imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	// imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(list_SJ_SBXXFJ_video.get(i).getFJBDLJ(),
	// 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));
	// // videoOnClick(imageView, list_SJ_SBXXFJ_video.get(i).getFJBDLJ(),
	// // list_SJ_SBXXFJ_video.get(i).getFJID());
	//
	// ll_video.addView(imageView);
	// }
	// }

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
				String a = responseInfo.result;
				List<FJ_SCFJ> list_fj = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					list_fj = JSON.parseArray(ResultDeal.getAllRow(result), FJ_SCFJ.class);
					if (list_fj == null)
					{
						list_fj = new ArrayList<FJ_SCFJ>();
					}
				} else
				{
					AppContext.makeToast(ShowEventContent.this, "error_connectDataBase");
					return;
				}
				for (int i = 0; i < list_fj.size(); i++)
				{
					ProgressBar progressBar = new ProgressBar(ShowEventContent.this, null, android.R.attr.progressBarStyleHorizontal);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, LayoutParams.MATCH_PARENT, 0);
					lp.setMargins(25, 4, 0, 4);
					progressBar.setLayoutParams(lp);
					ll_video.addView(progressBar);
					downloadVideo(list_fj.get(i).getFJID(), AppConfig.url + list_fj.get(i).getFJLJ(), AppConfig.DOWNLOADPATH_VIDEO + list_fj.get(i).getFJMC(), progressBar);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ShowEventContent.this, "获取失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void downloadVideo(final String ID, String path, final String target, final ProgressBar progressBar)
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
					// Toast.makeText(ShowAnimal.this, "文件已存在!",
					// Toast.LENGTH_SHORT).show();
					ll_video.removeView(progressBar);
					ImageView imageView = new ImageView(ShowEventContent.this);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, LayoutParams.MATCH_PARENT, 0); // ,
																														// 1是可选写的
					lp.setMargins(25, 4, 0, 4);
					imageView.setLayoutParams(lp);// 显示图片的大小
					imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(target, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));
					ll_video.addView(imageView);
					videoOnClick(imageView, target, ID);
				} else
				{
					Toast.makeText(ShowEventContent.this, "下载失败！找不到文件!", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo)
			{
				// progressBar.setVisibility(View.INVISIBLE);
				ll_video.removeView(progressBar);
				Toast.makeText(ShowEventContent.this, "下载成功！", Toast.LENGTH_SHORT).show();
				ImageView imageView = new ImageView(ShowEventContent.this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, LayoutParams.MATCH_PARENT, 0); // ,
																													// 1是可选写的
				lp.setMargins(25, 4, 0, 4);
				imageView.setLayoutParams(lp);// 显示图片的大小
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(target, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));
				ll_video.addView(imageView);
				videoOnClick(imageView, target, ID);
			}
		});

	}

	private void videoOnClick(View videoView, final String target, final String ID)
	{
		videoView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				File file = new File(target);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file), "video/*");
				startActivity(intent);
			}
		});
	}

	private void deleteCJXX(String CJID)
	{
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("SJID", CJID);
		hashMap.put("v_flag", "A");
		String params = ConnectionHelper.setParams("APP.DeleteSJ", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String a = responseInfo.result;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
				{
					Toast.makeText(ShowEventContent.this, "删除成功", Toast.LENGTH_SHORT).show();
					finish();
				} else
				{
					Toast.makeText(ShowEventContent.this, "删除失败", Toast.LENGTH_SHORT).show();
					return;
				}

			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ShowEventContent.this, "删除失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void showDialog(String title, String message, String str_Positive, String str_Negative)
	{
		AlertDialog.Builder builder = new Builder(ShowEventContent.this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(str_Positive, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				deleteCJXX(bhq_XHSJ.getSJID());
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(str_Negative, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}
}
