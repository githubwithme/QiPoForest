package com.bhq.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.bhq.R;
import com.bhq.app.AppContext;
import com.bhq.bean.RW_RW;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.SqliteDb;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 下午2:19:43
 * @description :主界面
 */
@EFragment
public class Offline_MainFragment extends Fragment
{
    Fragment mContent = new Fragment();
    @ViewById
    LinearLayout ll_survey;
    @ViewById
    LinearLayout ll_find;
    @ViewById
    LinearLayout ll_task;
    @ViewById
    LinearLayout ll_eventhappen;
    @ViewById
    ImageButton btn_account;

    @ViewById
    FrameLayout container_map;
    @ViewById
    View view_newmessage;

    @Click
    void btn_account()
    {
        Intent intent = new Intent();
        intent.setAction(AppContext.BROADCAST_OPENDL);
        getActivity().sendBroadcast(intent);
    }

    @Click
    void ll_survey()
    {
        Intent intent = new Intent(getActivity(), Offline_XBList_.class);
        getActivity().startActivity(intent);
    }

    @Click
    void ll_find()
    {
        Intent intent = new Intent(getActivity(), Offline_DailyProductActivity_.class);
        getActivity().startActivity(intent);
    }


    @Click
    void ll_task()
    {
        Intent intent = new Intent(getActivity(), Offline_Task_.class);
        getActivity().startActivity(intent);
    }

    @Click
    void ll_eventhappen()
    {
        Intent intent = new Intent(getActivity(), Offline_EventList_.class);
        getActivity().startActivity(intent);
    }

    @AfterViews
    void afterOncreate()
    {
        haveNewMessage();
        switchContent(mContent, new Offline_PatrolControlFragment_());
    }

    @Override
    public void onResume()
    {
        super.onResume();
        haveNewMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.offline_fragment_main, container, false);
        return rootView;
    }

    public void switchContent(Fragment from, Fragment to)
    {
        if (mContent != to)
        {
            mContent = to;
            FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
            if (!to.isAdded())
            { // 先判断是否被add过
                transaction.hide(from).add(R.id.container_map, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else
            {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    public void haveNewMessage()
    {
        int size = 0;
        dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(getActivity(), dt_manager_offline.class);
        List<RW_RW> list_rw = SqliteDb.getRenWuByRYID(getActivity(), dt_manager_offline.getid());
        for (int i = 0; i < list_rw.size(); i++)
        {
            if (list_rw.get(i).getIsRead() == null || list_rw.get(i).getIsRead().equals("0"))
            {
                size = size + 1;
            }
        }
        if (size > 0)
        {
            view_newmessage.setVisibility(View.VISIBLE);
        } else
        {
            view_newmessage.setVisibility(View.GONE);
        }
    }
}
