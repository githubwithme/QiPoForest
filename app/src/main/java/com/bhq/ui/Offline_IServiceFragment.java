package com.bhq.ui;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.app.AppManager;
import com.bhq.bean.Apk;
import com.bhq.bean.BHQ_XHQK;
import com.bhq.bean.BHQ_XHQK_GJ;
import com.bhq.bean.BHQ_XHQK_ZTCZ;
import com.bhq.bean.BHQ_XHSJ;
import com.bhq.bean.BHQ_XHSJCJ;
import com.bhq.bean.FJ_SCFJ;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.BitmapHelper;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.bhq.net.HttpUrlConnect;
import com.bhq.widget.CustomDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.entity.FileUploadEntity;
import com.service.UpdateApk;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author :hc-setImageViewBackground
 * @version :1.0
 * @createTime：2015-8-31 上午9:59:21
 * @description :意见反馈、技术支持、帮助、关于、
 */
@EFragment
public class Offline_IServiceFragment extends Fragment
{
    boolean isfail = false;
    CountDownLatch latch;
    int ThreadCount = 0;
    List<dt_manager_offline> list_dt_manager_offline = new ArrayList<dt_manager_offline>();
    List<BHQ_XHQK_ZTCZ> list_BHQ_XHQK_ZTCZ = new ArrayList<BHQ_XHQK_ZTCZ>();
    List<BHQ_XHSJCJ> list_BHQ_XHSJCJ = new ArrayList<BHQ_XHSJCJ>();
    List<BHQ_XHSJ> list_BHQ_XHSJ = new ArrayList<BHQ_XHSJ>();
    List<BHQ_XHQK> list_BHQ_XHQK = new ArrayList<BHQ_XHQK>();
    List<BHQ_XHQK_GJ> list_BHQ_XHQK_GJ = new ArrayList<BHQ_XHQK_GJ>();
    List<FJ_SCFJ> list_FJ_SCFJ = new ArrayList<FJ_SCFJ>();
    TextView tv_tips;
    Button btn_syn;
    Button btn_sure;
    Button btn_cancle;
    LinearLayout ll_syn;
    LinearLayout ll_sure;
    ProgressBar pb;
    CustomDialog customDialog;
    @ViewById
    TextView tv_changepwd;
    @ViewById
    TextView tv_renewversion;
    @ViewById
    TextView tv_name;
    @ViewById
    TextView tv_phone;
    @ViewById
    ImageView circle_img;
    @ViewById
    FrameLayout fl_new;

    @Click
    void rl_gx()
    {
        Intent intent = new Intent(getActivity(), UpdateApk.class);
        intent.setAction(UpdateApk.ACTION_NOTIFICATION_CONTROL);
        getActivity().startService(intent);

    }

    @Click
    void rl_yj()
    {
        Intent intent = new Intent(getActivity(), YiJianFanKui_.class);
        getActivity().startActivity(intent);
    }

    @Click
    void rl_exist()
    {
        CleanLoginInfo();
        AppManager.getAppManager().AppExit(getActivity());
    }

    @Click
    void rl_changeppdw()
    {
        Intent intent = new Intent(getActivity(), Offline_ChangePwd_.class);
        startActivity(intent);
    }

    @Click
    void rl_tb()
    {
        showSynchronousDialog();
    }

    @Click
    void rl_bz()
    {
        Intent intent = new Intent(getActivity(), Helper_.class);
        getActivity().startActivity(intent);
    }

    @Click
    void rl_gy()
    {
        Intent intent = new Intent(getActivity(), GuanYu_.class);
        getActivity().startActivity(intent);
    }

    // @Click
    // void tv_renewversion()
    // {
    // // if (utils.isServiceRunning(getActivity(), "com.service.UpdateApk"))
    // // {
    // // Toast.makeText(getActivity(), "正在更新，请勿重复点击!",
    // // Toast.LENGTH_SHORT).show();
    // // } else
    // // {
    // Intent intent = new Intent(getActivity(), UpdateApk.class);
    // intent.setAction(UpdateApk.ACTION_NOTIFICATION_CONTROL);
    // getActivity().startService(intent);
    // // }
    //
    // }


