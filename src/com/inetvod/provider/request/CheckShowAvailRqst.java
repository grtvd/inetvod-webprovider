/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Logger;
import com.inetvod.common.core.Writeable;
import com.inetvod.provider.rqdata.DataManager;
import com.inetvod.provider.rqdata.Show;
import com.inetvod.provider.rqdata.ShowCost;
import com.inetvod.provider.rqdata.ShowFormat;
import com.inetvod.provider.rqdata.ShowID;
import com.inetvod.provider.rqdata.ShowRental;
import com.inetvod.provider.rqdata.ShowRentalList;
import com.inetvod.provider.rqdata.StatusCode;

public class CheckShowAvailRqst extends AuthenRequestable
{
	/* Fields */
	private ShowID fShowID;
	private ShowFormat fShowFormat;
	private ShowCost fShowCost;

	/* Construction */
	public CheckShowAvailRqst(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public Writeable fulfillRequest() throws Exception
	{
		// validate autentication
		if(!confirmAuthentication())
			return null;

		// Fetch and validate the show
		Show show = validateShow();
		if (show == null)
			return null;

		// TODO: confirm Show is still avaliable for renting
		// If Member does not have a sufficient subscription level, return StatusCode.sc_ShowLevelInsufficient;
		// If Member is not allowed to rent this Show for other reason, return StatusCode.sc_ShowNoAccess;

		// TODO: fetch various rental periods for specific Member based on ShowFormat
		ShowRental showRental = validateShowFormat(show);
		if (showRental == null)
			return null;

		// TODO: confirm ShowCost is still valid, and/or that specific Member has access (Subscription)
		if(!validateShowCost(showRental))
			return null;

		// TODO: Note: if PayPerView, a new ShowCost, with different cost, rental period can be returned
		// set response data
		CheckShowAvailResp response = new CheckShowAvailResp();
		response.setShowCost(fShowCost);

		fStatusCode = StatusCode.sc_Success;

		return response;
	}

	private Show validateShow()
	{
		if(fShowID == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			Logger.logInfo(this, "validateShow", "ShowID == null");
			return null;
		}

		//TODO: Actually fetch Show from DB
		Show show = DataManager.getThe().getShowList().findByID(fShowID);
		if(show == null)
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			Logger.logInfo(this, "validateShow", String.format("ShowID(%s) not found", fShowID));
			return null;
		}

		return show;
	}

	private ShowRental validateShowFormat(Show show)
	{
		if(fShowFormat == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			Logger.logInfo(this, "validateShowFormat", "ShowFormat == null");
			return null;
		}

		ShowRentalList showRentalList = show.getShowRentalList();
		ShowRental showRental = showRentalList.findByShowFormat(fShowFormat);
		if(showRental == null)
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			Logger.logInfo(this, "validateShowFormat", "ShowFormat not found");
			return null;
		}

		return showRental;
	}

	private boolean validateShowCost(ShowRental showRental)
	{
		if(fShowCost == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			Logger.logInfo(this, "validateShowCost", "fShowCost == null");
			return false;
		}

		if(!showRental.getShowCostList().contains(fShowCost))
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			Logger.logInfo(this, "validateShowCost", "ShowCost not found");
			return false;
		}

		return true;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
		fShowFormat = reader.readObject("ShowFormat", ShowFormat.CtorDataReader);
		fShowCost = reader.readObject("ShowCost", ShowCost.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
		writer.writeObject("ShowFormat", fShowFormat);
		writer.writeObject("ShowCost", fShowCost);
	}
}
