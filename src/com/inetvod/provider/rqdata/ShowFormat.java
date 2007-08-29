/**
 * Copyright © 2005-2007 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.core.CompUtil;

public class ShowFormat implements Readable, Writeable
{
	/* Constants */
	public static Constructor<ShowFormat> CtorDataReader = DataReader.getCtor(ShowFormat.class);

	/* Fields */
	private ShowFormatID fShowFormatID;		//optional
	private MediaEncoding fMediaEncoding;
	private MediaContainer fMediaContainer;
	private Short fHorzResolution;
	private Short fVertResolution;
	private Short fFramesPerSecond;
	private Short fBitRate;

	/* Getters and Setters */
	public ShowFormatID getShowFormatID() { return fShowFormatID; }
	public void setShowFormatID(ShowFormatID showFormatID) { fShowFormatID = showFormatID; }

	public MediaEncoding getMediaEncoding() { return fMediaEncoding; }
	public void setMediaEncoding(MediaEncoding mediaEncoding) { fMediaEncoding = mediaEncoding; }

	public MediaContainer getMediaContainer() { return fMediaContainer; }
	public void setMediaContainer(MediaContainer mediaContainer) { fMediaContainer = mediaContainer; }

	public Short getHorzResolution() { return fHorzResolution; }
	public void setHorzResolution(Short horzResolution) { fHorzResolution = horzResolution; }

	public Short getVertResolution() { return fVertResolution; }
	public void setVertResolution(Short vertResolution) { fVertResolution = vertResolution; }

	public Short getFramesPerSecond() { return fFramesPerSecond; }
	public void setFramesPerSecond(Short framesPerSecond) { fFramesPerSecond = framesPerSecond; }

	public Short getBitRate() { return fBitRate; }
	public void setBitRate(Short bitRate) { fBitRate = bitRate; }

	/* Constuction */
	public ShowFormat()
	{
	}

	public ShowFormat(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	@SuppressWarnings({"NonFinalFieldReferenceInEquals"})
	@Override public boolean equals(Object obj)
	{
		if(!(obj instanceof ShowFormat))
			return false;
		ShowFormat showFormat = (ShowFormat)obj;

		return CompUtil.areEqual(fShowFormatID, showFormat.fShowFormatID)
			&& CompUtil.areEqual(fMediaEncoding, showFormat.fMediaEncoding)
			&& CompUtil.areEqual(fMediaContainer, showFormat.fMediaContainer)
			&& CompUtil.areEqual(fHorzResolution, showFormat.fHorzResolution)
			&& CompUtil.areEqual(fVertResolution, showFormat.fVertResolution)
			&& CompUtil.areEqual(fFramesPerSecond, showFormat.fFramesPerSecond)
			&& CompUtil.areEqual(fBitRate, showFormat.fBitRate);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowFormatID = reader.readDataID("ShowFormatID", ShowFormatID.MaxLength, ShowFormatID.CtorString);
		fMediaEncoding = MediaEncoding.convertFromString(reader.readString("MediaEncoding", MediaEncoding.MaxLength));
		fMediaContainer = MediaContainer.convertFromString(reader.readString("MediaContainer", MediaContainer.MaxLength));
		fHorzResolution = reader.readShort("HorzResolution");
		fVertResolution = reader.readShort("VertResolution");
		fFramesPerSecond = reader.readShort("FramesPerSecond");
		fBitRate = reader.readShort("BitRate");
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowFormatID", fShowFormatID, ShowFormatID.MaxLength);
		writer.writeString("MediaEncoding", MediaEncoding.convertToString(fMediaEncoding), MediaEncoding.MaxLength);
		writer.writeString("MediaContainer", MediaContainer.convertToString(fMediaContainer), MediaContainer.MaxLength);
		writer.writeShort("HorzResolution", fHorzResolution);
		writer.writeShort("VertResolution", fVertResolution);
		writer.writeShort("FramesPerSecond", fFramesPerSecond);
		writer.writeShort("BitRate", fBitRate);
	}
}