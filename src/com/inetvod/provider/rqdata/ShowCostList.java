/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.inetvod.common.core.CtorUtil;

public class ShowCostList extends ArrayList<ShowCost>
{
	public static final Constructor<ShowCostList> Ctor = CtorUtil.getCtorDefault(ShowCostList.class);
}
