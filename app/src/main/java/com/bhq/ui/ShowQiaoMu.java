package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.QiaoMuTree;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午6:33:24
 * @description :展示动物类
 */
@SuppressLint("ValidFragment")
@EActivity(R.layout.showqiaomu)
public class ShowQiaoMu extends Activity
{
	LinearLayout ll_video;
	Fragment mContent_container = new Fragment();
	Fragment mContent_container_more = new Fragment();
	QiaoMuTree qiaoMuTree;
	TextView tv_description;
	TextView tv_bz;
	OtherFragment otherFragment;
	FoundationFragment foundationFragment;
	@ViewById
	Button btn_foundation;
	@ViewById
	ImageButton imgbtn_back;
	@ViewById
	Button btn_other;
	@ViewById
	TextView tv_number;
	@ViewById
	TextView tv_name;
	@ViewById
	FrameLayout fl_contain;
	@ViewById
	FrameLayout img_container;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		qiaoMuTree = (QiaoMuTree) getIntent().getSerializableExtra("qiaoMuTree");
		otherFragment = new OtherFragment();
		foundationFragment = new FoundationFragment();
	}

	@AfterViews
	void afterOncreate()
	{
		btn_foundation.setSelected(true);
		btn_foundation.setTextColor(getResources().getColor(R.color.black));
		switchcontainer(R.id.fl_contain, mContent_container, foundationFragment);
		show(qiaoMuTree);
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

	private void show(QiaoMuTree qiaoMuTree)
	{
		tv_number.setText(qiaoMuTree.getZZH());
		tv_name.setText(qiaoMuTree.getZM());
		String result = utils.parseJsonFile(this, "qmtreefj.json");
		List<FJ_SCFJ> list_FJ_SCFJ = null;
		list_FJ_SCFJ = JSON.parseArray(result, FJ_SCFJ.class);
		List<String> imgurl = new ArrayList<String>();
		for (int i = 0; i < list_FJ_SCFJ.size(); i++)
		{
			if (qiaoMuTree.getQMID().equals(list_FJ_SCFJ.get(i).getGLID()))
			{
				imgurl.add(list_FJ_SCFJ.get(i).getFJLJ());
			}
		}
		PictureScrollFragment pictureScrollFragment = new PictureScrollFragment();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("imgurl", (ArrayList<String>) imgurl);
		pictureScrollFragment.setArguments(bundle);
//		getSupportFragmentManager().beginTransaction().replace(R.id.img_container, pictureScrollFragment).commit();
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
			View rootView = inflater.inflate(R.layout.foundationfragment_qmtree, container, false);
			tv_description = (TextView) rootView.findViewById(R.id.tv_description);
			tv_bz = (TextView) rootView.findViewById(R.id.tv_bz);
			tv_description.setText(qiaoMuTree.getSZZK());
			tv_bz.setText("暂无备注");
			return rootView;
		}

	}

	class OtherFragment extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.otherinfomationfragment_yangzhu, container, false);
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
					AppContext.makeToast(ShowQiaoMu.this, "error_connectDataBase");
					return;
				}
				PictureScrollFragment pictureScrollFragment = new PictureScrollFragment();
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("imgurl", (ArrayList<String>) imgurl);
				pictureScrollFragment.setArguments(bundle);
//				ShowQiaoMu.this.getFragmentManager().beginTransaction().replace(R.id.img_container, pictureScrollFragment).commit();
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ShowQiaoMu.this, "获取失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
