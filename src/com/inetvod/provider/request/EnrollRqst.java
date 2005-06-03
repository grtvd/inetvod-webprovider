/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.Requestable;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.CurrencyID;

import java.util.Date;

public class EnrollRqst implements Requestable
{
	/* Constants */
	public static final int UserIDMaxLength = 64;
	public static final int PasswordMaxLength = 32;
	public static final int FirstNameMaxLength = 32;
	public static final int LastNameMaxLength = 32;
	public static final int AddrStreetMaxLength = 64;
	public static final int CityMaxLength = 64;
	public static final int StateMaxLength = 64;
	public static final int PostalCodeMaxLength = 32;
	public static final int CountryMaxLength = 64;
	public static final int PhoneMaxLength = 32;

	/* Properties */
	protected String fUserID;
	protected String fPassword;
	protected String fFirstName;
	protected String fLastName;
	protected String fAddrStreet1;
	protected String fAddrStreet2;
	protected String fCity;
	protected String fState;
	protected String fPostalCode;
	protected String fCountry;
	protected String fPhone;
	protected Date fBirthDate;
	protected String fCurrencyID;

	protected StatusCode fStatusCode = StatusCode.sc_GeneralError;
	public StatusCode getStatusCode() { return fStatusCode; }

	public EnrollRqst(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	public Writeable fulfillRequest()
	{
		EnrollResp response = new EnrollResp();

		fStatusCode = StatusCode.sc_Success;
		return response;
	}

	public void readFrom(DataReader filer) throws Exception
	{
		fUserID = filer.readString("UserID", UserIDMaxLength);
		fPassword = filer.readString("Password", PasswordMaxLength);
		fFirstName = filer.readString("FirstName", FirstNameMaxLength);
		fLastName = filer.readString("LastName", LastNameMaxLength);
		fAddrStreet1 = filer.readString("AddrStreet1", AddrStreetMaxLength);
		fAddrStreet2 = filer.readString("AddrStreet2", AddrStreetMaxLength);
		fCity = filer.readString("City", CityMaxLength);
		fState = filer.readString("State", StateMaxLength);
		fPostalCode = filer.readString("PostalCode", PostalCodeMaxLength);
		fCountry = filer.readString("Country", CountryMaxLength);
		fPhone = filer.readString("Phone", PhoneMaxLength);
		fBirthDate = filer.readDate("BirthDate");
		fCurrencyID = filer.readString("CurrencyID", CurrencyID.MaxLength);
	}

	public void writeTo(DataWriter filer) throws Exception
	{
		filer.writeString("UserID", fUserID, UserIDMaxLength);
		filer.writeString("Password", fPassword, PasswordMaxLength);
		filer.writeString("FirstName", fFirstName, FirstNameMaxLength);
		filer.writeString("LastName", fLastName, LastNameMaxLength);
		filer.writeString("AddrStreet1", fAddrStreet1, AddrStreetMaxLength);
		filer.writeString("AddrStreet1", fAddrStreet2, AddrStreetMaxLength);
		filer.writeString("City", fCity, CityMaxLength);
		filer.writeString("State", fState, StateMaxLength);
		filer.writeString("PostalCode", fPostalCode, PostalCodeMaxLength);
		filer.writeString("Country", fCountry, CountryMaxLength);
		filer.writeString("Phone", fPhone, PhoneMaxLength);
		filer.writeDate("BirthDate", fBirthDate);
		filer.writeString("CurrencyID", fCurrencyID, CurrencyID.MaxLength);
	}
}
