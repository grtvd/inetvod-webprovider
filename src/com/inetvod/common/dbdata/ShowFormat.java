/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;

import java.lang.reflect.Constructor;

public class ShowFormat implements Readable, Writeable
{
	/* Constants */
	public static Constructor CtorDataReader = DataReader.getCtor(ShowFormat.class);

	/* Properties */
	protected MediaEncoding fMediaEncoding;
	protected MediaContainer fMediaContainer;
	protected Short fHorzResolution;
	protected Short fVertResolution;
	protected Short fFramesPerSecond;

	/* Getters and Setters */
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

	/* Constuction Methods */
	public ShowFormat()
	{
	}

	public ShowFormat(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fMediaEncoding = MediaEncoding.convertFromString(reader.readString("MediaEncoding", MediaEncoding.MaxLength));
		fMediaContainer = MediaContainer.convertFromString(reader.readString("MediaContainer", MediaContainer.MaxLength));
		fHorzResolution = reader.readShort("HorzResolution");
		fVertResolution = reader.readShort("VertResolution");
		fFramesPerSecond = reader.readShort("FramesPerSecond");
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("MediaEncoding", MediaEncoding.convertToString(fMediaEncoding), MediaEncoding.MaxLength);
		writer.writeString("MediaContainer", MediaContainer.convertToString(fMediaContainer), MediaContainer.MaxLength);
		writer.writeShort("HorzResolution", fHorzResolution);
		writer.writeShort("VertResolution", fVertResolution);
		writer.writeShort("FramesPerSecond", fFramesPerSecond);
	}
}