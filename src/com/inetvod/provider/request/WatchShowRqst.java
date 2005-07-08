/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.Show;
import com.inetvod.common.dbdata.ShowID;
import com.inetvod.common.dbdata.ShowList;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class WatchShowRqst extends AuthenRequestable
{
	/* Properties */
	protected ShowID fShowID;

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
		//TODO: set show DRM credentials
		response.setShowAccessKey(UUID.randomUUID().toString());

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
		Show show = ShowList.getAll().findByID(fShowID);
		if(show == null)
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			return null;
		}

		return show;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = (ShowID)reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
	}
}
