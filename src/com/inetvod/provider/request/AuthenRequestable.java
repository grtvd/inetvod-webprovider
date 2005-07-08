/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.provider.rqdata.Authenticate;
import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;

/**
 * A Requestable that understands Authenticate.
 */
public abstract class AuthenRequestable implements Requestable
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

	protected boolean isMemberRequest() { return true; }

	public boolean confirmAuthentication()
	{
		if(fAuthenticate == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		//TODO: validate admin credentials
		if(!"super".equals(fAuthenticate.getAdminUserID()) || !"superpassword".equals(fAuthenticate.getAdminPassword()))
		{
			fStatusCode = StatusCode.sc_InvalidAdminUserID;
			return false;
		}

		if(isMemberRequest())
		{
			//TODO: validate member credentials
			if(!"member".equals(fAuthenticate.getMemberUserID()) || !"memberpassword".equals(fAuthenticate.getMemberPassword()))
			{
				fStatusCode = StatusCode.sc_InvalidMemberUserID;
				return false;
			}
		}

		return true;
	}
}