/**
 * Copyright � 2004-2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.core;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil
{
	private static final int BufferSize = 4096;

	public static void streamCopy (InputStream input, OutputStream output, boolean keepPosition) throws Exception
	{
		byte[] bytes = new byte[BufferSize];
		int numBytes;

		if (keepPosition)
			input.mark(Integer.MAX_VALUE);

		while((numBytes = input.read(bytes, 0, BufferSize)) > 0)
			output.write(bytes, 0, numBytes);

		if (keepPosition)
			input.reset();
	}

	public static void streamToFile (InputStream stream, String file) throws Exception
	{
		FileOutputStream fileStream = new FileOutputStream(file);
		try
		{
			streamCopy (stream, fileStream, stream.markSupported());
		}
		finally
		{
			fileStream.close();
		}
	}
}
