/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.DataManager;
import com.inetvod.provider.rqdata.StatusCode;

public class ShowListRqst extends AuthenRequestable
{
	/* Construction */
	public ShowListRqst(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	protected boolean isMemberRequest() { return false; }

	public Writeable fulfillRequest() throws Exception
	{
		// validate autentication
		if(!confirmAuthentication())
			return null;

		ShowListResp response = new ShowListResp();
		response.ShowIDList = DataManager.getThe().getShowList().getShowIDList();

		fStatusCode = StatusCode.sc_Success;
		return response;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		// No Fields
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		// No Fields
	}
}
