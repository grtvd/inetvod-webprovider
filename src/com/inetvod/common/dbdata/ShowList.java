/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import com.inetvod.common.core.CtorUtil;

public class ShowList extends ArrayList<Show>
{
	public static final Constructor<ShowList> Ctor = CtorUtil.getCtorDefault(ShowList.class);

	public int indexOfByID(ShowID showID)
	{
		for(int i = 0; i < size(); i++)
			if(get(i).getShowID().equals(showID))
				return i;

		return -1;
	}

	public Show findByID(ShowID showID)
	{
		int pos = indexOfByID(showID);
		if(pos >= 0)
			return get(pos);

		return null;
	}

	public Show getByID(ShowID showID) throws Exception
	{
		int pos = indexOfByID(showID);
		if(pos >= 0)
			return get(pos);

		throw new Exception("ShowID(" + (showID == null ? "null" : showID.toString()) + ") not found");
	}

	public ShowIDList getShowIDList()
	{
		ShowIDList showIDList = new ShowIDList();

		for(Show show : this)
			showIDList.add(show.getShowID());

		return showIDList;
	}
}
