package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: RW_CYR 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0 时间 2015-3-3
 */
@Table(name = "RW_CYR")
public class RW_CYR implements Parcelable
{
	public static final Parcelable.Creator<RW_CYR> CREATOR = new Creator()
	{
		@Override
		public RW_CYR createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			RW_CYR p = new RW_CYR();
			p.setId(source.readString());
			p.setRWCYID(source.readString());
			p.setRWID(source.readString());
			p.setRYID(source.readString());
			p.setRYXM(source.readString());
			p.setSFWC(source.readString());
			p.setWCSJ(source.readString());
			p.setSFYSQYQ(source.readString());
			return p;
		}

		@Override
		public RW_CYR[] newArray(int size)
		{
			return new RW_CYR[size];
		}
	};

	String XGSJ;
	String id;
	String Change;
	String SFSC;

	public void setXGSJ(String xGSJ)
	{
		XGSJ = xGSJ;
	}

	public String getXGSJ()
	{
		return XGSJ;
	}

	public void setSFSC(String sFSC)
	{
		SFSC = sFSC;
	}

	public String getSFSC()
	{
		return SFSC;
	}

	public void setChange(String change)
	{
		Change = change;
	}

	public String getChange()
	{
		return Change;
	}

	/** identifier field */

	@Id
	@NoAutoIncrement
	private String RWCYID;

	/** identifier field */

	private String RWID;

	/** identifier field */

	private String RYID;

	/** identifier field */

	private String RYXM;

	/** identifier field */

	private String SFWC;

	/** identifier field */

	private String WCSJ;

	/** identifier field */

	private String SFYSQYQ;


	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	/**
	 * @return 返回 标识ID
	 */
	public String getRWCYID()
	{
		return RWCYID;
	}

	/**
	 * @param RWCYID
	 *            要设置的 标识ID
	 */
	public void setRWCYID(String RWCYID)
	{
		this.RWCYID = RWCYID;
	}

	/**
	 * @return 返回 任务ID
	 */
	public String getRWID()
	{
		return RWID;
	}

	/**
	 * @param RWID
	 *            要设置的 任务ID
	 */
	public void setRWID(String RWID)
	{
		this.RWID = RWID;
	}

	/**
	 * @return 返回 任务参与人ID
	 */
	public String getRYID()
	{
		return RYID;
	}

	/**
	 * @param RYID
	 *            要设置的 任务参与人ID
	 */
	public void setRYID(String RYID)
	{
		this.RYID = RYID;
	}

	/**
	 * @return 返回 参与人姓名
	 */
	public String getRYXM()
	{
		return RYXM;
	}

	/**
	 * @param RYXM
	 *            要设置的 参与人姓名
	 */
	public void setRYXM(String RYXM)
	{
		this.RYXM = RYXM;
	}

	public void setSFWC(String sFWC)
	{
		SFWC = sFWC;
	}

	public String getSFWC()
	{
		return SFWC;
	}

	/**
	 * @return 返回 完成时间
	 */
	public String getWCSJ()
	{
		return WCSJ;
	}

	/**
	 * @param WCSJ
	 *            要设置的 完成时间
	 */
	public void setWCSJ(String WCSJ)
	{
		this.WCSJ = WCSJ;
	}

	public void setSFYSQYQ(String sFYSQYQ)
	{
		SFYSQYQ = sFYSQYQ;
	}

	public String getSFYSQYQ()
	{
		return SFYSQYQ;
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
