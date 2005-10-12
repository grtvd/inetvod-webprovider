/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
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

public class INetVODProviderRqst implements Requestable
{
	/* Constants */
	public static final Constructor<INetVODProviderRqst> CtorDataFiler = DataReader.getCtor(INetVODProviderRqst.class);
	public static final int VersionMaxLength = 16;
	public static final int RequestIDMaxLength = 64;

	/* Fields */
	protected String fVersion;
	protected String fRequestID;
	public String getRequestID() { return fRequestID; }
	protected Authenticate fAuthenticate;
	protected RequestData fRequestData;
	public RequestData getRequestData() { return fRequestData; }

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public INetVODProviderRqst(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public Writeable fulfillRequest() throws Exception
	{
		INetVODProviderResp response = new INetVODProviderResp();

		// validate request
		//TODO: validate version of client

		// fulfull request
		response.setRequestID(fRequestID);

		fRequestData.setRequest(fVersion, fRequestID, fAuthenticate);
		response.setResponseData((ResponseData)fRequestData.fulfillRequest());
		fStatusCode = fRequestData.getStatusCode();
		response.setStatusCode(fStatusCode);

		return response;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fVersion = reader.readString("Version", VersionMaxLength);
		fRequestID = reader.readString("RequestID", RequestIDMaxLength);
		fAuthenticate = reader.readObject("Authenticate", Authenticate.CtorDataFiler);
		fRequestData = reader.readObject("RequestData", RequestData.CtorDataFiler);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("Version", fVersion, VersionMaxLength);
		writer.writeString("RequestID", fRequestID, RequestIDMaxLength);
		writer.writeObject("Authenticate", fAuthenticate);
		writer.writeObject("RequestData", fRequestData);
	}
}
