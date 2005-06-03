/**
 * Copyright © 2004 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.core;

public class StatusCode
{
	public static final StatusCode sc_Success = new StatusCode(0);

	public static final StatusCode sc_InvalidUserID = new StatusCode(1000);
//	public static final StatusCode sc_UserIDInUse = new StatusCode(1001);
	public static final StatusCode sc_UserIDPasswordMismatch = new StatusCode(1002);
	public static final StatusCode sc_InvalidProviderUserIDPassword = new StatusCode(1003);

	public static final StatusCode sc_AlreadyEnrolledAtProvider = new StatusCode(1004);
	public static final StatusCode sc_NoAutoProviderEnrollment = new StatusCode(1005);

	public static final StatusCode sc_ShowSearch_NeedCriteiia = new StatusCode(1020);

	public static final StatusCode sc_GeneralError = new StatusCode(9999);

	protected int fValue;

	private StatusCode(int value)
	{
		fValue = value;
	}

	public static StatusCode convertFromInt(Integer value)
	{
		if(value == null)
			return sc_Success;

		int val = value.intValue();

		if(val == sc_Success.fValue)
			return sc_Success;
		if(val == sc_InvalidUserID.fValue)
			return sc_InvalidUserID;
//		if(val == sc_UserIDInUse.fValue)
//			return sc_UserIDInUse;
		if(val == sc_UserIDPasswordMismatch.fValue)
			return sc_UserIDPasswordMismatch;
		if(val == sc_InvalidProviderUserIDPassword.fValue)
			return sc_InvalidProviderUserIDPassword;
		if(val == sc_AlreadyEnrolledAtProvider.fValue)
			return sc_AlreadyEnrolledAtProvider;

		if(val == sc_ShowSearch_NeedCriteiia.fValue)
			return sc_ShowSearch_NeedCriteiia;
		
		return sc_GeneralError;
	}

	public static Integer convertToInt(StatusCode statusCode)
	{
		if(statusCode == null)
			return new Integer(sc_Success.fValue);

		return new Integer(statusCode.fValue);
	}
}
