package com.bhq.bean;

import com.lidroid.xutils.db.annotation.Table;

/**
 * @author :hc-sima
 * @version :1.0
 * @createTime：2015-8-24 上午10:23:00
 * @description :
 */
@Table(name = "Track")
public class Track extends Entity
{
	String trackid;
	String description;
	String XHR;
	String XHSJ;
	String XHRXM;
	String XXZT;
	String starttime;
	String endtime;
	String xhlc;
	String startpoint;
	String endpoint;
	int id;

	public void setXhlc(String xhlc)
	{
		this.xhlc = xhlc;
	}

	public String getXhlc()
	{
		return xhlc;
	}

	public void setEndpoint(String endpoint)
	{
		this.endpoint = endpoint;
	}

	public String getEndpoint()
	{
		return endpoint;
	}

	public void setEndtime(String endtime)
	{
		this.endtime = endtime;
	}

	public String getEndtime()
	{
		return endtime;
	}

	public void setStartpoint(String startpoint)
	{
		this.startpoint = startpoint;
	}

	public String getStartpoint()
	{
		return startpoint;
	}

	public void setStarttime(String starttime)
	{
		this.starttime = starttime;
	}

	public String getStarttime()
	{
		return starttime;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setTrackid(String trackid)
	{
		this.trackid = trackid;
	}

	public String getTrackid()
	{
		return trackid;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setXHR(String xHR)
	{
		XHR = xHR;
	}

	public String getXHR()
	{
		return XHR;
	}

	public void setXHSJ(String xHSJ)
	{
		XHSJ = xHSJ;
	}

	public String getXHSJ()
	{
		return XHSJ;
	}

	public void setXHRXM(String xHRXM)
	{
		XHRXM = xHRXM;
	}

	public String getXHRXM()
	{
		return XHRXM;
	}

	public void setXXZT(String xXZT)
	{
		XXZT = xXZT;
	}

	public String getXXZT()
	{
		return XXZT;
	}
}
