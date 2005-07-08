/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;

public class PingRqst extends AuthenRequestable
{
	/* Construction */
	public PingRqst(DataReader filer)
	{
		readFrom(filer);
	}

	/* Implementation */
	protected boolean isMemberRequest() { return false; }

	public Writeable fulfillRequest()
	{
		// validate autentication
		if(!confirmAuthentication())
			return null;

		fStatusCode = StatusCode.sc_Success;
		return new PingResp();
	}

	public void readFrom(DataReader filer)
	{
		// No Fields
	}

	public void writeTo(DataWriter filer)
	{
		// No Fields
	}
}
