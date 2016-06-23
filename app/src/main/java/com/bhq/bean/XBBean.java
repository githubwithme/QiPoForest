package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Description: BHQ_ZSK 实体类
 * <p>
 * Copyright: Copyright (c) 2015
 * <p>
 * Company: 广州海川信息科技有限公司
 *
 * @version 1.0
 */
@Table(name = "XBBean")
public class XBBean implements Parcelable
{
    @Id
    @NoAutoIncrement
    public String ID;
    public String XBH;
    public String PJSG;
    public String PJXJ;
    public String MGQZS;
    public String DCR;
    public String DCRXM;
    public String DCSJ;
    public String X;
    public String Y;
    public String IsUpload;
    public String SFSC;

    public String getXBH()
    {
        return XBH;
    }

    public void setXBH(String XBH)
    {
        this.XBH = XBH;
    }

    public String getDCR()
    {
        return DCR;
    }

    public void setDCR(String DCR)
    {
        this.DCR = DCR;
    }

    public String getDCRXM()
    {
        return DCRXM;
    }

    public void setDCRXM(String DCRXM)
    {
        this.DCRXM = DCRXM;
    }

    public String getDCSJ()
    {
        return DCSJ;
    }

    public void setDCSJ(String DCSJ)
    {
        this.DCSJ = DCSJ;
    }

    public String getX()
    {
        return X;
    }

    public void setX(String x)
    {
        X = x;
    }

    public String getY()
    {
        return Y;
    }

    public void setY(String y)
    {
        Y = y;
    }

    public String getIsUpload()
    {
        return IsUpload;
    }

    public void setIsUpload(String isUpload)
    {
        IsUpload = isUpload;
    }

    public String getSFSC()
    {
        return SFSC;
    }

    public void setSFSC(String SFSC)
    {
        this.SFSC = SFSC;
    }

    public String getPJXJ()
    {
        return PJXJ;
    }

    public void setPJXJ(String PJXJ)
    {
        this.PJXJ = PJXJ;
    }

    public String getMGQZS()
    {
        return MGQZS;
    }

    public void setMGQZS(String MGQZS)
    {
        this.MGQZS = MGQZS;
    }

    public String getPJSG()
    {
        return PJSG;
    }

    public void setPJSG(String PJSG)
    {
        this.PJSG = PJSG;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getID()
    {
        return ID;
    }

    public boolean equals(Object o)
    {
        return false;
    }

    public int hashCode()
    {
        return 0;
    }

    public static final Creator<XBBean> CREATOR = new Creator()
    {
        @Override
        public XBBean createFromParcel(Parcel source)
        {
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            XBBean p = new XBBean();
            p.setID(source.readString());
            p.setXBH(source.readString());
            p.setPJSG(source.readString());
            p.setPJXJ(source.readString());
            p.setMGQZS(source.readString());
            p.setDCR(source.readString());
            p.setDCRXM(source.readString());
            p.setDCSJ(source.readString());
            p.setX(source.readString());
            p.setY(source.readString());
            p.setIsUpload(source.readString());
            p.setSFSC(source.readString());
            return p;
        }

        @Override
        public XBBean[] newArray(int size)
        {
            return new XBBean[size];
        }
    };

    @Override
    public void writeToParcel(Parcel p, int arg1)
    {
        p.writeString(ID);
        p.writeString(XBH);
        p.writeString(PJSG);
        p.writeString(PJXJ);
        p.writeString(MGQZS);
        p.writeString(DCR);
        p.writeString(DCRXM);
        p.writeString(DCSJ);
        p.writeString(X);
        p.writeString(Y);
        p.writeString(IsUpload);
        p.writeString(SFSC);

    }

    @Override
    public int describeContents()
    {
        return 0;
    }
}
