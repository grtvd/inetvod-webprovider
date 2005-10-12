/**
 * Copyright © 2004 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.util.Arrays;
import java.util.List;

public class StatusCode
{
	public static final StatusCode sc_Success = new StatusCode(0);

	public static final StatusCode sc_RequestNotSupported = new StatusCode(1);
	public static final StatusCode sc_RequestMissingRequired = new StatusCode(2);
	public static final StatusCode sc_RequestInvalid = new StatusCode(3);

	public static final StatusCode sc_InvalidMemberUserID = new StatusCode(1000);
	public static final StatusCode sc_InvalidAdminUserID = new StatusCode(1001);
	public static final StatusCode sc_UserIDInUse = new StatusCode(1002);

	public static final StatusCode sc_ShowNoAccess = new StatusCode(1100);
	public static final StatusCode sc_ShowLevelInsufficient = new StatusCode(1101);
	public static final StatusCode sc_ShowNotRented = new StatusCode(1102);
	public static final StatusCode sc_ShowRentExpired = new StatusCode(1103);
	public static final StatusCode sc_ShowPaymentDenied = new StatusCode(1104);

	public static final StatusCode sc_GeneralError = new StatusCode(9999);

	private static List<StatusCode> fAllValues = Arrays.asList(new StatusCode[]
		{
			sc_Success,
			sc_RequestNotSupported,
			sc_RequestMissingRequired,
			sc_RequestInvalid,
			sc_InvalidMemberUserID,
			sc_InvalidAdminUserID,
			sc_UserIDInUse,
			sc_ShowNoAccess,
			sc_ShowLevelInsufficient,
			sc_ShowNotRented,
			sc_ShowRentExpired,
			sc_ShowPaymentDenied,
			sc_GeneralError
		});

	protected int fValue;

	private StatusCode(int value)
	{
		fValue = value;
	}

	public static StatusCode convertFromInt(Integer value)
	{
		if(value == null)
			return sc_Success;

		for(StatusCode statusCode : fAllValues)
			if(statusCode.fValue == value)
				return statusCode;

		return sc_GeneralError;
	}

	public static Integer convertToInt(StatusCode statusCode)
	{
		if(statusCode == null)
			return sc_Success.fValue;

		return statusCode.fValue;
	}
}
