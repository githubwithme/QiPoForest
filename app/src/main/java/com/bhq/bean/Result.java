package com.bhq.bean;

import com.alibaba.fastjson.JSONArray;

/**
 * @author :sima
 * @version :1.0
 * @createTime：2015-8-5 下午3:04:57
 * @description :服务器返回数据实体类
 */
public class Result extends Entity
{
	int ResultCode;
	int AffectedRows;
	String Exception;
	JSONArray Rows;
	String[] ColumnNames;
	String ResultDesc;
	String OutParams;

	public void setRows(JSONArray rows)
	{
		Rows = rows;
	}

	public JSONArray getRows()
	{
		return Rows;
	}

	public void setColumnNames(String[] columnNames)
	{
		ColumnNames = columnNames;
	}

	public String[] getColumnNames()
	{
		return ColumnNames;
	}

	public void setResultCode(int resultCode)
	{
		ResultCode = resultCode;
	}

	public int getResultCode()
	{
		return ResultCode;
	}

	public void setAffectedRows(int affectedRows)
	{
		AffectedRows = affectedRows;
	}

	public int getAffectedRows()
	{
		return AffectedRows;
	}

	public void setException(String exception)
	{
		Exception = exception;
	}

	public String getException()
	{
		return Exception;
	}

	public void setResultDesc(String resultDesc)
	{
		ResultDesc = resultDesc;
	}

	public String getResultDesc()
	{
		return ResultDesc;
	}

	public void setOutParams(String outParams)
	{
		OutParams = outParams;
	}

	public String getOutParams()
	{
		return OutParams;
	}
}
