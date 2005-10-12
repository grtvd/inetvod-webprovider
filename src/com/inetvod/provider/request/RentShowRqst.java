/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import java.util.Calendar;
import java.util.Date;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.DataManager;
import com.inetvod.common.dbdata.License;
import com.inetvod.common.dbdata.LicenseMethod;
import com.inetvod.common.dbdata.Payment;
import com.inetvod.common.dbdata.Show;
import com.inetvod.common.dbdata.ShowCost;
import com.inetvod.common.dbdata.ShowCostType;
import com.inetvod.common.dbdata.ShowFormat;
import com.inetvod.common.dbdata.ShowID;
import com.inetvod.provider.rqdata.StatusCode;

public class RentShowRqst extends AuthenRequestable
{
	/* Constants */
	public static final int PlayerIPAddressMaxLength = 16;

	/* Properties */
	protected ShowID fShowID;
	protected String fPlayerIPAddress;
	protected ShowFormat fShowFormat;
	protected ShowCost fApprovedCost;
	protected Payment fPayment;

	/* Construction */
	public RentShowRqst(DataReader filer) throws Exception
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

		// Validate the request
		if(!validateShowFormat())
			return null;

		if(!validateShowCost())
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

	protected boolean validateShowFormat()
	{
		if(fShowFormat == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		//TODO: Confirm ShowFormat is valid.  If not, set fStatusCode = StatusCode.sc_RequestInvalid; and return false

		return true;
	}

	protected boolean validateShowCost()
	{
		if(fApprovedCost == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		//TODO: Confirm ApprovedCost is valid.  If not, set fStatusCode = StatusCode.sc_RequestInvalid; and return false

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
