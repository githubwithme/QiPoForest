package com.bhq.bean;

public class Apk
{

	String ID, Version, UserID, UpdateContent, ReleaseDate, Url;

	public void setUrl(String url)
	{
		Url = url;
	}

	public String getUrl()
	{
		return Url;
	}

	public void setID(String iD)
	{
		ID = iD;
	}

	public String getID()
	{
		return ID;
	}

	public void setVersion(String version)
	{
		Version = version;
	}

	public String getVersion()
	{
		return Version;
	}

	public void setUserID(String userID)
	{
		UserID = userID;
	}

	public String getUserID()
	{
		return UserID;
	}

	public void setUpdateContent(String updateContent)
	{
		UpdateContent = updateContent;
	}

	public String getUpdateContent()
	{
		return UpdateContent;
	}

	public void setReleaseDate(String releaseDate)
	{
		ReleaseDate = releaseDate;
	}

	public String getReleaseDate()
	{
		return ReleaseDate;
	}

}
