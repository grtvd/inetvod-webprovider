/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;

import java.util.Date;

public class WatchShowResp implements Writeable
{
	/* Constants */
	public static final int ShowAccessKeyMaxLength = Short.MAX_VALUE;

	/* Fields */
	protected Date fAvailableUntil;
	protected String fShowAccessKey;

	/* Getters/Setters */
	public void setAvailableUntil(Date availableUntil) { fAvailableUntil = availableUntil; }

	public void setShowAccessKey(String showAccessKey) { fShowAccessKey = showAccessKey; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDateTime("AvailableUntil", fAvailableUntil);
		writer.writeString("ShowAccessKey", fShowAccessKey, ShowAccessKeyMaxLength);
	}
}
