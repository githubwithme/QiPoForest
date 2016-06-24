package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Description: BHQ_XHQK 实体类
 * <p/>
 * Copyright: Copyright (c) 2015
 * <p/>
 * Company: 广州海川信息科技有限公司
 *
 * @version 1.0
 */
@Table(name = "SLXBL")
public class SLXBL implements Parcelable
{
    @Id
    @NoAutoIncrement
    public String ID;
    public String XM;
    public String XZM;
    public String CM;
    public String XBH;

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getXM()
    {
        return XM;
    }

    public void setXM(String XM)
    {
        this.XM = XM;
    }

    public String getXZM()
    {
        return XZM;
    }

    public void setXZM(String XZM)
    {
        this.XZM = XZM;
    }

    public String getCM()
    {
        return CM;
    }

    public void setCM(String CM)
    {
        this.CM = CM;
    }

    public String getXBH()
    {
        return XBH;
    }

    public void setXBH(String XBH)
    {
        this.XBH = XBH;
    }

    public boolean equals(Object o)
    {
        return false;
    }

    public int hashCode()
    {
        return 0;
    }

    public static final Creator<SLXBL> CREATOR = new Creator()
    {
        @Override
        public SLXBL createFromParcel(Parcel source)
        {
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            SLXBL p = new SLXBL();
            p.setID(source.readString());
            p.setXM(source.readString());
            p.setXZM(source.readString());
            p.setCM(source.readString());
            p.setXBH(source.readString());
            return p;
        }

        @Override
        public SLXBL[] newArray(int size)
        {
            return new SLXBL[size];
        }
    };

    @Override
    public void writeToParcel(Parcel p, int arg1)
    {
        p.writeString(ID);
        p.writeString(XM);
        p.writeString(XZM);
        p.writeString(CM);
        p.writeString(XBH);

    }

    @Override
    public int describeContents()
    {
        return 0;
    }
}
