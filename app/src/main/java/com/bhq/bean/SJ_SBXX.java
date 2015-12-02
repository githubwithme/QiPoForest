package com.bhq.bean;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: SJ_SBXX 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0 时间 2015-3-3
 */
@Table(name = "SJ_SBXX")
public class SJ_SBXX extends Entity
{

	int id;
	Boolean Change;

	public void setChange(Boolean change)
	{
		Change = change;
	}

	public Boolean getChange()
	{
		return Change;
	}

	/** identifier field */

	@Id
	private String SJID;

	/** identifier field */

	private String BH;

	/** identifier field */

	private String BT;

	/** identifier field */

	private String DSRXX;

	/** identifier field */

	private String SJRS;

	/** identifier field */

	private String FSDZ;

	/** identifier field */

	private String SJMS;

	/** identifier field */

	private String SJFSSJ;

	/** identifier field */

	private String JLR;

	/** identifier field */

	private String JLRXM;

	/** identifier field */

	private String JLSJ;

	/** identifier field */

	private String SJLCZT;

	/** identifier field */

	private String XGSJ;

	/** identifier field */

	private String LDID;

	/** identifier field */

	private String SSWG;

	/** identifier field */

	private String DL;

	/** identifier field */

	private String XL;

	/** identifier field */

	private String CZBMJB;

	/** identifier field */

	private String SJLX;

	/** identifier field */

	private String SJCZJB;

	/** identifier field */

	private String CZSJKS;

	/** identifier field */

	private String CZSJJS;

	/** identifier field */

	private String CZSJKG;

	/** identifier field */

	private String SJFFR;

	/** identifier field */

	private String SJFFRXM;

	/** identifier field */

	private String SJFFSJ;

	/** identifier field */

	private String CZBM;

	/** identifier field */

	private String SJLA;

	/** identifier field */

	private String SJGD;

	/** identifier field */

	private String SJFK;

	/** identifier field */

	private String SJGDR;

	/** identifier field */

	private String SJGDRXM;

	/** identifier field */

	private String SJGDSJ;

	/** identifier field */

	private String CZJX;

	/** identifier field */

	private String X;

	/** identifier field */

	private String Y;

	/** identifier field */

	private String SJLY;

	/** identifier field */

	private String SSSQ;

	/** identifier field */

	private String SJGDSM;

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	/**
	 * @return 返回 事件ID
	 */
	public String getSJID()
	{
		return SJID;
	}

	/**
	 * @param SJID
	 *            要设置的 事件ID
	 */
	public void setSJID(String SJID)
	{
		this.SJID = SJID;
	}

	/**
	 * @return 返回 编号
	 */
	public String getBH()
	{
		return BH;
	}

	/**
	 * @param BH
	 *            要设置的 编号
	 */
	public void setBH(String BH)
	{
		this.BH = BH;
	}

	/**
	 * @return 返回 标题
	 */
	public String getBT()
	{
		return BT;
	}

	/**
	 * @param BT
	 *            要设置的 标题
	 */
	public void setBT(String BT)
	{
		this.BT = BT;
	}

	/**
	 * @return 返回 当事人信息
	 */
	public String getDSRXX()
	{
		return DSRXX;
	}

	/**
	 * @param DSRXX
	 *            要设置的 当事人信息
	 */
	public void setDSRXX(String DSRXX)
	{
		this.DSRXX = DSRXX;
	}

	/**
	 * @return 返回 涉及人数
	 */
	public String getSJRS()
	{
		return SJRS;
	}

	/**
	 * @param SJRS
	 *            要设置的 涉及人数
	 */
	public void setSJRS(String SJRS)
	{
		this.SJRS = SJRS;
	}

	/**
	 * @return 返回 发生地
	 */
	public String getFSDZ()
	{
		return FSDZ;
	}

	/**
	 * @param FSDZ
	 *            要设置的 发生地
	 */
	public void setFSDZ(String FSDZ)
	{
		this.FSDZ = FSDZ;
	}

	/**
	 * @return 返回 事件描述
	 */
	public String getSJMS()
	{
		return SJMS;
	}

	/**
	 * @param SJMS
	 *            要设置的 事件描述
	 */
	public void setSJMS(String SJMS)
	{
		this.SJMS = SJMS;
	}

	/**
	 * @return 返回 事件发生时间
	 */
	public String getSJFSSJ()
	{
		return SJFSSJ;
	}

	/**
	 * @param SJFSSJ
	 *            要设置的 事件发生时间
	 */
	public void setSJFSSJ(String SJFSSJ)
	{
		this.SJFSSJ = SJFSSJ;
	}

	/**
	 * @return 返回 记录人
	 */
	public String getJLR()
	{
		return JLR;
	}

	/**
	 * @param JLR
	 *            要设置的 记录人
	 */
	public void setJLR(String JLR)
	{
		this.JLR = JLR;
	}

