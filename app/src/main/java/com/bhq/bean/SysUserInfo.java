package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: SysUserInfo 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0 时间 2015-3-3
 */
@Table(name = "SysUserInfo")
public class SysUserInfo implements Parcelable
{
	public static final Parcelable.Creator<SysUserInfo> CREATOR = new Creator()
	{
		@Override
		public SysUserInfo createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			SysUserInfo p = new SysUserInfo();
			p.setUserId(source.readString());
			p.setLoginName(source.readString());
			p.setpassword(source.readString());
			p.setUserName(source.readString());
			p.setDepartId(source.readString());
			p.setTelphone(source.readString());
			p.setPhone(source.readString());
			p.seteMail(source.readString());
			p.setAddress(source.readString());
			p.setUserType(source.readString());
			p.setCreateDate(source.readString());
			p.setAutoLogin(source.readString());
			return p;
		}

		@Override
		public SysUserInfo[] newArray(int size)
		{
			return new SysUserInfo[size];
		}
	};

	Boolean IsGrider;
	String ManagerGrids;
	String ManagerGrids2;
	Boolean SFSC;
	int id;
	Boolean Change;
	String XGSJ;
	public String autoLogin;

	public void setAutoLogin(String autoLogin)
	{
		this.autoLogin = autoLogin;
	}

	public String getAutoLogin()
	{
		return autoLogin;
	}

	public void setXGSJ(String xGSJ)
	{
		XGSJ = xGSJ;
	}

	public String getXGSJ()
	{
		return XGSJ;
	}

	public void setSFSC(Boolean sFSC)
	{
		SFSC = sFSC;
	}

	public Boolean getSFSC()
	{
		return SFSC;
	}

	public void setIsGrider(Boolean isGrider)
	{
		IsGrider = isGrider;
	}

	public Boolean getIsGrider()
	{
		return IsGrider;
	}

	public void setChange(Boolean change)
	{
		Change = change;
	}

	public Boolean getChange()
	{
		return Change;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	/** identifier field */

	@Id
	private String UserId;

	/** identifier field */

	private String LoginName;

	/** identifier field */

	private String password;

	/** identifier field */

	private String UserName;

	/** identifier field */

	private String DepartId;

	/** identifier field */

	private String Telphone;

	/** identifier field */

	private String Phone;

	/** identifier field */

	private String eMail;

	/** identifier field */

	private String Address;

	/** identifier field */

	private String UserType;

	/** identifier field */

	private String CreateDate;

	String UserPhoto;
	String UserPhotoInLocal;

	public void setUserPhotoInLocal(String userPhotoInLocal)
	{
		UserPhotoInLocal = userPhotoInLocal;
	}

	public String getUserPhotoInLocal()
	{
		return UserPhotoInLocal;
	}

	public void setUserPhoto(String userPhoto)
	{
		UserPhoto = userPhoto;
	}

	public String getUserPhoto()
	{
		return UserPhoto;
	}

	public void setManagerGrids2(String managerGrids2)
	{
		ManagerGrids2 = managerGrids2;
	}

	public String getManagerGrids2()
	{
		return ManagerGrids2;
	}

	public void setManagerGrids(String managerGrids)
	{
		ManagerGrids = managerGrids;
	}

	public String getManagerGrids()
	{
		return ManagerGrids;
	}

	/**
	 * @return 返回 UserId。
	 */
	public String getUserId()
	{
		return UserId;
	}

	/**
	 * @param UserId
	 *            要设置的 UserId。
	 */
	public void setUserId(String UserId)
	{
		this.UserId = UserId;
	}

	/**
	 * @return 返回 LoginName。
	 */
	public String getLoginName()
	{
		return LoginName;
	}

	/**
	 * @param LoginName
	 *            要设置的 LoginName。
	 */
	public void setLoginName(String LoginName)
	{
		this.LoginName = LoginName;
	}

	/**
	 * @return 返回 password。
	 */
	public String getpassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            要设置的 password。
	 */
	public void setpassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return 返回 UserName。
	 */
	public String getUserName()
	{
		return UserName;
	}

	/**
	 * @param UserName
	 *            要设置的 UserName。
	 */
	public void setUserName(String UserName)
	{
		this.UserName = UserName;
	}

	/**
	 * @return 返回 DepartId。
	 */
	public String getDepartId()
	{
		return DepartId;
	}

	/**
	 * @param DepartId
	 *            要设置的 DepartId。
	 */
	public void setDepartId(String DepartId)
	{
		this.DepartId = DepartId;
	}

	/**
	 * @return 返回 Telphone。
	 */
	public String getTelphone()
	{
		return Telphone;
	}

	/**
	 * @param Telphone
	 *            要设置的 Telphone。
	 */
	public void setTelphone(String Telphone)
	{
		this.Telphone = Telphone;
	}

	/**
	 * @return 返回 Phone。
	 */
	public String getPhone()
	{
		return Phone;
	}

	/**
	 * @param Phone
	 *            要设置的 Phone。
	 */
	public void setPhone(String Phone)
	{
		this.Phone = Phone;
	}

	/**
	 * @return 返回 eMail。
	 */
	public String geteMail()
	{
		return eMail;
	}

	/**
	 * @param eMail
	 *            要设置的 eMail。
	 */
	public void seteMail(String eMail)
	{
		this.eMail = eMail;
	}

	/**
	 * @return 返回 Address。
	 */
	public String getAddress()
	{
		return Address;
	}

	/**
	 * @param Address
	 *            要设置的 Address。
	 */
	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	/**
	 * @return 返回 UserType。
	 */
	public String getUserType()
	{
		return UserType;
	}

	/**
	 * @param UserType
	 *            要设置的 UserType。
	 */
	public void setUserType(String UserType)
	{
		this.UserType = UserType;
	}

	/**
	 * @return 返回 CreateDate。
	 */
	public String getCreateDate()
	{
		return CreateDate;
	}

	/**
	 * @param CreateDate
	 *            要设置的 CreateDate。
	 */
	public void setCreateDate(String CreateDate)
	{
		this.CreateDate = CreateDate;
	}

	public String toString()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(Object o)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public int hashCode()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1)
	{

	}
}
