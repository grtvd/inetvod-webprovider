/**
 * Copyright © 2004-2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.StringID;
import com.inetvod.common.core.CtorUtil;

import java.lang.reflect.Constructor;

public class ShowID extends StringID
{
	public static final Constructor CtorString = CtorUtil.getCtorString(ShowID.class);
	public static final int MaxLength = 64;

	public ShowID(String value)
	{
		super(value);
	}

	public boolean equals(Object o)
	{
		if ((o == null) || !(o instanceof Show))
			return super.equals(o);

		return super.equals(((Show)o).getShowID());
	}
}
