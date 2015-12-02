package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.Pest;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.common.BitmapHelper;
import com.bhq.common.ConnectionHelper;
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
@EActivity(R.layout.showpest)
public class ShowPest extends Activity
{
	LinearLayout ll_video;
	Fragment mContent_container = new Fragment();
	Fragment mContent_container_more = new Fragment();
	Pest pest;
	TextView tv_description;
	TextView tv_bz;
	OtherFragment otherFragment;
	FoundationFragment foundationFragment;
	RecordFragment recordFragment;
	VideoFragment videoFragment;
	@ViewById
	Button btn_foundation;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	Button btn_other;
	@ViewById
	Button btn_video;
	@ViewById
	Button btn_record;
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

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		pest = (Pest) getIntent().getSerializableExtra("pest");
		otherFragment = new OtherFragment();
		foundationFragment = new FoundationFragment();
		recordFragment = new RecordFragment();
		videoFragment = new VideoFragment();
	}

	@AfterViews
	void afterOncreate()
	{
		btn_video.setSelected(true);
		btn_foundation.setSelected(true);
		btn_foundation.setTextColor(getResources().getColor(R.color.black));
		switchcontainer(R.id.fl_contain, mContent_container, foundationFragment);
		switchcontainermore(R.id.contain_more, mContent_container_more, videoFragment);

		show(pest);
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

	private void show(Pest pest)
	{
		tv_number.setText(pest.getNumber());
		tv_name.setText(pest.getName());
		// BitmapHelper.setImageViewBackground(this, animal.getDwid(), img);
		setImage(pest.getPestid());
	}

	@Click
	void btn_video()
	{
		switchcontainermore(R.id.contain_more, mContent_container_more, videoFragment);
		btn_record.setSelected(false);
		btn_video.setSelected(true);
		// btn_foundation.setTextColor(getResources().getColor(R.color.black));
		// btn_other.setTextColor(getResources().getColor(R.color.black));
	}

	@Click
	void btn_record()
	{
		switchcontainermore(R.id.contain_more, mContent_container_more, foundationFragment);
		btn_video.setSelected(false);
		btn_record.setSelected(true);
		// btn_foundation.setTextColor(getResources().getColor(R.color.white));
		// btn_other.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
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
			View rootView = inflater.inflate(R.layout.foundationfragment_animal, container, false);
			tv_description = (TextView) rootView.findViewById(R.id.tv_description);
			tv_bz = (TextView) rootView.findViewById(R.id.tv_bz);
			tv_description.setText(pest.getDescription());
			tv_bz.setText(pest.getDescription());
			return rootView;
		}

	}

	class OtherFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.otherinfomationfragment_animal, container, false);
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
			getVideo(pest.getPestid());
			return rootView;
		}
	}

	class RecordFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.recordfragment, container, false);
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
				List<FJ_SCFJ> list_fj = null;
				List<String> imgurl = new ArrayList<String>();
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// 连接数据库成功
				{
					list_fj = JSON.parseArray(ResultDeal.getAllRow(result), FJ_SCFJ.class);
					if (list_fj == null)
					{
						list_fj = new ArrayList<FJ_SCFJ>();
					}
					for (int i = 0; i < list_fj.size(); i++)
					{
						imgurl.add(list_fj.get(i).getFJLJ());
					}
				} else
				{
					AppContext.makeToast(ShowPest.this, "error_connectDataBase");
					return;
				}
				PictureScrollFragment pictureScrollFragment = new PictureScrollFragment();
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("imgurl", (ArrayList<String>) imgurl);
				pictureScrollFragment.setArguments(bundle);
				// ShowPest.this.getFragmentManager().beginTransaction().replace(R.id.img_container,
				// pictureScrollFragment).commit();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ShowPest.this, "获取失败", Toast.LENGTH_SHORT).show();
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
					AppContext.makeToast(ShowPest.this, "error_connectDataBase");
					return;
				}
				for (int i = 0; i < list_fj.size(); i++)
				{
					ProgressBar progressBar = new ProgressBar(ShowPest.this, null, android.R.attr.progressBarStyleHorizontal);
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
				Toast.makeText(ShowPest.this, "获取失败", Toast.LENGTH_SHORT).show();
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
					ImageView imageView = new ImageView(ShowPest.this);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, LayoutParams.MATCH_PARENT, 0); // ,
																														// 1是可选写的
					lp.setMargins(25, 4, 0, 4);
					imageView.setLayoutParams(lp);// 显示图片的大小
					imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(target, 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));
					ll_video.addView(imageView);
					videoOnClick(imageView, target, ID);
				} else
				{
					Toast.makeText(ShowPest.this, "下载失败！找不到文件!", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onSuccess(ResponseInfo<File> responseInfo)
			{
				// progressBar.setVisibility(View.INVISIBLE);
				ll_video.removeView(progressBar);
				Toast.makeText(ShowPest.this, "下载成功！", Toast.LENGTH_SHORT).show();
				ImageView imageView = new ImageView(ShowPest.this);
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
}
