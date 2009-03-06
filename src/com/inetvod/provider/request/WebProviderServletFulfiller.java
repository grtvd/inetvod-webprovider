/**
 * Copyright © 2005-2009 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inetvod.common.core.DataFormat;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.ServletFulfiller;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.StatusCode;

public class WebProviderServletFulfiller extends ServletFulfiller
{
	public WebProviderServletFulfiller(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		super(httpServletRequest, httpServletResponse);
	}

	@Override
	public DataFormat getRequestDataFormat()
	{
		return DataFormat.XML;	//TODO: base format on URL
	}

	@Override
	protected Writeable createResponseFromException(Requestable requestable, Exception e)
	{
		ProviderResp response = new ProviderResp();

		response.setStatusCode(StatusCode.sc_GeneralError);

		return response;
	}

	/**
	 * Read a Requestable object from its name
	 */
	@Override
	protected Requestable readRequestableFromReader(DataReader dataReader) throws Exception
	{
		return dataReader.readObject("ProviderRqst", ProviderRqst.CtorDataReader);
	}

	@Override
	protected String getRequestType(Requestable requestable)
	{
		String requestType = null;

		if(requestable instanceof ProviderRqst)
		{
			ProviderRqst providerRqst = (ProviderRqst)requestable;
			if(providerRqst.getRequestData() != null)
				requestType = providerRqst.getRequestData().getRequestType();
		}

		return requestType;
	}
}
