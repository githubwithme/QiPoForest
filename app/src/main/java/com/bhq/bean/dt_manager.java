package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: dt_manager 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "dt_manager")
public class dt_manager implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String id;
	public String role_id;
	public String role_type;
	public String user_name;
	public String password;
	public String salt;
	public String real_name;
	public String telephone;
	public String email;
	public String is_lock;
	public String add_time;
	public String wxNum;
	public String agentId;
	public String reg_ip;
	public String qq;
	public String province;
	public String city;
	public String county;
	public String remark;
	public String sort_id;
	public String agentLevel;
	public String DepartId;
	public String Phone;
	public String Address;
	public String UserType;
	public String isPatrol;
	public String UserPhoto;
	public String Change;
	public String SFSC;
	public String XGSJ;
	public String AutoLogin;
	public String onceUsed;

	public void setOnceUsed(String onceUsed)
	{
		this.onceUsed = onceUsed;
	}

	public String getOnceUsed()
	{
		return onceUsed;
	}

	public void setAutoLogin(String autoLogin)
	{
		AutoLogin = autoLogin;
	}

	public String getAutoLogin()
	{
		return AutoLogin;
	}

	public String getid()
	{
		return id;
	}

	public void setid(String id)
	{
		this.id = id;
	}

	public String getrole_id()
	{
		return role_id;
	}

	public void setrole_id(String role_id)
	{
		this.role_id = role_id;
	}

	public String getrole_type()
	{
		return role_type;
	}

	public void setrole_type(String role_type)
	{
		this.role_type = role_type;
	}

	public String getuser_name()
	{
		return user_name;
	}

	public void setuser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getpassword()
	{
		return password;
	}

	public void setpassword(String password)
	{
		this.password = password;
	}

	public String getsalt()
	{
		return salt;
	}

	public void setsalt(String salt)
	{
		this.salt = salt;
	}

	public String getreal_name()
	{
		return real_name;
	}

	public void setreal_name(String real_name)
	{
		this.real_name = real_name;
	}

	public String gettelephone()
	{
		return telephone;
	}

	public void settelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getemail()
	{
		return email;
	}

	public void setemail(String email)
	{
		this.email = email;
	}

	public String getis_lock()
	{
		return is_lock;
	}

	public void setis_lock(String is_lock)
	{
		this.is_lock = is_lock;
	}

	public String getadd_time()
	{
		return add_time;
	}

	public void setadd_time(String add_time)
	{
		this.add_time = add_time;
	}

	public String getwxNum()
	{
		return wxNum;
	}

	public void setwxNum(String wxNum)
	{
		this.wxNum = wxNum;
	}

	public String getagentId()
	{
		return agentId;
	}

	public void setagentId(String agentId)
	{
		this.agentId = agentId;
	}

	public String getreg_ip()
	{
		return reg_ip;
	}

	public void setreg_ip(String reg_ip)
	{
		this.reg_ip = reg_ip;
	}

	public String getqq()
	{
		return qq;
	}

	public void setqq(String qq)
	{
		this.qq = qq;
	}

	public String getprovince()
	{
		return province;
	}

	public void setprovince(String province)
	{
		this.province = province;
	}

	public String getcity()
	{
		return city;
	}

	public void setcity(String city)
	{
		this.city = city;
	}

	public String getcounty()
	{
		return county;
	}

	public void setcounty(String county)
	{
		this.county = county;
	}

	public String getremark()
	{
		return remark;
	}

	public void setremark(String remark)
	{
		this.remark = remark;
	}

	public String getsort_id()
	{
		return sort_id;
	}

	public void setsort_id(String sort_id)
	{
		this.sort_id = sort_id;
	}

	public String getagentLevel()
	{
		return agentLevel;
	}

	public void setagentLevel(String agentLevel)
	{
		this.agentLevel = agentLevel;
	}

	public String getDepartId()
	{
		return DepartId;
	}

	public void setDepartId(String DepartId)
	{
		this.DepartId = DepartId;
	}

	public String getPhone()
	{
		return Phone;
	}

	public void setPhone(String Phone)
	{
		this.Phone = Phone;
	}

	public String getAddress()
	{
		return Address;
	}

	public void setAddress(String Address)
	{
		this.Address = Address;
	}

	public String getUserType()
	{
		return UserType;
	}

	public void setUserType(String UserType)
	{
		this.UserType = UserType;
	}

	public String getisPatrol()
	{
		return isPatrol;
	}

	public void setisPatrol(String isPatrol)
	{
		this.isPatrol = isPatrol;
	}

	public String getUserPhoto()
	{
		return UserPhoto;
	}

	public void setUserPhoto(String UserPhoto)
	{
		this.UserPhoto = UserPhoto;
	}

	public String getChange()
	{
		return Change;
	}

	public void setChange(String Change)
	{
		this.Change = Change;
	}

	public String getSFSC()
	{
		return SFSC;
	}

	public void setSFSC(String SFSC)
	{
		this.SFSC = SFSC;
	}

	public String getXGSJ()
	{
		return XGSJ;
	}

	public void setXGSJ(String XGSJ)
	{
		this.XGSJ = XGSJ;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<dt_manager> CREATOR = new Creator()
	{
		@Override
		public dt_manager createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			dt_manager p = new dt_manager();
			p.setid(source.readString());
			p.setrole_id(source.readString());
			p.setrole_type(source.readString());
			p.setuser_name(source.readString());
			p.setpassword(source.readString());
			p.setsalt(source.readString());
			p.setreal_name(source.readString());
			p.settelephone(source.readString());
			p.setemail(source.readString());
			p.setis_lock(source.readString());
			p.setadd_time(source.readString());
			p.setwxNum(source.readString());
			p.setagentId(source.readString());
			p.setreg_ip(source.readString());
			p.setqq(source.readString());
			p.setprovince(source.readString());
			p.setcity(source.readString());
			p.setcounty(source.readString());
			p.setremark(source.readString());
			p.setsort_id(source.readString());
			p.setagentLevel(source.readString());
			p.setDepartId(source.readString());
			p.setPhone(source.readString());
			p.setAddress(source.readString());
			p.setUserType(source.readString());
			p.setisPatrol(source.readString());
			p.setUserPhoto(source.readString());
			p.setChange(source.readString());
			p.setSFSC(source.readString());
			p.setXGSJ(source.readString());
			p.setAutoLogin(source.readString());
			p.setOnceUsed(source.readString());
			return p;
		}

		@Override
		public dt_manager[] newArray(int size)
		{
			return new dt_manager[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(id);
		p.writeString(role_id);
		p.writeString(role_type);
		p.writeString(user_name);
		p.writeString(password);
		p.writeString(salt);
		p.writeString(real_name);
		p.writeString(telephone);
		p.writeString(email);
		p.writeString(is_lock);
		p.writeString(add_time);
		p.writeString(wxNum);
		p.writeString(agentId);
		p.writeString(reg_ip);
		p.writeString(qq);
		p.writeString(province);
		p.writeString(city);
		p.writeString(county);
		p.writeString(remark);
		p.writeString(sort_id);
		p.writeString(agentLevel);
		p.writeString(DepartId);
		p.writeString(Phone);
		p.writeString(Address);
		p.writeString(UserType);
		p.writeString(isPatrol);
		p.writeString(UserPhoto);
		p.writeString(Change);
		p.writeString(SFSC);
		p.writeString(XGSJ);
		p.writeString(AutoLogin);
		p.writeString(onceUsed);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
