/**
 * Copyright © 2004 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.StringID;
import com.inetvod.common.core.CtorUtil;

import java.lang.reflect.Constructor;

public class RatingID extends StringID
{
	public static final Constructor CtorString = CtorUtil.getCtorString(RatingID.class);
	public static final int MaxLength = 32;

	public RatingID(String value)
	{
		super(value);
	}
}
