/**
 * Copyright © 2007 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary.
 */
package com.inetvod.provider.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.StringID;
import com.inetvod.common.core.CtorUtil;

public class ShowFormatID extends StringID
{
	public static final Constructor<ShowFormatID> CtorString = CtorUtil.getCtorString(ShowFormatID.class);
	public static final int MaxLength = 64;

	public ShowFormatID(String value)
	{
		super(value);
	}
}
