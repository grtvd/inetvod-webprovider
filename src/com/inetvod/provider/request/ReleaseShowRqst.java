/**
 * Copyright © 2005-2007 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.DataManager;
import com.inetvod.provider.rqdata.Show;
import com.inetvod.provider.rqdata.ShowID;
import com.inetvod.provider.rqdata.StatusCode;
import com.inetvod.provider.rqdata.ShowFormat;
import com.inetvod.provider.rqdata.ShowRental;

public class ReleaseShowRqst extends AuthenRequestable
{
	/* Properties */
	protected ShowID fShowID;
	protected ShowFormat fShowFormat;

	protected Show fShow;

	/* Construction */
	public ReleaseShowRqst(DataReader reader) throws Exception
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

		// Validate the request
		ShowRental showRental = validateShowFormat(fShow);
		if(showRental == null)
			return null;

		//TODO: Confirm member has rented the Show, if not, fStatusCode = StatusCode.sc_ShowNotRented; return;

		//TODO: remove the Show from the Member's rental queue

		fStatusCode = StatusCode.sc_Success;
		return new ReleaseShowResp();
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

	protected ShowRental validateShowFormat(Show show)
	{
		if(fShowFormat == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return null;
		}

		//TODO: Confirm ShowFormat is valid.  If not, set fStatusCode = StatusCode.sc_RequestInvalid; and return false

		ShowRental showRental = show.getShowRentalList().findByShowFormat(fShowFormat);
		if(showRental == null)
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			return null;
		}

		return showRental;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
		fShowFormat = reader.readObject("ShowFormat", ShowFormat.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
		writer.writeObject("ShowFormat", fShowFormat);
	}
}
