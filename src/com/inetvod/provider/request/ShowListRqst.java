/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.dbdata.ShowID;
import com.inetvod.common.dbdata.ShowIDList;

public class ShowListRqst extends SessionRequestable
{
	public ShowListRqst(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	public Writeable fulfillRequest() throws Exception
	{
		ShowListResp response = new ShowListResp();
		ShowIDList showIDList = response.ShowIDList;

		showIDList.add(new ShowID("1"));
		showIDList.add(new ShowID("2"));
		showIDList.add(new ShowID("3"));
		showIDList.add(new ShowID("4"));
		showIDList.add(new ShowID("5"));

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
