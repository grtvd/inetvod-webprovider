/**
 * Copyright © 2005 iNetVOD, Inc. All Rights Reserved.
 * Confidential and Proprietary
 */
package com.inetvod.provider.rqdata;

import java.lang.reflect.Constructor;

import com.inetvod.common.core.DataReader;
import com.inetvod.common.core.DataWriter;
import com.inetvod.common.core.Readable;
import com.inetvod.common.core.Writeable;

public class Authenticate implements Readable, Writeable
{
	/* Constants */
	public static final Constructor<Authenticate> CtorDataReader = DataReader.getCtor(Authenticate.class);

	public static final int AdminUserIDMaxLength = 64;
	public static final int AdminPasswordMaxLength = 16;
	public static final int MemberUserIDMaxLength = 64;
	public static final int MemberPasswordMaxLength = 16;

	/* Fields */
	protected String fAdminUserID;
	protected String fAdminPassword;
	protected String fMemberUserID;
	protected String fMemberPassword;

	/* Construction */
	public Authenticate(DataReader reader) throws Exception
	{
		readFrom(reader);
	}

	/* Getters/Setters */
	public String getAdminUserID() { return fAdminUserID; }
	public void setAdminUserID(String adminUserID) { fAdminUserID = adminUserID; }

	public String getAdminPassword() { return fAdminPassword; }
	public void setAdminPassword(String adminPassword) { fAdminPassword = adminPassword; }

	public String getMemberUserID() { return fMemberUserID; }
	public void setMemberUserID(String memberUserID) { fMemberUserID = memberUserID; }

	public String getMemberPassword() { return fMemberPassword; }
	public void setMemberPassword(String memberPassword) { fMemberPassword = memberPassword; }

	/* Implementation */
	public void readFrom(DataReader reader) throws Exception
	{
		fAdminUserID = reader.readString("AdminUserID", AdminUserIDMaxLength);
		fAdminPassword = reader.readString("AdminPassword", AdminPasswordMaxLength);
		fMemberUserID = reader.readString("MemberUserID", MemberUserIDMaxLength);
		fMemberPassword = reader.readString("MemberPassword", MemberPasswordMaxLength);
	}

	public void writeTo(DataWriter writer) throws Exception
	{
		writer.writeString("AdminUserID", fAdminUserID, AdminUserIDMaxLength);
		writer.writeString("AdminPassword", fAdminPassword, AdminPasswordMaxLength);
		writer.writeString("MemberUserID", fMemberUserID, MemberUserIDMaxLength);
		writer.writeString("MemberPassword", fMemberPassword, MemberPasswordMaxLength);
	}
}
