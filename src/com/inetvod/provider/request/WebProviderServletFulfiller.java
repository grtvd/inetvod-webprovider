/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.ServletFulfiller;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebProviderServletFulfiller extends ServletFulfiller
{
	public WebProviderServletFulfiller(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		super(httpServletRequest, httpServletResponse);
	}

	public DataFormat getRequestDataFormat()
	{
		return DataFormat.XML;	//TODO: base format on URL
	}

	protected Writeable createResponseFromException(Requestable requestable, Exception e)
	{
		INetVODProviderResp response = new INetVODProviderResp();

		if((requestable != null) && (requestable instanceof INetVODProviderRqst))
			response.setRequestID(((INetVODProviderRqst)requestable).getRequestID());

		response.setStatusCode(StatusCode.sc_GeneralError);

		return response;
	}

	/// <summary>
	/// Read a Requestable object from its name
	/// </summary>
	/// <param name="className"></param>
	/// <returns></returns>
	protected Requestable readRequestableFromReader(DataReader dataFiler) throws Exception
	{
		return (Requestable)dataFiler.readObject("INetVODProviderRqst", INetVODProviderRqst.CtorDataFiler);
	}
}
