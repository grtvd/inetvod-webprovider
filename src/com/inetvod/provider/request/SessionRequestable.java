/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.provider.rqdata.Authenticate;
import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;

/**
 * A Requestable that understands Authenticate.
 */
public abstract class SessionRequestable implements Requestable
{
	protected String fVersion;
	protected String fRequestID;
	protected Authenticate fAuthenticate;

	//protected Member fMember;

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public void setRequest(String version, String requestID, Authenticate authenticate)
	{
		fVersion = version;
		fRequestID = requestID;
		fAuthenticate = authenticate;

		//fMember = Member.get(fSessionData.getMemberID());
	}

//	public abstract Writeable fulfillRequest();
//
//	public abstract void readFrom(DataReader reader) throws Exception;
//	public abstract void writeTo(DataWriter writer) throws Exception;
}
