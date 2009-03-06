/**
 * Copyright © 2005-2009 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;


import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.Authenticate;
import com.inetvod.provider.rqdata.StatusCode;

public class ProviderRqst implements Requestable
{
	/* Constants */
	public static final Constructor<ProviderRqst> CtorDataReader = DataReader.getCtor(ProviderRqst.class);
	public static final int VersionMaxLength = 16;

	/* Fields */
	protected String fVersion;
	protected Authenticate fAuthenticate;
	protected RequestData fRequestData;
	public RequestData getRequestData() { return fRequestData; }

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public ProviderRqst(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public Writeable fulfillRequest() throws Exception
	{
		ProviderResp response = new ProviderResp();

		// validate request
		//TODO: validate version of client

		// fulfill request
		fRequestData.setRequest(fVersion, fAuthenticate);
		response.setResponseData((ResponseData)fRequestData.fulfillRequest());
		fStatusCode = fRequestData.getStatusCode();
		response.setStatusCode(fStatusCode);

		return response;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fVersion = reader.readString("Version", VersionMaxLength);
		fAuthenticate = reader.readObject("Authenticate", Authenticate.CtorDataReader);
		fRequestData = reader.readObject("RequestData", RequestData.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("Version", fVersion, VersionMaxLength);
		writer.writeObject("Authenticate", fAuthenticate);
		writer.writeObject("RequestData", fRequestData);
	}
}
