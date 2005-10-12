/**
 * Copyright � 2004-2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.core;

import java.lang.reflect.Constructor;

public class Money implements Readable, Writeable
{
	public static final Constructor<Money> CtorDataReader = DataReader.getCtor(Money.class);

	/* Properties */
	protected CurrencyID fCurrencyID;
	protected Double fAmount;

	/* Getters and Setters */
	public CurrencyID getCurrencyID() { return fCurrencyID; }
	public void setCurrencyID(CurrencyID currencyID) { fCurrencyID = currencyID; }

	public Double getAmount() { return fAmount; }
	public void setAmount(Double amount) { fAmount = amount; }

	/* Constuction Methods */
	protected Money()
	{
	}

	public Money(CurrencyID currencyID, Double amount)
	{
		fCurrencyID = currencyID;
		fAmount = amount;
	}

	public Money(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fCurrencyID = reader.readDataID("CurrencyID", CurrencyID.MaxLength, CurrencyID.CtorString);
		fAmount = reader.readDouble("Amount");
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("CurrencyID", fCurrencyID, CurrencyID.MaxLength);
		writer.writeDouble("Amount", fAmount);
	}
}
