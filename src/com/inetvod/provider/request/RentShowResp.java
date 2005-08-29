/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import java.util.Date;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.License;

public class RentShowResp implements Writeable
{
	/* Constants */
	public static final int ShowURLMaxLength = 4096;

	/* Fields */
	protected Date fAvailableUntil;
	protected License fLicense;

	/* Getters/Setters */
	public void setAvailableUntil(Date availableUntil) { fAvailableUntil = availableUntil; }

	public void setLicense(License license) { fLicense = license; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDateTime("AvailableUntil", fAvailableUntil);
		writer.writeObject("License", fLicense);
	}
}
