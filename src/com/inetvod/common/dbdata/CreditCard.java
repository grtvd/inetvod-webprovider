/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;

import java.lang.reflect.Constructor;

public class CreditCard implements Readable, Writeable
{
	/* Constants */
	public static Constructor CtorDataReader = DataReader.getCtor(CreditCard.class);

	public static final int NameOnCCMaxLength = 64;
	public static final int CCTypeMaxLength = 16;
	public static final int CCNumberMaxLength = 32;
	public static final int CCSICMaxLength = 16;
	public static final int ExpireDateMaxLength = 6;

	/* Properties */
	protected String fNameOnCC;
	protected String fCCType;
	protected String fCCNumber;
	protected String fCCSIC;
	protected String fExpireDate;
	protected Address fBillingAddress;

	/* Getters/Setters */
	public String getNameOnCC() { return fNameOnCC; }
	public void setNameOnCC(String nameOnCC) { fNameOnCC = nameOnCC; }

	public String getCCType() { return fCCType; }
	public void setCCType(String CCType) { fCCType = CCType; }

	public String getCCNumber() { return fCCNumber; }
	public void setCCNumber(String CCNumber) { fCCNumber = CCNumber; }

	public String getCCSIC() { return fCCSIC; }
	public void setCCSIC(String CCSIC) { fCCSIC = CCSIC; }

	public String getExpireDate() { return fExpireDate; }
	public void setExpireDate(String expireDate) { fExpireDate = expireDate; }

	public Address getBillingAddress() { return fBillingAddress; }
	public void setBillingAddress(Address billingAddress) { fBillingAddress = billingAddress; }

	/* Implementation */
	public CreditCard(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fNameOnCC = reader.readString("NameOnCC", NameOnCCMaxLength);
		fCCType = reader.readString("CCType", CCTypeMaxLength);
		fCCNumber = reader.readString("CCNumber", CCNumberMaxLength);
		fCCSIC = reader.readString("CCSIC", CCSICMaxLength);
		fExpireDate = reader.readString("ExpireDate", ExpireDateMaxLength);
		fBillingAddress = (Address)reader.readObject("BillingAddress", Address.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("NameOnCC", fNameOnCC, NameOnCCMaxLength);
		writer.writeString("CCType", fCCType, CCTypeMaxLength);
		writer.writeString("CCNumber", fCCNumber, CCNumberMaxLength);
		writer.writeString("CCSIC", fCCSIC, CCSICMaxLength);
		writer.writeString("ExpireDate", fExpireDate, ExpireDateMaxLength);
		writer.writeObject("BillingAddress", fBillingAddress);
	}
}
