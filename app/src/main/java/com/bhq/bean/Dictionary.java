package com.bhq.bean;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "Dictionary")
public class Dictionary
{
	int id;
	String DID;
	String NAME;
	String SORT;
	String LX;
	String PID;

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setDID(String dID)
	{
		DID = dID;
	}

	public String getDID()
	{
		return DID;
	}

	public void setNAME(String nAME)
	{
		NAME = nAME;
	}

	public String getNAME()
	{
		return NAME;
	}

	public void setSORT(String sORT)
	{
		SORT = sORT;
	}

	public String getSORT()
	{
		return SORT;
	}

	public void setLX(String lX)
	{
		LX = lX;
	}

	public String getLX()
	{
		return LX;
	}

	public void setPID(String pID)
	{
		PID = pID;
	}

	public String getPID()
	{
		return PID;
	}
}
