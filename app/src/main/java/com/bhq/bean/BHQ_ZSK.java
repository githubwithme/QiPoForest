package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: BHQ_ZSK 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "BHQ_ZSK")
public class BHQ_ZSK implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String ZSID;
	public String ZSBT;
	public String ZSDL;
	public String ZSXL;
	public String imgurl;
	public String ZSZY;
	public String ZSNR;
	public String CJR;
	public String CJSJ;
	public String CJRXM;
	public String XGR;
	public String XGSJ;
	public String XGRXM;
	public String XXZT;
	public String BDLJ;

	public void setBDLJ(String bDLJ)
	{
		BDLJ = bDLJ;
	}

	public String getBDLJ()
	{
		return BDLJ;
	}

	public String getZSID()
	{
		return ZSID;
	}

	public void setZSID(String ZSID)
	{
		this.ZSID = ZSID;
	}

	public String getZSBT()
	{
		return ZSBT;
	}

	public void setZSBT(String ZSBT)
	{
		this.ZSBT = ZSBT;
	}

	public String getZSDL()
	{
		return ZSDL;
	}

	public void setZSDL(String ZSDL)
	{
		this.ZSDL = ZSDL;
	}

	public String getZSXL()
	{
		return ZSXL;
	}

	public void setZSXL(String ZSXL)
	{
		this.ZSXL = ZSXL;
	}

	public String getimgurl()
	{
		return imgurl;
	}

	public void setimgurl(String imgurl)
	{
		this.imgurl = imgurl;
	}

	public String getZSZY()
	{
		return ZSZY;
	}

	public void setZSZY(String ZSZY)
	{
		this.ZSZY = ZSZY;
	}

	public String getZSNR()
	{
		return ZSNR;
	}

	public void setZSNR(String ZSNR)
	{
		this.ZSNR = ZSNR;
	}

	public String getCJR()
	{
		return CJR;
	}

	public void setCJR(String CJR)
	{
		this.CJR = CJR;
	}

	public String getCJSJ()
	{
		return CJSJ;
	}

	public void setCJSJ(String CJSJ)
	{
		this.CJSJ = CJSJ;
	}

	public String getCJRXM()
	{
		return CJRXM;
	}

	public void setCJRXM(String CJRXM)
	{
		this.CJRXM = CJRXM;
	}

	public String getXGR()
	{
		return XGR;
	}

	public void setXGR(String XGR)
	{
		this.XGR = XGR;
	}

	public String getXGSJ()
	{
		return XGSJ;
	}

	public void setXGSJ(String XGSJ)
	{
		this.XGSJ = XGSJ;
	}

	public String getXGRXM()
	{
		return XGRXM;
	}

	public void setXGRXM(String XGRXM)
	{
		this.XGRXM = XGRXM;
	}

	public String getXXZT()
	{
		return XXZT;
	}

	public void setXXZT(String XXZT)
	{
		this.XXZT = XXZT;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<BHQ_ZSK> CREATOR = new Creator()
	{
		@Override
		public BHQ_ZSK createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			BHQ_ZSK p = new BHQ_ZSK();
			p.setZSID(source.readString());
			p.setZSBT(source.readString());
			p.setZSDL(source.readString());
			p.setZSXL(source.readString());
			p.setimgurl(source.readString());
			p.setZSZY(source.readString());
			p.setZSNR(source.readString());
			p.setCJR(source.readString());
			p.setCJSJ(source.readString());
			p.setCJRXM(source.readString());
			p.setXGR(source.readString());
			p.setXGSJ(source.readString());
			p.setXGRXM(source.readString());
			p.setXXZT(source.readString());
			p.setBDLJ(source.readString());
			return p;
		}

		@Override
		public BHQ_ZSK[] newArray(int size)
		{
			return new BHQ_ZSK[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(ZSID);
		p.writeString(ZSBT);
		p.writeString(ZSDL);
		p.writeString(ZSXL);
		p.writeString(imgurl);
		p.writeString(ZSZY);
		p.writeString(ZSNR);
		p.writeString(CJR);
		p.writeString(CJSJ);
		p.writeString(CJRXM);
		p.writeString(XGR);
		p.writeString(XGSJ);
		p.writeString(XGRXM);
		p.writeString(XXZT);
		p.writeString(BDLJ);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
