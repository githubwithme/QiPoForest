package com.bhq.bean;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: RK_LDRK 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0 时间 2015-3-3
 */
@Table(name = "RK_LDRK")
public class RK_LDRK implements Parcelable
{

	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
	// android.os.BadParcelableException:
	// Parcelable protocol requires a Parcelable.Creator object called CREATOR
	// on class com.um.demo.Person
	// 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
	// 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
	// 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
	// 5.反序列化对象
	public static final Parcelable.Creator<RK_LDRK> CREATOR = new Creator()
	{

		@Override
		public RK_LDRK createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			RK_LDRK p = new RK_LDRK();
			p.setLDRKID(source.readString());
			p.setFWID(source.readString());
			p.setXM(source.readString());
			p.setZJLX(source.readString());
			p.setZJH(source.readString());
			p.setCSRQ(source.readString());
			p.setLXDH(source.readString());
			p.setJG(source.readString());
			p.setXB(source.readString());
			p.setMZ(source.readString());
			p.setZJXY(source.readString());
			p.setJKZK(source.readString());
			p.setZZMM(source.readString());
			p.setWHCD(source.readString());
			p.setHYZK(source.readString());
			p.setZY(source.readString());
			p.setHJLX(source.readString());
			p.setHJDZ(source.readString());
			p.setXZDZ(source.readString());
			p.setLRLCZT(source.readString());
			p.setLRLCYY(source.readString());
			p.setFWCS(source.readString());
			p.setBZLX(source.readString());
			p.setZJHM(source.readString());
			p.setDJRQ(source.readString());
			p.setZJDQRQ(source.readString());
			p.setZSLX(source.readString());
			p.setSFZDGZRY(source.readInt() == 0);
			p.setZPDZ(source.readString());
			p.setBZ(source.readString());
			p.setCJSJ(source.readString());
			p.setCJR(source.readString());
			p.setCJRXM(source.readString());
			p.setXGSJ(source.readString());
			p.setXGR(source.readString());
			p.setXGRXM(source.readString());
			p.setHCSJ(source.readString());
			p.setHCR(source.readString());
			p.setHCRXM(source.readString());
			p.setHCZT(source.readString());
			p.setXXZT(source.readString());
			p.setJB(source.readString());
			p.setNL(source.readString());
			p.setLRLCSJ(source.readString());
			p.setSFDS(source.readInt() == 0);
			p.setSXZNSL(source.readString());
			return p;
		}

