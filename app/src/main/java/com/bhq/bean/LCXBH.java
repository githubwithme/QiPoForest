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
@Table(name = "LCXBH")
public class LCXBH implements Parcelable
{
    @Id
    @NoAutoIncrement
    public String ID;
    public String ZC;//总场
    public String FC;//分场
    public String LBH;//林班号
    public String XBH;//小班号


    public LCXBH()
    {
        super();
    }

    protected LCXBH(Parcel in)
    {
        ID = in.readString();
        ZC = in.readString();
        FC = in.readString();
        LBH = in.readString();
        XBH = in.readString();
    }

    public static final Creator<LCXBH> CREATOR = new Creator<LCXBH>()
    {
        @Override
        public LCXBH createFromParcel(Parcel in)
        {
            return new LCXBH(in);
        }

        @Override
        public LCXBH[] newArray(int size)
        {
            return new LCXBH[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(ID);
        dest.writeString(ZC);
        dest.writeString(FC);
        dest.writeString(LBH);
        dest.writeString(XBH);
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getZC()
    {
        return ZC;
    }

    public void setZC(String ZC)
    {
        this.ZC = ZC;
    }

    public String getFC()
    {
        return FC;
    }

    public void setFC(String FC)
    {
        this.FC = FC;
    }

    public String getLBH()
    {
        return LBH;
    }

    public void setLBH(String LBH)
    {
        this.LBH = LBH;
    }

    public String getXBH()
    {
        return XBH;
    }

    public void setXBH(String XBH)
    {
        this.XBH = XBH;
    }
}
