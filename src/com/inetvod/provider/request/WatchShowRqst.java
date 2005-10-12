/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import java.util.Calendar;
import java.util.Date;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.DataManager;
import com.inetvod.common.dbdata.License;
import com.inetvod.common.dbdata.LicenseMethod;
import com.inetvod.common.dbdata.Show;
import com.inetvod.common.dbdata.ShowID;

public class WatchShowRqst extends AuthenRequestable
{
	/* Constants */
	public static final int PlayerIPAddressMaxLength = 16;

	/* Properties */
	protected ShowID fShowID;
	protected String fPlayerIPAddress;

	/* Construction */
	public WatchShowRqst(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	/* Implementation */
	public Writeable fulfillRequest()
	{
		// validate autentication
		if(!confirmAuthentication())
			return null;

		// TODO: Confirm valid Show
		// Fetch and validate the show
		Show show = validateShow();
		if (show == null)
			return null;

		//TODO: Confirm member has rented the Show, if not, fStatusCode = StatusCode.sc_ShowNotRented; return;

		//TODO: Confirm rental period for the Show has not expired, if so, fStatusCode = StatusCode.sc_ShowRentExpired; return;

		WatchShowResp response = new WatchShowResp();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		//TODO: set expriation date of rental, if any
		response.setAvailableUntil(cal.getTime());

		//TODO: return the shows licensing information
		License license = new License();
		license.setLicenseMethod(LicenseMethod.URLOnly);
		license.setShowURL("http://api.inetvod.com/mce/videos/TestVideo.wmv");
		response.setLicense(license);

		fStatusCode = StatusCode.sc_Success;

		return response;
	}

	protected Show validateShow()
	{
		if(fShowID == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return null;
		}

		//TODO: Actually fetch Show from DB
		Show show = DataManager.getThe().getShowList().findByID(fShowID);
		if(show == null)
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			return null;
		}

		return show;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
		fPlayerIPAddress = reader.readString("PlayerIPAddress", PlayerIPAddressMaxLength);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
		writer.writeString("PlayerIPAddress", fPlayerIPAddress, PlayerIPAddressMaxLength);
	}
}