		@Override
		public RK_LDRK[] newArray(int size)
		{
			return new RK_LDRK[size];
		}
	};
	String ZPBDDZ;
	int id;

	private void setid()
	{
		// TODO Auto-generated method stub

	}

	private void getid()
	{
		// TODO Auto-generated method stub

	}

	public void setZPBDDZ(String zPBDDZ)
	{
		ZPBDDZ = zPBDDZ;
	}

	public String getZPBDDZ()
	{
		return ZPBDDZ;
	}

	Boolean Change;

	public void setChange(Boolean change)
	{
		Change = change;
	}

	public Boolean getChange()
	{
		return Change;
	}

	Drawable drawable;

	public void setDrawable(Drawable drawable)
	{
		this.drawable = drawable;
	}

	public Drawable getDrawable()
	{
		return drawable;
	}

	/** identifier field */

	@Id
	private String LDRKID;

	/** identifier field */

	private String FWID;

	/** identifier field */

	private String XM;

	/** identifier field */

	private String ZJLX;

	/** identifier field */

	private String ZJH;

	/** identifier field */

	private String CSRQ;

	/** identifier field */

	private String LXDH;

	/** identifier field */

	private String JG;

	/** identifier field */

	private String XB;

	/** identifier field */

	private String MZ;

	/** identifier field */

	private String ZJXY;

	/** identifier field */

	private String JKZK;

	/** identifier field */

	private String ZZMM;

	/** identifier field */

	private String WHCD;

	/** identifier field */

	private String HYZK;

	/** identifier field */

	private String ZY;

	/** identifier field */

	private String HJLX;

	/** identifier field */

	private String HJDZ;

	/** identifier field */

	private String XZDZ;

	/** identifier field */

	private String LRLCZT;

	/** identifier field */

	private String LRLCYY;

	/** identifier field */

	private String FWCS;

	/** identifier field */

	private String BZLX;

	/** identifier field */

	private String ZJHM;

	/** identifier field */

	private String DJRQ;

	/** identifier field */

	private String ZJDQRQ;

	/** identifier field */

	private String ZSLX;

	/** identifier field */

	private Boolean SFZDGZRY;

	/** identifier field */

	private String ZPDZ;

	/** identifier field */

	private String BZ;

	/** identifier field */

	private String CJSJ;

	/** identifier field */

	private String CJR;

	/** identifier field */

	private String CJRXM;

	/** identifier field */

	private String XGSJ;

	/** identifier field */

	private String XGR;

	/** identifier field */

	private String XGRXM;

	/** identifier field */

	private String HCSJ;

	/** identifier field */

	private String HCR;

	/** identifier field */

	private String HCRXM;

	/** identifier field */

	private String HCZT;

	/** identifier field */

	private String XXZT;

	/** identifier field */

	private String JB;

	/** identifier field */

	private String NL;

	/** identifier field */

	private String LRLCSJ;

	/** identifier field */

	private Boolean SFDS;

	/** identifier field */

	private String SXZNSL;

	/**
	 * @return 返回 LDRKID。
	 */
	public String getLDRKID()
	{
		return LDRKID;
	}

	/**
	 * @param LDRKID
	 *            要设置的 LDRKID。
	 */
	public void setLDRKID(String LDRKID)
	{
		this.LDRKID = LDRKID;
	}

	/**
	 * @return 返回 FWID。
	 */
	public String getFWID()
	{
		return FWID;
	}

	/**
	 * @param FWID
	 *            要设置的 FWID。
	 */
	public void setFWID(String FWID)
	{
		this.FWID = FWID;
	}

	/**
	 * @return 返回 XM。
	 */
	public String getXM()
	{
		return XM;
	}

	/**
	 * @param XM
	 *            要设置的 XM。
	 */
	public void setXM(String XM)
	{
		this.XM = XM;
	}

	/**
	 * @return 返回 ZJLX。
	 */
	public String getZJLX()
	{
		return ZJLX;
	}

	/**
	 * @param ZJLX
	 *            要设置的 ZJLX。
	 */
	public void setZJLX(String ZJLX)
	{
		this.ZJLX = ZJLX;
	}

	/**
	 * @return 返回 ZJH。
	 */
	public String getZJH()
	{
		return ZJH;
	}

	/**
	 * @param ZJH
	 *            要设置的 ZJH。
	 */
	public void setZJH(String ZJH)
	{
		this.ZJH = ZJH;
	}

	/**
	 * @return 返回 CSRQ。
	 */
	public String getCSRQ()
	{
		return CSRQ;
	}

	/**
	 * @param CSRQ
	 *            要设置的 CSRQ。
	 */
	public void setCSRQ(String CSRQ)
	{
		this.CSRQ = CSRQ;
	}

	/**
	 * @return 返回 LXDH。
	 */
	public String getLXDH()
	{
		return LXDH;
	}

	/**
	 * @param LXDH
	 *            要设置的 LXDH。
	 */
	public void setLXDH(String LXDH)
	{
		this.LXDH = LXDH;
	}

	/**
	 * @return 返回 JG。
	 */
	public String getJG()
	{
		return JG;
	}

	/**
	 * @param JG
	 *            要设置的 JG。
	 */
	public void setJG(String JG)
	{
		this.JG = JG;
	}

	/**
	 * @return 返回 XB。
	 */
	public String getXB()
	{
		return XB;
	}

	/**
	 * @param XB
	 *            要设置的 XB。
	 */
	public void setXB(String XB)
	{
		this.XB = XB;
	}

	/**
	 * @return 返回 MZ。
	 */
	public String getMZ()
	{
		return MZ;
	}

	/**
	 * @param MZ
	 *            要设置的 MZ。
	 */
	public void setMZ(String MZ)
	{
		this.MZ = MZ;
	}

	/**
	 * @return 返回 ZJXY。
	 */
	public String getZJXY()
	{
		return ZJXY;
	}

	/**
	 * @param ZJXY
	 *            要设置的 ZJXY。
	 */
	public void setZJXY(String ZJXY)
	{
		this.ZJXY = ZJXY;
	}

	/**
	 * @return 返回 JKZK。
	 */
	public String getJKZK()
	{
		return JKZK;
	}

	/**
	 * @param JKZK
	 *            要设置的 JKZK。
	 */
	public void setJKZK(String JKZK)
	{
		this.JKZK = JKZK;
	}

	/**
	 * @return 返回 ZZMM。
	 */
	public String getZZMM()
	{
		return ZZMM;
	}

	/**
	 * @param ZZMM
	 *            要设置的 ZZMM。
	 */
	public void setZZMM(String ZZMM)
	{
		this.ZZMM = ZZMM;
	}

	/**
	 * @return 返回 WHCD。
	 */
	public String getWHCD()
	{
		return WHCD;
	}

	/**
	 * @param WHCD
	 *            要设置的 WHCD。
	 */
	public void setWHCD(String WHCD)
	{
		this.WHCD = WHCD;
	}

	/**
	 * @return 返回 HYZK。
	 */
	public String getHYZK()
	{
		return HYZK;
	}

	/**
	 * @param HYZK
	 *            要设置的 HYZK。
	 */
	public void setHYZK(String HYZK)
	{
		this.HYZK = HYZK;
	}

	/**
	 * @return 返回 ZY。
	 */
	public String getZY()
	{
		return ZY;
	}

	/**
	 * @param ZY
	 *            要设置的 ZY。
	 */
	public void setZY(String ZY)
	{
		this.ZY = ZY;
	}

	/**
	 * @return 返回 HJLX。
	 */
	public String getHJLX()
	{
		return HJLX;
	}

	/**
	 * @param HJLX
	 *            要设置的 HJLX。
	 */
	public void setHJLX(String HJLX)
	{
		this.HJLX = HJLX;
	}

	/**
	 * @return 返回 HJDZ。
	 */
	public String getHJDZ()
	{
		return HJDZ;
	}

	/**
	 * @param HJDZ
	 *            要设置的 HJDZ。
	 */
	public void setHJDZ(String HJDZ)
	{
		this.HJDZ = HJDZ;
	}

	/**
	 * @return 返回 XZDZ。
	 */
	public String getXZDZ()
	{
		return XZDZ;
	}

	/**
	 * @param XZDZ
	 *            要设置的 XZDZ。
	 */
	public void setXZDZ(String XZDZ)
	{
		this.XZDZ = XZDZ;
	}

	/**
	 * @return 返回 LRLCZT。
	 */
	public String getLRLCZT()
	{
		return LRLCZT;
	}

	/**
	 * @param LRLCZT
	 *            要设置的 LRLCZT。
	 */
	public void setLRLCZT(String LRLCZT)
	{
		this.LRLCZT = LRLCZT;
	}

	/**
	 * @return 返回 LRLCYY。
	 */
	public String getLRLCYY()
	{
		return LRLCYY;
	}

	/**
	 * @param LRLCYY
	 *            要设置的 LRLCYY。
	 */
	public void setLRLCYY(String LRLCYY)
	{
		this.LRLCYY = LRLCYY;
	}

	/**
	 * @return 返回 FWCS。
	 */
	public String getFWCS()
	{
		return FWCS;
	}

	/**
	 * @param FWCS
	 *            要设置的 FWCS。
	 */
	public void setFWCS(String FWCS)
	{
		this.FWCS = FWCS;
	}

	/**
	 * @return 返回 BZLX。
	 */
	public String getBZLX()
	{
		return BZLX;
	}

	/**
	 * @param BZLX
	 *            要设置的 BZLX。
	 */
	public void setBZLX(String BZLX)
	{
		this.BZLX = BZLX;
	}

	/**
	 * @return 返回 ZJHM。
	 */
	public String getZJHM()
	{
		return ZJHM;
	}

	/**
	 * @param ZJHM
	 *            要设置的 ZJHM。
	 */
	public void setZJHM(String ZJHM)
	{
		this.ZJHM = ZJHM;
	}

	/**
	 * @return 返回 DJRQ。
	 */
	public String getDJRQ()
	{
		return DJRQ;
	}

	/**
	 * @param DJRQ
	 *            要设置的 DJRQ。
	 */
	public void setDJRQ(String DJRQ)
	{
		this.DJRQ = DJRQ;
	}

	/**
	 * @return 返回 ZJDQRQ。
	 */
	public String getZJDQRQ()
	{
		return ZJDQRQ;
	}

	/**
	 * @param ZJDQRQ
	 *            要设置的 ZJDQRQ。
	 */
	public void setZJDQRQ(String ZJDQRQ)
	{
		this.ZJDQRQ = ZJDQRQ;
	}

	/**
	 * @return 返回 ZSLX。
	 */
	public String getZSLX()
	{
		return ZSLX;
	}

	/**
	 * @param ZSLX
	 *            要设置的 ZSLX。
	 */
	public void setZSLX(String ZSLX)
	{
		this.ZSLX = ZSLX;
	}

	public void setSFZDGZRY(Boolean sFZDGZRY)
	{
		SFZDGZRY = sFZDGZRY;
	}

	public Boolean getSFZDGZRY()
	{
		return SFZDGZRY;
	}

	/**
	 * @return 返回 ZPDZ。
	 */
	public String getZPDZ()
	{
		return ZPDZ;
	}

	/**
	 * @param ZPDZ
	 *            要设置的 ZPDZ。
	 */
	public void setZPDZ(String ZPDZ)
	{
		this.ZPDZ = ZPDZ;
	}

	/**
	 * @return 返回 BZ。
	 */
	public String getBZ()
	{
		return BZ;
	}

	/**
	 * @param BZ
	 *            要设置的 BZ。
	 */
	public void setBZ(String BZ)
	{
		this.BZ = BZ;
	}

	/**
	 * @return 返回 CJSJ。
	 */
	public String getCJSJ()
	{
		return CJSJ;
	}

	/**
	 * @param CJSJ
	 *            要设置的 CJSJ。
	 */
	public void setCJSJ(String CJSJ)
	{
		this.CJSJ = CJSJ;
	}

	/**
	 * @return 返回 CJR。
	 */
	public String getCJR()
	{
		return CJR;
	}

	/**
	 * @param CJR
	 *            要设置的 CJR。
	 */
	public void setCJR(String CJR)
	{
		this.CJR = CJR;
	}

	/**
	 * @return 返回 CJRXM。
	 */
	public String getCJRXM()
	{
		return CJRXM;
	}

	/**
	 * @param CJRXM
	 *            要设置的 CJRXM。
	 */
	public void setCJRXM(String CJRXM)
	{
		this.CJRXM = CJRXM;
	}

	/**
	 * @return 返回 XGSJ。
	 */
	public String getXGSJ()
	{
		return XGSJ;
	}

	/**
	 * @param XGSJ
	 *            要设置的 XGSJ。
	 */
	public void setXGSJ(String XGSJ)
	{
		this.XGSJ = XGSJ;
	}

	/**
	 * @return 返回 XGR。
	 */
	public String getXGR()
	{
		return XGR;
	}

	/**
	 * @param XGR
	 *            要设置的 XGR。
	 */
	public void setXGR(String XGR)
	{
		this.XGR = XGR;
	}

	/**
	 * @return 返回 XGRXM。
	 */
	public String getXGRXM()
	{
		return XGRXM;
	}

	/**
	 * @param XGRXM
	 *            要设置的 XGRXM。
	 */
	public void setXGRXM(String XGRXM)
	{
		this.XGRXM = XGRXM;
	}

	/**
	 * @return 返回 HCSJ。
	 */
	public String getHCSJ()
	{
		return HCSJ;
	}

	/**
	 * @param HCSJ
	 *            要设置的 HCSJ。
	 */
	public void setHCSJ(String HCSJ)
	{
		this.HCSJ = HCSJ;
	}

	/**
	 * @return 返回 HCR。
	 */
	public String getHCR()
	{
		return HCR;
	}

	/**
	 * @param HCR
	 *            要设置的 HCR。
	 */
	public void setHCR(String HCR)
	{
		this.HCR = HCR;
	}

	/**
	 * @return 返回 HCRXM。
	 */
	public String getHCRXM()
	{
		return HCRXM;
	}

	/**
	 * @param HCRXM
	 *            要设置的 HCRXM。
	 */
	public void setHCRXM(String HCRXM)
	{
		this.HCRXM = HCRXM;
	}

	/**
	 * @return 返回 HCZT。
	 */
	public String getHCZT()
	{
		return HCZT;
	}

	/**
	 * @param HCZT
	 *            要设置的 HCZT。
	 */
	public void setHCZT(String HCZT)
	{
		this.HCZT = HCZT;
	}

	/**
	 * @return 返回 XXZT。
	 */
	public String getXXZT()
	{
		return XXZT;
	}

	/**
	 * @param XXZT
	 *            要设置的 XXZT。
	 */
	public void setXXZT(String XXZT)
	{
		this.XXZT = XXZT;
	}

	/**
	 * @return 返回 JB。
	 */
	public String getJB()
	{
		return JB;
	}

	/**
	 * @param JB
	 *            要设置的 JB。
	 */
	public void setJB(String JB)
	{
		this.JB = JB;
	}

	/**
	 * @return 返回 NL。
	 */
	public String getNL()
	{
		return NL;
	}

	/**
	 * @param NL
	 *            要设置的 NL。
	 */
	public void setNL(String NL)
	{
		this.NL = NL;
	}

	/**
	 * @return 返回 LRLCSJ。
	 */
	public String getLRLCSJ()
	{
		return LRLCSJ;
	}

	/**
	 * @param LRLCSJ
	 *            要设置的 LRLCSJ。
	 */
	public void setLRLCSJ(String LRLCSJ)
	{
		this.LRLCSJ = LRLCSJ;
	}

	public void setSFDS(Boolean sFDS)
	{
		SFDS = sFDS;
	}

	public Boolean getSFDS()
	{
		return SFDS;
	}

	/**
	 * @return 返回 SXZNSL。
	 */
	public String getSXZNSL()
	{
		return SXZNSL;
	}

	/**
	 * @param SXZNSL
	 *            要设置的 SXZNSL。
	 */
	public void setSXZNSL(String SXZNSL)
	{
		this.SXZNSL = SXZNSL;
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
