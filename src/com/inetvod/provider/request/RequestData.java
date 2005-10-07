/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.Authenticate;

public class RequestData implements Requestable
{
	/* Constants */
	public static final Constructor<RequestData> CtorDataFiler = DataReader.getCtor(RequestData.class);
	public static final int RequestTypeMaxLength = 64;

	/* Properties */
	protected String fRequestType;
	public String getRequestType() { return fRequestType; }
	protected Requestable fRequest;

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public RequestData(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	public void setRequest(String version, String requestID, Authenticate authenticate) throws Exception
	{
		if(fRequest instanceof AuthenRequestable)
			((AuthenRequestable)fRequest).setRequest(version, requestID, authenticate);
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

		Class<Requestable> cl = (Class<Requestable>)Class.forName(getClass().getPackage().getName() + "." + fRequestType);
		Constructor<Requestable> ctor = cl.getConstructor(new Class[] { DataReader.class });
		fRequest = reader.readObject(fRequestType, ctor);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("RequestType", fRequestType, RequestTypeMaxLength);
		writer.writeObject(fRequestType, fRequest);
	}
}
