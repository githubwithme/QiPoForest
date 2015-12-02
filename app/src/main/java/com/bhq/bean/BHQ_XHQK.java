package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: BHQ_XHQK 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "BHQ_XHQK")
public class BHQ_XHQK implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String XHID;
	public String XHRY;
	public String XHRQ;
	public String XHRYXM;
	public String XHKSSJ;
	public String XHJSSJ;
	public String XHLC = "0";
	public String XHXSS = "0";
	public String XHFZS = "0";
	public String XHZT = "1";
	// 自定义
	public String ZTCS;// 暂停次数
	public String IsUpload;// 暂停次数

	public void setIsUpload(String isUpload)
	{
		IsUpload = isUpload;
	}

	public String getIsUpload()
	{
		return IsUpload;
	}

	public void setZTCS(String zTCS)
	{
		ZTCS = zTCS;
	}

	public String getZTCS()
	{
		return ZTCS;
	}

	public String getXHID()
	{
		return XHID;
	}

	public void setXHID(String XHID)
	{
		this.XHID = XHID;
	}

	public String getXHRY()
	{
		return XHRY;
	}

	public void setXHRY(String XHRY)
	{
		this.XHRY = XHRY;
	}

	public String getXHRQ()
	{
		return XHRQ;
	}

	public void setXHRQ(String XHRQ)
	{
		this.XHRQ = XHRQ;
	}

	public String getXHRYXM()
	{
		return XHRYXM;
	}

	public void setXHRYXM(String XHRYXM)
	{
		this.XHRYXM = XHRYXM;
	}

	public String getXHKSSJ()
	{
		return XHKSSJ;
	}

	public void setXHKSSJ(String XHKSSJ)
	{
		this.XHKSSJ = XHKSSJ;
	}

	public String getXHJSSJ()
	{
		return XHJSSJ;
	}

	public void setXHJSSJ(String XHJSSJ)
	{
		this.XHJSSJ = XHJSSJ;
	}

	public String getXHLC()
	{
		return XHLC;
	}

	public void setXHLC(String XHLC)
	{
		this.XHLC = XHLC;
	}

	public String getXHXSS()
	{
		return XHXSS;
	}

	public void setXHXSS(String XHXSS)
	{
		this.XHXSS = XHXSS;
	}

	public String getXHFZS()
	{
		return XHFZS;
	}

	public void setXHFZS(String XHFZS)
	{
		this.XHFZS = XHFZS;
	}

	public String getXHZT()
	{
		return XHZT;
	}

	public void setXHZT(String XHZT)
	{
		this.XHZT = XHZT;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<BHQ_XHQK> CREATOR = new Creator()
	{
		@Override
		public BHQ_XHQK createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			BHQ_XHQK p = new BHQ_XHQK();
			p.setXHID(source.readString());
			p.setXHRY(source.readString());
			p.setXHRQ(source.readString());
			p.setXHRYXM(source.readString());
			p.setXHKSSJ(source.readString());
			p.setXHJSSJ(source.readString());
			p.setXHLC(source.readString());
			p.setXHXSS(source.readString());
			p.setXHFZS(source.readString());
			p.setXHZT(source.readString());
			p.setZTCS(source.readString());
			p.setIsUpload(source.readString());
			return p;
		}

		@Override
		public BHQ_XHQK[] newArray(int size)
		{
			return new BHQ_XHQK[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(XHID);
		p.writeString(XHRY);
		p.writeString(XHRQ);
		p.writeString(XHRYXM);
		p.writeString(XHKSSJ);
		p.writeString(XHJSSJ);
		p.writeString(XHLC);
		p.writeString(XHXSS);
		p.writeString(XHFZS);
		p.writeString(XHZT);
		p.writeString(ZTCS);
		p.writeString(IsUpload);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
