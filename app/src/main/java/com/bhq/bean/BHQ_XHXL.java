

package com.bhq.bean;
import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
/**
 *
 * Description: BHQ_XHXL 实体类</p>
 *
 * Copyright: Copyright (c) 2015
 *
 * Company: 广州海川信息科技有限公司
 * @version 1.0 
 */
@Table(name="BHQ_XHXL")
public class BHQ_XHXL implements Parcelable 
{
	@Id
	@NoAutoIncrement
	public String XLID;
	public String XLBH;
	public String XLXHNR;
	public String SSBHZ;
	public String XLLX;
	public String XLBZ;
	public String CJSJ;
	public String XGSJ;
	public String XXZT;
    

	public String getXLID() 
    {
		return XLID;
	}

	public void setXLID(String XLID) 
    {
		this.XLID = XLID;
	}

	public String getXLBH() 
    {
		return XLBH;
	}

	public void setXLBH(String XLBH) 
    {
		this.XLBH = XLBH;
	}

	public String getXLXHNR() 
    {
		return XLXHNR;
	}

	public void setXLXHNR(String XLXHNR) 
    {
		this.XLXHNR = XLXHNR;
	}

	public String getSSBHZ() 
    {
		return SSBHZ;
	}

	public void setSSBHZ(String SSBHZ) 
    {
		this.SSBHZ = SSBHZ;
	}

	public String getXLLX() 
    {
		return XLLX;
	}

	public void setXLLX(String XLLX) 
    {
		this.XLLX = XLLX;
	}

	public String getXLBZ() 
    {
		return XLBZ;
	}

	public void setXLBZ(String XLBZ) 
    {
		this.XLBZ = XLBZ;
	}

	public String getCJSJ() 
    {
		return CJSJ;
	}

	public void setCJSJ(String CJSJ) 
    {
		this.CJSJ = CJSJ;
	}

	public String getXGSJ() 
    {
		return XGSJ;
	}

	public void setXGSJ(String XGSJ) 
    {
		this.XGSJ = XGSJ;
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
    
   public static final Creator<BHQ_XHXL> CREATOR = new Creator()
   {  
      @Override  
      public BHQ_XHXL createFromParcel(Parcel source)
      {  
         // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
    	 BHQ_XHXL p = new BHQ_XHXL();
         p.setXLID(source.readString());
         p.setXLBH(source.readString());
         p.setXLXHNR(source.readString());
         p.setSSBHZ(source.readString());
         p.setXLLX(source.readString());
         p.setXLBZ(source.readString());
         p.setCJSJ(source.readString());
         p.setXGSJ(source.readString());
         p.setXXZT(source.readString());
         return p;  
      }  

      @Override  
      public BHQ_XHXL[] newArray(int size) 
      {  
          return new BHQ_XHXL[size];  
      }  
   }; 
   
   	@Override
	public void writeToParcel(Parcel p, int arg1) 
    {
         p.writeString(XLID);
         p.writeString(XLBH);
         p.writeString(XLXHNR);
         p.writeString(SSBHZ);
         p.writeString(XLLX);
         p.writeString(XLBZ);
         p.writeString(CJSJ);
         p.writeString(XGSJ);
         p.writeString(XXZT);
	}
    @Override
	public int describeContents()
	{
		return 0;
	}
}
