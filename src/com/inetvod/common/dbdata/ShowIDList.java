/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.CtorUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class ShowIDList extends ArrayList<ShowID>
{
	public static final Constructor Ctor = CtorUtil.getCtorDefault(ShowIDList.class);
}