    @AfterViews
    void afterOncreate()
    {
        getListData();
        dt_manager_offline dt_manager_offline = (dt_manager_offline) SqliteDb.getCurrentUser(getActivity(), dt_manager_offline.class);
        tv_name.setText(dt_manager_offline.getreal_name());
        tv_phone.setText(dt_manager_offline.getPhone());
        BitmapHelper.loadImage(getActivity(), circle_img, dt_manager_offline.getBDLJ());
//        BitmapHelper.setImageViewBackground(getActivity(), circle_img, "http://img.popoho.com/UploadPic/2010-12/201012297441441.png");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.iservicefragment, container, false);
        // IntentFilter intentFilter = new
        // IntentFilter(AppContext.Progress_SynchronousData);
        // getActivity().registerReceiver(Progress_SynchronousData,
        // intentFilter);
        return rootView;

    }

    // BroadcastReceiver Progress_SynchronousData = new BroadcastReceiver()//
    // 植物（0为整体照，1为花照，2为果照，3为叶照）；动物（0为整体照，1为脚印照，2为粪便照）
    // {
    // @Override
    // public void onReceive(Context context, Intent intent)
    // {
    // int progress = intent.getIntExtra("progress", 0);
    // int status = intent.getIntExtra("status", -1);
    // pb.setProgress(progress);
    // if (status == 0)
    // {
    // tv_tips.setText("数据同步失败!");
    // pb.setVisibility(View.GONE);
    // } else if (status == 1)
    // {
    // tv_tips.setText("数据同步成功!");
    // pb.setVisibility(View.GONE);
    // }
    //
    // }
    // };

