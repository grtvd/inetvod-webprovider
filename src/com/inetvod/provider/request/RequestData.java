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
import com.inetvod.provider.rqdata.ProviderRequestable;
import com.inetvod.provider.rqdata.StatusCode;

public class RequestData implements Requestable
{
	/* Constants */
	public static final Constructor<RequestData> CtorDataReader = DataReader.getCtor(RequestData.class);
	public static final int RequestTypeMaxLength = 64;

	/* Properties */
	protected String fRequestType;
	public String getRequestType() { return fRequestType; }
	protected ProviderRequestable fRequest;

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public RequestData(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void setRequest(String version, Authenticate authenticate) throws Exception
	{
		if(fRequest instanceof AuthenRequestable)
			((AuthenRequestable)fRequest).setRequest(version, authenticate);
	}

	public Writeable fulfillRequest() throws Exception
	{
		if(fRequest == null)
		{
			fStatusCode = StatusCode.sc_GeneralError;
			return null;
		}

		Writeable response = fRequest.fulfillRequest();
		fStatusCode = fRequest.getStatusCode();

		ResponseData responseData = null;
		if((response != null))
			responseData = new ResponseData(response);
		if((responseData == null) && (fStatusCode.equals(StatusCode.sc_Success)))
			fStatusCode = StatusCode.sc_GeneralError;

		return responseData;
	}

	@SuppressWarnings({"unchecked"})
	public void readFrom(DataReader reader) throws Exception
	{
		fRequestType = reader.readString("RequestType", RequestTypeMaxLength);

		Class<ProviderRequestable> cl = (Class<ProviderRequestable>)Class.forName(getClass().getPackage().getName() + "." + fRequestType);
		Constructor<ProviderRequestable> ctor = cl.getConstructor(DataReader.class);
		fRequest = reader.readObject(fRequestType, ctor);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("RequestType", fRequestType, RequestTypeMaxLength);
		writer.writeObject(fRequestType, fRequest);
	}
}
