package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: RW_RW 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "RW_RW")
public class RW_RW implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String RWID;
	public String RWMC;
	public String WRKSSJ;
	public String WRJZSJ;
	public String WRTXSJ;
	public String ZYD;
	public String ZRR;
	public String ZCRXM;
	public String RWMS;
	public String HYSFQX;
	public String HYQXSJ;
	public String QXR;
	public String QXRXM;
	public String RWSFJS;
	public String RWJSSJ;
	public String CJSJ;
	public String CJR;
	public String CJRXM;
	public String XGSJ;
	public String XGR;
	public String XGRXM;
	public String SFSC;
	public String Change;
	public String IsUpload;

	public void setIsUpload(String isUpload)
	{
		IsUpload = isUpload;
	}

	public String getIsUpload()
	{
		return IsUpload;
	}

	public String getRWID()
	{
		return RWID;
	}

	public void setRWID(String RWID)
	{
		this.RWID = RWID;
	}

	public String getRWMC()
	{
		return RWMC;
	}

	public void setRWMC(String RWMC)
	{
		this.RWMC = RWMC;
	}

	public String getWRKSSJ()
	{
		return WRKSSJ;
	}

	public void setWRKSSJ(String WRKSSJ)
	{
		this.WRKSSJ = WRKSSJ;
	}

	public String getWRJZSJ()
	{
		return WRJZSJ;
	}

	public void setWRJZSJ(String WRJZSJ)
	{
		this.WRJZSJ = WRJZSJ;
	}

	public String getWRTXSJ()
	{
		return WRTXSJ;
	}

	public void setWRTXSJ(String WRTXSJ)
	{
		this.WRTXSJ = WRTXSJ;
	}

	public String getZYD()
	{
		return ZYD;
	}

	public void setZYD(String ZYD)
	{
		this.ZYD = ZYD;
	}

	public String getZRR()
	{
		return ZRR;
	}

	public void setZRR(String ZRR)
	{
		this.ZRR = ZRR;
	}

	public String getZCRXM()
	{
		return ZCRXM;
	}

	public void setZCRXM(String ZCRXM)
	{
		this.ZCRXM = ZCRXM;
	}

	public String getRWMS()
	{
		return RWMS;
	}

	public void setRWMS(String RWMS)
	{
		this.RWMS = RWMS;
	}

	public String getHYSFQX()
	{
		return HYSFQX;
	}

	public void setHYSFQX(String HYSFQX)
	{
		this.HYSFQX = HYSFQX;
	}

	public String getHYQXSJ()
	{
		return HYQXSJ;
	}

	public void setHYQXSJ(String HYQXSJ)
	{
		this.HYQXSJ = HYQXSJ;
	}

	public String getQXR()
	{
		return QXR;
	}

	public void setQXR(String QXR)
	{
		this.QXR = QXR;
	}

	public String getQXRXM()
	{
		return QXRXM;
	}

	public void setQXRXM(String QXRXM)
	{
		this.QXRXM = QXRXM;
	}

	public String getRWSFJS()
	{
		return RWSFJS;
	}

	public void setRWSFJS(String RWSFJS)
	{
		this.RWSFJS = RWSFJS;
	}

	public String getRWJSSJ()
	{
		return RWJSSJ;
	}

	public void setRWJSSJ(String RWJSSJ)
	{
		this.RWJSSJ = RWJSSJ;
	}

	public String getCJSJ()
	{
		return CJSJ;
	}

	public void setCJSJ(String CJSJ)
	{
		this.CJSJ = CJSJ;
	}

	public String getCJR()
	{
		return CJR;
	}

	public void setCJR(String CJR)
	{
		this.CJR = CJR;
	}

	public String getCJRXM()
	{
		return CJRXM;
	}

	public void setCJRXM(String CJRXM)
	{
		this.CJRXM = CJRXM;
	}

	public String getXGSJ()
	{
		return XGSJ;
	}

	public void setXGSJ(String XGSJ)
	{
		this.XGSJ = XGSJ;
	}

	public String getXGR()
	{
		return XGR;
	}

	public void setXGR(String XGR)
	{
		this.XGR = XGR;
	}

	public String getXGRXM()
	{
		return XGRXM;
	}

	public void setXGRXM(String XGRXM)
	{
		this.XGRXM = XGRXM;
	}

	public String getSFSC()
	{
		return SFSC;
	}

	public void setSFSC(String SFSC)
	{
		this.SFSC = SFSC;
	}

	public String getChange()
	{
		return Change;
	}

	public void setChange(String Change)
	{
		this.Change = Change;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<RW_RW> CREATOR = new Creator()
	{
		@Override
		public RW_RW createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			RW_RW p = new RW_RW();
			p.setRWID(source.readString());
			p.setRWMC(source.readString());
			p.setWRKSSJ(source.readString());
			p.setWRJZSJ(source.readString());
			p.setWRTXSJ(source.readString());
			p.setZYD(source.readString());
			p.setZRR(source.readString());
			p.setZCRXM(source.readString());
			p.setRWMS(source.readString());
			p.setHYSFQX(source.readString());
			p.setHYQXSJ(source.readString());
			p.setQXR(source.readString());
			p.setQXRXM(source.readString());
			p.setRWSFJS(source.readString());
			p.setRWJSSJ(source.readString());
			p.setCJSJ(source.readString());
			p.setCJR(source.readString());
			p.setCJRXM(source.readString());
			p.setXGSJ(source.readString());
			p.setXGR(source.readString());
			p.setXGRXM(source.readString());
			p.setSFSC(source.readString());
			p.setChange(source.readString());
			p.setIsUpload(source.readString());
			return p;
		}

		@Override
		public RW_RW[] newArray(int size)
		{
			return new RW_RW[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(RWID);
		p.writeString(RWMC);
		p.writeString(WRKSSJ);
		p.writeString(WRJZSJ);
		p.writeString(WRTXSJ);
		p.writeString(ZYD);
		p.writeString(ZRR);
		p.writeString(ZCRXM);
		p.writeString(RWMS);
		p.writeString(HYSFQX);
		p.writeString(HYQXSJ);
		p.writeString(QXR);
		p.writeString(QXRXM);
		p.writeString(RWSFJS);
		p.writeString(RWJSSJ);
		p.writeString(CJSJ);
		p.writeString(CJR);
		p.writeString(CJRXM);
		p.writeString(XGSJ);
		p.writeString(XGR);
		p.writeString(XGRXM);
		p.writeString(SFSC);
		p.writeString(Change);
		p.writeString(IsUpload);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
