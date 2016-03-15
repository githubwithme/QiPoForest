

package com.bhq.bean;
import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
/**
 *
 * Description: BHQ_XHXL_GJ 实体类</p>
 *
 * Copyright: Copyright (c) 2015
 *
 * Company: 广州海川信息科技有限公司
 * @version 1.0 
 */
@Table(name="BHQ_XHXL_GJ")
public class BHQ_XHXL_GJ implements Parcelable 
{
	@Id
	@NoAutoIncrement
	public String ID;
	public String X;
	public String Y;
	public String XLID;
	public String SORT;
	public String QZD;
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

	public String getXLID() 
    {
		return XLID;
	}

	public void setXLID(String XLID) 
    {
		this.XLID = XLID;
	}

	public String getSORT() 
    {
		return SORT;
	}

	public void setSORT(String SORT) 
    {
		this.SORT = SORT;
	}

	public String getQZD() 
    {
		return QZD;
	}

	public void setQZD(String QZD) 
    {
		this.QZD = QZD;
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
    
   public static final Creator<BHQ_XHXL_GJ> CREATOR = new Creator()
   {  
      @Override  
      public BHQ_XHXL_GJ createFromParcel(Parcel source)
      {  
         // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错  
    	 BHQ_XHXL_GJ p = new BHQ_XHXL_GJ();
         p.setID(source.readString());
         p.setX(source.readString());
         p.setY(source.readString());
         p.setXLID(source.readString());
         p.setSORT(source.readString());
         p.setQZD(source.readString());
         p.setCJSJ(source.readString());
         p.setXGSJ(source.readString());
         return p;  
      }  

      @Override  
      public BHQ_XHXL_GJ[] newArray(int size) 
      {  
          return new BHQ_XHXL_GJ[size];  
      }  
   }; 
   
   	@Override
	public void writeToParcel(Parcel p, int arg1) 
    {
         p.writeString(ID);
         p.writeString(X);
         p.writeString(Y);
         p.writeString(XLID);
         p.writeString(SORT);
         p.writeString(QZD);
         p.writeString(CJSJ);
         p.writeString(XGSJ);
	}
    @Override
	public int describeContents()
	{
		return 0;
	}
}
