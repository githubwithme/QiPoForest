package com.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bhq.app.AppConfig;
import com.bhq.bean.BHQ_XHXL;
import com.bhq.bean.BHQ_XHXL_GJ;
import com.bhq.bean.BHQ_ZSK;
import com.bhq.bean.BQH_XHRY;
import com.bhq.bean.Dictionary;
import com.bhq.bean.RW_CYR;
import com.bhq.bean.RW_RW;
import com.bhq.bean.RW_YQB;
import com.bhq.bean.Result;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.SqliteDb;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class UpdateData extends Service
{
    SharedPreferences sp;
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
//            Thread thread = new Thread(new Runnable()
//            {
//                @Override
//                public void run()
//                {
//                    startInitData();
//                }
//            });
//            thread.start();
            startInitData();
        } else
        {
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startInitData()
    {
         sp= this.getSharedPreferences("MY_PRE", MODE_PRIVATE);
        String sysntime = sp.getString("sysntime", "1900-01-01");
        InitTable("APP.InitDictionaryTable",sysntime, Dictionary.class);
        InitTable("APP.InitBHQ_ZSKData",sysntime, BHQ_ZSK.class);
        InitTable("APP.InitRW_RWData",sysntime, RW_RW.class);
        InitTable("APP.InitRW_CYRData", sysntime, RW_CYR.class);
        InitTable("APP.InitRW_YQBData", sysntime, RW_YQB.class);
        InitTable("APP.InitBHQ_XHXLData", sysntime, BHQ_XHXL.class);
        InitTable("APP.InitBHQ_XHXL_GJInfo", sysntime, BHQ_XHXL_GJ.class);
        InitTable("APP.InitBQH_XHRYData", sysntime, BQH_XHRY.class);
//        InitTable("APP.InitUserTable", sysntime, dt_manager_offline.class);

        getServerSystemTime();
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
                        if (jsonArray_Rows.size() > 0)
                        {
                            if (action.equals("APP.InitBHQ_ZSKData"))
                            {
                                SqliteDb.insertData(UpdateData.this, "BHQ_ZSK", ColumnNames, jsonArray_Rows);
                                initZSNR(sysntime);
//                                for (int i = 0; i < jsonArray_Rows.size(); i++)
//                                {
//                                    getZSNR(jsonArray_Rows.getJSONArray(i).getString(0));
//                                }
                            } else if (action.equals("APP.InitRW_RWData"))
                            {
                                SqliteDb.insertRW_RWData(UpdateData.this, "RW_RW", ColumnNames, jsonArray_Rows);
                            } else if (action.equals("APP.InitRW_CYRData"))
                            {
                                SqliteDb.insertRW_CYRData(UpdateData.this, "RW_CYR", ColumnNames, jsonArray_Rows);
                            } else if (action.equals("APP.InitBHQ_XHXLData"))
                            {
                                SqliteDb.insertData(UpdateData.this, "BHQ_XHXL", ColumnNames, jsonArray_Rows);
                            }else if (action.equals("APP.InitBHQ_XHXL_GJInfo"))
                            {
                                SqliteDb.insertData(UpdateData.this, "BHQ_XHXL_GJ", ColumnNames, jsonArray_Rows);
                            }else if (action.equals("APP.InitBQH_XHRYData"))
                            {
                                SqliteDb.insertData(UpdateData.this, "BQH_XHRY", ColumnNames, jsonArray_Rows);
                            }
                            else if (action.equals("APP.InitRW_YQBData"))
                            {
                                SqliteDb.insertData(UpdateData.this, "RW_YQB", ColumnNames, jsonArray_Rows);
                            } else if (action.equals("APP.InitUserTable"))
                            {
                                SqliteDb.insertData(UpdateData.this, "dt_manager_offline", ColumnNames, jsonArray_Rows);
//                                for (int i = 0; i < jsonArray_Rows.size(); i++)
//                                {
//                                            String id=jsonArray_Rows.getJSONArray(i).getString(0);
//                                            String url=jsonArray_Rows.getJSONArray(i).getString(26);
//                                            if (url!=null && !url.equals(""))
//                                            {
//                                                String BDLJ = AppConfig.MEDIA_PATH +url.subSequence(url.lastIndexOf("/") + 1, url.length());
//                                                SqliteDb.updatedt_manager_offline(UpdateData.this,"dt_manager_offline",id,BDLJ);
//                                                getPhotos(AppConfig.url + url, BDLJ);
//                                            }
//                                }

                            } else if (action.equals("APP.InitDictionaryTable"))
                            {
                                SqliteDb.deleteAllRecord(UpdateData.this, Dictionary.class);//因为该表无法创建主键，所以避免重复数据插入，执行此。
                                SqliteDb.insertData(UpdateData.this, "Dictionary", ColumnNames, jsonArray_Rows);
                            }
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
    private <T> void initZSNR(String sysntime)
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("sysntime", sysntime);
        String params = ConnectionHelper.setParams("APP.InitZSNR", "0", hashMap);
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
                            String zsid = result.getRows().getJSONArray(i).get(0).toString();
                            String zsnr = result.getRows().getJSONArray(i).get(1).toString();
                            SqliteDb.updateZSK(UpdateData.this,"BHQ_ZSK",zsid,zsnr);
                        }
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
            boolean issuccess = SqliteDb.saveAll(UpdateData.this, listData);
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
                boolean issuccess = SqliteDb.saveUserInfo(UpdateData.this, obj);
                return;
            }
            boolean issuccess = SqliteDb.save(UpdateData.this, obj);
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
    private <T> void getServerSystemTime()
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String params = ConnectionHelper.setParams("APP.getServerSystemTime", "0", hashMap);
        new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
        {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo)
            {
                String a = responseInfo.result;
                Result result = JSON.parseObject(responseInfo.result, Result.class);
                if (result.getResultCode() == 200)
                {
                    if (result.getAffectedRows() != 0)
                    {
                        JSONArray jsonArray_Rows = result.getRows();
                        if (jsonArray_Rows.size() > 0)
                        {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("sysntime", jsonArray_Rows.getJSONArray(0).get(0).toString());
                            editor.commit();
                        }
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
                    SqliteDb.updateZSK(UpdateData.this,"BHQ_ZSK",zsid,zsnr);
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
