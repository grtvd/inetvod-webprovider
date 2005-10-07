/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.core;

public class Logger
{
	private static String buildMethod(Class objClass, String method)
	{
		String className = objClass.getName();
		String[] parts = className.split("\\.");
		if(parts.length >= 2)
			className = parts[parts.length - 2] + "." + parts[parts.length - 1];
		return String.format("%s.%s", className, method);
	}

	private static String buildMethod(Object objClass, String method)
	{
		return buildMethod(objClass.getClass(), method);
	}

	private static org.apache.log4j.Logger getLogger()
	{
		return org.apache.log4j.Logger.getLogger(Logger.class);
	}

	public static void logInfo(Class objClass, String method, String message)
	{
		getLogger().info(String.format("%s|%s", buildMethod(objClass, method), message));
	}

	public static void logInfo(Object objClass, String method, String message)
	{
		logInfo(objClass.getClass(), method, message);
	}

	public static void logErr(Class objClass, String method, String message, Exception e)
	{
		getLogger().error(String.format("%s|%s", buildMethod(objClass, method), message), e);
	}

	public static void logErr(Object objClass, String method, String message, Exception e)
	{
		logErr(objClass.getClass(), method, message, e);
	}
}