	/**
	 * @return 返回 记录人姓名
	 */
	public String getJLRXM()
	{
		return JLRXM;
	}

	/**
	 * @param JLRXM
	 *            要设置的 记录人姓名
	 */
	public void setJLRXM(String JLRXM)
	{
		this.JLRXM = JLRXM;
	}

	/**
	 * @return 返回 记录时间
	 */
	public String getJLSJ()
	{
		return JLSJ;
	}

	/**
	 * @param JLSJ
	 *            要设置的 记录时间
	 */
	public void setJLSJ(String JLSJ)
	{
		this.JLSJ = JLSJ;
	}

	/**
	 * @return 返回
	 *         事件流程状态（0为新增,1为网格长上报，2为网格长退回处理，3为网格管理中心分发，4网格中心不能处理向区上报，5为网格管理中心退回
	 *         ，6为处理完成，7为一级网格退回）
	 */
	public String getSJLCZT()
	{
		return SJLCZT;
	}

	/**
	 * @param SJLCZT
	 *            要设置的 事件流程状态（0为新增,1为网格长上报，2为网格长退回处理，3为网格管理中心分发，4网格中心不能处理向区上报，5
	 *            为网格管理中心退回，6为处理完成，7为一级网格退回）
	 */
	public void setSJLCZT(String SJLCZT)
	{
		this.SJLCZT = SJLCZT;
	}

	/**
	 * @return 返回 更新时间
	 */
	public String getXGSJ()
	{
		return XGSJ;
	}

	/**
	 * @param XGSJ
	 *            要设置的 更新时间
	 */
	public void setXGSJ(String XGSJ)
	{
		this.XGSJ = XGSJ;
	}

	/**
	 * @return 返回 楼栋信息
	 */
	public String getLDID()
	{
		return LDID;
	}

	/**
	 * @param LDID
	 *            要设置的 楼栋信息
	 */
	public void setLDID(String LDID)
	{
		this.LDID = LDID;
	}

	/**
	 * @return 返回 所属网格
	 */
	public String getSSWG()
	{
		return SSWG;
	}

	/**
	 * @param SSWG
	 *            要设置的 所属网格
	 */
	public void setSSWG(String SSWG)
	{
		this.SSWG = SSWG;
	}

	/**
	 * @return 返回 大类
	 */
	public String getDL()
	{
		return DL;
	}

	/**
	 * @param DL
	 *            要设置的 大类
	 */
	public void setDL(String DL)
	{
		this.DL = DL;
	}

	/**
	 * @return 返回 小类
	 */
	public String getXL()
	{
		return XL;
	}

	/**
	 * @param XL
	 *            要设置的 小类
	 */
	public void setXL(String XL)
	{
		this.XL = XL;
	}

	/**
	 * @return 返回 处置部门级别
	 */
	public String getCZBMJB()
	{
		return CZBMJB;
	}

	/**
	 * @param CZBMJB
	 *            要设置的 处置部门级别
	 */
	public void setCZBMJB(String CZBMJB)
	{
		this.CZBMJB = CZBMJB;
	}

	/**
	 * @return 返回 事件类型
	 */
	public String getSJLX()
	{
		return SJLX;
	}

	/**
	 * @param SJLX
	 *            要设置的 事件类型
	 */
	public void setSJLX(String SJLX)
	{
		this.SJLX = SJLX;
	}

	/**
	 * @return 返回 事件处置级别
	 */
	public String getSJCZJB()
	{
		return SJCZJB;
	}

	/**
	 * @param SJCZJB
	 *            要设置的 事件处置级别
	 */
	public void setSJCZJB(String SJCZJB)
	{
		this.SJCZJB = SJCZJB;
	}

	/**
	 * @return 返回 处置时限开始时间
	 */
	public String getCZSJKS()
	{
		return CZSJKS;
	}

	/**
	 * @param CZSJKS
	 *            要设置的 处置时限开始时间
	 */
	public void setCZSJKS(String CZSJKS)
	{
		this.CZSJKS = CZSJKS;
	}

	/**
	 * @return 返回 处置时限结束时间
	 */
	public String getCZSJJS()
	{
		return CZSJJS;
	}

	/**
	 * @param CZSJJS
	 *            要设置的 处置时限结束时间
	 */
	public void setCZSJJS(String CZSJJS)
	{
		this.CZSJJS = CZSJJS;
	}

	/**
	 * @return 返回 处置时限开关(0关1开)
	 */
	public String getCZSJKG()
	{
		return CZSJKG;
	}

	/**
	 * @param CZSJKG
	 *            要设置的 处置时限开关(0关1开)
	 */
	public void setCZSJKG(String CZSJKG)
	{
		this.CZSJKG = CZSJKG;
	}

	/**
	 * @return 返回 事件分发人
	 */
	public String getSJFFR()
	{
		return SJFFR;
	}

