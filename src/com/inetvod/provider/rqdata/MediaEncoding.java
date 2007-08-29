/**
 * Copyright © 2005-2007 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.util.HashMap;

public enum MediaEncoding
{
	WMV1("WMV1"),
	WMV2("WMV2"),
	WMV3("WMV3"),
	RV30("RV30"),
	RV40("RV40"),
	SVQ3("SVQ3"),
	AVC1("avc1"),
	MP4V("mp4v"),
	DIVX("divx"),
	MPGA("mpga"),
	MP4A("mp4a"),
	WMA2("WMA2");

	/* Constants */
	public static final int MaxLength = 32;

	/* Fields */
	private static HashMap<String, MediaEncoding> fAllValues = new HashMap<String, MediaEncoding>();

	private final String fValue;

	static
	{
		for(MediaEncoding value : values())
			fAllValues.put(value.toString(), value);
	}

	/* Getters and Setters */
	public String toString() { return fValue; }

	/* Construction */
	private MediaEncoding(String name)
	{
		fValue = name;
	}

	public static MediaEncoding convertFromString(String value)
	{
		if((value == null) || (value.length() == 0))
			return null;

		MediaEncoding mediaEncoding = fAllValues.get(value);
		if(mediaEncoding != null)
			return mediaEncoding;

		throw new IllegalArgumentException("bad value(" + value + ")");
	}

	public static String convertToString(MediaEncoding value)
	{
		if(value == null)
			return null;
		return value.toString();
	}
}
