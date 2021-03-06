/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.lang.reflect.Constructor;
import java.util.Date;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.LanguageID;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;

public class Show implements Readable, Writeable
{
	/* Constants */
	public static final Constructor<Show> CtorDataReader = DataReader.getCtor(Show.class);

	public static final int NumFields = 11;
	public static final int NameMaxLength = 64;
	public static final int EpisodeNameMaxLength = 64;
	public static final int EpisodeNumberMaxLength = 32;
	public static final int DescriptionMaxLength = Short.MAX_VALUE;
	public static final int PictureURLMaxLength = 4096;
	public static final int ShowAccessKeyMaxLength = Short.MAX_VALUE;

	/* Fields */
	private ShowID fShowID;
	private String fName;
	private String fEpisodeName;
	private String fEpisodeNumber;
	private Date fReleasedOn;
	private Short fReleasedYear;
	private String fDescription;
	private Short fRunningMins;
	private String fPictureURL;
	private CategoryIDList fCategoryIDList;
	private RatingID fRatingID;
	private LanguageID fLanguageID;
	private Boolean fIsAdult;
	private ShowRentalList fShowRentalList;
	private License fLicense;

	/* Getters and Setters */
	public ShowID getShowID() { return fShowID; }

	public String getName() { return fName; }
	public void setName(String name) { fName = name; }

	public String getEpisodeName() { return fEpisodeName; }
	public void setEpisodeName(String episodeName) { fEpisodeName = episodeName; }

	public String getEpisodeNumber() { return fEpisodeNumber; }
	public void setEpisodeNumber(String episodeNumber) { fEpisodeNumber = episodeNumber; }

	public Date getReleasedOn() { return fReleasedOn; }
	public void setReleasedOn(Date releasedOn) { fReleasedOn = releasedOn; }

	public Short getReleasedYear() { return fReleasedYear; }
	public void setReleasedYear(Short releasedYear) { fReleasedYear = releasedYear; }

	public String getDescription() { return fDescription; }
	public void setDescription(String description) { fDescription = description; }

	public Short getRunningMins() { return fRunningMins; }
	public void setRunningMins(Short runningMins) { fRunningMins = runningMins; }

	public String getPictureURL() { return fPictureURL; }
	public void setPictureURL(String pictureURL) { fPictureURL = pictureURL; }

	public CategoryIDList getCategoryIDList() { return fCategoryIDList; }

	public RatingID getRatingID() { return fRatingID; }
	public void setRatingID(RatingID ratingID) { fRatingID = ratingID; }

	public LanguageID getLanguageID() { return fLanguageID; }
	public void setLanguageID(LanguageID languageID) { fLanguageID = languageID; }

	public Boolean getIsAdult() { return fIsAdult; }
	public void setIsAdult(Boolean isAdult) { fIsAdult = isAdult; }

	public ShowRentalList getShowRentalList() { return fShowRentalList; }

	public License getLicense() { return fLicense; }
	public void setLicense(License license) { fLicense = license; }

	public boolean isFreeShowCost()
	{
		for(ShowRental showRental : fShowRentalList)
			for(ShowCost showCost : showRental.getShowCostList())
				if(ShowCostType.Free.equals(showCost.getShowCostType()))
					return true;

		return false;
	}

	/* Constuction Methods */
	private Show()
	{
	}

	public Show(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	public static Show newInstance(ShowID showID)
	{
		Show show = new Show();
		show.fShowID = showID;

		return show;
	}

	/* Implementation */

	public void readFrom(DataReader reader) throws Exception
	{
		fShowID = reader.readDataID("ShowID", ShowID.MaxLength, ShowID.CtorString);
		fName = reader.readString("Name", NameMaxLength);
		fEpisodeName = reader.readString("EpisodeName", EpisodeNameMaxLength);
		fEpisodeNumber = reader.readString("EpisodeNumber", EpisodeNumberMaxLength);
		fReleasedOn = reader.readDate("ReleasedOn");
		fReleasedYear = reader.readShort("ReleasedYear");
		fDescription = reader.readString("Description", DescriptionMaxLength);
		fRunningMins = reader.readShort("RunningMins");
		fPictureURL = reader.readString("PictureURL", PictureURLMaxLength);
		fCategoryIDList = reader.readStringList("CategoryID", CategoryID.MaxLength, CategoryIDList.Ctor, CategoryID.CtorString);
		fRatingID = reader.readDataID("RatingID", RatingID.MaxLength, RatingID.CtorString);
		fLanguageID = reader.readDataID("LanguageID", LanguageID.MaxLength, LanguageID.CtorString);
		fIsAdult = reader.readBoolean("IsAdult");
		fShowRentalList = reader.readList("ShowRental", ShowRentalList.Ctor, ShowRental.CtorDataReader);
		fLicense = reader.readObject("License", License.CtorDataReader);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeDataID("ShowID", fShowID, ShowID.MaxLength);
		writer.writeString("Name", fName, NameMaxLength);
		writer.writeString("EpisodeName", fEpisodeName, EpisodeNameMaxLength);
		writer.writeString("EpisodeNumber", fEpisodeNumber, EpisodeNumberMaxLength);
		writer.writeDate("ReleasedOn", fReleasedOn);
		writer.writeShort("ReleasedYear", fReleasedYear);
		writer.writeString("Description", fDescription, DescriptionMaxLength);
		writer.writeShort("RunningMins", fRunningMins);
		writer.writeString("PictureURL", fPictureURL, PictureURLMaxLength);
		writer.writeStringList("CategoryID", fCategoryIDList, CategoryID.MaxLength);
		writer.writeDataID("RatingID", fRatingID, RatingID.MaxLength);
		writer.writeDataID("LanguageID", fLanguageID, LanguageID.MaxLength);
		writer.writeBoolean("IsAdult", fIsAdult);
		writer.writeList("ShowRental", fShowRentalList);
		writer.writeObject("License", fLicense);
	}
}
