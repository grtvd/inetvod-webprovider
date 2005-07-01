/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.LanguageID;
import com.inetvod.common.core.Writeable;
import com.inetvod.common.dbdata.CategoryIDList;
import com.inetvod.common.dbdata.RatingID;
import com.inetvod.common.dbdata.Show;
import com.inetvod.common.dbdata.ShowCostList;
import com.inetvod.common.dbdata.ShowFormatList;
import com.inetvod.common.dbdata.ShowID;
import com.inetvod.common.dbdata.CategoryID;

import java.util.Date;

public class ShowDetail implements Writeable
{
	/* Fields */
	protected ShowID fShowID;
	protected String fName;
	protected String fEpisodeName;
	protected String fEpisodeNumber;
	protected Date fReleasedOn;
	protected Short fReleasedYear;
	protected String fDescription;
	protected Short fRunningMins;
	protected String fPictureURL;

	protected CategoryIDList fCategoryIDList;
	protected RatingID fRatingID;
	protected LanguageID fLanguageID;
	protected Boolean fIsAdult;

	protected ShowFormatList fShowFormatList;
	protected ShowCostList fShowCostList;

	/* Constuction Methods */
	public ShowDetail(Show show, CategoryIDList categoryIDList, ShowFormatList showFormatList,
		ShowCostList showCostList)
	{
		fShowID = show.getShowID();
		fName = show.getName();
		fEpisodeName = show.getEpisodeName();
		fEpisodeNumber = show.getEpisodeNumber();
		fReleasedOn = show.getReleasedOn();
		fReleasedYear = show.getReleasedYear();
		fDescription = show.getDescription();
		fRunningMins = show.getRunningMins();
		fPictureURL = show.getPictureURL();

		fCategoryIDList = categoryIDList;
		fRatingID = show.getRatingID();
		fLanguageID = show.getLanguageID();
		fIsAdult = show.getIsAdult();

		fShowFormatList = showFormatList;
		fShowCostList = showCostList;
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
		writer.writeString("Name", fName, Show.NameMaxLength);
		writer.writeString("EpisodeName", fEpisodeName, Show.EpisodeNameMaxLength);
		writer.writeString("EpisodeNumber", fEpisodeNumber, Show.EpisodeNumberMaxLength);

		writer.writeDate("ReleasedOn", fReleasedOn);
		writer.writeShort("ReleasedYear", fReleasedYear);
		writer.writeString("Description", fDescription, Show.DescriptionMaxLength);

		writer.writeShort("RunningMins", fRunningMins);
		writer.writeString("PictureURL", fPictureURL, Show.PictureURLMaxLength);

		writer.writeStringList("CategoryID", fCategoryIDList, CategoryID.MaxLength);
		writer.writeDataID("RatingID", fRatingID, RatingID.MaxLength);
		writer.writeDataID("LanguageID", fLanguageID, LanguageID.MaxLength);
		writer.writeBoolean("IsAdult", fIsAdult);

		writer.writeList("ShowFormat", fShowFormatList);
		writer.writeList("ShowCost", fShowCostList);
	}
}