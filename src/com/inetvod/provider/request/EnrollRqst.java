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
		fUserID = filer.readString("UserID", 64);
		fPassword = filer.readString("Password", 32);
		fFirstName = filer.readString("FirstName", 16);
		fLastName = filer.readString("LastName", 32);
		fAddrStreet1 = filer.readString("AddrStreet1", 64);
		fAddrStreet2 = filer.readString("AddrStreet2", 64);
		fCity = filer.readString("City", 64);
		fState = filer.readString("State", 64);
		fPostalCode = filer.readString("PostalCode", 32);
		fCountry = filer.readString("Country", 64);
		fPhone = filer.readString("Phone", 32);
		fBirthDate = filer.readDate("BirthDate");
		fCurrencyID = filer.readString("CurrencyID", CurrencyID.MaxLength);
	}

	public void writeTo(DataWriter filer) throws Exception
	{
		filer.writeString("UserID", fUserID, 64);
		filer.writeString("Password", fPassword, 32);
		filer.writeString("FirstName", fFirstName, 16);
		filer.writeString("LastName", fLastName, 32);
		filer.writeString("AddrStreet1", fAddrStreet1, 64);
		filer.writeString("AddrStreet1", fAddrStreet2, 64);
		filer.writeString("City", fCity, 64);
		filer.writeString("State", fState, 64);
		filer.writeString("PostalCode", fPostalCode, 32);
		filer.writeString("Country", fCountry, 64);
		filer.writeString("Phone", fPhone, 32);
		filer.writeDate("BirthDate", fBirthDate);
		filer.writeString("CurrencyID", fCurrencyID, CurrencyID.MaxLength);
	}
}
