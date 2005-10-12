/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.common.dbdata;

import java.util.ArrayList;
import java.lang.reflect.Constructor;

import com.inetvod.common.core.LanguageID;
import com.inetvod.common.core.CtorUtil;

public class ShowList extends ArrayList<Show>
{
	public static final Constructor<ShowList> Ctor = CtorUtil.getCtorDefault(ShowList.class);

	/**
	 * TODO: remove
	 * Helper function with static data instead of hitting a DB
	 * @return ShowList
	 */
	public static ShowList getAll()
	{
		if(fShowListCache != null)
			return fShowListCache;

		ShowList showList = new ShowList();
		Show show;

		show = Show.newInstance(new ShowID("1"));
		show.setEpisodeName("The Matrix");
		show.setLanguageID(LanguageID.English);
		show.setIsAdult(false);
		showList.add(show);

		show = Show.newInstance(new ShowID("2"));
		show.setEpisodeName("The Matrix Reloaded");
		show.setLanguageID(LanguageID.English);
		show.setIsAdult(false);
		showList.add(show);

		show = Show.newInstance(new ShowID("3"));
		show.setEpisodeName("The Matrix Revolutions");
		show.setLanguageID(LanguageID.English);
		show.setIsAdult(false);
		showList.add(show);

		show = Show.newInstance(new ShowID("4"));
		show.setEpisodeName("The Secret Agent");
		show.setLanguageID(LanguageID.English);
		show.setIsAdult(false);
		showList.add(show);

		show = Show.newInstance(new ShowID("5"));
		show.setEpisodeName("The Second Woman");
		show.setLanguageID(LanguageID.English);
		show.setIsAdult(true);
		showList.add(show);

		show = Show.newInstance(new ShowID("6"));
		show.setEpisodeName("A Farewell To Arms");
		show.setLanguageID(LanguageID.English);
		show.setIsAdult(false);
		showList.add(show);

		fShowListCache = showList;
		return fShowListCache;
	}
	static private ShowList fShowListCache;

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
}
