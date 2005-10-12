/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import java.util.Date;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.Address;
import com.inetvod.provider.rqdata.StatusCode;

public class EnrollRqst extends AuthenRequestable
{
	/* Constants */
	public static final int UserIDMaxLength = 64;
	public static final int PasswordMaxLength = 32;
	public static final int FirstNameMaxLength = 32;
	public static final int LastNameMaxLength = 32;
	public static final int EmailMaxLength = 64;

	/* Properties */
	protected String fUserID;
	protected String fPassword;
	protected String fFirstName;
	protected String fLastName;
	protected String fEmail;

	protected Date fBirthDate;
	protected Address fShippingAddress;
	protected Address fBillingAddress;

	/* Construction */
	public EnrollRqst(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	/* Implementation */
	protected boolean isMemberRequest() { return false; }

	public Writeable fulfillRequest()
	{
		// validate autentication
		if(!confirmAuthentication())
			return null;

		//TODO: If this request is not supported
		// return StatusCode.sc_RequestNotSupported;

		// Validate the request
		if(!validateRequest())
			return null;

		// TODO: Confirm fUserID is unique.  If not, set fStatusCode = StatusCode.sc_UserIDInUse; and return

		EnrollResp response = new EnrollResp();

		// Enroll user to membership
		//TODO: Enroll the user

		fStatusCode = StatusCode.sc_Success;

		return response;
	}

	protected boolean validateRequest()
	{
		// Have the required fields been specified?
		if((fUserID == null) || (fUserID.length() == 0)
			|| (fPassword == null) || (fPassword.length() == 0)
			|| (fFirstName == null) || (fFirstName.length() == 0)
			|| (fLastName == null) || (fLastName.length() == 0)
			|| (fEmail == null) || (fEmail.length() == 0))
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		//TODO: Is the ShippingAddress required
		if((fShippingAddress == null) || !isValidAddress(fShippingAddress))
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		//TODO: Is the BillingAddress required
		if((fBillingAddress == null) || !isValidAddress(fBillingAddress))
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return false;
		}

		return true;
	}

	protected boolean isValidAddress(Address address)
	{
		if((address.getAddrStreet1() == null) || (address.getAddrStreet1().length() == 0)
			|| (address.getCity() == null) || (address.getCity().length() == 0)
			|| (address.getState() == null) || (address.getState().length() == 0)
			|| (address.getPostalCode() == null) || (address.getPostalCode().length() == 0)
			|| (address.getCountry() == null)
			|| (address.getPhone() == null) || (address.getPhone().length() == 0))
		{
			return false;
		}

		return true;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fUserID = reader.readString("UserID", UserIDMaxLength);
		fPassword = reader.readString("Password", PasswordMaxLength);
		fFirstName = reader.readString("FirstName", FirstNameMaxLength);
		fLastName = reader.readString("LastName", LastNameMaxLength);
		fEmail = reader.readString("Email", EmailMaxLength);

		fBirthDate = reader.readDate("BirthDate");
		fShippingAddress = reader.readObject("ShippingAddress", Address.CtorDataReader);
		fBillingAddress = reader.readObject("BillingAddress", Address.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("UserID", fUserID, UserIDMaxLength);
		writer.writeString("Password", fPassword, PasswordMaxLength);
		writer.writeString("FirstName", fFirstName, FirstNameMaxLength);
		writer.writeString("LastName", fLastName, LastNameMaxLength);
		writer.writeString("Email", fEmail, EmailMaxLength);

		writer.writeDate("BirthDate", fBirthDate);
		writer.writeObject("ShippingAddress", fShippingAddress);
		writer.writeObject("BillingAddress", fBillingAddress);
	}
}
