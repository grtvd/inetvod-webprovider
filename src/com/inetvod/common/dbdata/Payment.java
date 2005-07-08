/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;

import java.lang.reflect.Constructor;

public class Payment implements Readable, Writeable
{
	/* Constants */
	public static Constructor CtorDataReader = DataReader.getCtor(Payment.class);

	/* Properties */
	protected PaymentType fPaymentType;
	protected CreditCard fCreditCard;

	/* Getters and Setters */

	/* Constuction Methods */
	public Payment(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fPaymentType = PaymentType.convertFromString(reader.readString("PaymentType", PaymentType.MaxLength));
		fCreditCard = (CreditCard) reader.readObject("CreditCard", CreditCard.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("PaymentType", PaymentType.convertToString(fPaymentType), PaymentType.MaxLength);
		writer.writeObject("CreditCard", fCreditCard);
	}
}
