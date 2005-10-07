/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.webprovider;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inetvod.common.core.ServletFulfiller;
import com.inetvod.provider.request.WebProviderServletFulfiller;
import org.apache.log4j.xml.DOMConfigurator;

public class WebProviderServlet extends HttpServlet
{
	public void init() throws ServletException
	{
		String realPath = getServletContext().getRealPath("/log4j.xml");
		File log4jFile = new File(realPath);
		try
		{
			DOMConfigurator.configure(log4jFile.toURL());
		}
		catch(MalformedURLException e)
		{
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
