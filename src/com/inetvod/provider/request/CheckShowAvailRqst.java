/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.CurrencyID;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Money;
import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.ShowCost;
import com.inetvod.common.dbdata.ShowCostList;
import com.inetvod.common.dbdata.ShowCostType;
import com.inetvod.common.dbdata.ShowID;
import com.inetvod.common.dbdata.ShowList;
import com.inetvod.common.dbdata.Show;

public class CheckShowAvailRqst implements Requestable
{
	/* Properties */
	protected ShowID fShowID;

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;

	public StatusCode getStatusCode() { return fStatusCode; }

	/* Construction */
	public CheckShowAvailRqst(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	/* Implementation */
	public Writeable fulfillRequest() throws Exception
	{
		CheckShowAvailResp response = new CheckShowAvailResp();

		// Validate the request
		if(validateRequest())
		{
			// TODO: confirm Show is still avaliable for renting
			// If Member does not have a sufficient subscription level, return StatusCode.sc_ShowLevelInsufficient;
			// If Member is not allowed to rent this Show for other reason, return StatusCode.sc_ShowNoAccess;

			// TODO: fetch the request Show (throws an exception if invalid)
			Show show = ShowList.getAll().findByID(fShowID);
			if(show == null)
				fStatusCode = StatusCode.sc_RequestInvalid;
			else
			{
				// TODO: fetch various show costs and rental periods for specific Member
				ShowCostList showCostList = new ShowCostList();
				ShowCost showCost;

				showCost = new ShowCost();
				showCost.setShowCostType(ShowCostType.PayPerView);
				showCost.setCost(new Money(CurrencyID.USD, 2.95));
				showCost.setCostDisplay("$2.95");
				showCost.setRentalHours((short)48);
				showCostList.add(showCost);

				showCost = new ShowCost();
				showCost.setShowCostType(ShowCostType.PayPerView);
				showCost.setCost(new Money(CurrencyID.USD, 4.95));
				showCost.setCostDisplay("$4.95");
				showCost.setRentalHours((short)168);
				showCostList.add(showCost);

				// set response data
				response.setShowCostList(showCostList);

				fStatusCode = StatusCode.sc_Success;
			}
		}

		return response;
	}

	protected boolean validateRequest()
	{
		// Have the required fields been specified?
		if(fShowID == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		return true;
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