	/**
	 * @param SJFFR
	 *            要设置的 事件分发人
	 */
	public void setSJFFR(String SJFFR)
	{
		this.SJFFR = SJFFR;
	}

	/**
	 * @return 返回 事件分发人姓名
	 */
	public String getSJFFRXM()
	{
		return SJFFRXM;
	}

	/**
	 * @param SJFFRXM
	 *            要设置的 事件分发人姓名
	 */
	public void setSJFFRXM(String SJFFRXM)
	{
		this.SJFFRXM = SJFFRXM;
	}

	/**
	 * @return 返回 事件分发时间
	 */
	public String getSJFFSJ()
	{
		return SJFFSJ;
	}

	/**
	 * @param SJFFSJ
	 *            要设置的 事件分发时间
	 */
	public void setSJFFSJ(String SJFFSJ)
	{
		this.SJFFSJ = SJFFSJ;
	}

	/**
	 * @return 返回 处置部门
	 */
	public String getCZBM()
	{
		return CZBM;
	}

	/**
	 * @param CZBM
	 *            要设置的 处置部门
	 */
	public void setCZBM(String CZBM)
	{
		this.CZBM = CZBM;
	}

	/**
	 * @return 返回 是否立案(0否1是)
	 */
	public String getSJLA()
	{
		return SJLA;
	}

	/**
	 * @param SJLA
	 *            要设置的 是否立案(0否1是)
	 */
	public void setSJLA(String SJLA)
	{
		this.SJLA = SJLA;
	}

	/**
	 * @return 返回 是否归档(0否1是)
	 */
	public String getSJGD()
	{
		return SJGD;
	}

	/**
	 * @param SJGD
	 *            要设置的 是否归档(0否1是)
	 */
	public void setSJGD(String SJGD)
	{
		this.SJGD = SJGD;
	}

	/**
	 * @return 返回 是否反馈(0否1是)
	 */
	public String getSJFK()
	{
		return SJFK;
	}

	/**
	 * @param SJFK
	 *            要设置的 是否反馈(0否1是)
	 */
	public void setSJFK(String SJFK)
	{
		this.SJFK = SJFK;
	}

	/**
	 * @return 返回 事件归档人
	 */
	public String getSJGDR()
	{
		return SJGDR;
	}

	/**
	 * @param SJGDR
	 *            要设置的 事件归档人
	 */
	public void setSJGDR(String SJGDR)
	{
		this.SJGDR = SJGDR;
	}

	/**
	 * @return 返回 事件归档人姓名
	 */
	public String getSJGDRXM()
	{
		return SJGDRXM;
	}

	/**
	 * @param SJGDRXM
	 *            要设置的 事件归档人姓名
	 */
	public void setSJGDRXM(String SJGDRXM)
	{
		this.SJGDRXM = SJGDRXM;
	}

	/**
	 * @return 返回 归档时间
	 */
	public String getSJGDSJ()
	{
		return SJGDSJ;
	}

	/**
	 * @param SJGDSJ
	 *            要设置的 归档时间
	 */
	public void setSJGDSJ(String SJGDSJ)
	{
		this.SJGDSJ = SJGDSJ;
	}

	/**
	 * @return 返回 处置期限
	 */
	public String getCZJX()
	{
		return CZJX;
	}

	/**
	 * @param CZJX
	 *            要设置的 处置期限
	 */
	public void setCZJX(String CZJX)
	{
		this.CZJX = CZJX;
	}

	/**
	 * @return 返回 纬度
	 */
	public String getX()
	{
		return X;
	}

	/**
	 * @param X
	 *            要设置的 纬度
	 */
	public void setX(String X)
	{
		this.X = X;
	}

	/**
	 * @return 返回 经度
	 */
	public String getY()
	{
		return Y;
	}

	/**
	 * @param Y
	 *            要设置的 经度
	 */
	public void setY(String Y)
	{
		this.Y = Y;
	}

	/**
	 * @return 返回 事件来源
	 */
	public String getSJLY()
	{
		return SJLY;
	}

	/**
	 * @param SJLY
	 *            要设置的 事件来源
	 */
	public void setSJLY(String SJLY)
	{
		this.SJLY = SJLY;
	}

	/**
	 * @return 返回 事件所属社区
	 */
	public String getSSSQ()
	{
		return SSSQ;
	}

	/**
	 * @param SSSQ
	 *            要设置的 事件所属社区
	 */
	public void setSSSQ(String SSSQ)
	{
		this.SSSQ = SSSQ;
	}

	/**
	 * @return 返回 事件归档说明
	 */
	public String getSJGDSM()
	{
		return SJGDSM;
	}

	/**
	 * @param SJGDSM
	 *            要设置的 事件归档说明
	 */
	public void setSJGDSM(String SJGDSM)
	{
		this.SJGDSM = SJGDSM;
	}

	public String toString()
	{
		return null;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}
}
