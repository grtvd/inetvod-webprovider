/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.StringID;
import com.inetvod.common.core.CtorUtil;

import java.lang.reflect.Constructor;

public class CategoryID extends StringID
{
	public static final Constructor CtorString = CtorUtil.getCtorString(CategoryID.class);
	public static final int MaxLength = 32;

	public CategoryID(String value)
	{
		super(value);
	}
}
