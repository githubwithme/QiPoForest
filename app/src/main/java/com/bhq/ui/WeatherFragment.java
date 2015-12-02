package com.bhq.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhq.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-14 上午11:57:37
 * @description :天气情况界面
 */
@EFragment
public class WeatherFragment extends Fragment
{
	// private WeatherUtil mWeatherUtil;
	// private List<WeatherReport> mListReport = new ArrayList<WeatherReport>();
	// Handler handler = new Handler()
	// {
	// public void handleMessage(Message msg)
	// {
	// switch (msg.arg1)
	// {
	// case 8:
	// boolean b = (Boolean) msg.obj;
	// if (b)
	// {
	// getWaetherData(AppConfig.weather_city);
	// }
	// break;
	// case 9:
	// List<WeatherReport> listReport = (List<WeatherReport>) msg.obj;
	// String a = listReport.get(0).getCity();
	// String c = listReport.get(0).getWeather();
	// break;
	// default:
	// break;
	// }
	//
	// };
	// };

	@AfterViews
	void afterOncreate()
	{
		// initWaetherData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
		return rootView;
	}

	// public void initWaetherData()
	// {
	// new Thread(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// try
	// {
	// mWeatherUtil = new WeatherUtil(getActivity());
	// // boolean b = mWeatherUtil.prepareDB();
	// boolean b = true;
	// Message message = new Message();
	// message.arg1 = 8;
	// message.obj = b;
	// handler.sendMessage(message);
	//
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }).start();
	// }
	//
	// public void getWaetherData(final String city)
	// {
	// new Thread(new Runnable()
	// {
	// @Override
	// public void run()
	// {
	// try
	// {
	// mListReport.clear();
	// mListReport = mWeatherUtil.getWeatherReports(city);
	// Message message = new Message();
	// message.arg1 = 9;
	// message.obj = mListReport;
	// handler.sendMessage(message);
	//
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }).start();
	// }
}
