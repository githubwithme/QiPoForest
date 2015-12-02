package com.bhq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "AppKnowledge")
public class AppKnowledge implements Parcelable
{
	public static final Parcelable.Creator<AppKnowledge> CREATOR = new Creator()
	{
		@Override
		public AppKnowledge createFromParcel(Parcel source)
		{
			// 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
			AppKnowledge p = new AppKnowledge();
			// p.setId(source.readInt());
			p.setUserId(source.readInt());
			p.setKnowledgeId(source.readInt());
			p.setType(source.readString());
			p.setMinitype(source.readString());
			p.setTitle(source.readString());
			p.setInputDate(source.readString());
			p.setContent(source.readString());
			p.setImgPath(source.readString());
			p.setBDLJ(source.readString());
			return p;
		}

		@Override
		public AppKnowledge[] newArray(int size)
		{
			return new AppKnowledge[size];
		}
	};
	// int id;
	@Id
	int KnowledgeId;
	int UserId;
	String Type, Minitype, Title, InputDate, Content, ImgPath;
	Boolean Change;
	String BDLJ;

	public void setBDLJ(String bDLJ)
	{
		BDLJ = bDLJ;
	}

	public String getBDLJ()
	{
		return BDLJ;
	}

	public void setChange(Boolean change)
	{
		Change = change;
	}

	public Boolean getChange()
	{
		return Change;
	}

	// public void setId(int id) {
	// this.id = id;
	// }
	// public int getId() {
	// return id;
	// }
	public void setKnowledgeId(int knowledgeId)
	{
		KnowledgeId = knowledgeId;
	}

	public int getKnowledgeId()
	{
		return KnowledgeId;
	}

	public void setUserId(int userId)
	{
		UserId = userId;
	}

	public int getUserId()
	{
		return UserId;
	}

	public void setType(String type)
	{
		Type = type;
	}

	public String getType()
	{
		return Type;
	}

	public void setMinitype(String minitype)
	{
		Minitype = minitype;
	}

	public String getMinitype()
	{
		return Minitype;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public String getTitle()
	{
		return Title;
	}

	public void setInputDate(String inputDate)
	{
		InputDate = inputDate;
	}

	public String getInputDate()
	{
		return InputDate;
	}

	public void setContent(String content)
	{
		Content = content;
	}

	public String getContent()
	{
		return Content;
	}

	public void setImgPath(String imgPath)
	{
		ImgPath = imgPath;
	}

	public String getImgPath()
	{
		return ImgPath;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p, int arg1)
	{
		// p.writeInt(id);
		p.writeInt(KnowledgeId);
		p.writeInt(UserId);
		p.writeString(Title);
		p.writeString(Minitype);
		p.writeString(Title);
		p.writeString(InputDate);
		p.writeString(Content);
		p.writeString(ImgPath);
	}
}
