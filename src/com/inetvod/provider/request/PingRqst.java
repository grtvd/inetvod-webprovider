/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;

public class PingRqst implements Requestable
{
	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public PingRqst(DataReader filer)
	{
		readFrom(filer);
	}

	public Writeable fulfillRequest()
	{
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
