/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.request;

import java.util.Iterator;

import com.inetvod.common.core.CurrencyID;
import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Money;
import com.inetvod.common.core.StatusCode;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.CategoryID;
import com.inetvod.common.dbdata.CategoryIDList;
import com.inetvod.common.dbdata.MediaContainer;
import com.inetvod.common.dbdata.MediaEncoding;
import com.inetvod.common.dbdata.Show;
import com.inetvod.common.dbdata.ShowCost;
import com.inetvod.common.dbdata.ShowCostList;
import com.inetvod.common.dbdata.ShowCostType;
import com.inetvod.common.dbdata.ShowFormat;
import com.inetvod.common.dbdata.ShowFormatList;
import com.inetvod.common.dbdata.ShowID;
import com.inetvod.common.dbdata.ShowIDList;
import com.inetvod.common.dbdata.ShowList;
import com.inetvod.provider.rqdata.ShowDetail;
import com.inetvod.provider.rqdata.ShowDetailList;

public class ShowDetailRqst extends AuthenRequestable
{
	/* Fields */
	protected ShowIDList fShowIDList = new ShowIDList();

	/* Construction */
	public ShowDetailRqst(DataReader filer) throws Exception
	{
		readFrom(filer);
	}

	/* Implementation */
	protected boolean isMemberRequest() { return false; }

	public Writeable fulfillRequest() throws Exception
	{
		// validate autentication
		if(!confirmAuthentication())
			return null;

		ShowDetailResp response = new ShowDetailResp();
		ShowDetailList showDetailList = response.ShowDetailList;

		CategoryIDList categoryIDList = new CategoryIDList();
		ShowFormatList showFormatList = new ShowFormatList();
		ShowFormat showFormat;
		ShowCostList showCostList = new ShowCostList();
		ShowCost showCost;

		categoryIDList.add(new CategoryID("drama"));

		showFormat = new ShowFormat();
		showFormat.setMediaContainer(MediaContainer.ASF);
		showFormat.setMediaEncoding(MediaEncoding.WMV9);
		showFormat.setHorzResolution((short)600);
		showFormat.setVertResolution((short)480);
		showFormat.setFramesPerSecond((short)30);
		showFormat.setBitRate((short)750);
		showFormatList.add(showFormat);

		showCost = new ShowCost();
		showCost.setShowCostType(ShowCostType.PayPerView);
		showCost.setCost(new Money(CurrencyID.USD, 3.95));
		showCost.setCostDisplay("$3.95");
		showCost.setRentalHours((short)48);
		showCostList.add(showCost);

		showCost = new ShowCost();
		showCost.setShowCostType(ShowCostType.PayPerView);
		showCost.setCost(new Money(CurrencyID.USD, 5.95));
		showCost.setCostDisplay("$5.95");
		showCost.setRentalHours((short)168);
		showCostList.add(showCost);

		ShowList showList = ShowList.getAll();
		Iterator<ShowID> iter = fShowIDList.iterator();

		Show show;
		while(iter.hasNext())
		{
			show = showList.findByID(iter.next());
			if(show == null)
			{
				fStatusCode = StatusCode.sc_RequestInvalid;
				return response;
			}

			showDetailList.add(new ShowDetail(show, categoryIDList, showFormatList, showCostList));
		}

		fStatusCode = StatusCode.sc_Success;
		return response;
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowIDList = reader.readStringList("ShowID", ShowID.MaxLength, ShowIDList.Ctor, ShowID.CtorString);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeStringList("ShowID", fShowIDList, ShowID.MaxLength);
	}
}
