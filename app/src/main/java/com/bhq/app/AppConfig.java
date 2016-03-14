package com.bhq.app;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @version :1.0
 * @createTime：2015-8-6 上午10:54:13
 * @description :应用程序的配置类：用于保存用户相关信息及设置
 */
public class AppConfig
{
	public final static String weather_city = "田东";
	private final static String APP_CONFIG = "config";
	public final static String CONF_VOICE = "perf_voice";
	/* 事件附件下载到本地的路径 */
	public static String DOWNLOADPATH_VIDEO = Environment.getExternalStorageDirectory().getPath() + "/bhq/FJ/VIDEO/";
	/* 多媒体制作保存路径 */
	public static String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/bhq/MEDIA/";
    //服务地址
//	public final static String url = "http://192.168.31.163:8066/";
//	public final static String url_web = "http://192.168.31.163:8066/";
	public final static String url = "http://183.234.8.166/";
	public final static String url_web = "http://183.234.8.166/";
//	public final static String url = "http://192.168.1.103:8066/";
//	public final static String url_web = "http://192.168.1.103:8066/";
//	public final static String url = "http://192.168.23.1:8078/";
//	public final static String url_web = "http://192.168.23.1:8078/";
	// public final static String url = "http://192.168.23.1:8078/";
	/* 附件地址 */
	public final static String uploadUrl = url + "HttpUploadHandler.ashx?";
//	public final static String uploadUrltemp = url + "tools/upload_ajax.ashx";
	public final static String dataBaseUrl = url + "mydataservice.ashx";
	/* 服务器地址 */
	// public static final String SERVER_URL = "192.168.31.163";
	// public static final String SERVER_URL = "192.168.1.101";
	public static String UPLOADPATH = "C:/inetpub/wwwroot/HNDZG/Upload/";// 244
	public static String apkpath = Environment.getExternalStorageDirectory().getPath() + "/bhq/APK/";
	private Context mContext;
	private static AppConfig appConfig;

	public static AppConfig getAppConfig(Context context)
	{
		if (appConfig == null)
		{
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	public String get(String key)
	{
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get()
	{
		FileInputStream fis = null;
		Properties props = new Properties();
		try
		{
			// 读取files目录下的config
			// fis = activity.openFileInput(APP_CONFIG);

			// 读取app_config目录下的config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);

			props.load(fis);
		} catch (Exception e)
		{
		} finally
		{
			try
			{
				fis.close();
			} catch (Exception e)
			{
			}
		}
		return props;
	}
}
