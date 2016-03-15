

package com.bhq.bean;
import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
/**
 *
 * Description: BQH_XHRY 实体类</p>
 *
 * Copyright: Copyright (c) 2015
 *
 * Company: 广州海川信息科技有限公司
 * @version 1.0 
 */
@Table(name="BQH_XHRY")
public class BQH_XHRY implements Parcelable 
{
	@Id
	@NoAutoIncrement
	public String ID;
	public String XHRYID;
	public String XHNRD;
	public String XHNRN;
	public String XHNRX;
	public String XHNRB;
	public String XHRXL;
	public String XHZXL;
	public String XHYXL;
	public String XHMJ;
	public String BZ;
	public String CJSJ;
	public String XGSJ;
    

	public String getID() 
    {
		return ID;
	}

	public void setID(String ID) 
    {
		this.ID = ID;
	}

	public String getXHRYID() 
    {
		return XHRYID;
	}

	public void setXHRYID(String XHRYID) 
    {
		this.XHRYID = XHRYID;
	}

	public String getXHNRD() 
    {
		return XHNRD;
	}

	public void setXHNRD(String XHNRD) 
    {
		this.XHNRD = XHNRD;
	}

	public String getXHNRN() 
    {
		return XHNRN;
	}

	public void setXHNRN(String XHNRN) 
    {
		this.XHNRN = XHNRN;
	}

	public String getXHNRX() 
    {
		return XHNRX;
	}

	public void setXHNRX(String XHNRX) 
    {
		this.XHNRX = XHNRX;
	}

	public String getXHNRB() 
    {
		return XHNRB;
	}

	public void setXHNRB(String XHNRB) 
    {
		this.XHNRB = XHNRB;
	}

	public String getXHRXL() 
    {
		return XHRXL;
	}

	public void setXHRXL(String XHRXL) 
    {
		this.XHRXL = XHRXL;
	}

	public String getXHZXL() 
    {
		return XHZXL;
	}

	public void setXHZXL(String XHZXL) 
    {
		this.XHZXL = XHZXL;
	}

	public String getXHYXL() 
    {
		return XHYXL;
	}

	public void setXHYXL(String XHYXL) 
    {
		this.XHYXL = XHYXL;
	}

	public String getXHMJ() 
    {
		return XHMJ;
	}

	public void setXHMJ(String XHMJ) 
    {
		this.XHMJ = XHMJ;
	}

	public String getBZ() 
    {
		return BZ;
	}

	public void setBZ(String BZ) 
    {
		this.BZ = BZ;
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
	
	public boolean equals(Object o) 
    {
		return false;
	}

	public int hashCode()
    {
		return 0;
	}
    
   public static final Creator<BQH_XHRY> CREATOR = new Creator()
   {  
      @Override  
      public BQH_XHRY createFromParcel(Parcel source)
      {  
         // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
    	 BQH_XHRY p = new BQH_XHRY();
         p.setID(source.readString());
         p.setXHRYID(source.readString());
         p.setXHNRD(source.readString());
         p.setXHNRN(source.readString());
         p.setXHNRX(source.readString());
         p.setXHNRB(source.readString());
         p.setXHRXL(source.readString());
         p.setXHZXL(source.readString());
         p.setXHYXL(source.readString());
         p.setXHMJ(source.readString());
         p.setBZ(source.readString());
         p.setCJSJ(source.readString());
         p.setXGSJ(source.readString());
         return p;  
      }  

      @Override  
      public BQH_XHRY[] newArray(int size) 
      {  
          return new BQH_XHRY[size];  
      }  
   }; 
   
   	@Override
	public void writeToParcel(Parcel p, int arg1) 
    {
         p.writeString(ID);
         p.writeString(XHRYID);
         p.writeString(XHNRD);
         p.writeString(XHNRN);
         p.writeString(XHNRX);
         p.writeString(XHNRB);
         p.writeString(XHRXL);
         p.writeString(XHZXL);
         p.writeString(XHYXL);
         p.writeString(XHMJ);
         p.writeString(BZ);
         p.writeString(CJSJ);
         p.writeString(XGSJ);
	}
    @Override
	public int describeContents()
	{
		return 0;
	}
}
