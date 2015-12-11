package com.bhq.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bhq.R;
import com.bhq.bean.BHQ_ZSK;
import com.bhq.common.SqliteDb;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@SuppressLint("JavascriptInterface")
@EActivity(R.layout.showknowledge)
public class Offline_ShowKnowledge extends Activity
{
	BHQ_ZSK bhq_ZSK;
	@ViewById
	WebView webview;
	private Handler mHandler = new Handler();

	@AfterViews
	void afterOncreate()
	{
		WebSettings webSettings = webview.getSettings();
		webSettings.setDefaultTextEncodingName("UTF-8");
		webSettings.setSavePassword(true);
		webSettings.setSaveFormData(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		// 设置可以支持缩放
		webSettings.setSupportZoom(true);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(true);
		// 扩大比例的缩放
		webSettings.setUseWideViewPort(true);
		// 自适应屏幕
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setLoadWithOverviewMode(true);

		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setBuiltInZoomControls(true);// support zoom
		webSettings.setLoadWithOverviewMode(true);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int mDensity = metrics.densityDpi;
		if (mDensity == 240)
		{
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		} else if (mDensity == 160)
		{
			webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
		} else if (mDensity == 120)
		{
			webSettings.setDefaultZoom(ZoomDensity.CLOSE);
		} else if (mDensity == DisplayMetrics.DENSITY_XHIGH)
		{
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		} else if (mDensity == DisplayMetrics.DENSITY_TV)
		{
			webSettings.setDefaultZoom(ZoomDensity.FAR);
		}
		// wView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");
		// webview.setWebChromeClient(new MyWebChromeClient());
		// webview.addJavascriptInterface(new DemoJavaScriptInterface(),
		// "demo");
		// 打开本包内asset目录下的index.html文件
		// wView.loadUrl("file:///android_asset/index.html");
		// 打开本地sd卡内的index.html文件
		// wView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");
		// 打开指定URL的html文件
		// wView.loadUrl("http://wap.baidu.com");
		webview.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}
		});
		getListData(bhq_ZSK.getZSID());

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		bhq_ZSK = getIntent().getParcelableExtra("bean");
	}

	final class DemoJavaScriptInterface
	{
		DemoJavaScriptInterface()
		{
		}

		public void clickOnAndroid()
		{
			mHandler.post(new Runnable()
			{
				public void run()
				{
					webview.loadUrl("javascript:wave()");
				}
			});
		}
	}

	/**
	 * Provides a hook for calling "alert" from javascript. Useful for debugging
	 * your javascript.
	 */
	final class MyWebChromeClient extends WebChromeClient
	{
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result)
		{
			result.confirm();
			return true;
		}
	}

	private void getListData(String zsid)
	{
		BHQ_ZSK bhq_ZSK = (BHQ_ZSK) SqliteDb.getZSNR(Offline_ShowKnowledge.this, BHQ_ZSK.class, zsid);
		if (bhq_ZSK.ZSNR==null || bhq_ZSK.ZSNR.toString().equals(""))
		{
			String url = "file:///android_asset/demo.html";
			webview.getSettings().setDefaultTextEncodingName("UTF-8");
			webview.loadUrl(url);
		} else
		{
			webview.loadDataWithBaseURL(null, bhq_ZSK.ZSNR, "text/html", "utf-8", null);// 可用
		}
	}

}
