/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.ShowID;
import com.inetvod.provider.rqdata.ShowIDList;

public class ShowListResp implements Writeable
{
	/* Fields */
	public ShowIDList ShowIDList = new ShowIDList();

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeStringList("ShowID", ShowIDList, ShowID.MaxLength);
	}
}
