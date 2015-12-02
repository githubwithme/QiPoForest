package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * Description: BHQ_XHSJCJ 实体类
 * 
 * Copyright: Copyright (c) 2015
 * 
 * Company: 广州海川信息科技有限公司
 * 
 * @version 1.0
 */
@Table(name = "BHQ_XHSJCJ")
public class BHQ_XHSJCJ implements Parcelable
{
	@Id
	@NoAutoIncrement
	public String CJID;
	public String XHID;
	public String SSBHZ;
	public String CJR;
	public String CJRXM;
	public String CJSJ;
	public String ZYDL;
	public String ZYXL;
	public String ZM="";
	public String ZMLDM="";
	public String ZMYWM="";
	public String GANG="";
	public String MU="";
	public String KE="";
	public String SHU="";
	public String BHJB;
	public String BWD;
	public String TZ="";
	public String XX="";
	public String FB="";
	public String ZQ="";
	public String SZHJ="";
	public String BWYS="";
	public String BDZYBZ;
	public String X;
	public String Y;
	public String SFXWZ="";
	public String SFBDZY="";
	public String SFSP="";
	public String SPYJ="";
	public String SPR;
	public String SPRXM;
	public String SPSJ;
	public String GLBDZYID;
	public String ZYDLMC;
	public String ZYXLMC;
	public String BWDMC;
	public String BHJBMC;
	public String IMGURL;
	public String SFSC;
	public String IsUpload;
	public String BDLJ;

	public void setBDLJ(String bDLJ)
	{
		BDLJ = bDLJ;
	}

	public String getBDLJ()
	{
		return BDLJ;
	}

	public void setSFSC(String sFSC)
	{
		SFSC = sFSC;
	}

	public String getSFSC()
	{
		return SFSC;
	}

	public void setIsUpload(String isUpload)
	{
		IsUpload = isUpload;
	}

	public String getIsUpload()
	{
		return IsUpload;
	}

	public void setIMGURL(String iMGURL)
	{
		IMGURL = iMGURL;
	}

	public String getIMGURL()
	{
		return IMGURL;
	}

	public void setZYDLMC(String zYDLMC)
	{
		ZYDLMC = zYDLMC;
	}

	public String getZYDLMC()
	{
		return ZYDLMC;
	}

	public void setZYXLMC(String zYXLMC)
	{
		ZYXLMC = zYXLMC;
	}

	public String getZYXLMC()
	{
		return ZYXLMC;
	}

	public void setBWDMC(String bWDMC)
	{
		BWDMC = bWDMC;
	}

	public String getBWDMC()
	{
		return BWDMC;
	}

	public void setBHJBMC(String bHJBMC)
	{
		BHJBMC = bHJBMC;
	}

	public String getBHJBMC()
	{
		return BHJBMC;
	}

	public String getCJID()
	{
		return CJID;
	}

	public void setCJID(String CJID)
	{
		this.CJID = CJID;
	}

	public String getXHID()
	{
		return XHID;
	}

	public void setXHID(String XHID)
	{
		this.XHID = XHID;
	}

	public String getSSBHZ()
	{
		return SSBHZ;
	}

	public void setSSBHZ(String SSBHZ)
	{
		this.SSBHZ = SSBHZ;
	}

	public String getCJR()
	{
		return CJR;
	}

	public void setCJR(String CJR)
	{
		this.CJR = CJR;
	}

	public String getCJRXM()
	{
		return CJRXM;
	}

	public void setCJRXM(String CJRXM)
	{
		this.CJRXM = CJRXM;
	}

	public String getCJSJ()
	{
		return CJSJ;
	}

	public void setCJSJ(String CJSJ)
	{
		this.CJSJ = CJSJ;
	}

	public String getZYDL()
	{
		return ZYDL;
	}

	public void setZYDL(String ZYDL)
	{
		this.ZYDL = ZYDL;
	}

	public String getZYXL()
	{
		return ZYXL;
	}

	public void setZYXL(String ZYXL)
	{
		this.ZYXL = ZYXL;
	}

	public String getZM()
	{
		return ZM;
	}

	public void setZM(String ZM)
	{
		this.ZM = ZM;
	}

	public String getZMLDM()
	{
		return ZMLDM;
	}

	public void setZMLDM(String ZMLDM)
	{
		this.ZMLDM = ZMLDM;
	}

	public String getZMYWM()
	{
		return ZMYWM;
	}

	public void setZMYWM(String ZMYWM)
	{
		this.ZMYWM = ZMYWM;
	}

	public String getGANG()
	{
		return GANG;
	}

	public void setGANG(String GANG)
	{
		this.GANG = GANG;
	}

	public String getMU()
	{
		return MU;
	}

	public void setMU(String MU)
	{
		this.MU = MU;
	}

	public String getKE()
	{
		return KE;
	}

	public void setKE(String KE)
	{
		this.KE = KE;
	}

	public String getSHU()
	{
		return SHU;
	}

	public void setSHU(String SHU)
	{
		this.SHU = SHU;
	}

	public String getBHJB()
	{
		return BHJB;
	}

	public void setBHJB(String BHJB)
	{
		this.BHJB = BHJB;
	}

	public String getBWD()
	{
		return BWD;
	}

	public void setBWD(String BWD)
	{
		this.BWD = BWD;
	}

	public String getTZ()
	{
		return TZ;
	}

	public void setTZ(String TZ)
	{
		this.TZ = TZ;
	}

	public String getXX()
	{
		return XX;
	}

	public void setXX(String XX)
	{
		this.XX = XX;
	}

	public String getFB()
	{
		return FB;
	}

