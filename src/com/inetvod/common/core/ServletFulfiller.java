/**
 * Copyright © 2004-2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.File;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ServletFulfiller
{
	protected HttpServletRequest fHttpServletRequest;
	protected HttpServletResponse fHttpServletResponse;

	protected ServletFulfiller(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		fHttpServletRequest = httpServletRequest;
		fHttpServletResponse = httpServletResponse;
	}

	public abstract DataFormat getRequestDataFormat();


	public void fulfill() throws Exception
	{
		Requestable request = null;
		Writeable response = null;
		Date startTime = new Date();
		boolean logged = false;

		try
		{
			request = readRequestable();
		}
		catch(Exception e)
		{
			response = createResponseFromException(request, e);

			logRequest(false, "Failed reading request", fHttpServletRequest.getInputStream(), startTime, e);
			logged = true;

			// if we have a valid response, don't propogate exception
			if(response == null)
				throw e;
		}

		try
		{
			if((response == null) && (request != null))
				response = request.fulfillRequest();
		}
		catch(Exception e)
		{
			response = createResponseFromException(request, e);

			logRequest(false, "Failed fulfilling request", request, response, startTime, e);
			logged = true;

			// if we have a valid response, don't propogate exception
			if(response == null)
				throw e;
		}

		try
		{
			writeWriteable(response);
		}
		catch(Exception e)
		{
			logRequest(false, "Failed writing response", request, response, startTime, e);
			throw e;
		}

		if(!logged)
			logRequest(request, response, startTime);
	}

	protected abstract Writeable createResponseFromException(Requestable requestable, Exception e);

	protected Requestable readRequestable() throws Exception
	{
		DataFormat dataFormat = getRequestDataFormat();

		if(dataFormat.equals(DataFormat.XML))
			return readRequestableFromXmlStream();
		else if(dataFormat.equals(DataFormat.Binary))
			return readRequestableFromBinaryStream();

		throw new Exception("Unknown DataFormat: " + dataFormat.toString());
	}

	protected Requestable readRequestableFromXmlStream() throws Exception
	{
		XmlDataReader reader;
		Requestable requestable;

		reader = new XmlDataReader(fHttpServletRequest.getInputStream());
		//reader = new XmlDataReader(fHttpServletRequest.getReader());
		requestable = readRequestableFromReader(reader);

		return requestable;
	}

	protected Requestable readRequestableFromBinaryStream() throws Exception
	{
		BinaryDataReader reader;
		Requestable requestable;

		reader = new BinaryDataReader(fHttpServletRequest.getInputStream(), ByteOrder.BIG_ENDIAN);
		requestable = readRequestableFromReader(reader);

		return requestable;
	}

	/// <summary>
	/// Read a Requestable object from its name
	/// </summary>
	/// <param name="className"></param>
	/// <returns></returns>
	protected abstract Requestable readRequestableFromReader(DataReader dataFiler) throws Exception;

	protected void writeWriteable(Writeable writeable) throws Exception
	{
		DataFormat dataFormat = getRequestDataFormat();

		if(dataFormat.equals(DataFormat.XML))
			writeWriteableToXmlPrintWriter(writeable);
		else if(dataFormat.equals(DataFormat.Binary))
			writeWriteableToBinaryStream(writeable);
		else
			throw new Exception("Unknown DataFormat: " + dataFormat.toString());
	}

	protected void writeWriteableToXmlStream(Writeable writeable) throws Exception
	{
		XmlDataWriter writer;

		writer = new XmlDataWriter(fHttpServletResponse.getOutputStream());
		writeWriteableToWriter(writeable, writer);
	}

	protected void writeWriteableToXmlPrintWriter(Writeable writeable) throws Exception
	{
		XmlDataWriter writer;

		fHttpServletResponse.setContentType("text/xml; charset=UTF-8");
		writer = new XmlDataWriter(fHttpServletResponse.getWriter(), fHttpServletResponse.getCharacterEncoding());
		writeWriteableToWriter(writeable, writer);
		//writer.flush();
	}

	protected void writeWriteableToBinaryStream(Writeable writeable) throws Exception
	{
		BinaryDataWriter writer;

		writer = new BinaryDataWriter(fHttpServletResponse.getOutputStream(), ByteOrder.BIG_ENDIAN);
		writeWriteableToWriter(writeable, writer);
	}

	protected void writeWriteableToWriter(Writeable writeable, DataWriter writer) throws Exception
	{
		String[] nameParts = writeable.getClass().getName().split("\\.");
		writer.writeObject(nameParts[nameParts.length - 1], writeable);
	}

	protected void logRequest(boolean success, String msg, InputStream requestStream, InputStream responseStream,
		String requestFileExt, String responseFileExt, Date startTime, Exception exception)
	{
		PrintWriter writer = null;

		try
		{
			StringBuffer sb = new StringBuffer();
			long milliSecs = (new Date()).getTime() - startTime.getTime();
			String fileDir = "c:\\temp\\iNetVOD\\requests\\";
			String baseFileName = String.format ("%s-%d_", (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSSS")).format(
				startTime, sb, new FieldPosition(DateFormat.YEAR_FIELD)).toString(), Thread.currentThread().getId());
			String fileName = (new File(fileDir, baseFileName)).getPath();

			if(requestStream != null)
				StreamUtil.streamToFile(requestStream, fileName + "Rqst" + requestFileExt);

			if(responseStream != null)
				StreamUtil.streamToFile(responseStream, fileName + "Resp" + responseFileExt);

			writer = new PrintWriter(new FileOutputStream(fileName + (success ? "Success" : "Failed") + ".txt"));
			writer.println((new MessageFormat("Millis to build: {0}")).format(new Object[] { milliSecs }));
			if((msg != null) && (msg.length() > 0))
				writer.println((new MessageFormat("Message: {0}")).format(new Object[] { msg }));
			if(exception != null)
			{
				writer.print("Exception: ");
				writer.println(exception.toString());
				writer.println("StackTrace: ");
				exception.printStackTrace(writer);
			}
			writer.close();
		}
		catch(Exception e)
		{
			if(writer != null)
				try { writer.close(); } catch(Exception e2) {}
			//TODO: error during logging, don't raise, but maybe log to event viewer
		}
	}

	protected void logRequest(boolean success, String msg, InputStream requestStream,
		Date startTime, Exception exception)
	{
		logRequest(success, msg, requestStream, null, ".raw", null, startTime, exception);
	}

	protected void logRequest(boolean success, String msg, Requestable request,
		Date startTime, Exception exception) throws Exception
	{
		XmlDataWriter requestWriter = null;
		try
		{
			ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
			requestWriter = new XmlDataWriter(requestStream);
			writeWriteableToWriter(request, requestWriter);
			requestWriter.close();

			logRequest(success, msg, new ByteArrayInputStream(requestStream.toByteArray()),
				null, ".xml", null, startTime, exception);
		}
		catch(Exception e)
		{
		}
		finally
		{
			if(requestWriter != null)
				requestWriter.close();
		}
	}

	protected void logRequest(boolean success, String msg, Requestable request, Writeable response,
		Date startTime, Exception exception) throws Exception
	{
		XmlDataWriter requestWriter = null;
		XmlDataWriter responseWriter = null;
		try
		{
			ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
			requestWriter = new XmlDataWriter(requestStream);
			writeWriteableToWriter(request, requestWriter);
			requestWriter.flush();

			ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
			responseWriter = new XmlDataWriter(responseStream);
			writeWriteableToWriter(response, responseWriter);
			responseWriter.flush();

			ByteArrayInputStream requestInStream = new ByteArrayInputStream(requestStream.toByteArray());
			ByteArrayInputStream responseInStream = new ByteArrayInputStream(responseStream.toByteArray());

			logRequest(success, msg, requestInStream, responseInStream, ".xml", ".xml", startTime, exception);
		}
		catch(Exception e)
		{
		}
		finally
		{
			if(requestWriter != null)
				requestWriter.close();
			if(responseWriter != null)
				responseWriter.close();
		}
	}

	protected void logRequest(Requestable request, Writeable response, Date startTime) throws Exception
	{
		logRequest(true, null, request, response, startTime, null);
	}
}
