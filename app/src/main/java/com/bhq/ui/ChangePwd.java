package com.bhq.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bhq.R;
import com.bhq.app.AppConfig;
import com.bhq.app.AppContext;
import com.bhq.bean.Result;
import com.bhq.bean.dt_manager;
import com.bhq.common.ConnectionHelper;
import com.bhq.common.SqliteDb;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;

@EActivity(R.layout.changepwd)
public class ChangePwd extends Activity
{

	@ViewById
	Button btn_sure;
	@ViewById
	EditText et_confirmnewpwd;
	@ViewById
	EditText et_newpwd;
	@ViewById
	EditText et_oldpwd;

	@Click
	void btn_back()
	{
		finish();
	}

	@Click
	void btn_sure()
	{
		if (!et_oldpwd.getText().toString().equals("") && !et_newpwd.getText().toString().equals("") && !et_confirmnewpwd.getText().equals(""))
		{
			if (et_newpwd.getText().toString().equals(et_confirmnewpwd.getText().toString()))
			{
				changePassword();
			} else
			{
				Toast.makeText(ChangePwd.this, "两次密码不一致!请重新输入", Toast.LENGTH_SHORT).show();
			}
		} else
		{
			Toast.makeText(ChangePwd.this, "请输入密码!", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
	}

	public void changePassword()
	{
		dt_manager dt_manager = AppContext.getUserInfo(ChangePwd.this);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("name", dt_manager.getuser_name());
		hashMap.put("oldpwd", et_oldpwd.getText().toString());
		hashMap.put("newpwd", et_newpwd.getText().toString());
		hashMap.put("newpwd", et_newpwd.getText().toString());
		hashMap.put("v_flag", "A");
		String params = ConnectionHelper.setParams("APP.ChangePwd", "0", hashMap);
		new HttpUtils().send(HttpRequest.HttpMethod.POST, AppConfig.dataBaseUrl, ConnectionHelper.getParas(params), new RequestCallBack<String>()
		{
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo)
			{
				String a = responseInfo.result;
				List<dt_manager> listData = null;
				Result result = JSON.parseObject(responseInfo.result, Result.class);
				if (result.getResultCode() == 200)// -1出错；0结果集数量为0；结果列表
				{
					if (result.getAffectedRows() >= 0)
					{
						Toast.makeText(ChangePwd.this, "修改成功!", Toast.LENGTH_SHORT).show();
						CleanLoginInfo();
						Intent intent = new Intent(ChangePwd.this, Login_.class);
						startActivity(intent);
						finish();
					} else
					{
						Toast.makeText(ChangePwd.this, "原密码错误!", Toast.LENGTH_SHORT).show();
					}
				} else
				{
					Toast.makeText(ChangePwd.this, "连接数据服务器出错了!", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(HttpException error, String msg)
			{
				Toast.makeText(ChangePwd.this, "连接服务器出错了!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void CleanLoginInfo()
	{
		dt_manager dt_manager = (dt_manager) SqliteDb.getCurrentUser(ChangePwd.this, dt_manager.class);
		dt_manager.setpassword("");
		dt_manager.setAutoLogin("0");
		SqliteDb.existSystem(ChangePwd.this, dt_manager);
	}
}
