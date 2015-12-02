package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.common.BitmapHelper;
import com.bhq.common.SqliteDb;
import com.bhq.widget.MyDialog;
import com.bhq.widget.MyDialog.CustomDialogListener;

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
 * @createTime：2015-8-14 下午6:33:24
 * @description :展示动物类
 */
@SuppressLint("NewApi")
@EActivity(R.layout.showeventcontent)
public class Offline_ShowEventContent extends Activity
{
	MyDialog myDialog;
	List<FJ_SCFJ> list_FJ_SCFJ_video = null;
	List<FJ_SCFJ> list_FJ_SCFJ_image = null;
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
		showDialog();
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
		Intent intent = new Intent(Offline_ShowEventContent.this, Offline_EditEvent_.class);
		intent.putExtra("bean", bhq_XHSJ);
		intent.putParcelableArrayListExtra("beanlist", (ArrayList<? extends Parcelable>) list_FJ_SCFJ_image);
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
			tv_cjsj.setText(bhq_XHSJ.getSBSJ());
			tv_xgr.setText(bhq_XHSJ.getSBRXM());
			tv_xgsj.setText(bhq_XHSJ.getSBSJ());
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
		list_FJ_SCFJ_image = SqliteDb.getFJ_SCFJList(Offline_ShowEventContent.this, FJ_SCFJ.class, glid, "1");
		if (list_FJ_SCFJ_image == null)
		{
			list_FJ_SCFJ_image = new ArrayList<FJ_SCFJ>();
		}
		Offline_PictureScrollFragment offline_PictureScrollFragment = new Offline_PictureScrollFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("imgurl", (ArrayList<? extends Parcelable>) list_FJ_SCFJ_image);
		offline_PictureScrollFragment.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.img_container, offline_PictureScrollFragment).commit();

	}

	private void getVideo(String glid)
	{
		list_FJ_SCFJ_video = SqliteDb.getFJ_SCFJList(Offline_ShowEventContent.this, FJ_SCFJ.class, glid, "2");
		if (list_FJ_SCFJ_video == null)
		{
			list_FJ_SCFJ_video = new ArrayList<FJ_SCFJ>();
		}
		for (int i = 0; i < list_FJ_SCFJ_video.size(); i++)
		{
			ImageView imageView = new ImageView(Offline_ShowEventContent.this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, LayoutParams.MATCH_PARENT, 0); // ,
																												// 1是可选写的
			lp.setMargins(25, 4, 0, 4);
			imageView.setLayoutParams(lp);// 显示图片的大小
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setImageBitmap(BitmapHelper.getVideoThumbnail(list_FJ_SCFJ_video.get(i).getFJBDLJ(), 120, 120, MediaStore.Images.Thumbnails.MICRO_KIND));
			ll_video.addView(imageView);
			videoOnClick(imageView, list_FJ_SCFJ_video.get(i).getFJBDLJ());
		}
	}

	private void videoOnClick(View videoView, final String target)
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

	private void deleteCJXX()
	{
		bhq_XHSJ.setIsUpload("0");
		bhq_XHSJ.setSFSC("1");
		boolean issuccess = SqliteDb.deleteCJXX(Offline_ShowEventContent.this, bhq_XHSJ);
		if (issuccess)
		{
			Toast.makeText(Offline_ShowEventContent.this, "删除成功！", Toast.LENGTH_SHORT).show();
			finish();
		} else
		{
			Toast.makeText(Offline_ShowEventContent.this, "删除失败！", Toast.LENGTH_SHORT).show();
		}

	}

	public void showDialog()
	{
		View dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.customdialog_callback, null);
		myDialog = new MyDialog(Offline_ShowEventContent.this, R.style.MyDialog, dialog_layout, "删除事件", "确定删除该事件?", "确定", "取消", new CustomDialogListener()
		{
			@Override
			public void OnClick(View v)
			{
				switch (v.getId())
				{
				case R.id.btn_sure:
					deleteCJXX();
					break;
				case R.id.btn_cancle:
					myDialog.dismiss();
					break;
				}
			}
		});
		myDialog.show();
	}
}
