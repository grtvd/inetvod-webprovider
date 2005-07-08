/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;

import java.util.Date;

public class RentShowResp implements Writeable
{
	/* Constants */
	public static final int ShowURLMaxLength = 4096;

	/* Fields */
	protected Date fAvailableUntil;
	protected String fShowURL;

	/* Getters/Setters */
	public void setAvailableUntil(Date availableUntil) { fAvailableUntil = availableUntil; }

	public void setShowURL(String showURL) { fShowURL = showURL; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDateTime("AvailableUntil", fAvailableUntil);
		writer.writeString("ShowURL", fShowURL, ShowURLMaxLength);
	}
}
