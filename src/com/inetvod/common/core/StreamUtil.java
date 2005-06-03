package com.inetvod.common.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Bob
 * Date: Jul 11, 2004
 * Time: 11:23:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreamUtil
{
	public static void streamCopy (InputStream input, OutputStream output, boolean keepPosition) throws Exception
	{
		int size = 4096;
		byte[] bytes = new byte[4096];
		int numBytes;

		if (keepPosition)
			input.mark(Integer.MAX_VALUE);

		while((numBytes = input.read(bytes, 0, size)) > 0)
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
