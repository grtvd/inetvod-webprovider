/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;
import com.inetvod.provider.rqdata.Authenticate;

import java.lang.reflect.Constructor;

public class RequestData implements Requestable
{
	public static final Constructor CtorDataFiler = DataReader.getCtor(RequestData.class);

	protected String fRequestType;
	protected Requestable fRequest;

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public RequestData(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	public void setRequest(String version, String requestID, Authenticate authenticate) throws Exception
	{
		if(fRequest instanceof SessionRequestable)
			((SessionRequestable)fRequest).setRequest(version, requestID, authenticate);
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
		ResponseData responseData = new ResponseData(response);

		return responseData;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fRequestType = reader.readString("RequestType", 64);

		Class cl = Class.forName(getClass().getPackage().getName() + "." + fRequestType);
		Constructor ctor = cl.getConstructor(new Class[] { DataReader.class });
		fRequest = (Requestable)reader.readObject(fRequestType, ctor);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("RequestType", fRequestType, 64);
		writer.writeObject(fRequestType, fRequest);
	}
}
