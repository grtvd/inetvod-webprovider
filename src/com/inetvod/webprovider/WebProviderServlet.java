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

import org.apache.log4j.xml.DOMConfigurator;

import com.inetvod.common.core.Logger;
import com.inetvod.common.core.ServletFulfiller;
import com.inetvod.provider.request.WebProviderServletFulfiller;
import com.inetvod.provider.rqdata.DataManager;

public class WebProviderServlet extends HttpServlet
{
	public void init() throws ServletException
	{
		File file;

		String configDir = getServletContext().getInitParameter("configdir");
		//TODO: can revert to: file = new File(getServletContext().getRealPath("/log4j.xml"));
		file = determineDataFile(configDir, "log4j", ".xml");
		try
		{
			DOMConfigurator.configure(file.toURL());
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}

		//TODO: can remove, the DataManager is a helper object to simulate database access
		file = determineDataFile(configDir, "data", ".xml");
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
	private File determineDataFile(String configDir, String defaultDataFilePrefix, String defaultDataFileExt)
	{
		String contextBegin = "provider_";
		String dataFile = "/" + defaultDataFilePrefix + defaultDataFileExt;

		String contextRoot = determineContextRoot(dataFile);

		if(contextRoot != null)
		{
			if(contextRoot.startsWith(contextBegin))
			{
				String fileName = String.format(defaultDataFilePrefix + "_%s" + defaultDataFileExt,
					contextRoot.substring(contextBegin.length()));
				File file = new File(configDir, fileName);
				if(file.exists())
					return file;

				dataFile = "/" + fileName;
			}
		}

		return new File(getServletContext().getRealPath(dataFile));
	}

	//TODO: can remove, only needed to load the DataManager object
	private String determineContextRoot(String checkFile)
	{
		try
		{
			URI realURL = getServletContext().getResource(checkFile).toURI();

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
