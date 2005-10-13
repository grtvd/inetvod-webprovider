/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.util.Date;

import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.LanguageID;
import com.inetvod.common.core.Writeable;

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

	protected ShowRentalList fShowRentalList;

	/* Constuction */
	public ShowDetail(Show show)
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

		fCategoryIDList = show.getCategoryIDList();
		fRatingID = show.getRatingID();
		fLanguageID = show.getLanguageID();
		fIsAdult = show.getIsAdult();

		fShowRentalList = show.getShowRentalList();
	}

	/* Implementation */
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

		writer.writeList("ShowRental", fShowRentalList);
	}
}
