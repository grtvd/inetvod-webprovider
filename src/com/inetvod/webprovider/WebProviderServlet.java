/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.webprovider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inetvod.common.core.ServletFulfiller;
import com.inetvod.common.core.Logger;
import com.inetvod.provider.request.WebProviderServletFulfiller;
import com.inetvod.provider.rqdata.DataManager;
import org.apache.log4j.xml.DOMConfigurator;

public class WebProviderServlet extends HttpServlet
{
	public void init() throws ServletException
	{
		String realPath;
		File file;

		realPath = getServletContext().getRealPath("/log4j.xml");
		file = new File(realPath);
		try
		{
			DOMConfigurator.configure(file.toURL());
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}

		//TODO: can remove, the DataManager is a helper object to simulate database access
		realPath = getServletContext().getRealPath(determineDataFile());
		file = new File(realPath);
		try
		{
			DataManager.load(file);
		}
		catch(Exception e)
		{
			Logger.logErr(this, "init", e);
		}
	}

	//TODO: can remove, only needed to load the DataManager object
	private String determineDataFile()
	{
		final String contextBegin = "provider_";
		final String defaultDataFile = "/data.xml";

		String contextRoot = determineContextRoot();

		if(contextRoot != null)
		{
			if(contextRoot.startsWith(contextBegin))
			{
				String dataFile = String.format("/data_%s.xml", contextRoot.substring(contextBegin.length()));
				return dataFile;
			}
		}

		return defaultDataFile;
	}

	//TODO: can remove, only needed to load the DataManager object
	private String determineContextRoot()
	{
		try
		{
			URI realURL = getServletContext().getResource("/data.xml").toURI();

			String realPath = realURL.getPath();
			if((realPath == null) || (realPath.length() == 0))
				return null;

			String[] parts = realPath.split("\\/");
			if((parts == null) || (parts.length < 3))
				return null;

			return parts[2];
		}
		catch(Exception e)
		{
			Logger.logErr(this, "determineContextRoot", e);
			return null;
		}
	}

	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws ServletException, IOException
	{
	}

	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws ServletException, IOException
	{
		try
		{
			ServletFulfiller fulfiller = new WebProviderServletFulfiller(httpServletRequest, httpServletResponse);
			fulfiller.fulfill();
		}
		catch(Exception e)
		{
			//throw new ServletException(e);
		}
	}
}
