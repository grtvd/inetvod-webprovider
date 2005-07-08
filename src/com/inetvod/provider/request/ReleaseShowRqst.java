/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.Show;
import com.inetvod.common.dbdata.ShowID;
import com.inetvod.common.dbdata.ShowList;

public class ReleaseShowRqst extends AuthenRequestable
{
	/* Properties */
	protected ShowID fShowID;

	/* Construction */
	public ReleaseShowRqst(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	/* Implementation */
	public Writeable fulfillRequest()
	{
		// validate autentication
		if(!confirmAuthentication())
			return null;

		// TODO: Confirm valid Show
		// Fetch and validate the show
		Show show = validateShow();
		if (show == null)
			return null;

		//TODO: Confirm member has rented the Show, if not, fStatusCode = StatusCode.sc_ShowNotRented; return;

		//TODO: remove the Show from the Member's rental queue

		fStatusCode = StatusCode.sc_Success;
		return new ReleaseShowResp();
	}

	protected Show validateShow()
	{
		if(fShowID == null)
		{
			fStatusCode = StatusCode.sc_RequestMissingRequired;
			return null;
		}

		//TODO: Actually fetch Show from DB
		Show show = ShowList.getAll().findByID(fShowID);
		if(show == null)
		{
			fStatusCode = StatusCode.sc_RequestInvalid;
			return null;
		}

		return show;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = (ShowID)reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
	}
}
