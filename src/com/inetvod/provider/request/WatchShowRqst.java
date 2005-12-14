/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.DataManager;
import com.inetvod.provider.rqdata.License;
import com.inetvod.provider.rqdata.LicenseMethod;
import com.inetvod.provider.rqdata.Show;
import com.inetvod.provider.rqdata.ShowID;
import com.inetvod.provider.rqdata.StatusCode;

public class WatchShowRqst extends AuthenRequestable
{
	/* Constants */
	public static final int PlayerIPAddressMaxLength = 16;

	/* Properties */
	protected ShowID fShowID;
	protected String fPlayerIPAddress;

	protected Show fShow;

	/* Construction */
	public WatchShowRqst(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public Writeable fulfillRequest()
	{
		// Fetch and validate the show
		if(!validateShow())
			return null;

		// validate autentication
		if(!confirmAuthentication())
			return null;

		//TODO: Confirm member has rented the Show, if not, fStatusCode = StatusCode.sc_ShowNotRented; return;

		//TODO: Confirm rental period for the Show has not expired, if so, fStatusCode = StatusCode.sc_ShowRentExpired; return;

		WatchShowResp response = new WatchShowResp();

		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.HOUR, 48);
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

	protected boolean isMemberRequest()
	{
		return wasMemberProvidered() || !fShow.isFreeShowCost();
	}

	protected boolean validateShow()
	{
		if(fShowID == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		//TODO: Actually fetch Show from DB
		fShow = DataManager.getThe().getShowList().findByID(fShowID);
		if(fShow == null)
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			return false;
		}

		return true;
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
