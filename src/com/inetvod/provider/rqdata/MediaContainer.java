/**
 * Copyright © 2005-2007 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.util.HashMap;

public enum MediaContainer
{
	ASF("ASF"),
	RM("RM"),
	RTSP("RTSP"),
	AVI("AVI"),
	MOV("MOV"),
	MP3("MP3");

	/* Constants */
	public static final int MaxLength = 32;

	/* Fields */
	private static HashMap<String, MediaContainer> fAllValues = new HashMap<String, MediaContainer>();

	private final String fValue;

	/* Getters and Setters */
	public String toString() { return fValue; }

	/* Construction */
	static
	{
		for(MediaContainer value : values())
			fAllValues.put(value.toString(), value);
	}

	private MediaContainer(String name)
	{
		fValue = name;
	}

	public static MediaContainer convertFromString(String value)
	{
		if((value == null) || (value.length() == 0))
			return null;

		MediaContainer mediaContainer = fAllValues.get(value);
		if(mediaContainer != null)
			return mediaContainer;

		throw new IllegalArgumentException("bad value(" + value + ")");
	}

	public static String convertToString(MediaContainer value)
	{
		if(value == null)
			return null;
		return value.toString();
	}
}
