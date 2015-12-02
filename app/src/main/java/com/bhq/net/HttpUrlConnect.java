package com.bhq.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bhq.bean.CommandEntity;
import com.lidroid.xutils.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlConnect
{

	public static Context context;

	@SuppressWarnings("static-access")
	public HttpUrlConnect(Context context)
	{
		this.context = context;
	}

	public static JSONObject connectServer(Activity activity, String urlStr,
			String params)
	{
		JSONObject jsonObj = null;
		HttpURLConnection con = null;
		InputStream is;
		try
		{
			URL url = new URL(urlStr);
			if (urlStr.substring(0, 5).equalsIgnoreCase("https"))
			{
				con = (HttpsURLConnection) url.openConnection();
			} else
			{
				con = (HttpURLConnection) url.openConnection();
			}
			con.setConnectTimeout(20000);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json");
			OutputStream os = con.getOutputStream(); // 输出流，写数据
			os.write(params.getBytes());
			if (con.getResponseCode() == 200)// 连接服務器成功
			{
				is = con.getInputStream();
				String result = HttpUrlConnect.getInputStreamString(is);
				jsonObj = new JSONObject(result);
				int resultcode = jsonObj.getInt("ResultCode");
				if (resultcode != 200) // 连接数据库失败
				{
					Log.d("failtodatabase", "连接数据库失败！");
					jsonObj = null;
				}
			} else
			{
				Log.d("failtoserver", "连接服务器失败！");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return jsonObj;
	}

	public static RequestParams getParas(String p)
	{
		RequestParams params = new RequestParams();
		try
		{
			params.setContentType("application/json");
			params.setBodyEntity(new StringEntity(p, "utf-8"));
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return params;
	}

	public static String getInputStreamString(InputStream inStream)
	{
		BufferedReader bufferReader = null;
		String line = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			bufferReader = new BufferedReader(new InputStreamReader(inStream));
			while ((line = bufferReader.readLine()) != null)
			{
				sb.append(line);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String setParams(String commandName, String principalId,
			HashMap<String, String> hashMap)
	{
		CommandEntity commandEntity = new CommandEntity();
		commandEntity.setCommandName(commandName);
		commandEntity.setIsCached(true);
		commandEntity.setPrincipalType("1");
		commandEntity.setPrincipalId(principalId);
		commandEntity.setRequestId("22856468-507e-40dc-bc40-470c9fe9f44c");
		commandEntity.setParams(hashMap);
		String str_params = JSON.toJSONString(commandEntity);
		return str_params;
	}

	public static String setDataSyncParams(String commandName,
			String principalId, List<HashMap<String, String>> list)
	{
		CommandEntity commandEntity = new CommandEntity();
		commandEntity.setCommandName(commandName);
		commandEntity.setIsCached(true);
		commandEntity.setPrincipalType("1");
		commandEntity.setPrincipalId(principalId);
		commandEntity.setRequestId("22856468-507e-40dc-bc40-470c9fe9f44c");
		commandEntity.setDataSycParams(list);
		String str_params = JSON.toJSONString(commandEntity);
		return str_params;
	}

}
