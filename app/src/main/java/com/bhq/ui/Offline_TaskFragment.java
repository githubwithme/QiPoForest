package com.bhq.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bhq.R;
import com.bhq.bean.RW_RW;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.SqliteDb;
import com.swipelistview.Offline_SwipeListViewImpl_RW;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-23 下午3:37:11
 * @description :
 */
@EFragment
public class Offline_TaskFragment extends Fragment
{
	@ViewById
	com.swipelistview.ExpandAniLinearLayout swipe_list_ani;
	@ViewById
	ListView swipe_list;
	@ViewById
	TextView tv_tip;

	@AfterViews
	void afterOncreate()
	{
		getTask();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.taskfragment, container, false);
		return rootView;
	}

	private void getTask()
	{
		dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(getActivity(), dt_manager_offline.class);
		List<RW_RW> list_rw = SqliteDb.getRenWuByRYID(getActivity(), dt_manager_offline.getid());
		if (list_rw == null)
		{
			list_rw = new ArrayList<RW_RW>();
		}

		if (list_rw.size() != 0)
		{
			Offline_SwipeListViewImpl_RW offline_SwipeListViewImpl_RW = new Offline_SwipeListViewImpl_RW();
			offline_SwipeListViewImpl_RW.setMyadapter("CYR", getActivity(), swipe_list_ani, list_rw, swipe_list);
		} else
		{
			tv_tip.setVisibility(View.VISIBLE);
		}
	}

}