    private void getListData()
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String params = ConnectionHelper.setParams("APP.getReNewInfo", "0", hashMap);
        new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
        {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo)
            {
                List<Apk> listNewData = null;
                Result result = JSON.parseObject(responseInfo.result, Result.class);
                if (result.getResultCode() == 200)// 连接数据库成功
                {
                    listNewData = JSON.parseArray(ResultDeal.getAllRow(result), Apk.class);
                    if (listNewData == null)
                    {
                        listNewData = new ArrayList<Apk>();
                    } else
                    {
                        Apk apk = listNewData.get(0);
                        PackageInfo packageInfo = null;
                        try
                        {
                            packageInfo = getActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                        } catch (NameNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                        String localVersion = packageInfo.versionName;
                        if (localVersion.equals(apk.getVersion()))
                        {
                        } else
                        {
                            fl_new.setVisibility(View.VISIBLE);
                        }
                    }
                } else
                {
                    AppContext.makeToast(getActivity(), "error_connectDataBase");
                    return;
                }

            }

            @Override
            public void onFailure(HttpException error, String msg)
            {
                AppContext.makeToast(getActivity(), "error_connectServer");
            }
        });
    }

    private void showSynchronousDialog()
    {
        View dialog_layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.synchronous_dialog, null);
        tv_tips = (TextView) dialog_layout.findViewById(R.id.tv_tips);
        btn_syn = (Button) dialog_layout.findViewById(R.id.btn_syn);
        btn_sure = (Button) dialog_layout.findViewById(R.id.btn_sure);
        btn_cancle = (Button) dialog_layout.findViewById(R.id.btn_cancle);
        ll_syn = (LinearLayout) dialog_layout.findViewById(R.id.ll_syn);
        ll_sure = (LinearLayout) dialog_layout.findViewById(R.id.ll_sure);
        pb = (ProgressBar) dialog_layout.findViewById(R.id.pb);
        btn_sure.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                customDialog.dismiss();
            }
        });
        btn_syn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                ll_sure.setVisibility(View.VISIBLE);
                ll_syn.setVisibility(View.GONE);
                if (utils.isConnect(getActivity()))
                {
                    ThreadCount = getUploadCount();
                    if (ThreadCount > 0)
                    {
                        tv_tips.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                        uploadData(ThreadCount);
                    } else
                    {
                        tv_tips.setText("无数据需要同步！");
                    }
                }

            }
        });
        btn_cancle.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                customDialog.dismiss();
            }
        });
        customDialog = new CustomDialog(getActivity(), R.style.MyDialog, dialog_layout);
        customDialog.show();
    }

    private int getUploadCount()
    {
        list_dt_manager_offline = SqliteDb.getNotUploadData(getActivity(), dt_manager_offline.class);
        list_BHQ_XHQK_ZTCZ = SqliteDb.getNotUploadData(getActivity(), BHQ_XHQK_ZTCZ.class);
        list_BHQ_XHSJCJ = SqliteDb.getNotUploadData(getActivity(), BHQ_XHSJCJ.class);
        list_BHQ_XHSJ = SqliteDb.getNotUploadData(getActivity(), BHQ_XHSJ.class);
        list_BHQ_XHQK = SqliteDb.getNotUploadData(getActivity(), BHQ_XHQK.class);
        list_BHQ_XHQK_GJ = SqliteDb.getNotUploadData(getActivity(), BHQ_XHQK_GJ.class);
        list_FJ_SCFJ = SqliteDb.getNotUploadData(getActivity(), FJ_SCFJ.class);
        int count = list_dt_manager_offline.size() + list_BHQ_XHQK_ZTCZ.size() + list_BHQ_XHSJCJ.size() + list_BHQ_XHSJ.size() + list_BHQ_XHQK_GJ.size() + list_BHQ_XHQK.size() + list_FJ_SCFJ.size() * 2;
        return count;
    }

    private void uploadData(int count)
    {
        latch = new CountDownLatch(count);
        uploadUser();
        uploadBHQ_XHQK_ZTCZ();
        uploadXXCJ();
        uploadSJ();
        uploadFJ();
        uploadGJ();
        uploadXHQK();
        uploadMedia(list_FJ_SCFJ);
    }

    private void uploadUser()
    {
        for (int i = 0; i < list_dt_manager_offline.size(); i++)
        {
            final dt_manager_offline dt_manager_offline = list_dt_manager_offline.get(i);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("id", dt_manager_offline.getid());
            hashMap.put("password", dt_manager_offline.getpassword());
            hashMap.put("v_flag", "A");
            String params = HttpUrlConnect.setParams("APP.updatepassword", "0", hashMap);
            new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    Result result = JSON.parseObject(responseInfo.result, Result.class);
                    if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                    {
                        dt_manager_offline.setIsUpload("1");
                        SqliteDb.save(getActivity(), dt_manager_offline);
                        showProgress();
                    } else
                    {
                        isfail = true;
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    isfail = true;
                }
            });
        }
    }

    private void uploadBHQ_XHQK_ZTCZ()
    {
        for (int i = 0; i < list_BHQ_XHQK_ZTCZ.size(); i++)
        {
            final BHQ_XHQK_ZTCZ bhq_XHQK_ZTCZ = list_BHQ_XHQK_ZTCZ.get(i);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("XHID", bhq_XHQK_ZTCZ.getXHID());
            hashMap.put("ZTSJD", bhq_XHQK_ZTCZ.getZTSJD());
            hashMap.put("SZCZ", bhq_XHQK_ZTCZ.getSZCZ());
            hashMap.put("v_flag", "A");
            String params = HttpUrlConnect.setParams("APP.AddNewBHQ_XHQK_ZTCZ", "0", hashMap);
            new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    Result result = JSON.parseObject(responseInfo.result, Result.class);
                    if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                    {
                        bhq_XHQK_ZTCZ.setIsUpload("1");
                        SqliteDb.save(getActivity(), bhq_XHQK_ZTCZ);
                        showProgress();
                    } else
                    {
                        failupload();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    failupload();
                }
            });
        }
    }

    private void uploadXXCJ()
    {
        for (int i = 0; i < list_BHQ_XHSJCJ.size(); i++)
        {
            final BHQ_XHSJCJ bhq_XHSJCJ = list_BHQ_XHSJCJ.get(i);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("CJID", bhq_XHSJCJ.getCJID());
            hashMap.put("XHID", bhq_XHSJCJ.getXHID());
            hashMap.put("SSBHZ", bhq_XHSJCJ.getSSBHZ());// ?
            hashMap.put("CJR", bhq_XHSJCJ.getCJR());
            hashMap.put("CJRXM", bhq_XHSJCJ.getCJRXM());
            hashMap.put("CJSJ", bhq_XHSJCJ.getCJSJ());
            hashMap.put("ZYDL", bhq_XHSJCJ.getZYDL());
            hashMap.put("ZYXL", bhq_XHSJCJ.getZYXL());
            hashMap.put("ZM", bhq_XHSJCJ.getZM());
            hashMap.put("GANG", bhq_XHSJCJ.getGANG());
            hashMap.put("MU", bhq_XHSJCJ.getMU());
            hashMap.put("KE", bhq_XHSJCJ.getKE());
            hashMap.put("BHJB", bhq_XHSJCJ.getBHJB());
            hashMap.put("BWD", bhq_XHSJCJ.getBWD());
            hashMap.put("TZ", bhq_XHSJCJ.getTZ());
            hashMap.put("XX", bhq_XHSJCJ.getXX());
            hashMap.put("SZHJ", bhq_XHSJCJ.getSZHJ());
            hashMap.put("BWYS", bhq_XHSJCJ.getBWYS());
            hashMap.put("BDZYBZ", bhq_XHSJCJ.getBDZYBZ());
            hashMap.put("X", bhq_XHSJCJ.getX());
            hashMap.put("Y", bhq_XHSJCJ.getY());
            hashMap.put("SFSC", bhq_XHSJCJ.getSFSC());
            hashMap.put("v_flag", "A");
            String params = HttpUrlConnect.setParams("APP.AddCJXX", "0", hashMap);
            new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    Result result = JSON.parseObject(responseInfo.result, Result.class);
                    if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                    {
                        bhq_XHSJCJ.setIsUpload("1");
                        SqliteDb.save(getActivity(), bhq_XHSJCJ);
                        showProgress();
                    } else
                    {
                        failupload();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    failupload();
                }
            });
        }
    }

    private void uploadSJ()
    {
        for (int i = 0; i < list_BHQ_XHSJ.size(); i++)
        {
            final BHQ_XHSJ bhq_XHSJ = list_BHQ_XHSJ.get(i);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("SJID", bhq_XHSJ.getSJID());
            hashMap.put("SJLX", bhq_XHSJ.getSJLX());
            hashMap.put("SJMS", bhq_XHSJ.getSJMS());
            hashMap.put("CLQK", bhq_XHSJ.getCLQK());
            hashMap.put("SBR", bhq_XHSJ.getSBR());
            hashMap.put("SBRXM", bhq_XHSJ.getSBRXM());
            hashMap.put("SBSJ", bhq_XHSJ.getSBSJ());
            hashMap.put("SSBHZ", bhq_XHSJ.getSSBHZ());
            hashMap.put("LCZT", bhq_XHSJ.getLCZT());
            hashMap.put("X", bhq_XHSJ.getX());
            hashMap.put("Y", bhq_XHSJ.getY());
            hashMap.put("XHID", bhq_XHSJ.getXHID());
            hashMap.put("v_flag", "A");
            String params = HttpUrlConnect.setParams("APP.InsertBHQ_XHSJ", "0", hashMap);
            new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    Result result = JSON.parseObject(responseInfo.result, Result.class);
                    if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                    {
                        bhq_XHSJ.setIsUpload("1");
                        SqliteDb.save(getActivity(), bhq_XHSJ);
                        showProgress();
                    } else
                    {
                        failupload();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    failupload();
                }
            });
        }
    }

    private void uploadFJ()
    {
        for (int i = 0; i < list_FJ_SCFJ.size(); i++)
        {
            final FJ_SCFJ fj_SCFJ = list_FJ_SCFJ.get(i);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("FJID", fj_SCFJ.getFJID());
            hashMap.put("GLID", fj_SCFJ.getGLID());
            hashMap.put("SCSJ", fj_SCFJ.getSCSJ());
            hashMap.put("GLBM", fj_SCFJ.getGLBM());
            hashMap.put("FJMC", fj_SCFJ.getFJMC());
            hashMap.put("FJLJ", fj_SCFJ.getFJLJ());
            hashMap.put("SCR", fj_SCFJ.getSCR());
            hashMap.put("SCRXM", fj_SCFJ.getSCRXM());
            hashMap.put("FJLX", fj_SCFJ.getFJLX());
            hashMap.put("SFSC", fj_SCFJ.getSFSC());
            hashMap.put("SCZT", fj_SCFJ.getSCZT());
            hashMap.put("Change", fj_SCFJ.getChange());
            hashMap.put("FL", fj_SCFJ.getFL());
            hashMap.put("v_flag", "A");
            String params = HttpUrlConnect.setParams("APP.InsertFJ_SCFJ", "0", hashMap);
            new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    Result result = JSON.parseObject(responseInfo.result, Result.class);
                    if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                    {
                        fj_SCFJ.setISUPLOAD("1");
                        SqliteDb.save(getActivity(), fj_SCFJ);
                        showProgress();
                    } else
                    {
                        failupload();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    failupload();
                }
            });
        }
    }

    private void uploadXHQK()
    {
        for (int i = 0; i < list_BHQ_XHQK.size(); i++)
        {
            final BHQ_XHQK bhq_XHQK = list_BHQ_XHQK.get(i);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("XHID", bhq_XHQK.getXHID());
            hashMap.put("XHRY", bhq_XHQK.getXHRY());
            hashMap.put("XHRQ", bhq_XHQK.getXHRQ());
            hashMap.put("XHRYXM", bhq_XHQK.getXHRYXM());
            hashMap.put("XHKSSJ", bhq_XHQK.getXHKSSJ());
            hashMap.put("XHJSSJ", bhq_XHQK.getXHJSSJ());
            hashMap.put("XHLC", bhq_XHQK.getXHLC());
            hashMap.put("XHXSS", bhq_XHQK.getXHXSS());
            hashMap.put("XHFZS", bhq_XHQK.getXHFZS());
            hashMap.put("XHZT", bhq_XHQK.getXHZT());
            hashMap.put("v_flag", "A");
            String params = HttpUrlConnect.setParams("APP.AddBHQ_XHQK", "0", hashMap);
            new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    String a = responseInfo.result;
                    Result result = JSON.parseObject(responseInfo.result, Result.class);
                    if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                    {
                        bhq_XHQK.setIsUpload("1");
                        SqliteDb.save(getActivity(), bhq_XHQK);
                        showProgress();
                    } else
                    {
                        failupload();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    failupload();
                }
            });
        }
    }

    private void uploadGJ()
    {
        for (int i = 0; i < list_BHQ_XHQK_GJ.size(); i++)
        {
            final BHQ_XHQK_GJ bhq_XHQK_GJ = list_BHQ_XHQK_GJ.get(i);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("XHID", bhq_XHQK_GJ.getXHID());
            hashMap.put("X", bhq_XHQK_GJ.getX());
            hashMap.put("Y", bhq_XHQK_GJ.getY());
            hashMap.put("JLSJ", bhq_XHQK_GJ.getJLSJ());
            hashMap.put("v_flag", "A");
            String params = HttpUrlConnect.setParams("APP.AddNewBHQ_XHQK_GJ", "0", hashMap);
            new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    Result result = JSON.parseObject(responseInfo.result, Result.class);
                    if (result.getResultCode() == 200 && result.getAffectedRows() > 0)// 连接数据库成功
                    {
                        bhq_XHQK_GJ.setIsUpload("1");
                        SqliteDb.save(getActivity(), bhq_XHQK_GJ);
                        showProgress();
                    } else
                    {
                        failupload();
                    }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    failupload();
                }
            });
        }
    }

    private void uploadMedia(List<FJ_SCFJ> list_allpicture)
    {
        for (int i = 0; i < list_allpicture.size(); i++)
        {
            String aa = list_allpicture.get(i).getFJBDLJ();
            File file = new File(list_allpicture.get(i).getFJBDLJ());
            RequestParams params = new RequestParams();
            params.addQueryStringParameter("param", list_allpicture.get(i).getSCSJ());
            params.addQueryStringParameter("first", "true");
            params.addQueryStringParameter("last", "true");
            params.addQueryStringParameter("offset", "0");
            params.addQueryStringParameter("file", file.getName());
            params.setBodyEntity(new FileUploadEntity(file, "text/html"));
            HttpUtils http = new HttpUtils();
            http.configTimeout(60000);
            http.send(HttpRequest.HttpMethod.POST, AppConfig.uploadUrl, params, new RequestCallBack<String>()
            {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo)
                {
                    String aa = responseInfo.result;
                    String adda = responseInfo.result;
                    String ada = responseInfo.result;
                    // Result result = JSON.parseObject(responseInfo.result,
                    // Result.class);
                    // if (result.getResultCode() == 200 &&
                    // result.getAffectedRows() > 0)// 连接数据库成功
                    // {
                    showProgress();
                    // } else
                    // {
                    // isfail = true;
                    // }
                }

                @Override
                public void onFailure(HttpException error, String msg)
                {
                    String aa = error.getMessage();
                    failupload();
                }
            });
        }
    }

    private void showProgress()
    {
        latch.countDown();
        Long l = latch.getCount();
        pb.setProgress(Integer.valueOf(utils.getRate(ThreadCount - Integer.valueOf(String.valueOf(latch.getCount())), ThreadCount)));
        if (l.intValue() == 0) // 全部线程是否已经结束
        {
            pb.setVisibility(View.GONE);
            tv_tips.setVisibility(View.VISIBLE);
            tv_tips.setText("数据同步成功!");
        }
    }

    private void failupload()
    {
        pb.setVisibility(View.GONE);
        tv_tips.setVisibility(View.VISIBLE);
        tv_tips.setText("数据同步失败!");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        // getActivity().unregisterReceiver(Progress_SynchronousData);
    }

    private void CleanLoginInfo()
    {
        dt_manager_offline dt_manager_offline = (com.bhq.bean.dt_manager_offline) SqliteDb.getCurrentUser(getActivity(), dt_manager_offline.class);
        dt_manager_offline.setAutoLogin("0");
        dt_manager_offline.setIsLogin("0");
        SqliteDb.existSystemoffline(getActivity(), dt_manager_offline);
    }
}
