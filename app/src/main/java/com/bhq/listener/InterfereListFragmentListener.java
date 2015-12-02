package com.bhq.listener;

import android.os.Handler;
import android.os.Message;

import com.bhq.widget.PullToRefreshLayout;
import com.bhq.widget.PullToRefreshLayout.OnRefreshListener;

public class InterfereListFragmentListener implements OnRefreshListener
{

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// Toast.makeText(PullableGridViewActivity.this, " Click on " +
		// parent.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 1000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 1000);
	}

}
