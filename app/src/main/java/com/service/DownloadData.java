package com.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bhq.app.AppConfig;
import com.bhq.bean.BHQ_ZSK;
import com.bhq.bean.RW_CYR;
import com.bhq.bean.RW_RW;
import com.bhq.bean.RW_YQB;
import com.bhq.bean.Result;
import com.bhq.bean.ResultDeal;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.SqliteDb;
import com.bhq.common.utils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DownloadData extends Service
{

    public static final String ACTION_DOWNLOADDATA = "ACTION_DOWNLOADDATA";
    String action;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        action = intent.getAction();

        if (ACTION_DOWNLOADDATA.equals(action))
        {
            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    startInitData();
                }
            });
            thread.start();
        } else
        {
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startInitData()
    {
        SharedPreferences sp= this.getSharedPreferences("MY_PRE", MODE_PRIVATE);
        String sysntime = sp.getString("sysntime", "1900-01-01");
        //		InitTable("APP.InitDictionaryTable", Dictionary.class);
        InitTable("APP.InitZSKTable",sysntime, BHQ_ZSK.class);
        InitTable("APP.getRW_RW",sysntime, RW_RW.class);
        InitTable("APP.getRW_CYR",sysntime, RW_CYR.class);
        InitTable("APP.getRW_YQB",sysntime, RW_YQB.class);
        InitTable("APP.InitUserTable",sysntime, dt_manager_offline.class);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sysntime", utils.getTime());
        editor.commit();
    }

    private <T> void InitTable(final String action,final String sysntime, final Class<T> className)
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sysntime", sysntime);
        String params = ConnectionHelper.setParams(action, "0", hashMap);
        new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
        {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo)
            {
                String a = responseInfo.result;
                List<?> listData = null;
                Result result = JSON.parseObject(responseInfo.result, Result.class);
                if (result.getResultCode() == 200)
                {
                    if (result.getAffectedRows() != 0)
                    {
                        JSONArray jsonArray_Rows = result.getRows();
                        String[] ColumnNames = result.getColumnNames();
                        for (int i = 0; i < jsonArray_Rows.size(); i++)
                        {
                            JSONArray jsonArry = new JSONArray();
                            jsonArry.add(jsonArray_Rows.get(i));
                            Object obj = JSON.parseObject(ResultDeal.convertSingleRow(jsonArry, ColumnNames), className);
                            saveSingleData(obj, action);
                        }


//                        int size = jsonArray_Rows.size() / 20;
//                        if (size != 0)
//                        {
//                            for (int k = 0; k <= size; k++)
//                            {
//                                if (k == size)
//                                {
//                                    JSONArray jsonArry = new JSONArray();
//                                    jsonArry.addAll(jsonArray_Rows.subList((k - 1) * 20, jsonArray_Rows.size()));
//                                    listData = JSON.parseArray(ResultDeal.ConvertAllRow(jsonArry, ColumnNames), className);
//                                    saveData(listData, action);
//                                }
//                                JSONArray jsonArry = new JSONArray();
//                                if ((k + 1) * 20 <= jsonArray_Rows.size())
//                                {
//                                    jsonArry.addAll(jsonArray_Rows.subList(k * 20, (k + 1) * 20));
//                                }
//                                listData = JSON.parseArray(ResultDeal.ConvertAllRow(jsonArry, ColumnNames), className);
//                                saveData(listData, action);
//                            }
//
//                        } else
//                        {
//                            listData = JSON.parseArray(ResultDeal.getAllRow(result), className);
//                            saveData(listData, action);
//                        }
                    }
                } else
                {
                }
            }

            @Override
            public void onFailure(HttpException error, String msg)
            {
            }
        });

    }

    public void saveData(List<?> listData, String action)
    {
        if (listData == null)
        {
        } else
        {
            if (action.equals("APP.InitZSKTable"))
            {
                for (int i = 0; i < listData.size(); i++)
                {
                    BHQ_ZSK bhq_ZSK = (BHQ_ZSK) listData.get(i);
                    if (bhq_ZSK.getimgurl() != null && !bhq_ZSK.getimgurl().equals(""))
                    {
                        String bdlj = AppConfig.MEDIA_PATH + bhq_ZSK.getimgurl().subSequence(bhq_ZSK.getimgurl().lastIndexOf("/") + 1, bhq_ZSK.getimgurl().length());
                        bhq_ZSK.setBDLJ(bdlj);
                        getPhotos(AppConfig.url + bhq_ZSK.getimgurl(), bdlj);
                    }
                }
            }
            boolean issuccess = SqliteDb.saveAll(DownloadData.this, listData);
            if (issuccess)
            {
                if (action.equals("APP.InitZSKTable"))
                {
                    for (int i = 0; i < listData.size(); i++)
                    {
                        BHQ_ZSK bhq_ZSK = (BHQ_ZSK) listData.get(i);
                        getZSNR(bhq_ZSK.getZSID());
                    }
                }

            } else
            {
            }
        }
    }

    public void saveSingleData(Object obj, String action)
    {
        if (obj == null)
        {
        } else
        {
            if (action.equals("APP.InitZSKTable"))
            {
                BHQ_ZSK bhq_ZSK = (BHQ_ZSK) obj;
                if (bhq_ZSK.getimgurl() != null & !bhq_ZSK.getimgurl().equals(""))
                {
                    String bdlj = AppConfig.MEDIA_PATH + bhq_ZSK.getimgurl().subSequence(bhq_ZSK.getimgurl().lastIndexOf("/") + 1, bhq_ZSK.getimgurl().length());
                    bhq_ZSK.setBDLJ(bdlj);
                    getPhotos(AppConfig.url + bhq_ZSK.getimgurl(), bdlj);
                }
            }
            if (action.equals("APP.InitUserTable"))
            {
//                dt_manager_offline dt_manager_offline = (dt_manager_offline) obj;
//                if (dt_manager_offline.getUserPhoto() != null && !dt_manager_offline.getUserPhoto().equals(""))
//                {
//                    String BDLJ = AppConfig.MEDIA_PATH + dt_manager_offline.getUserPhoto().subSequence(dt_manager_offline.getUserPhoto().lastIndexOf("/") + 1, dt_manager_offline.getUserPhoto().length());
//                    dt_manager_offline.setBDLJ(BDLJ);
//                    getPhotos(AppConfig.url + dt_manager_offline.getUserPhoto(), BDLJ);
//                }
                boolean issuccess = SqliteDb.saveUserInfo(DownloadData.this, obj);
                return;
            }
            boolean issuccess = SqliteDb.save(DownloadData.this, obj);
            if (issuccess)
            {
                if (action.equals("APP.InitZSKTable"))
                {
                    BHQ_ZSK bhq_ZSK = (BHQ_ZSK) obj;
                    getZSNR(bhq_ZSK.getZSID());
                }

            } else
            {
            }
        }
    }

    public void getPhotos(String path, final String target)
    {
        HttpUtils http = new HttpUtils();
        http.download(path, target, true, true, new RequestCallBack<File>()
        {
            @Override
            public void onFailure(HttpException error, String msg)
            {
                if (msg.equals("maybe the file has downloaded completely"))
                {

                } else
                {
                }
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo)
            {

            }
        });

    }

    private void getZSNR(final String zsid)
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("ZSID", zsid);
        String params = ConnectionHelper.setParams("APP.GetZSNR", "0", hashMap);
        new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
        {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo)
            {
                String ba = responseInfo.result;
                Result result = JSON.parseObject(responseInfo.result, Result.class);
                if (result.getResultCode() == 200)// 连接数据库成功
                {
                    String zsnr = result.getRows().getJSONArray(0).get(0).toString();
                    BHQ_ZSK bhq_ZSK = new BHQ_ZSK();
                    bhq_ZSK.setZSID(zsid);
                    bhq_ZSK.setZSNR(zsnr);
                    boolean issuccess = SqliteDb.updateZSNR(DownloadData.this, bhq_ZSK);
                    if (issuccess)
                    {

                    } else
                    {
                    }
                } else
                {
                }

            }

            @Override
            public void onFailure(HttpException error, String msg)
            {
                String aa = error.getMessage();
                String ab = error.getMessage();
            }
        });
    }
}
