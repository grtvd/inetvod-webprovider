/**
 * Copyright © 2005-2009 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.StatusCode;

public class ProviderResp implements Writeable
{
	protected StatusCode fStatusCode;
	protected ResponseData fResponseData;

	public void setStatusCode(StatusCode statusCode) { fStatusCode = statusCode; }
	public void setResponseData(ResponseData responseData) { fResponseData = responseData; }

	public void readFrom(DataReader reader) throws Exception
	{
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeInt("StatusCode", StatusCode.convertToInt(fStatusCode));
		writer.writeObject("ResponseData", fResponseData);
	}
}
