package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: BHQ_XHQK_GJ 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "BHQ_XHQK_GJ")
public class BHQ_XHQK_GJ implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String GJID;
	public String XHID;
	public String X;
	public String Y;
	public String JLSJ;

	public String IsUpload;
	public String intervaldistance;
	public String altitude;
	public String accuracy;
	public String bearing;
	public String speed;

	public void setSpeed(String speed)
	{
		this.speed = speed;
	}

	public String getSpeed()
	{
		return speed;
	}




	public void setBearing(String bearing)
	{
		this.bearing = bearing;
	}

	public String getBearing()
	{
		return bearing;
	}

	public void setAltitude(String altitude)
	{
		this.altitude = altitude;
	}

	public String getAltitude()
	{
		return altitude;
	}

	public void setAccuracy(String accuracy)
	{
		this.accuracy = accuracy;
	}

	public String getAccuracy()
	{
		return accuracy;
	}


	public void setIntervaldistance(String intervaldistance)
	{
		this.intervaldistance = intervaldistance;
	}

	public String getIntervaldistance()
	{
		return intervaldistance;
	}

	public void setIsUpload(String isUpload)
	{
		IsUpload = isUpload;
	}

	public String getIsUpload()
	{
		return IsUpload;
	}

	public String getGJID()
	{
		return GJID;
	}

	public void setGJID(String GJID)
	{
		this.GJID = GJID;
	}

	public String getXHID()
	{
		return XHID;
	}

	public void setXHID(String XHID)
	{
		this.XHID = XHID;
	}

	public String getX()
	{
		return X;
	}

	public void setX(String X)
	{
		this.X = X;
	}

	public String getY()
	{
		return Y;
	}

	public void setY(String Y)
	{
		this.Y = Y;
	}

	public String getJLSJ()
	{
		return JLSJ;
	}

	public void setJLSJ(String JLSJ)
	{
		this.JLSJ = JLSJ;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<BHQ_XHQK_GJ> CREATOR = new Creator()
	{
		@Override
		public BHQ_XHQK_GJ createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			BHQ_XHQK_GJ p = new BHQ_XHQK_GJ();
			p.setGJID(source.readString());
			p.setXHID(source.readString());
			p.setX(source.readString());
			p.setY(source.readString());
			p.setJLSJ(source.readString());
			p.setIsUpload(source.readString());
			p.setIntervaldistance(source.readString());

			p.setAltitude(source.readString());
			p.setAccuracy(source.readString());
			p.setBearing(source.readString());
			p.setSpeed(source.readString());
			return p;
		}

		@Override
		public BHQ_XHQK_GJ[] newArray(int size)
		{
			return new BHQ_XHQK_GJ[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(GJID);
		p.writeString(XHID);
		p.writeString(X);
		p.writeString(Y);
		p.writeString(JLSJ);
		p.writeString(IsUpload);
		p.writeString(intervaldistance);
		p.writeString(altitude);
		p.writeString(accuracy);
		p.writeString(bearing);
		p.writeString(speed);


	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
