/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import com.inetvod.common.core.Readable;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.CountryID;

import java.lang.reflect.Constructor;

public class Address implements Readable, Writeable
{
	/* Constants */
	public static Constructor CtorDataReader = DataReader.getCtor(Address.class);

	public static final int AddrStreetMaxLength = 64;
	public static final int CityMaxLength = 64;
	public static final int StateMaxLength = 64;
	public static final int PostalCodeMaxLength = 32;
	public static final int PhoneMaxLength = 32;

	/* Properties */
	protected String fAddrStreet1;
	protected String fAddrStreet2;
	protected String fCity;
	protected String fState;
	protected String fPostalCode;
	protected CountryID fCountry;
	protected String fPhone;

	/* Getters/Setters */
	public String getAddrStreet1() { return fAddrStreet1; }
	public void setAddrStreet1(String addrStreet1) { fAddrStreet1 = addrStreet1; }

	public String getAddrStreet2() { return fAddrStreet2; }
	public void setAddrStreet2(String addrStreet2) { fAddrStreet2 = addrStreet2; }

	public String getCity() { return fCity; }
	public void setCity(String city) { fCity = city; }

	public String getState() { return fState; }
	public void setState(String state) { fState = state; }

	public String getPostalCode() { return fPostalCode; }
	public void setPostalCode(String postalCode) { fPostalCode = postalCode; }

	public CountryID getCountry() { return fCountry; }
	public void setCountry(CountryID country) { fCountry = country; }

	public String getPhone() { return fPhone; }
	public void setPhone(String phone) { fPhone = phone; }

	/* Construction */
	public Address(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fAddrStreet1 = reader.readString("AddrStreet1", AddrStreetMaxLength);
		fAddrStreet2 = reader.readString("AddrStreet2", AddrStreetMaxLength);
		fCity = reader.readString("City", CityMaxLength);
		fState = reader.readString("State", StateMaxLength);
		fPostalCode = reader.readString("PostalCode", PostalCodeMaxLength);
		fCountry = (CountryID)reader.readDataID("Country", CountryID.MaxLength, CountryID.CtorString);
		fPhone = reader.readString("Phone", PhoneMaxLength);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("AddrStreet1", fAddrStreet1, AddrStreetMaxLength);
		writer.writeString("AddrStreet1", fAddrStreet2, AddrStreetMaxLength);
		writer.writeString("City", fCity, CityMaxLength);
		writer.writeString("State", fState, StateMaxLength);
		writer.writeString("PostalCode", fPostalCode, PostalCodeMaxLength);
		writer.writeDataID("Country", fCountry, CountryID.MaxLength);
		writer.writeString("Phone", fPhone, PhoneMaxLength);
	}
}
