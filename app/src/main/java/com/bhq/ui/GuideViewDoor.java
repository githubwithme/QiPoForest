package com.bhq.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.bhq.R;
import com.bhq.app.AppManager;
import com.bhq.widget.CircleImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @author HMJ 功能描述：
 *         1开屏画面停留5秒，5秒后，如果SharedPreferences中含有用户信息则自动登录，修改在线状态和记录登录日志，
 *         并进入主界面，否则跳进登录界面； 2开一个线程进行数据库、站点、作物品种数据更新； 3开一个线程记录应用运行日志和通讯录信息；
 */
public class GuideViewDoor extends Activity
{
	private Animation animationTop;
	CircleImageView image;
	LinearLayout ll_txt;
	Long lg_knowledge, lg_site, lg_zuowupinzhong;
	SharedPreferences sp;
	String userName;
	String userId;
	String psw;
	String phone, account, password, IsOnline;
	String params;
	InputStream is;
	String result;
	JSONObject jsonObj;
	JSONArray jsonArray;
	int resultCode;
	int affecteRows;
	HashMap<String, String> hashMap;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_door);
		AppManager.getAppManager().addActivity(this);
		image = (CircleImageView) findViewById(R.id.image);
		ll_txt = (LinearLayout) findViewById(R.id.ll_txt);
		animationTop = AnimationUtils.loadAnimation(GuideViewDoor.this, R.anim.tutorail_scalate_top);
		ll_txt.startAnimation(animationTop);
		image.startAnimation(animationTop);
		sp = GuideViewDoor.this.getSharedPreferences("MY_PRE", MODE_PRIVATE);
		userId = sp.getString("UserId", "");
		new Handler().postDelayed(new Runnable()// 5秒开屏界面,之后检查是否自动登录，可以自动登录就直接进入主界面，否则进入注册登录界面
				{
					@Override
					public void run()
					{
						Intent intent = new Intent(GuideViewDoor.this, Login_.class);
						startActivity(intent);
						GuideViewDoor.this.finish();
					}
				}, 3000);
	}

}
