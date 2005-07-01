/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.dbdata.ShowCostList;

public class CheckShowAvailResp implements Writeable
{
	/* Properties */
	protected ShowCostList fShowCostList;

	/* Getters/Setters */
	public ShowCostList getShowCostList() { return fShowCostList; }
	public void setShowCostList(ShowCostList showCostList) { fShowCostList = showCostList; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeList("ShowCost", fShowCostList);
	}
}
