package com.bhq.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bhq.R;
import com.bhq.bean.dt_manager_offline;
import com.bhq.common.CryptoTools;
import com.bhq.common.SqliteDb;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.UnsupportedEncodingException;

@EActivity(R.layout.diary)
public class Offline_Diary extends Activity
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
				Toast.makeText(Offline_Diary.this, "两次密码不一致!请重新输入", Toast.LENGTH_SHORT).show();
			}
		} else
		{
			Toast.makeText(Offline_Diary.this, "请输入密码!", Toast.LENGTH_SHORT).show();
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
		dt_manager_offline dt_manager = (dt_manager_offline) SqliteDb.getCurrentUser(Offline_Diary.this, dt_manager_offline.class);
		String oldpdw = encryptPassword(dt_manager, et_oldpwd.getText().toString());
		dt_manager_offline dt_manager_offlinetemp = (dt_manager_offline) SqliteDb.login(Offline_Diary.this, dt_manager_offline.class, dt_manager.getuser_name(), oldpdw);
		if (dt_manager_offlinetemp == null)
		{
			Toast.makeText(Offline_Diary.this, "原密码错误!", Toast.LENGTH_SHORT).show();
		} else
		{
			String newpdw = encryptPassword(dt_manager, et_newpwd.getText().toString());
			dt_manager_offlinetemp.setpassword(newpdw);
			dt_manager_offlinetemp.setIsUpload("0");
			boolean issuccess = SqliteDb.changePassword(Offline_Diary.this, dt_manager_offlinetemp);
			if (issuccess)
			{
				Toast.makeText(Offline_Diary.this, "修改成功!", Toast.LENGTH_SHORT).show();
				CleanLoginInfo(dt_manager_offlinetemp);
				Intent intent = new Intent(Offline_Diary.this, Login_.class);
				startActivity(intent);
				finish();
			} else
			{
				Toast.makeText(Offline_Diary.this, "修改密码失败!", Toast.LENGTH_SHORT).show();
			}

		}
	}

	private String encryptPassword(dt_manager_offline dt_manager, String pdw)
	{
		String encodePdw = "";
		byte[] DESkey;
		try
		{
			DESkey = dt_manager.getsalt().getBytes("UTF-8");
			CryptoTools des = new CryptoTools(DESkey);// 自定义密钥
			encodePdw = des.encode(pdw);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return encodePdw;
	}

	private void CleanLoginInfo(dt_manager_offline dt_manager_offline)
	{
		dt_manager_offline.setAutoLogin("0");
		SqliteDb.existSystemoffline(Offline_Diary.this, dt_manager_offline);
	}
}
