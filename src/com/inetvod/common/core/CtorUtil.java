package com.inetvod.common.core;

import java.lang.reflect.Constructor;

/**
 * Created by IntelliJ IDEA.
 * User: Bob
 * Date: Jun 8, 2004
 * Time: 11:36:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class CtorUtil
{
	public static Constructor getCtorDefault(Class cl)
	{
		try
		{
			return cl.getConstructor(new Class[] {} );
		}
		catch(Exception e)
		{
		}

		return null;
	}
	public static Constructor getCtorString(Class cl)
	{
		try
		{
			return cl.getConstructor(new Class[] { String.class } );
		}
		catch(Exception e)
		{
		}

		return null;
	}
}
