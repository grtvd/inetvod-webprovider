/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;

public class License implements Writeable
{
	/* Constants */
	public static Constructor CtorDataReader = DataReader.getCtor(License.class);
	public static final int ShowURLMaxLength = 4096;
	public static final int LicenseURLMaxLength = 4096;

	/* Properties */
	protected LicenseMethod fLicenseMethod;
	protected String fShowURL;
	protected String fLicenseURL;

	/* Getters and Setters */
	public LicenseMethod getLicenseMethod() { return fLicenseMethod; }
	public void setLicenseMethod(LicenseMethod licenseMethod) { fLicenseMethod = licenseMethod; }

	public String getShowURL() { return fShowURL; }
	public void setShowURL(String showURL) { fShowURL = showURL; }

	public String getLicenseURL() { return fLicenseURL; }
	public void setLicenseURL(String licenseURL) { fLicenseURL = licenseURL; }

	/* Implementation */
	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("LicenseMethod", LicenseMethod.convertToString(fLicenseMethod), LicenseMethod.MaxLength);
		writer.writeString("ShowURL", fShowURL, ShowURLMaxLength);
		writer.writeString("LicenseURL", fLicenseURL, LicenseURLMaxLength);
	}
}
