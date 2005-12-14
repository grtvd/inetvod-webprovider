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
import com.inetvod.provider.rqdata.Payment;
import com.inetvod.provider.rqdata.Show;
import com.inetvod.provider.rqdata.ShowCost;
import com.inetvod.provider.rqdata.ShowCostType;
import com.inetvod.provider.rqdata.ShowFormat;
import com.inetvod.provider.rqdata.ShowID;
import com.inetvod.provider.rqdata.ShowRental;
import com.inetvod.provider.rqdata.StatusCode;

public class RentShowRqst extends AuthenRequestable
{
	/* Constants */
	public static final int PlayerIPAddressMaxLength = 16;

	/* Fields */
	protected ShowID fShowID;
	protected String fPlayerIPAddress;
	protected ShowFormat fShowFormat;
	protected ShowCost fApprovedCost;
	protected Payment fPayment;

	protected Show fShow;

	/* Construction */
	public RentShowRqst(DataReader reader) throws Exception
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

		if(!validateShowCost(showRental))
			return null;

		if(!validatePayment())
			return null;

		// Charge rental to member
		if(fApprovedCost.getShowCostType().equals(ShowCostType.Subscription))
		{
			//TODO: confirm membership level
			//TODO: if too low, set fStatusCode = StatusCode.sc_ShowLevelInsufficient; return
			//TODO: if no membership or unknown, set fStatusCode = StatusCode.sc_ShowNoAccess; return
		}
		else if(fApprovedCost.getShowCostType().equals(ShowCostType.PayPerView))
		{
			//TODO: charge credit card/account
			//TODO: if authorization fails, set fStatusCode = StatusCode.sc_ShowPaymentDenied; return
		}
		// else ShowCostType.Free

		RentShowResp response = new RentShowResp();

		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, 30);
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

	protected boolean validateShowCost(ShowRental showRental)
	{
		if(fApprovedCost == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		//TODO: Confirm ApprovedCost is valid.  If not, set fStatusCode = StatusCode.sc_RequestInvalid; and return false

		if(!showRental.getShowCostList().contains(fApprovedCost))
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			return false;
		}

		return true;
	}

	protected boolean validatePayment()
	{
		// TODO: Is the Show cost "pay-per-view", if not, Payment not needed.
		if(!fApprovedCost.getShowCostType().equals(ShowCostType.PayPerView))
			return true;

		//TODO: Validate payment information.  If invalid, set fStatusCode = StatusCode.sc_RequestInvalid; and return false

		return true;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
		fPlayerIPAddress = reader.readString("PlayerIPAddress", PlayerIPAddressMaxLength);
		fShowFormat = reader.readObject("ShowFormat", ShowFormat.CtorDataReader);
		fApprovedCost = reader.readObject("ApprovedCost", ShowCost.CtorDataReader);
		fPayment = reader.readObject("Payment", Payment.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
		writer.writeString("PlayerIPAddress", fPlayerIPAddress, PlayerIPAddressMaxLength);
		writer.writeObject("ShowFormat", fShowFormat);
		writer.writeObject("ApprovedCost", fApprovedCost);
		writer.writeObject("Payment", fPayment);
	}
}
