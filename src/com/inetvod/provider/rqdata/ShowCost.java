/**
 * Copyright � 2004-2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Money;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.CompUtil;

public class ShowCost implements Readable, Writeable
{
	/* Constants */
	public static Constructor<ShowCost> CtorDataReader = DataReader.getCtor(ShowCost.class);
	public static final int DescriptionMaxLength = 32;

	/* Fields */
	private ShowCostType fShowCostType;
	private Money fCost;
	private String fCostDisplay;
	private Short fRentalHours;

	/* Getters and Setters */
	public ShowCostType getShowCostType() { return fShowCostType; }
	public void setShowCostType(ShowCostType showCostType) { fShowCostType = showCostType; }

	public Money getCost() { return fCost; }
	public void setCost(Money cost) { fCost = cost; }

	public String getCostDisplay() { return fCostDisplay; }
	public void setCostDisplay(String costDisplay) { fCostDisplay = costDisplay; }

	public Short getRentalHours() { return fRentalHours; }
	public void setRentalHours(Short rentalHours) { fRentalHours = rentalHours; }

	/* Constuction */
	public ShowCost()
	{
	}

	public ShowCost(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	@SuppressWarnings({"NonFinalFieldReferenceInEquals"})
	@Override public boolean equals(Object obj)
	{
		if(!(obj instanceof ShowCost))
			return false;
		ShowCost showCost = (ShowCost)obj;

		return CompUtil.areEqual(fShowCostType, showCost.fShowCostType)
			&& CompUtil.areEqual(fCost, showCost.fCost)
			&& CompUtil.areEqual(fCostDisplay, showCost.fCostDisplay)
			&& CompUtil.areEqual(fRentalHours, showCost.fRentalHours);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowCostType = ShowCostType.convertFromString(reader.readString("ShowCostType", ShowCostType.MaxLength));
		fCost = reader.readObject("Cost", Money.CtorDataReader);
		fCostDisplay = reader.readString("CostDisplay", DescriptionMaxLength);
		fRentalHours = reader.readShort("RentalHours");
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("ShowCostType", ShowCostType.convertToString(fShowCostType), ShowCostType.MaxLength);
		writer.writeObject("Cost", fCost);
		writer.writeString("CostDisplay", fCostDisplay, DescriptionMaxLength);
		writer.writeShort("RentalHours", fRentalHours);
	}
}