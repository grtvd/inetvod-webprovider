/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;
import com.inetvod.provider.rqdata.ShowDetailList;

public class ShowDetailResp implements Writeable
{
	/* Fields */
	public ShowDetailList ShowDetailList = new ShowDetailList();

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeList("ShowDetail", ShowDetailList);
	}
}
