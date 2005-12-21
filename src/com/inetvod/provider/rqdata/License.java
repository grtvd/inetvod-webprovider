/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;

public class License implements Readable, Writeable
{
	/* Constants */
	public static Constructor<License> CtorDataReader = DataReader.getCtor(License.class);
	public static final int ShowURLMaxLength = 4096;
	public static final int LicenseURLMaxLength = 4096;

	/* Properties */
	protected LicenseMethod fLicenseMethod;
	protected String fShowURL;
	protected String fLicenseURL;

	/* Constuction */
	public License()
	{
	}

	public License(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Getters and Setters */
	public LicenseMethod getLicenseMethod() { return fLicenseMethod; }
	public void setLicenseMethod(LicenseMethod licenseMethod) { fLicenseMethod = licenseMethod; }

	public String getShowURL() { return fShowURL; }
	public void setShowURL(String showURL) { fShowURL = showURL; }

	public String getLicenseURL() { return fLicenseURL; }
	public void setLicenseURL(String licenseURL) { fLicenseURL = licenseURL; }

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fLicenseMethod = LicenseMethod.convertFromString(reader.readString("LicenseMethod", LicenseMethod.MaxLength));
		fShowURL = reader.readString("ShowURL", ShowURLMaxLength);
		fLicenseURL = reader.readString("LicenseURL", LicenseURLMaxLength);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("LicenseMethod", LicenseMethod.convertToString(fLicenseMethod), LicenseMethod.MaxLength);
		writer.writeString("ShowURL", fShowURL, ShowURLMaxLength);
		writer.writeString("LicenseURL", fLicenseURL, LicenseURLMaxLength);
	}
}
