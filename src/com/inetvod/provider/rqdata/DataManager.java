/**
 * Copyright � 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.XmlDataReader;

/**
  * TODO: remove
  * Helper class thats loads static data from XML instead of hitting a DB
 */
public class DataManager implements Readable
{
	/* Constants */
	public static final Constructor<DataManager> CtorDataReader = DataReader.getCtor(DataManager.class);

	/* Fields */
	private static DataManager fTheDataManager;

	private String fShowURL;
	private ShowList fShowList;

	/* Getters & Setters */
	public String getShowURL() { return fShowURL; }
	public ShowList getShowList() { return fShowList; }

	/* Constuction */
	public DataManager(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Implementation */
	public static DataManager getThe() { return fTheDataManager; }

	public static void load(File dataManagerXml) throws Exception
	{
		FileInputStream inputStream = new FileInputStream(dataManagerXml);
		XmlDataReader dataReader = new XmlDataReader(inputStream);

		fTheDataManager = dataReader.readObject("DataManager", CtorDataReader);
	}

	public void readFrom(DataReader reader) throws Exception
	{
		fShowURL = reader.readString("ShowURL", Integer.MAX_VALUE);
		fShowList = reader.readList("Show", ShowList.Ctor, Show.CtorDataReader);
	}
}
