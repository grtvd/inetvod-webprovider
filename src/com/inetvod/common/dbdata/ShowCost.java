/**
 * Copyright © 2004 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Money;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;

import java.lang.reflect.Constructor;

public class ShowCost implements Readable, Writeable
{
	/* Constants */
	public static Constructor CtorDataReader = DataReader.getCtor(ShowCost.class);
	public static final int DescriptionMaxLength = 32;

	/* Properties */
	protected ShowCostType fShowCostType;
	protected Money fMoney;
	protected String fDescription;
	protected Short fRentalHours;

	/* Getters and Setters */
	public ShowCostType getShowCostType() { return fShowCostType; }
	public void setShowCostType(ShowCostType showCostType) { fShowCostType = showCostType; }

	public Money getMoney() { return fMoney; }
	public void setMoney(Money money) { fMoney = money; }

	public String getDescription() { return fDescription; }
	public void setDescription(String description) { fDescription = description; }

	public Short getRentalHours() { return fRentalHours; }
	public void setRentalHours(Short rentalHours) { fRentalHours = rentalHours; }

	/* Constuction Methods */
	public ShowCost()
	{
	}

	public ShowCost(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowCostType = ShowCostType.convertFromString(reader.readString("ShowCostType", ShowCostType.MaxLength));
		fMoney = (Money) reader.readObject("Money", Money.CtorDataFiler);
		fDescription = reader.readString("Description", DescriptionMaxLength);
		fRentalHours = reader.readShort("RentalHours");
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("ShowCostType", ShowCostType.convertToString(fShowCostType), ShowCostType.MaxLength);
		writer.writeObject("Money", fMoney);
		writer.writeString("Description", fDescription, DescriptionMaxLength);
		writer.writeShort("RentalHours", fRentalHours);
	}
}