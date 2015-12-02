package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: FJ_SCFJ 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "FJ_SCFJ")
public class FJ_SCFJ implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String FJID;
	public String GLID;
	public String GLBM;
	public String FJMC;
	public String LSTLJ;
	public String FJLJ;
	public String SCSJ;
	public String SCR;
	public String SCRXM;
	public String FJLX;
	public String BZ;
	public String SFSC;
	public String SCZT;
	public String Change;
	public String Sort;
	public String FL;
	public String FJBDLJ;
	public String ISUPLOAD;

	public void setISUPLOAD(String iSUPLOAD)
	{
		ISUPLOAD = iSUPLOAD;
	}

	public String getISUPLOAD()
	{
		return ISUPLOAD;
	}

	public void setFJBDLJ(String fJBDLJ)
	{
		FJBDLJ = fJBDLJ;
	}

	public String getFJBDLJ()
	{
		return FJBDLJ;
	}

	public String getFJID()
	{
		return FJID;
	}

	public void setFJID(String FJID)
	{
		this.FJID = FJID;
	}

	public String getGLID()
	{
		return GLID;
	}

	public void setGLID(String GLID)
	{
		this.GLID = GLID;
	}

	public String getGLBM()
	{
		return GLBM;
	}

	public void setGLBM(String GLBM)
	{
		this.GLBM = GLBM;
	}

	public String getFJMC()
	{
		return FJMC;
	}

	public void setFJMC(String FJMC)
	{
		this.FJMC = FJMC;
	}

	public String getLSTLJ()
	{
		return LSTLJ;
	}

	public void setLSTLJ(String LSTLJ)
	{
		this.LSTLJ = LSTLJ;
	}

	public String getFJLJ()
	{
		return FJLJ;
	}

	public void setFJLJ(String FJLJ)
	{
		this.FJLJ = FJLJ;
	}

	public String getSCSJ()
	{
		return SCSJ;
	}

	public void setSCSJ(String SCSJ)
	{
		this.SCSJ = SCSJ;
	}

	public String getSCR()
	{
		return SCR;
	}

	public void setSCR(String SCR)
	{
		this.SCR = SCR;
	}

	public String getSCRXM()
	{
		return SCRXM;
	}

	public void setSCRXM(String SCRXM)
	{
		this.SCRXM = SCRXM;
	}

	public String getFJLX()
	{
		return FJLX;
	}

	public void setFJLX(String FJLX)
	{
		this.FJLX = FJLX;
	}

	public String getBZ()
	{
		return BZ;
	}

	public void setBZ(String BZ)
	{
		this.BZ = BZ;
	}

	public String getSFSC()
	{
		return SFSC;
	}

	public void setSFSC(String SFSC)
	{
		this.SFSC = SFSC;
	}

	public String getSCZT()
	{
		return SCZT;
	}

	public void setSCZT(String SCZT)
	{
		this.SCZT = SCZT;
	}

	public String getChange()
	{
		return Change;
	}

	public void setChange(String Change)
	{
		this.Change = Change;
	}

	public String getSort()
	{
		return Sort;
	}

	public void setSort(String Sort)
	{
		this.Sort = Sort;
	}

	public String getFL()
	{
		return FL;
	}

	public void setFL(String FL)
	{
		this.FL = FL;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<FJ_SCFJ> CREATOR = new Creator()
	{
		@Override
		public FJ_SCFJ createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			FJ_SCFJ p = new FJ_SCFJ();
			p.setFJID(source.readString());
			p.setGLID(source.readString());
			p.setGLBM(source.readString());
			p.setFJMC(source.readString());
			p.setLSTLJ(source.readString());
			p.setFJLJ(source.readString());
			p.setSCSJ(source.readString());
			p.setSCR(source.readString());
			p.setSCRXM(source.readString());
			p.setFJLX(source.readString());
			p.setBZ(source.readString());
			p.setSFSC(source.readString());
			p.setSCZT(source.readString());
			p.setChange(source.readString());
			p.setSort(source.readString());
			p.setFL(source.readString());
			p.setFJBDLJ(source.readString());
			p.setISUPLOAD(source.readString());
			return p;
		}

		@Override
		public FJ_SCFJ[] newArray(int size)
		{
			return new FJ_SCFJ[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(FJID);
		p.writeString(GLID);
		p.writeString(GLBM);
		p.writeString(FJMC);
		p.writeString(LSTLJ);
		p.writeString(FJLJ);
		p.writeString(SCSJ);
		p.writeString(SCR);
		p.writeString(SCRXM);
		p.writeString(FJLX);
		p.writeString(BZ);
		p.writeString(SFSC);
		p.writeString(SCZT);
		p.writeString(Change);
		p.writeString(Sort);
		p.writeString(FL);
		p.writeString(FJBDLJ);
		p.writeString(ISUPLOAD);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
