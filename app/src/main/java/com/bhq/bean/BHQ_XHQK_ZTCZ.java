package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: BHQ_XHQK_ZTCZ 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "BHQ_XHQK_ZTCZ")
public class BHQ_XHQK_ZTCZ implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String ZTID;
	public String XHID;
	public String ZTSJD;
	public String SZCZ;
	public String IsUpload;// 暂停次数

	public void setIsUpload(String isUpload)
	{
		IsUpload = isUpload;
	}

	public String getIsUpload()
	{
		return IsUpload;
	}

	public String getZTID()
	{
		return ZTID;
	}

	public void setZTID(String ZTID)
	{
		this.ZTID = ZTID;
	}

	public String getXHID()
	{
		return XHID;
	}

	public void setXHID(String XHID)
	{
		this.XHID = XHID;
	}

	public String getZTSJD()
	{
		return ZTSJD;
	}

	public void setZTSJD(String ZTSJD)
	{
		this.ZTSJD = ZTSJD;
	}

	public String getSZCZ()
	{
		return SZCZ;
	}

	public void setSZCZ(String SZCZ)
	{
		this.SZCZ = SZCZ;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<BHQ_XHQK_ZTCZ> CREATOR = new Creator()
	{
		@Override
		public BHQ_XHQK_ZTCZ createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			BHQ_XHQK_ZTCZ p = new BHQ_XHQK_ZTCZ();
			p.setZTID(source.readString());
			p.setXHID(source.readString());
			p.setZTSJD(source.readString());
			p.setSZCZ(source.readString());
			p.setIsUpload(source.readString());
			return p;
		}

		@Override
		public BHQ_XHQK_ZTCZ[] newArray(int size)
		{
			return new BHQ_XHQK_ZTCZ[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(ZTID);
		p.writeString(XHID);
		p.writeString(ZTSJD);
		p.writeString(SZCZ);
		p.writeString(IsUpload);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
