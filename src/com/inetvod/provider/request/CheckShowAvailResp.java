/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.ShowCost;

public class CheckShowAvailResp implements Writeable
{
	/* Properties */
	protected ShowCost fShowCost;

	/* Getters/Setters */
	public ShowCost getShowCost() { return fShowCost; }
	public void setShowCost(ShowCost showCost) { fShowCost = showCost; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeObject("ShowCost", fShowCost);
	}
}
