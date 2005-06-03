/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.CtorUtil;

import java.util.ArrayList;
import java.lang.reflect.Constructor;

public class CategoryIDList extends ArrayList<CategoryID>
{
	public static final Constructor Ctor = CtorUtil.getCtorDefault(CategoryIDList.class);
}