	public void setFB(String FB)
	{
		this.FB = FB;
	}

	public String getZQ()
	{
		return ZQ;
	}

	public void setZQ(String ZQ)
	{
		this.ZQ = ZQ;
	}

	public String getSZHJ()
	{
		return SZHJ;
	}

	public void setSZHJ(String SZHJ)
	{
		this.SZHJ = SZHJ;
	}

	public String getBWYS()
	{
		return BWYS;
	}

	public void setBWYS(String BWYS)
	{
		this.BWYS = BWYS;
	}

	public String getBDZYBZ()
	{
		return BDZYBZ;
	}

	public void setBDZYBZ(String BDZYBZ)
	{
		this.BDZYBZ = BDZYBZ;
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

	public String getSFXWZ()
	{
		return SFXWZ;
	}

	public void setSFXWZ(String SFXWZ)
	{
		this.SFXWZ = SFXWZ;
	}

	public String getSFBDZY()
	{
		return SFBDZY;
	}

	public void setSFBDZY(String SFBDZY)
	{
		this.SFBDZY = SFBDZY;
	}

	public String getSFSP()
	{
		return SFSP;
	}

	public void setSFSP(String SFSP)
	{
		this.SFSP = SFSP;
	}

	public String getSPYJ()
	{
		return SPYJ;
	}

	public void setSPYJ(String SPYJ)
	{
		this.SPYJ = SPYJ;
	}

	public String getSPR()
	{
		return SPR;
	}

	public void setSPR(String SPR)
	{
		this.SPR = SPR;
	}

	public String getSPRXM()
	{
		return SPRXM;
	}

	public void setSPRXM(String SPRXM)
	{
		this.SPRXM = SPRXM;
	}

	public String getSPSJ()
	{
		return SPSJ;
	}

	public void setSPSJ(String SPSJ)
	{
		this.SPSJ = SPSJ;
	}

	public String getGLBDZYID()
	{
		return GLBDZYID;
	}

	public void setGLBDZYID(String GLBDZYID)
	{
		this.GLBDZYID = GLBDZYID;
	}

	public boolean equals(Object o)
	{
		return false;
	}

	public int hashCode()
	{
		return 0;
	}

	public static final Parcelable.Creator<BHQ_XHSJCJ> CREATOR = new Creator()
	{
		@Override
		public BHQ_XHSJCJ createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			BHQ_XHSJCJ p = new BHQ_XHSJCJ();
			p.setCJID(source.readString());
			p.setXHID(source.readString());
			p.setSSBHZ(source.readString());
			p.setCJR(source.readString());
			p.setCJRXM(source.readString());
			p.setCJSJ(source.readString());
			p.setZYDL(source.readString());
			p.setZYXL(source.readString());
			p.setZM(source.readString());
			p.setZMLDM(source.readString());
			p.setZMYWM(source.readString());
			p.setGANG(source.readString());
			p.setMU(source.readString());
			p.setKE(source.readString());
			p.setSHU(source.readString());
			p.setBHJB(source.readString());
			p.setBWD(source.readString());
			p.setTZ(source.readString());
			p.setXX(source.readString());
			p.setFB(source.readString());
			p.setZQ(source.readString());
			p.setSZHJ(source.readString());
			p.setBWYS(source.readString());
			p.setBDZYBZ(source.readString());
			p.setX(source.readString());
			p.setY(source.readString());
			p.setSFXWZ(source.readString());
			p.setSFBDZY(source.readString());
			p.setSFSP(source.readString());
			p.setSPYJ(source.readString());
			p.setSPR(source.readString());
			p.setSPRXM(source.readString());
			p.setSPSJ(source.readString());
			p.setGLBDZYID(source.readString());
			p.setZYDLMC(source.readString());
			p.setZYXLMC(source.readString());
			p.setBWDMC(source.readString());
			p.setBHJBMC(source.readString());
			p.setIMGURL(source.readString());
			p.setSFSC(source.readString());
			p.setIsUpload(source.readString());
			p.setBDLJ(source.readString());

			return p;
		}

		@Override
		public BHQ_XHSJCJ[] newArray(int size)
		{
			return new BHQ_XHSJCJ[size];
		}
	};

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		p.writeString(CJID);
		p.writeString(XHID);
		p.writeString(SSBHZ);
		p.writeString(CJR);
		p.writeString(CJRXM);
		p.writeString(CJSJ);
		p.writeString(ZYDL);
		p.writeString(ZYXL);
		p.writeString(ZM);
		p.writeString(ZMLDM);
		p.writeString(ZMYWM);
		p.writeString(GANG);
		p.writeString(MU);
		p.writeString(KE);
		p.writeString(SHU);
		p.writeString(BHJB);
		p.writeString(BWD);
		p.writeString(TZ);
		p.writeString(XX);
		p.writeString(FB);
		p.writeString(ZQ);
		p.writeString(SZHJ);
		p.writeString(BWYS);
		p.writeString(BDZYBZ);
		p.writeString(X);
		p.writeString(Y);
		p.writeString(SFXWZ);
		p.writeString(SFBDZY);
		p.writeString(SFSP);
		p.writeString(SPYJ);
		p.writeString(SPR);
		p.writeString(SPRXM);
		p.writeString(SPSJ);
		p.writeString(GLBDZYID);

		p.writeString(ZYDLMC);
		p.writeString(ZYXLMC);
		p.writeString(BWDMC);
		p.writeString(BHJBMC);
		p.writeString(IMGURL);
		p.writeString(SFSC);
		p.writeString(IsUpload);
		p.writeString(BDLJ);

	}

	@Override
	public int describeContents()
	{
		return 0;
	}
}
