/**
 * Copyright � 2005-2009 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.provider.rqdata.Authenticate;
import com.inetvod.provider.rqdata.DataManager;
import com.inetvod.provider.rqdata.ProviderRequestable;
import com.inetvod.provider.rqdata.StatusCode;

/**
 * A Requestable that understands Authenticate.
 */
public abstract class AuthenRequestable implements ProviderRequestable
{
	protected String fVersion;
	protected Authenticate fAuthenticate;

	//protected Member fMember;

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public void setRequest(String version, Authenticate authenticate)
	{
		fVersion = version;
		fAuthenticate = authenticate;

		//fMember = Member.get(fSessionData.getMemberID());
	}

	protected boolean isMemberRequest() { return true; }

	public boolean wasMemberProvidered()
	{
		return ((fAuthenticate.getMemberUserID() != null) && (fAuthenticate.getMemberUserID().length() > 0));
	}

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
			//TODO: remove temporary conversion to lower-case
			if((fAuthenticate.getMemberUserID() == null)
				|| (fAuthenticate.getMemberPassword() == null)
				|| !DataManager.getThe().getMemberPassword().equals(fAuthenticate.getMemberPassword()))
			{
				fStatusCode = StatusCode.sc_InvalidMemberUserID;
				return false;
			}
		}

		return true;
	}
}
